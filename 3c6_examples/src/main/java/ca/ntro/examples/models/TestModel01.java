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
package ca.ntro.examples.models;


import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableString;
import ca.ntro.core.models.properties.observable.simple.ObservableValue;
import ca.ntro.core.models.properties.stored.simple.StoredString;
import ca.ntro.core.system.debug.T;
import ca.ntro.examples.values.TestValue01;

public class TestModel01 extends Model {

	private ObservableString observableString = new ObservableString();
	private ObservableValue<TestValue01> observableValue = new ObservableValue<TestValue01>();
	
	// XXX: StoredValues must NEVER be null
	// FIXME: TEST this by calling getters and crashing if one returns NULL
	private StoredString storedString = new StoredString();

	// FIXME: protected does not work for access from JsonObjectIO (superclass???)
	public ObservableString getObservableString() {
		T.call(this);
		return observableString;
	}

	public void setObservableString(ObservableString observableString) {
		T.call(this);
		this.observableString = observableString;
	}

	public ObservableValue<TestValue01> getObservableValue() {
		T.call(this);
		return observableValue;
	}

	public void setObservableValue(ObservableValue<TestValue01> observableValue) {
		T.call(this);
		this.observableValue = observableValue;
	}

	public StoredString getStoredString() {
		T.call(this);
		return storedString;
	}

	public void setStoredString(StoredString storedString) {
		T.call(this);
		this.storedString = storedString;
	}

	@Override
	public void initializeStoredValues() {
		T.call(this);
		
		storedString = new StoredString("test");
	}
}
