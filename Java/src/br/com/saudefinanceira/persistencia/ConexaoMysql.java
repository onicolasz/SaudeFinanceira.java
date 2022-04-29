package br.com.saudefinanceira.persistencia;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConexaoMysql {
	private String ip;
	private String porta;
	private String login;
	private String senha;
	private String nomeBd;
	private Connection conexao;

	public ConexaoMysql(String ip, String porta, String login, String senha, String nomeBd) {
		super();
		this.ip = ip;
		this.porta = porta;
		this.login = login;
		this.senha = senha;
		this.nomeBd = nomeBd;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeBd() {
		return nomeBd;
	}

	public void setNomeBd(String nomeBd) {
		this.nomeBd = nomeBd;
	}

	// ABRIR CONEXAO
	public void abrirConexao() {
		// IMPLEMENTAR O METODO
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + ip + ":" + porta + "/" + nomeBd + "?autoReconnect=true&useSSL=false";
			this.conexao = (Connection) DriverManager.getConnection(url, login, senha);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// FECHAR CONEXAO
	public void fecharConexao() {
		try {
			if (!this.conexao.isClosed()) {
				this.conexao.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
