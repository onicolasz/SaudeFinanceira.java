package br.com.saudefinanceira.model;

import java.util.ArrayList;

public class Funcionarios implements Interface {
	private long id_funcionario;
	private String nome;
	private String funcao;
	private double salario;
	Conta_Empresa id_conta_empresa;
	private ArrayList<Funcionarios> listaFuncionarios;
	
	public Funcionarios() {
		super();
	}
	
	public Funcionarios(long id_funcionario, String nome, String funcao, double salario, Conta_Empresa id_conta_empresa, ArrayList<Funcionarios> listaFuncionarios) {
		this.id_funcionario = id_funcionario;
		this.nome = nome;
		this.funcao = funcao;
		this.salario = salario;
		this.id_conta_empresa = id_conta_empresa;
		this.listaFuncionarios = listaFuncionarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public long getId() {
		return id_funcionario;
	}

	public void setId(long id_funcionario) {
		this.id_funcionario = id_funcionario;
	}
	
	public Conta_Empresa getId_conta_empresa() {
		return id_conta_empresa;
	}

	public void setId_conta_empresa(Conta_Empresa id_conta_empresa) {
		this.id_conta_empresa = id_conta_empresa;
	}

	public ArrayList<Funcionarios> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(ArrayList<Funcionarios> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}
	
}