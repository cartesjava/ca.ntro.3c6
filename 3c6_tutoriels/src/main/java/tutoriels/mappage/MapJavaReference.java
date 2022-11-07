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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapJavaReference<K extends Object, C extends Cle<K>, V extends Object> extends MapJava<C,V> {
	
	private Map<K,V> map = new HashMap<>();

	@Override
	public void put(C c, V v) {
		map.put(c.getValeur(), v);
	}

	@Override
	public V get(C c) {
		return map.get(c.getValeur());
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(C c) {
		return map.containsKey(c.getValeur());
	}

	@Override
	public boolean containsValue(V v) {
		return map.containsValue(v);
	}

	@Override
	public void remove(C c) {
		map.remove(c.getValeur());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<C> keys() {
		List<C> cles = new ArrayList<>();
		for(K valeurCle : map.keySet()) {
			cles.add((C) new Cle(valeurCle));
		}
		return cles;
	}
}
