package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;

public class Gruppo {
	
	public enum Stato {
		ATTESA, // un nuovo gruppo attende
		SEDUTO10, // gruppo assegnato a tavolo
		SEDUTO8, // gruppo assegnato a tavolo
		SEDUTO6, // gruppo assegnato a tavolo
		SEDUTO4, // gruppo assegnato a tavolo
		INPIEDI, // gruppo assegnato a bancone
		PAGATO, // gruppo paga
		OUT, // gruppo out
	}
	
	private Stato stato ;
	
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	public Gruppo(LocalTime oraArrivo, int id, int pax, Duration durata, float tolerance) {
		super();
		this.oraArrivo = oraArrivo;
		this.id = id;
		this.pax = pax;
		this.durata = durata;
		this.tolerance = tolerance;
	}
	
	private LocalTime oraArrivo ;
	private int id ;
	private int pax ;
	private Duration durata;
	private float tolerance;
	
	public LocalTime getOraArrivo() {
		return oraArrivo;
	}
	public void setOraArrivo(LocalTime oraArrivo) {
		this.oraArrivo = oraArrivo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPax() {
		return pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	public Duration getDurata() {
		return durata;
	}
	public void setDurata(Duration durata) {
		this.durata = durata;
	}
	public float getTolerance() {
		return tolerance;
	}
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}

}
