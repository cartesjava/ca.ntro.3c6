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
package tutoriels.mappage;

public class Cle<T> {
	
	private T valeur;

	public T getValeur() {
		return valeur;
	}

	public void setValeur(T valeur) {
		this.valeur = valeur;
	}

	public Cle(T valeur) {
		this.valeur = valeur;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object autre) {
		boolean siEgal = false;
		
		if(autre instanceof Cle) {
			siEgal = siEgal((Cle<T>) autre);
		}
		
		return siEgal;
	}

	public boolean siEgal(Cle<T> autreCle) {
		return valeur.equals(autreCle.getValeur());
	}

}
