package br.com.saudefinanceira.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.saudefinanceira.model.Usuario;

public class UsuarioDAO {

	private ConexaoMysql conexao;

	public UsuarioDAO() {
		super();
		this.conexao = new ConexaoMysql(BdConfigs.ip, BdConfigs.porta ,BdConfigs.login, BdConfigs.senha, BdConfigs.nomeBd);
	}

	// SALVAR
	public Usuario salvar(Usuario usuario) {
		this.conexao.abrirConexao();
		String sqlInsert = "INSERT INTO usuario VALUES(null,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlInsert,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getCpf());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getSenha());
			statement.setString(5, usuario.getData_nascimento());
			statement.setString(6, usuario.getProfissao());
			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				usuario.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return usuario;
	}

	// EDITAR
	public boolean editar(Usuario usuario) {
		this.conexao.abrirConexao();
		String sqlUpdate = "UPDATE usuario SET nome=?, cpf=?, email=?, senha=?, data_nascimento=?, profissao=? WHERE id_usuario=?";
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getCpf());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getSenha());
			statement.setString(5, usuario.getData_nascimento());
			statement.setString(6, usuario.getProfissao());
			statement.setLong(7, usuario.getId());
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
	// EDITAR SENHA
		public boolean editarSenha(String senha, Usuario usuario) {
			this.conexao.abrirConexao();
			String sqlUpdate = "UPDATE usuario SET senha=? WHERE id_usuario=?";
			try {
				PreparedStatement statement = (PreparedStatement) this.conexao.getConexao().prepareStatement(sqlUpdate);
				statement.setString(1, senha);
				statement.setLong(2, usuario.getId());
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
		String sqlExcluir = "DELETE FROM usuario WHERE id_usuario=?";
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
	public Usuario buscarPorId(long id) {
		this.conexao.abrirConexao();
		String sqlBuscarPorId = "SELECT * FROM usuario WHERE id_usuario=?";
		Usuario usuario = null;
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
					.prepareStatement(sqlBuscarPorId);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			// CONVERTER O RESULTSET EM UM OBJETO USUARIO
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setData_nascimento(rs.getString("data_nascimento"));
				usuario.setProfissao(rs.getString("profissao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return usuario;
	}

	// BUSCAR TODOS
	public List<Usuario> buscarTodos() {
		this.conexao.abrirConexao();
		String sqlBuscarTodos = "SELECT * FROM usuario";
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
					.prepareStatement(sqlBuscarTodos);
			ResultSet rs = statement.executeQuery();
			// CONVERTER O RESULTSET EM UM OBJETO USUARIO
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setData_nascimento(rs.getString("data_nascimento"));
				usuario.setProfissao(rs.getString("profissao"));
				listaUsuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return listaUsuarios;
	}

	// BUSCAR POR EMAIL E SENHA
	public Usuario buscarPorEmailSenha(String email, String senha) {
		this.conexao.abrirConexao();
		String sqlBuscarPorEmailSenha = "SELECT * FROM usuario WHERE email=? AND senha=?";
		Usuario usuario = null;
		try {
			PreparedStatement statement = (PreparedStatement) this.conexao.getConexao()
					.prepareStatement(sqlBuscarPorEmailSenha);
			statement.setString(1, email);
			statement.setString(2, senha);
			ResultSet rs = statement.executeQuery();
			// CONVERTER O RESULTSET EM UM OBJETO USUARIO
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getLong("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setData_nascimento(rs.getString("data_nascimento"));
				usuario.setProfissao(rs.getString("profissao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conexao.fecharConexao();
		}
		return usuario;
	}

}
