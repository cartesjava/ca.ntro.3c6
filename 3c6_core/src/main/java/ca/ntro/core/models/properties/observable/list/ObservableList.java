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
package ca.ntro.core.models.properties.observable.list;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;

@SuppressWarnings("serial")
public abstract class ObservableList<I extends Object> extends ObservableProperty<List<I>> {
	
	private List<ListObserver<I>> listObservers = new ArrayList<>();
	
	public ObservableList(List<I> value) {
		T.call(this);

		setValue(NtroCollections.synchronizedList(value));
	}
	
	public int size() {
		T.call(this);
		
		return getValue().size();
	}

	public int indexOf(I item) {
		T.call(this);

		return getValue().indexOf(item);
	}

	public void insertItem(int index, I item) {
		T.call(this);
		
		getValue().add(index, item);
		
		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemAdded(index, item);
		}
	}

	public void updateItem(int index, I item) {
		
		getValue().set(index, item);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemUpdated(index, item);
		}
	}
	
	public void addItem(I item) {
		T.call(this);
		
		insertItem(getValue().size(), item);
	}

	public void removeItem(I item) {
		T.call(this);
		
		int index = getValue().lastIndexOf(item);
		
		getValue().remove(index);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemRemoved(index, item);
		}
	}
	
	public void removeObserver(ListObserver<I> listObserver) {
		T.call(this);
		
		boolean removed = this.listObservers.remove(listObserver);
		MustNot.beTrue(!removed);
	}

	public void removeAllObservers() {
		T.call(this);
		
		this.listObservers.clear();
	}

	public void observe(ListObserver<I> listObserver) {
		T.call(this);
		
		this.listObservers.add(listObserver);
		
		List<I> list = getValue();
		
		if(list != null) {
			
			synchronized (list) {
				for(int i = 0; i < list.size(); i++) {

					listObserver.onItemAdded(i, list.get(i));
				}
			}
		}

		//super.observe(listObserver);
	}
}
