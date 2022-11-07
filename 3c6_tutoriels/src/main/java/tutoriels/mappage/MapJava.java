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

import java.util.List;

public abstract class MapJava<C extends Cle<?>, V extends Object> {
	
	public abstract void       put(C c, V v);      // associe la valeur v à la clé c
	public abstract V          get(C c);           // obtenir la valeur associée à la clé c
	public abstract void       clear();            // vide le map
	public abstract int        size();             // taille du map
	public abstract boolean    isEmpty();          // si vide
	public abstract boolean    containsKey(C c);   // si le map contient la clé c
	public abstract boolean    containsValue(V v); // si le map contient la valeur v
	public abstract void       remove(C c);        // si le map contient la valeur v
	public abstract List<C>    keys();             // si le map contient la valeur v
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("{");
		
		List<C> cles = keys();
		
		if(cles.size() > 0) {

			formatKeyValue(builder, cles.get(0));
		}
		
		for(int i = 1; i < cles.size(); i++) {
			builder.append(", ");
			formatKeyValue(builder, cles.get(i));
		}

		builder.append("}");

		return builder.toString();
	}

	private void formatKeyValue(StringBuilder builder, C cle) {
		builder.append("\"");
		builder.append(cle.getValeur());
		builder.append("\"");
		builder.append(":");
		builder.append(get(cle));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		boolean siEgal = false;
		
		if(o instanceof MapJava) {
			siEgal = siEgal((MapJava<C,V>) o);
		}
		
		return siEgal;
	}
	
	private boolean siEgal(MapJava<C,V> autre) {
		boolean siEgal = true;

		List<C> cles = keys();

		if(cles.size() != autre.keys().size()) {
			siEgal = false;
		}else {
			
			for(C cle : cles) {
				if(!get(cle).equals(autre.get(cle))) {
					siEgal = false;
					break;
				}
			}
		}
		
		return siEgal;
	}
}
