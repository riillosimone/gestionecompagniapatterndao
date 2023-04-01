package it.prova.gestionecompagniapatterndao.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Impiegato {
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private LocalDate dataNascita;
	private LocalDate dataAssunzione;
	private Compagnia compagnia;
	
	//costruttori
	public Impiegato() {
	}

	
	public Impiegato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}


	public Impiegato(String nome, String cognome, String codiceFiscale) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
	}

	public Impiegato(String nome, String cognome, String codiceFiscale, LocalDate dataNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
	}

	public Impiegato(String nome, String cognome, String codiceFiscale, LocalDate dataNascita,
			LocalDate dataAssunzione) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
	}

	public Impiegato(String nome, String cognome, String codiceFiscale, LocalDate dataNascita, LocalDate dataAssunzione,
			Compagnia compagnia) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.compagnia = compagnia;
	}


	//get e set
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public LocalDate getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	public LocalDate getDataAssunzione() {
		return dataAssunzione;
	}


	public void setDataAssunzione(LocalDate dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}


	public Compagnia getCompagnia() {
		return compagnia;
	}


	public void setCompagnia(Compagnia compagnia) {
		this.compagnia = compagnia;
	}


	@Override
	public String toString() {
		String dataNascitaString = dataNascita != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataNascita)
				: " N.D.";
		String dataAssunzioneString = dataAssunzione != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataAssunzione)
				: " N.D.";
		return "Impiegato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataNascita=" + dataNascitaString + ", dataAssunzione=" + dataAssunzioneString + ", compagnia=" + compagnia
				+ "]";
	}
	
	
	
	
}
