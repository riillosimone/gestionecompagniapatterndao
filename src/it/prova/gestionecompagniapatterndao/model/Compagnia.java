package it.prova.gestionecompagniapatterndao.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Compagnia {
	private Long id;
	private String ragioneSociale;
	private Long fatturatoAnnuo;
	private LocalDate dataFondazione;
	private List<Impiegato> impiegati = new ArrayList<>();
	
	//costruttori
	public Compagnia() {
	}

	public Compagnia(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Compagnia(String ragioneSociale, Long fatturatoAnnuo, LocalDate dataFondazione) {
		this.ragioneSociale = ragioneSociale;
		this.fatturatoAnnuo = fatturatoAnnuo;
		this.dataFondazione = dataFondazione;
	}

	//get e set
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Long getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(Long fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public LocalDate getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(LocalDate dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Impiegato> getImpiegati() {
		return impiegati;
	}

	public void setImpiegati(List<Impiegato> impiegati) {
		this.impiegati = impiegati;
	}

	@Override
	public String toString() {
		String dataFondazioneString = dataFondazione != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataFondazione)
				: " N.D.";
		return "Compagnia [id=" + id + ", ragioneSociale=" + ragioneSociale + ", fatturatoAnnuo=" + fatturatoAnnuo
				+ ", dataFondazione=" + dataFondazioneString + "]";
	}
	
	
	
	
}
