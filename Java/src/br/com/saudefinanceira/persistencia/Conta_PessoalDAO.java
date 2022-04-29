package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Conta_Pessoal;
import br.com.saudefinanceira.model.Usuario;

public class Conta_PessoalDAO {
	private ConexaoMysql conexao;
	
	public Conta_PessoalDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}
	
	// SALVAR
	
	public Conta_Pessoal salvar(Conta_Pessoal conta_pessoal) {
		this.conexao.abrirConexao();
		String sqlInsert = "INSERT INTO conta_pessoal VALUES(null, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, conta_pessoal.getSaldo());
			statement.setDouble(2, conta_pessoal.getMeta());
			statement.setString(3, conta_pessoal.getTipo_conta());
			statement.setLong(4, conta_pessoal.getId_usuario().getId());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				conta_pessoal.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return conta_pessoal;
	}
	// EDITAR
		public boolean editar(Conta_Pessoal conta_pessoal) {
			this.conexao.abrirConexao();
			String sqlUpdate = "UPDATE conta_pessoal SET saldo=?, meta=?, tipo_conta=?, id_usuario=? WHERE id_conta_pessoal=?";
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
				statement.setDouble(1, conta_pessoal.getSaldo());
				statement.setDouble(2, conta_pessoal.getMeta());
				statement.setString(3, conta_pessoal.getTipo_conta());
				statement.setLong(4, conta_pessoal.getId_usuario().getId());
				statement.setLong(5, conta_pessoal.getId());
				
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
			String sqlExcluir = "DELETE FROM conta_pessoal WHERE id_conta_pessoal=?";
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlExcluir);
				statement.setLong(1, id);
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
		public Conta_Pessoal buscarPorId(long id) {
			this.conexao.abrirConexao();
			String sqlBuscarPorId = "SELECT * FROM conta_pessoal WHERE id_conta_pessoal=?";
			Conta_Pessoal conta_pessoal = null;
			UsuarioDAO uDAO = new UsuarioDAO();
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
						.prepareStatement(sqlBuscarPorId);
				statement.setLong(1, id);
				ResultSet rs = statement.executeQuery();
				// CONVERTER O RESULTSET EM UM OBJETO USUARIO
				if (rs.next()) {
					conta_pessoal = new Conta_Pessoal();
					conta_pessoal.setId(rs.getLong("id_conta_pessoal"));
					conta_pessoal.setSaldo(rs.getDouble("saldo"));
					conta_pessoal.setMeta(rs.getDouble("meta"));
					conta_pessoal.setTipo_conta(rs.getString("tipo_conta"));
					Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
					conta_pessoal.setId_usuario(usuario);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.conexao.fecharConexao();
			}
			return conta_pessoal;
		}
		// BuscarPorIdUsuario
				public Conta_Pessoal buscarPorIdUsuario(long id) {
					this.conexao.abrirConexao();
					String sqlBuscarPorId = "SELECT * FROM conta_pessoal WHERE id_usuario=?";
					Conta_Pessoal conta_pessoal = null;
					UsuarioDAO uDAO = new UsuarioDAO();
					try {
						PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
								.prepareStatement(sqlBuscarPorId);
						statement.setLong(1, id);
						ResultSet rs = statement.executeQuery();
						// CONVERTER O RESULTSET EM UM OBJETO USUARIO
						if (rs.next()) {
							conta_pessoal = new Conta_Pessoal();
							conta_pessoal.setId(rs.getLong("id_conta_pessoal"));
							conta_pessoal.setSaldo(rs.getDouble("saldo"));
							conta_pessoal.setMeta(rs.getDouble("meta"));
							conta_pessoal.setTipo_conta(rs.getString("tipo_conta"));
							Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
							conta_pessoal.setId_usuario(usuario);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.conexao.fecharConexao();
					}
					return conta_pessoal;
				}
		// BUSCAR TODOS
		public List<Conta_Pessoal> buscarTodos() {
			this.conexao.abrirConexao();
			String sqlBuscarTodos = "SELECT * FROM conta_pessoal";
			List<Conta_Pessoal> listaContas_Pessoal = new ArrayList<Conta_Pessoal>();
			Conta_Pessoal conta_pessoal = null;
			UsuarioDAO uDAO = new UsuarioDAO();
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
						.prepareStatement(sqlBuscarTodos);
				ResultSet rs = statement.executeQuery();
				// CONVERTER O RESULTSET EM UM OBJETO USUARIO
				while (rs.next()) {
					conta_pessoal = new Conta_Pessoal();
					conta_pessoal.setId(rs.getLong("id_conta_pessoal"));
					conta_pessoal.setSaldo(rs.getDouble("saldo"));
					conta_pessoal.setMeta(rs.getDouble("meta"));
					conta_pessoal.setTipo_conta(rs.getString("tipo_conta"));
					
					Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
					conta_pessoal.setId_usuario(usuario);
					
					listaContas_Pessoal.add(conta_pessoal);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.conexao.fecharConexao();
			}
			return listaContas_Pessoal;
		}
}
