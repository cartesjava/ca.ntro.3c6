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
package tutoriels.validateurs;

import java.util.List;

import tutoriels.mappage.Cle;

public class ListeDeCles<V extends Object> {
	
	protected List<Cle<V>> cles;
	
	public ListeDeCles(List<Cle<V>> cles) {
		this.cles = cles;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		
		if(cles.size() > 0) {
			builder.append(cles.get(0).getValeur());
		}
		
		for(int i = 1; i < cles.size(); i++) {
			builder.append(",");
			builder.append(cles.get(i).getValeur());
		}
		
		
		builder.append("]");

		return builder.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		boolean siEgal = false;
		
		if(o instanceof ListeDeCles) {
			siEgal = siEgal((ListeDeCles<V>) o);
		}
		
		return siEgal;
	}
	
	private boolean siEgal(ListeDeCles<V> autreListe) {
		boolean siEgal = true;
		
		if(cles.size() != autreListe.cles.size()) {
			siEgal = false;
		}else {
			
			for(int i = 0; i < cles.size(); i++) {
				if(!autreListe.cles.contains(cles.get(i))) {
					siEgal = false;
					break;
				}
			}
		}
		
		return siEgal;
	}

}
