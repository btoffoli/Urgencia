package br.com.btoffoli.model;

import android.location.Location;

public class Ocorrencia {
	private String solicitante;
	private String contato;
	private String natureza;
	private Boolean estaNoLocal = false;
	private Boolean manterRastreamento = false;
	private Location localizacao;

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public Boolean getEstaNoLocal() {
		return estaNoLocal;
	}

	public void setEstaNoLocal(Boolean estaNoLocal) {
		this.estaNoLocal = estaNoLocal;
	}

	public Boolean getManterRastreamento() {
		return manterRastreamento;
	}

	public void setManterRastreamento(Boolean manterRastreamento) {
		this.manterRastreamento = manterRastreamento;
	}

	public Location getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Location localizacao) {
		this.localizacao = localizacao;		
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}	
}
