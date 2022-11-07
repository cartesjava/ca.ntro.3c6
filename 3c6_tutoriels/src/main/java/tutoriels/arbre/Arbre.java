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
package tutoriels.arbre;

import java.util.List;

public abstract class Arbre<C extends Comparable<C>> {


	protected Noeud<C> racine;

	public Noeud<C> getRacine() {
		return racine;
	}

	public void setRacine(Noeud<C> racine) {
		this.racine = racine;
	}

	public abstract void ajouter(C valeur);
	public abstract void retirer(C valeur);
	public abstract Noeud<C> racine();
	public abstract int nombreDeNoeuds();
	public abstract Noeud<C> trouverNoeud(C valeur);
	public abstract List<Noeud<C>> tousLesNoeuds();
	
	@Override
	public String toString() {
		Noeud<C> racine = racine();
		String result = "null";
		
		if(racine != null) {
			result = racine.toString();
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		boolean siEgal = false;
		
		if(o instanceof Arbre) {
			siEgal = siEgal((Arbre<C>) o);
		}
		
		return siEgal;
	}
	
	private boolean siEgal(Arbre<C> autre) {
		boolean siEgal = true;
		
		Noeud<C> racine = racine();
		Noeud<C> autreRacine = autre.racine();
		
		if(racine == null && autreRacine != null) {

			siEgal = false;

		}else if(racine != null) {
			
			siEgal = racine.equals(autreRacine);
			
		}
		
		return siEgal;
	}
}
