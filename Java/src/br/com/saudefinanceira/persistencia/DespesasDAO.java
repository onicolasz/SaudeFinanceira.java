package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Conta_Empresa;
import br.com.saudefinanceira.model.Conta_Pessoal;
import br.com.saudefinanceira.model.Despesas;

public class DespesasDAO {

	private ConexaoMysql conexao;
	
	public DespesasDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}

	// SALVAR
	public Despesas salvar(Despesas despesas) {
		this.conexao.abrirConexao();
		Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
		Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
		String sqlInsert = "INSERT INTO despesas VALUES(null,?,?,?,?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, despesas.getValor());
			statement.setString(2, despesas.getClassifica());
			if(despesas.getId_conta_pessoal() != null) {
				statement.setLong(3, despesas.getId_conta_pessoal().getId());	
				despesas.getId_conta_pessoal().setSaldo(despesas.getId_conta_pessoal().getSaldo() - despesas.getValor());
				cpDAO.editar(despesas.getId_conta_pessoal());
			}else {
				statement.setNull(3, 0);
			}
			if(despesas.getId_conta_empresa() != null) {
				statement.setLong(4, despesas.getId_conta_empresa().getId());	
				despesas.getId_conta_empresa().setSaldo(despesas.getId_conta_empresa().getSaldo() - despesas.getValor());
				ceDAO.editar(despesas.getId_conta_empresa());
			}else {
				statement.setNull(4, 0);
			}
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				despesas.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return despesas;
	}
	// EDITAR
			public boolean editar(Despesas despesas) {
				this.conexao.abrirConexao();
				String sqlUpdate = "UPDATE despesas SET valor=?, classifica=?, id_conta_pessoal=?, id_conta_empresa=? WHERE id_despesa=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
					statement.setDouble(1, despesas.getValor());
					statement.setString(2, despesas.getClassifica());
					statement.setLong(3, despesas.getId_conta_pessoal().getId());
					statement.setLong(4, despesas.getId_conta_empresa().getId());
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
				DespesasDAO deDAO = new DespesasDAO();
				Despesas despesas = deDAO .buscarPorId(id);
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				String sqlExcluir = "DELETE FROM despesas WHERE id_despesa=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlExcluir);
					statement.setLong(1, id);
					if(despesas.getId_conta_pessoal() != null) {
						despesas.getId_conta_pessoal().setSaldo(despesas.getId_conta_pessoal().getSaldo() + despesas.getValor());
						cpDAO.editar(despesas.getId_conta_pessoal());
						} 
						if(despesas.getId_conta_empresa() != null) {
							despesas.getId_conta_empresa().setSaldo(despesas.getId_conta_empresa().getSaldo() + despesas.getValor());
							ceDAO.editar(despesas.getId_conta_empresa());
						}
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
			public Despesas buscarPorId(long id) {
				this.conexao.abrirConexao();
				String sqlBuscarPorId = "SELECT * FROM despesas WHERE id_despesa=?";
				Despesas despesas = null;
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarPorId);
					statement.setLong(1, id);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					if (rs.next()) {
						despesas = new Despesas();
						despesas.setId(rs.getLong("id_despesa"));
						despesas.setValor(rs.getDouble("valor"));
						despesas.setClassifica(rs.getString("classifica"));
									
						Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
						despesas.setId_conta_pessoal(conta_pessoal);
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						despesas.setId_conta_empresa(conta_empresa);

						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return despesas;
			}

			// BUSCAR TODOS
			public List<Despesas> buscarTodos() {
				this.conexao.abrirConexao();
				String sqlBuscarTodos = "SELECT * FROM despesas";
				List<Despesas> listaDespesas = new ArrayList<Despesas>();
				Despesas despesas = null;
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarTodos);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					while (rs.next()) {
						despesas = new Despesas();
						despesas.setId(rs.getLong("id_despesa"));
						despesas.setValor(rs.getDouble("valor"));
						despesas.setClassifica(rs.getString("classifica"));
						
						Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
						despesas.setId_conta_pessoal(conta_pessoal);
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						despesas.setId_conta_empresa(conta_empresa);

						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return listaDespesas;
			}
			// BUSCAR TODOS POR ID DA CONTA PESSOAL
			public List<Despesas> buscarTodosPorIdPessoal(Long id) {
				this.conexao.abrirConexao();
				String sqlBuscarPorIdUsu = "SELECT * FROM despesas WHERE id_conta_pessoal=?";
				List<Despesas> listaDespesas = new ArrayList<Despesas>();
				Despesas despesas = null;
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarPorIdUsu);
					statement.setLong(1, id);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					while (rs.next()) {
						despesas = new Despesas();
						despesas.setId(rs.getLong("id_despesa"));
						despesas.setValor(rs.getDouble("valor"));
						despesas.setClassifica(rs.getString("classifica"));
						
						Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
						despesas.setId_conta_pessoal(conta_pessoal);
						
						listaDespesas.add(despesas);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return listaDespesas;
			}
			// BUSCAR TODOS POR ID DA CONTA EMPRESA
						public List<Despesas> buscarTodosPorIdEmpresa(Long id) {
							this.conexao.abrirConexao();
							String sqlBuscarPorIdUsu = "SELECT * FROM despesas WHERE id_conta_empresa=?";
							List<Despesas> listaDespesas = new ArrayList<Despesas>();
							Despesas despesas = null;
							Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
							try {
								PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
										.prepareStatement(sqlBuscarPorIdUsu);
								statement.setLong(1, id);
								ResultSet rs = statement.executeQuery();
								// CONVERTER O RESULTSET EM UM OBJETO USUARIO
								while (rs.next()) {
									despesas = new Despesas();
									despesas.setId(rs.getLong("id_despesa"));
									despesas.setValor(rs.getDouble("valor"));
									despesas.setClassifica(rs.getString("classifica"));
									
									Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
									despesas.setId_conta_empresa(conta_empresa);
									
									listaDespesas.add(despesas);
									
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} finally {
								this.conexao.fecharConexao();
							}
							return listaDespesas;
						}
}