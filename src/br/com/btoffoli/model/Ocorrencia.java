package br.com.btoffoli.model;

public class Ocorrencia {
	private String solicitante;
	private String natureza;
	private Boolean estaNoLocal;
	private Boolean manterRastreamento;

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

}
