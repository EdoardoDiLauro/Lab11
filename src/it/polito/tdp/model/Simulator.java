package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.model.Evento.TipoEvento;
import it.polito.tdp.model.Gruppo.Stato;

public class Simulator {
	
	// Coda degli eventi
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	
	private List<Gruppo> gruppi;
	private LocalTime T_inizio = LocalTime.now();
	private PriorityQueue<Gruppo> attesa;
	int NE = 2000;
	int t10 = 2;
	int t8 = 4;
	int t6 = 4;
	int t4 = 5;
	private Duration intervalloPolling = Duration.ofMinutes(1);
	int numTotClienti;
	int numSoddisfatti;
	int numInsoddisfatti;
	
	public Simulator() {
		this.gruppi = new ArrayList<Gruppo>();
	}

	public void init() {
		// Creare i pazienti
		LocalTime oraArrivo = T_inizio;
		gruppi.clear();
		
		for (int i = 0; i < NE; i++) {
			Random rand = new Random();
			int p = rand.nextInt((10 - 1) + 1) + 1;
			int t = rand.nextInt((10 - 1) + 1) + 1;
			Duration arrival = Duration.ofMinutes(t);
			int d = rand.nextInt((120 - 60) + 1) + 60;
			Duration permanenza = Duration.ofMinutes(d);
			float tolerance = (float) (rand.nextFloat()* 0.9);
			oraArrivo = oraArrivo.plus(arrival);
			
			Gruppo g = new Gruppo(oraArrivo, i+1, p, permanenza, tolerance);
			gruppi.add(g);
		}
		
		// Inizializzo la sala d'attesa vuota
		this.attesa = new PriorityQueue<>(new PrioritaGruppo());
		
		// Creare gli eventi iniziali
		queue.clear();
		for (Gruppo g : gruppi) {
			queue.add(new Evento(g.getOraArrivo(), TipoEvento.ARRIVO, g));
		}

		// lancia l'osservatore in polling
		queue.add(new Evento(T_inizio.plus(intervalloPolling), TipoEvento.POLLING, null));
		
		// Resettare le statistiche
		numTotClienti = 0;
		numSoddisfatti = 0;
		numInsoddisfatti = 0;


	}
	
	public void run() {

		while (!queue.isEmpty()) {
			Evento ev = queue.poll();
//			System.out.println(ev);

			Gruppo g = ev.getGruppo();

			/*
			 * se la simulazione dovesse terminare alle 20:00
			 * if(ev.getOra().isAfter(T_fine)) break ;
			 */

			switch (ev.getTipo()) {

			case ARRIVO:
				attesa.add(g);
				break;
			
			case ATTESA :
				Gruppo gruppoChiamato = attesa.poll();
				
				if (gruppoChiamato == null)
					break;
				
				int pax = gruppoChiamato.getPax();
				
				numTotClienti = numTotClienti + pax;
				
				if (pax > 8 && t10>0) {
						gruppoChiamato.setStato(Stato.SEDUTO10);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t10--;
				}
				else if (pax > 6) {
					if (t8>0) {
						gruppoChiamato.setStato(Stato.SEDUTO8);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t8--;
						}
					else if (t10>0) {
						gruppoChiamato.setStato(Stato.SEDUTO10);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t10--;
						}

				}
				else if (pax > 4) {
					if (t6>0) {
						gruppoChiamato.setStato(Stato.SEDUTO6);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t6--;
						}
					else if (t8>0) {
						gruppoChiamato.setStato(Stato.SEDUTO8);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t8--;
						}
					else if (t10>0) {
						gruppoChiamato.setStato(Stato.SEDUTO10);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t10--;
						}

				}
				else if (pax > 2) {
					if (t4>0) {
						gruppoChiamato.setStato(Stato.SEDUTO4);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t4--;
						}
					else if (t6>0) {
						gruppoChiamato.setStato(Stato.SEDUTO6);
						queue.add(new Evento(ev.getOra().plusMinutes(gruppoChiamato.getDurata().toMinutes()), TipoEvento.PAGATO, gruppoChiamato));
						t6--;
						}
				

				}
				else {
					gruppoChiamato.setStato(Stato.INPIEDI);
					if (gruppoChiamato.getTolerance()>0.45) numSoddisfatti = numSoddisfatti + pax;
					else numInsoddisfatti = numInsoddisfatti + pax;
				}
				break;
				
			case PAGATO:
				
				if (g.getStato().equals(Stato.SEDUTO10)) t10++;
				else if (g.getStato().equals(Stato.SEDUTO8)) t8++;
				else if (g.getStato().equals(Stato.SEDUTO6)) t6++;
				else if (g.getStato().equals(Stato.SEDUTO4)) t4++;
				
				g.setStato(Stato.PAGATO);
				int coperti = g.getPax();
				
				numTotClienti = numTotClienti + coperti;
				numSoddisfatti = numSoddisfatti + coperti;
				
				break;
				
			case POLLING:
				// verifica se ci sono pazienti in attesa con studi liberi
				if (!attesa.isEmpty()) {
					queue.add(new Evento(ev.getOra(), TipoEvento.ATTESA, null));
				}
				// rischedula sé stesso
				else queue.add(new Evento(ev.getOra().plus(intervalloPolling), TipoEvento.POLLING, null));
				break ;
				
				
			}
				
		}
		}

	public int getNumTotClienti() {
		return numTotClienti;
	}

	public int getNumSoddisfatti() {
		return numSoddisfatti;
	}

	public int getNumInsoddisfatti() {
		return numInsoddisfatti;
	}
}
