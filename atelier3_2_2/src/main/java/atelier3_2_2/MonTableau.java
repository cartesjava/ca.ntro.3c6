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
package atelier3_2_2;

import java.io.Serializable;
import java.util.Arrays;

public class MonTableau <C extends Comparable<C>> implements Tableau<C>, Serializable {
	private static final long serialVersionUID = 2317796024770893684L;
	
	private C[] valeurs;
	
	@SuppressWarnings("unchecked")
	public MonTableau() {
		this.valeurs = (C[]) new Comparable[0];
	}

	public MonTableau(C[] valeurs) {
		this.valeurs = valeurs;
	}

	@Override
	public boolean siVide() {
		return valeurs.length == 0;
	}

	@Override
	public int longueur() {
		return valeurs.length;
	}

	@Override
	public C obtenirValeur(int index) {
		return valeurs[index];
	}

	@Override
	public void modifierValeur(int index, C nouvelleValeur) {
		valeurs[index] = nouvelleValeur;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void ajouter(C nouvelleValeur) {
		C[] nouvellesValeurs = (C[]) new Comparable[valeurs.length+1];

		for(int i = 0; i < valeurs.length ; i++) {
			nouvellesValeurs[i] = valeurs[i];
		}
		
		nouvellesValeurs[valeurs.length] = nouvelleValeur;

		valeurs = nouvellesValeurs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void retirer(int indice) {
		C[] nouvellesValeurs = (C[]) new Comparable[valeurs.length-1];

		for(int i = 0; i < indice; i++) {
			nouvellesValeurs[i] = valeurs[i];
		}

		for(int i = indice+1; i < valeurs.length; i++) {
			nouvellesValeurs[i-1] = valeurs[i];
		}
		
		valeurs = nouvellesValeurs;
	}

	@Override
	public void retirer(C valeur) {
		C[] nouvellesValeurs = (C[]) new Comparable[valeurs.length-1];
		
		int indice = 0;

		while(indice < valeurs.length) {
			if(valeurs[indice].compareTo(valeur) == 0) {
				indice++;
				break;
			}else {
				nouvellesValeurs[indice] = valeurs[indice];
				indice++;
			}
		}

		while(indice < valeurs.length) {
			nouvellesValeurs[indice-1] = valeurs[indice];
			indice++;
		}

		
		valeurs = nouvellesValeurs;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		
		if(valeurs.length > 0) {
			builder.append(valeurs[0]);
		}
		
		for(int i = 1; i < valeurs.length; i++) {
			builder.append(", ");
			builder.append(valeurs[i]);
		}

		builder.append("]");
		
		return builder.toString();
	}
	
	@Override 
	@SuppressWarnings({"rawtypes", "unchecked"})
	public boolean equals(Object autre) {
		boolean equals = false;

		if(autre instanceof Tableau) {
			Tableau autreTableau = (Tableau) autre;
			if(autreTableau.longueur() == valeurs.length) {
				int i = 0;
				while(i < valeurs.length) {
					if(valeurs[i].compareTo((C) autreTableau.obtenirValeur(i)) != 0) {
						break;
					}
					i++;
				}
				
				if(i == valeurs.length) {
					equals = true;
				}
			}
		}

		return equals;
	}

	@Override
	public Tableau<C> cloner() {
		return new MonTableau<C>(valeurs.clone());
	}

	@Override
	public Object clone() {
		return new MonTableau<C>(valeurs.clone());
	}

	@Override
	public C[] valeurs() {
		return valeurs;
	}
}
