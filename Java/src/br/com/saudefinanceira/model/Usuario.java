package br.com.saudefinanceira.model;

public class Usuario implements Interface {
	private String nome;
	private String profissao;
	private String cpf;
	private String email;
	private String senha;
	private String data_nascimento;
	private long id_usuario;
	
	public Usuario() {
		super();
	}
	public Usuario(long id_usuario, String nome, String cpf, String email, String senha, String data_nascimento, String profissao) {
		this.id_usuario = id_usuario;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.data_nascimento = data_nascimento;
		this.profissao = profissao;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public String getData_nascimento() {
		return data_nascimento;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public long getId() {
		return id_usuario;
	}
	public void setId(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	
}