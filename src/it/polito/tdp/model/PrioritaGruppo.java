package it.polito.tdp.model;

import java.util.Comparator;

public class PrioritaGruppo implements Comparator<Gruppo> {

	@Override
	public int compare(Gruppo g1, Gruppo g2) {
		
		if (g1.getTolerance()>g2.getTolerance()) return -1;
		if (g1.getTolerance()<g2.getTolerance()) return 1;
		
		return g1.getOraArrivo().compareTo(g2.getOraArrivo());

	}
}
