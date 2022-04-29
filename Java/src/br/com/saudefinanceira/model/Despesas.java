package br.com.saudefinanceira.model;

import java.util.ArrayList;

public class Despesas implements Interface {
	private long id_despesa;
	private double valor;
	private String classifica;
	Conta_Pessoal id_conta_pessoal;
	Conta_Empresa id_conta_empresa;
	private ArrayList<Rendas> listaDespesas;
	
	public Despesas() {
		super();
	}
	
	public Despesas(long id_despesa, Double valor, String classifica, Conta_Pessoal id_conta_pessoal, Conta_Empresa id_conta_empresa, ArrayList<Rendas> listaDespesas) {
		this.id_despesa = id_despesa;
		this.valor = valor;
		this.classifica = classifica;
		this.id_conta_pessoal = id_conta_pessoal;
		this.id_conta_empresa = id_conta_empresa;
		this.listaDespesas = listaDespesas;
	}
	public long getId() {
		return id_despesa;
	}

	public void setId(long id_despesa) {
		this.id_despesa = id_despesa;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getClassifica() {
		return classifica;
	}

	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public Conta_Pessoal getId_conta_pessoal() {
		return id_conta_pessoal;
	}

	public void setId_conta_pessoal(Conta_Pessoal id_conta_pessoal) {
		this.id_conta_pessoal = id_conta_pessoal;
	}

	public Conta_Empresa getId_conta_empresa() {
		return id_conta_empresa;
	}

	public void setId_conta_empresa(Conta_Empresa id_conta_empresa) {
		this.id_conta_empresa = id_conta_empresa;
	}

	public ArrayList<Rendas> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(ArrayList<Rendas> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}
	
}