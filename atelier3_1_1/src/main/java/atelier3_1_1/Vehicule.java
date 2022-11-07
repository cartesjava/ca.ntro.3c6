/*
Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)

This file is part of Ntro, an application framework designed with teaching in mind.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
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
