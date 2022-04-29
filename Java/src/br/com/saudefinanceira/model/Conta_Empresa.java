package br.com.saudefinanceira.model;

import java.util.ArrayList;

public class Conta_Empresa implements Interface {
	private long id_conta_empresa;
	private double saldo;
	private double meta;
	private String nome_empresa;
	private String cnpj;
	private String tipo_conta;
	private Usuario id_usuario;
	private ArrayList<Conta_Empresa> listaContas_Empresa;
	
	public Conta_Empresa() {
		super();
	}
	
	public Conta_Empresa(long id_conta_empresa, double saldo, double meta, String nome_empresa, String cnpj, String tipo_conta, Usuario id_usuario, ArrayList<Conta_Empresa> listaContas_Empresa) {
		this.id_conta_empresa = id_conta_empresa;
		this.saldo = saldo;
		this.meta = meta;
		this.setNome_empresa(nome_empresa);
		this.cnpj = cnpj;
		this.tipo_conta = tipo_conta;
		this.id_usuario = id_usuario;
		this.listaContas_Empresa = listaContas_Empresa;
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
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public long getId() {
		return id_conta_empresa;
	}
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setId(long id_conta_empresa) {
		this.id_conta_empresa = id_conta_empresa;
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
	public ArrayList<Conta_Empresa> getListaContas_Empresa() {
		return listaContas_Empresa;
	}

	public void setListaContas_Empresa(ArrayList<Conta_Empresa> listaContas_Empresa) {
		this.listaContas_Empresa = listaContas_Empresa;
	}

	public String getNome_empresa() {
		return nome_empresa;
	}

	public void setNome_empresa(String nome_empresa) {
		this.nome_empresa = nome_empresa;
	}
	
}