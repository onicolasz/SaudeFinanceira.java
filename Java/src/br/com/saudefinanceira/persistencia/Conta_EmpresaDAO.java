package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Conta_Empresa;
import br.com.saudefinanceira.model.Usuario;

public class Conta_EmpresaDAO {
	private ConexaoMysql conexao;
	
	public Conta_EmpresaDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}
	
	// SALVAR
	
	public Conta_Empresa salvar(Conta_Empresa conta_empresa) {
		this.conexao.abrirConexao();
		String sqlInsert = "INSERT INTO conta_empresa VALUES(null, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, conta_empresa.getSaldo());
			statement.setDouble(2, conta_empresa.getMeta());
			statement.setString(3, conta_empresa.getNome_empresa());
			statement.setString(4, conta_empresa.getCnpj());
			statement.setString(5, conta_empresa.getTipo_conta());
			statement.setLong(6, conta_empresa.getId_usuario().getId());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				conta_empresa.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return conta_empresa;
	}
	// EDITAR
		public boolean editar(Conta_Empresa conta_empresa) {
			this.conexao.abrirConexao();
			String sqlUpdate = "UPDATE conta_empresa SET saldo=?, meta=?, nome_empresa=?, cnpj=?, tipo_conta=?, id_usuario=? WHERE id_conta_empresa=?";
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
				statement.setDouble(1, conta_empresa.getSaldo());
				statement.setDouble(2, conta_empresa.getMeta());
				statement.setString(3, conta_empresa.getNome_empresa());
				statement.setString(4, conta_empresa.getCnpj());
				statement.setString(5, conta_empresa.getTipo_conta());
				statement.setLong(6, conta_empresa.getId_usuario().getId());
				statement.setLong(7, conta_empresa.getId());
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
			String sqlExcluir = "DELETE FROM conta_empresa WHERE id_conta_empresa=?";
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
		public Conta_Empresa buscarPorId(long id) {
			this.conexao.abrirConexao();
			String sqlBuscarPorId = "SELECT * FROM conta_empresa WHERE id_conta_empresa=?";
			Conta_Empresa conta_empresa = null;
			UsuarioDAO uDAO = new UsuarioDAO();
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
						.prepareStatement(sqlBuscarPorId);
				statement.setLong(1, id);
				ResultSet rs = statement.executeQuery();
				// CONVERTER O RESULTSET EM UM OBJETO USUARIO
				if (rs.next()) {
					conta_empresa = new Conta_Empresa();
					conta_empresa.setId(rs.getLong("id_conta_empresa"));
					conta_empresa.setSaldo(rs.getDouble("saldo"));
					conta_empresa.setMeta(rs.getDouble("meta"));
					conta_empresa.setNome_empresa(rs.getString("nome_empresa"));
					conta_empresa.setCnpj(rs.getString("cnpj"));
					conta_empresa.setTipo_conta(rs.getString("tipo_conta"));
					Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
					conta_empresa.setId_usuario(usuario);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.conexao.fecharConexao();
			}
			return conta_empresa;
		}
		// BuscarPorIdUsuario
				public Conta_Empresa buscarPorIdUsuario(long id) {
					this.conexao.abrirConexao();
					String sqlBuscarPorId = "SELECT * FROM conta_empresa WHERE id_usuario=?";
					Conta_Empresa conta_empresa = null;
					UsuarioDAO uDAO = new UsuarioDAO();
					try {
						PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
								.prepareStatement(sqlBuscarPorId);
						statement.setLong(1, id);
						ResultSet rs = statement.executeQuery();
						// CONVERTER O RESULTSET EM UM OBJETO USUARIO
						if (rs.next()) {
							conta_empresa = new Conta_Empresa();
							conta_empresa.setId(rs.getLong("id_conta_empresa"));
							conta_empresa.setSaldo(rs.getDouble("saldo"));
							conta_empresa.setMeta(rs.getDouble("meta"));
							conta_empresa.setNome_empresa(rs.getString("nome_empresa"));
							conta_empresa.setCnpj(rs.getString("cnpj"));
							conta_empresa.setTipo_conta(rs.getString("tipo_conta"));
							Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
							conta_empresa.setId_usuario(usuario);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.conexao.fecharConexao();
					}
					return conta_empresa;
				}
		// BUSCAR TODOS
		public List<Conta_Empresa> buscarTodos() {
			this.conexao.abrirConexao();
			String sqlBuscarTodos = "SELECT * FROM conta_pessoal";
			List<Conta_Empresa> listaContas_Empresa = new ArrayList<Conta_Empresa>();
			Conta_Empresa conta_empresa = null;
			UsuarioDAO uDAO = new UsuarioDAO();
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
						.prepareStatement(sqlBuscarTodos);
				ResultSet rs = statement.executeQuery();
				// CONVERTER O RESULTSET EM UM OBJETO USUARIO
				while (rs.next()) {
					conta_empresa = new Conta_Empresa();
					conta_empresa.setId(rs.getLong("id_conta_empresa"));
					conta_empresa.setSaldo(rs.getDouble("saldo"));
					conta_empresa.setMeta(rs.getDouble("meta"));
					conta_empresa.setNome_empresa(rs.getString("nome_empresa"));
					conta_empresa.setCnpj(rs.getString("cnpj"));
					conta_empresa.setTipo_conta(rs.getString("tipo_conta"));
					Usuario usuario = uDAO.buscarPorId(rs.getLong("id_usuario"));
					conta_empresa.setId_usuario(usuario);
					
					listaContas_Empresa.add(conta_empresa);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.conexao.fecharConexao();
			}
			return listaContas_Empresa;
		}
}
