package br.com.saudefinanceira.model;

import java.util.ArrayList;

public class Conta_Pessoal implements Interface {
	private long id_conta_pessoal;
	private double saldo;
	private double meta;
	private String tipo_conta;
	private Usuario id_usuario;
	private ArrayList<Conta_Pessoal> listaContas_Pessoal;
	
	public Conta_Pessoal() {
		super();
	}
	
	public Conta_Pessoal(long id_conta_pessoal, double saldo, double meta, String tipo_conta, Usuario id_usuario, ArrayList<Conta_Pessoal> listaContas_Pessoal) {
		this.id_conta_pessoal = id_conta_pessoal;
		this.saldo = saldo;
		this.meta = meta;
		this.tipo_conta = tipo_conta;
		this.id_usuario = id_usuario;
		this.listaContas_Pessoal = listaContas_Pessoal;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setMeta(double meta) {
		this.meta = meta;
	}
	public double getMeta() {
		return meta;
	}
	public long getId() {
		return id_conta_pessoal;
	}
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setId(long id_conta_pessoal) {
		this.id_conta_pessoal = id_conta_pessoal;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Usuario getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Usuario id_usuario) {
		this.id_usuario = id_usuario;
	}
	public ArrayList<Conta_Pessoal> getListaContas_Pessoal() {
		return listaContas_Pessoal;
	}

	public void setListaContas_Pessoal(ArrayList<Conta_Pessoal> listaContas_Pessoal) {
		this.listaContas_Pessoal = listaContas_Pessoal;
	}
	
}