package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Conta_Empresa;
import br.com.saudefinanceira.model.Conta_Pessoal;
import br.com.saudefinanceira.model.Rendas;

public class RendasDAO {

	private ConexaoMysql conexao;
	
	public RendasDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}

	// SALVAR
	public Rendas salvar(Rendas rendas) {
		this.conexao.abrirConexao();
		Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
		Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
		String sqlInsert = "INSERT INTO rendas VALUES(null,?,?,?,?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, rendas.getValor());
			statement.setString(2, rendas.getClassifica());
			if(rendas.getId_conta_pessoal() != null) {
				statement.setLong(3, rendas.getId_conta_pessoal().getId());	
				rendas.getId_conta_pessoal().setSaldo(rendas.getId_conta_pessoal().getSaldo() + rendas.getValor());
				
			    cpDAO.editar(rendas.getId_conta_pessoal());
			}else {
				statement.setNull(3, 0);
			}
			if(rendas.getId_conta_empresa() != null) {
				statement.setLong(4, rendas.getId_conta_empresa().getId());	
				rendas.getId_conta_empresa().setSaldo(rendas.getId_conta_empresa().getSaldo() + rendas.getValor());
				
				ceDAO.editar(rendas.getId_conta_empresa());
			}else {
				statement.setNull(4, 0);
			}
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				rendas.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return rendas;
	}
	// EDITAR
			public boolean editar(Rendas rendas) {
				this.conexao.abrirConexao();
				String sqlUpdate = "UPDATE rendas SET valor=?, classifica=?, id_conta_pessoal=?, id_conta_empresa=? WHERE id_renda=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
					statement.setDouble(1, rendas.getValor());
					statement.setString(2, rendas.getClassifica());
					statement.setLong(3, rendas.getId_conta_pessoal().getId());
					statement.setLong(4, rendas.getId_conta_empresa().getId());
					
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
				RendasDAO rDAO = new RendasDAO();
				Rendas rendas = rDAO .buscarPorId(id);
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				String sqlExcluir = "DELETE FROM rendas WHERE id_renda=?";
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlExcluir);
					statement.setLong(1, id);
					if(rendas.getId_conta_pessoal() != null) {
					rendas.getId_conta_pessoal().setSaldo(rendas.getId_conta_pessoal().getSaldo() - rendas.getValor());
					cpDAO.editar(rendas.getId_conta_pessoal());
					} 
					if(rendas.getId_conta_empresa() != null) {
						rendas.getId_conta_empresa().setSaldo(rendas.getId_conta_empresa().getSaldo() - rendas.getValor());
						ceDAO.editar(rendas.getId_conta_empresa());
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
			public Rendas buscarPorId(long id) {
				this.conexao.abrirConexao();
				String sqlBuscarPorId = "SELECT * FROM rendas WHERE id_renda=?";
				Rendas rendas = null;
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarPorId);
					statement.setLong(1, id);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					if (rs.next()) {
						rendas = new Rendas();
						rendas.setId(rs.getLong("id_renda"));
						rendas.setValor(rs.getDouble("valor"));
						rendas.setClassifica(rs.getString("classifica"));
									
						Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
						rendas.setId_conta_pessoal(conta_pessoal);
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						rendas.setId_conta_empresa(conta_empresa);

						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return rendas;
			}

			// BUSCAR TODOS
			public List<Rendas> buscarTodos() {
				this.conexao.abrirConexao();
				String sqlBuscarTodos = "SELECT * FROM rendas";
				List<Rendas> listaRendas = new ArrayList<Rendas>();
				Rendas rendas = null;
				Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
				Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
				try {
					PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
							.prepareStatement(sqlBuscarTodos);
					ResultSet rs = statement.executeQuery();
					// CONVERTER O RESULTSET EM UM OBJETO USUARIO
					while (rs.next()) {
						rendas = new Rendas();
						rendas.setId(rs.getLong("id_renda"));
						rendas.setValor(rs.getDouble("valor"));
						rendas.setClassifica(rs.getString("classifica"));
						
						Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
						rendas.setId_conta_pessoal(conta_pessoal);
						Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
						rendas.setId_conta_empresa(conta_empresa);
						
						listaRendas.add(rendas);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					this.conexao.fecharConexao();
				}
				return listaRendas;
			}
				// BUSCAR TODOS POR ID DA CONTA PESSOAL
				public List<Rendas> buscarTodosPorIdPessoal(Long id) {
					this.conexao.abrirConexao();
					String sqlBuscarPorIdUsu = "SELECT * FROM rendas WHERE id_conta_pessoal=?";
					List<Rendas> listaRendas = new ArrayList<Rendas>();
					Rendas rendas = null;
					Conta_PessoalDAO cpDAO = new Conta_PessoalDAO();
					try {
						PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
								.prepareStatement(sqlBuscarPorIdUsu);
						statement.setLong(1, id);
						ResultSet rs = statement.executeQuery();
						// CONVERTER O RESULTSET EM UM OBJETO USUARIO
						while (rs.next()) {
							rendas = new Rendas();
							rendas.setId(rs.getLong("id_renda"));
							rendas.setValor(rs.getDouble("valor"));
							rendas.setClassifica(rs.getString("classifica"));
							
							Conta_Pessoal conta_pessoal = cpDAO.buscarPorId(rs.getLong("id_conta_pessoal"));
							rendas.setId_conta_pessoal(conta_pessoal);
							
							listaRendas.add(rendas);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.conexao.fecharConexao();
					}
					return listaRendas;
				}
				// BUSCAR TODOS POR ID DA CONTA EMPRESA
				public List<Rendas> buscarTodosPorIdEmpresa(Long id) {
					this.conexao.abrirConexao();
					String sqlBuscarPorIdUsu = "SELECT * FROM rendas WHERE id_conta_empresa=?";
					List<Rendas> listaRendas = new ArrayList<Rendas>();
					Rendas rendas = null;
					Conta_EmpresaDAO ceDAO = new Conta_EmpresaDAO();
					try {
						PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
								.prepareStatement(sqlBuscarPorIdUsu);
						statement.setLong(1, id);
						ResultSet rs = statement.executeQuery();
						// CONVERTER O RESULTSET EM UM OBJETO USUARIO
						while (rs.next()) {
							rendas = new Rendas();
							rendas.setId(rs.getLong("id_renda"));
							rendas.setValor(rs.getDouble("valor"));
							rendas.setClassifica(rs.getString("classifica"));
							
							Conta_Empresa conta_empresa = ceDAO.buscarPorId(rs.getLong("id_conta_empresa"));
							rendas.setId_conta_empresa(conta_empresa);
							
							listaRendas.add(rendas);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.conexao.fecharConexao();
					}
					return listaRendas;
				}

	}