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
package tutoriels.liste;

import java.lang.reflect.Array;

public abstract class ListeJava<E extends Object> {
	
	protected Class<E> typeElement;
	
	public ListeJava(Class<E> typeElement) {
		this.typeElement = typeElement;
	}
	
	protected Class<E> typeElement(){
		return typeElement;
	}

	protected E[] nouveauTableau(int taille) {
		return (E[]) Array.newInstance(typeElement, taille);
	}

	public abstract void    add(E e);                   // ajoute à la fin
	public abstract void    addAll(E[] elements);       // insère tout
	public abstract void    insert(int position, E e);  // insère une nouvelle valeur à la position i
	public abstract void    set(int position, E e);     // modifie la valeur à la position i
	public abstract E       get(int position);          // obtenir la valeur à la position i
	public abstract void    clear();                    // vide la liste
	public abstract int     size();                     // taille de la liste
	public abstract boolean isEmpty();                  // si vide
	public abstract boolean contains(Object o);         // si la liste contient la valeur o
	public abstract int     indexOf(Object o);          // indice de la valeur o
	public abstract void    removeValue(Object o);      // indice de la valeur o
	public abstract void    remove(int position);       // indice de la valeur o
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		
		if(size() > 0) {
			builder.append(get(0));
		}
	
		for(int i = 1; i < size(); i++) {
			builder.append(", ");
			builder.append(get(i));
		}

		builder.append("]");
		
		return builder.toString();
	}
	
	@Override 
	public boolean equals(Object autre) {
		boolean siEgal = false;
		
		try {

			siEgal = siEgal((ListeJava<E>) autre);
			
		}catch(ClassCastException e) {}
		
		
		return siEgal;
	}
	
	private boolean siEgal(ListeJava<E> autreListe) {
		boolean siEgal = true;
		
		if(size() != autreListe.size()) {

			siEgal = false;

		}else {
			
			for(int i = 0; i < size(); i++) {
				E cetteValeur = get(i);
				E autreValeur = autreListe.get(i);
				
				if(cetteValeur != null && !cetteValeur.equals(autreValeur)) {
					siEgal = false;
					break;
					
				}else if(cetteValeur == null && autreValeur != null){
					siEgal = false;
					break;
				}
			}
		}
		
		return siEgal;
	}
}    

