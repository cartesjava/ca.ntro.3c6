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
package ca.ntro.core.models.properties.observable.map;

import java.util.Map;

import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.system.debug.T;

public abstract class ObservableMap<V extends Object> extends StoredProperty<Map<String, V>> {

	public ObservableMap(Map<String, V> value) {
		super(value);
		T.call(this);
	}

	public void addEntry(String key, V value) {
		
		//getValue().put(key, value);
		
		// FIXME: go through the  Backend
		//getObserver().onEntryAdded(key, value);
		
	}
	
	public void removeEntry(String key) {
		
		//V value = getValue().get(key);
		//getValue().remove(key);
		
		// FIXME: go through the Backend
		//getObserver().onEntryRemoved(key, value);
		
	}
	

}
