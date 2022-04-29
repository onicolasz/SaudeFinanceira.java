package br.com.saudefinanceira.model;

import java.util.ArrayList;

public class Rendas implements Interface {
	private long id_renda;
	private double valor;
	private String classifica;
	Conta_Pessoal id_conta_pessoal;
	Conta_Empresa id_conta_empresa;
	private ArrayList<Rendas> listaRendas;
	
	public Rendas() {
		super();
	}
	
	public Rendas(long id_renda, Double valor, String classifica, Conta_Pessoal id_conta_pessoal, Conta_Empresa id_conta_empresa, ArrayList<Rendas> listaRendas) {
		this.id_renda = id_renda;
		this.valor = valor;
		this.classifica = classifica;
		this.id_conta_pessoal = id_conta_pessoal;
		this.id_conta_empresa = id_conta_empresa;
		this.listaRendas = listaRendas;
	}
	public double getValor() {
		return valor;
	}
	public String getClassifica() {
		return classifica;
	}
	public long getId() {
		return id_renda;
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
	public void setId(long id_renda) {
		this.id_renda = id_renda;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}
	public ArrayList<Rendas> getListaRendas() {
		return listaRendas;
	}
	public void setListaRendas(ArrayList<Rendas> listaRendas) {
		this.listaRendas = listaRendas;
	}
}
	
