package atelier3_1_1;

import java.io.Serializable;

@SuppressWarnings({"rawtypes"})
public abstract class Vehicule implements Comparable, Serializable {
	private static final long serialVersionUID = 7514696972779090323L;

	private double totalKilometres = 0;
	
	public Vehicule(double totalKilometres) {
		this.totalKilometres = totalKilometres;
	}

	@Override
	public String toString() {
		return nomVehicule() + "[" + totalKilometres + " kilomètres]";
	}
	
	protected double getTotalKilometres() {
		return totalKilometres;
	}

	protected abstract String nomVehicule();

	@Override
	public int compareTo(Object autreObjet) {
		int resultat = 0;
		
		Vehicule autreVehicule = (Vehicule) autreObjet;

		if(this.siMoinsDeKilometrageQue(autreVehicule)) {

			resultat = -1;

		}else if(autreVehicule.siMoinsDeKilometrageQue(this)) {

			resultat = +1;
		}

		return resultat;
	}

	private boolean siMoinsDeKilometrageQue(Vehicule autreValeur) {
		return totalKilometres < autreValeur.getTotalKilometres();
	}
	
	@Override 
	public boolean equals(Object autre) {
		boolean equals = false;
		
		if(autre != null && this.getClass().equals(autre.getClass())) {
			equals = ((Vehicule)autre).totalKilometres == this.totalKilometres;
		}
		
		return equals;
	}
	
}
