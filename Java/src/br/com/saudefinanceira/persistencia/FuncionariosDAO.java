package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Conta_Empresa;
import br.com.saudefinanceira.model.Funcionarios;

public class FuncionariosDAO {
	
private ConexaoMysql conexao;
	
	public FuncionariosDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}

	// SALVAR
	public Funcionarios salvar(Funcionarios funcionarios) {
		this.conexao.abrirConexao();
		Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
		String sqlInsert = "INSERT INTO funcionarios VALUES(null,?,?,?,?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, funcionarios.getSalario());
			statement.setString(2, funcionarios.getNome());
			statement.setString(3, funcionarios.getFuncao());
			statement.setLong(4, funcionarios.getId_conta_empresa().getId());	
			funcionarios.getId_conta_empresa().setSaldo(funcionarios.getId_conta_empresa().getSaldo() - funcionarios.getSalario());
			ceDAO.editar(funcionarios.getId_conta_empresa());
			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				funcionarios.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return funcionarios;
	}
	// EDITAR
			public boolean editar(Funcionarios funcionarios) {
				this.conexao.abrirConexao();
				String sqlUpdate = "UPDATE funcionarios SET salario=?, nome=?, funcao=?, id_conta_empresa=? WHERE id_funcionario=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
					statement.setDouble(1, funcionarios.getSalario());
					statement.setString(2, funcionarios.getNome());
					statement.setString(3, funcionarios.getFuncao());
					statement.setLong(4, funcionarios.getId_conta_empresa().getId());
					int linhasAfetadas = statement.executeUpdate();
					if (linhasAfetadas > 0) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return false;
			}
			// EXCLUIR
			public boolean excluir(long id) {
				this.conexao.abrirConexao();
				FuncionariosDAO fDAO = new FuncionariosDAO();
				Funcionarios funcionarios = fDAO.buscarPorId(id);
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				String sqlExcluir = "DELETE FROM funcionarios WHERE id_funcionario=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlExcluir);
					statement.setLong(1, id);
					funcionarios.getId_conta_empresa().setSaldo(funcionarios.getId_conta_empresa().getSaldo() + funcionarios.getSalario());
					ceDAO.editar(funcionarios.getId_conta_empresa());
					
					int linhasAfetadas = statement.executeUpdate();
					if (linhasAfetadas > 0) {
						return true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return false;
			}
			// BuscarPorId
			public Funcionarios buscarPorId(long id) {
				this.conexao.abrirConexao();
				String sqlBuscarPorId = "SELECT * FROM funcionarios WHERE id_funcionario=?";
				Funcionarios funcionarios = null;
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarPorId);
					statement.setLong(1, id);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					if (rs.next()) {
						funcionarios = new Funcionarios();
						funcionarios.setId(rs.getLong("id_funcionario"));
						funcionarios.setSalario(rs.getDouble("salario"));
						funcionarios.setNome(rs.getString("nome"));
						funcionarios.setFuncao(rs.getString("funcao"));
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						funcionarios.setId_conta_empresa(conta_empresa);

						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return funcionarios;
			}

			// BUSCAR TODOS
			public List<Funcionarios> buscarTodos() {
				this.conexao.abrirConexao();
				String sqlBuscarTodos = "SELECT * FROM funcionarios";
				List<Funcionarios> listaFuncionarios = new ArrayList<Funcionarios>();
				Funcionarios funcionarios = null;
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarTodos);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					while (rs.next()) {
						funcionarios = new Funcionarios();
						funcionarios.setId(rs.getLong("id_funcionario"));
						funcionarios.setSalario(rs.getDouble("salario"));
						funcionarios.setNome(rs.getString("nome"));
						funcionarios.setFuncao(rs.getString("funcao"));
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						funcionarios.setId_conta_empresa(conta_empresa);
						
						listaFuncionarios.add(funcionarios);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return listaFuncionarios;
			}
				// BUSCAR TODOS POR ID DA CONTA
				public List<Funcionarios> buscarTodosPorId(Long id) {
					this.conexao.abrirConexao();
					String sqlBuscarPorIdUsu = "SELECT * FROM funcionarios WHERE id_conta_empresa=?";
					List<Funcionarios> listaFuncionarios = new ArrayList<Funcionarios>();
					Funcionarios funcionarios = null;
					Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
					try {
						PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
								.prepareStatement(sqlBuscarPorIdUsu);
						statement.setLong(1, id);
						ResultSet rs = statement.executeQuery();
						// CONVERTER O RESULTSET EM UM OBJETO USUARIO
						while (rs.next()) {
							funcionarios = new Funcionarios();
							funcionarios.setId(rs.getLong("id_funcionario"));
							funcionarios.setSalario(rs.getDouble("salario"));
							funcionarios.setNome(rs.getString("nome"));
							funcionarios.setFuncao(rs.getString("funcao"));
							Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
							funcionarios.setId_conta_empresa(conta_empresa);
							
							listaFuncionarios.add(funcionarios);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.conexao.fecharConexao();
					}
					return listaFuncionarios;
				}

	}