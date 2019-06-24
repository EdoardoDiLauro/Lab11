package it.polito.tdp.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{

	/**
	 * Tipologie di eventi che possono accadere 
	 */
	public enum TipoEvento {
		ARRIVO, // un nuovo gruppo arriva
		ATTESA, // un nuovo gruppo attende
		
		PAGATO, // gruppo out
		
		POLLING, // evento periodico per verificare se ci sono tavoli liberi e gruppi in attesa
	}
	
	private LocalTime ora ; // timestamp dell'evento
	private TipoEvento tipo ; // tipologia
	private Gruppo gruppo ; // gruppo coinvolto nell'evento
	
	public Evento(LocalTime ora, TipoEvento tipo, Gruppo gruppo) {
		super();
		this.ora = ora;
		this.tipo = tipo;
		this.gruppo = gruppo;
	}
	public LocalTime getOra() {
		return ora;
	}
	public TipoEvento getTipo() {
		return tipo;
	}
	
	
	public Gruppo getGruppo() {
		return gruppo;
	}
	// Ordina per orario dell'evento
	@Override
	public int compareTo(Evento other) {
		return this.ora.compareTo(other.ora);
	}
	
}
