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
package ca.ntro.core.models.properties.stored.list;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ItemAddedListener;
import ca.ntro.core.models.properties.observable.list.ItemRemovedListener;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.system.debug.T;

public abstract class StoredList<I extends Object> extends ObservableList<I> {
	
	public StoredList(List<I> value) {
		super(value);
		T.call(this);
	}

	public StoredList<I> slice(int firstIndex, int lastIndex){
		
		// TODO
		return null;
	}
	
	public int size() {
		
		// TODO
		return 0;
	}
	
	public StoredProperty<I> first(){
		
		return get(0);
	}

	public StoredProperty<I> last(){
		
		return get(size()-1);
	}

	public StoredProperty<I> get(int index){
		
		// TODO
		return null;
	}

	public void addItem(I item) {
		
		// FIXME: this has to go through
		//        the backend
		//getValue().add(item);
		//getObserver().onItemAdded(item);
	}

	public void removeItem(I item) {
		
		// FIXME: this has to go through
		//        the backend
		//int index = getValue().indexOf(item);
		//getValue().remove(item);
		//getObserver().onItemRemoved(index, item);
	}
	
	public void setOnItemAddedListener(ItemAddedListener<I> itemAddedListener) {
		
	}

	public void setOnItemRemovedListener(ItemRemovedListener<I> itemRemovedListener) {
		
	}
}
