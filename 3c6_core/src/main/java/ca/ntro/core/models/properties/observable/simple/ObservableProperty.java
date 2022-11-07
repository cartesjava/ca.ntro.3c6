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
package ca.ntro.core.models.properties.observable.simple;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;

public abstract class ObservableProperty<V extends Object> extends JsonObjectIO {
	
	private V value;

	private ValueListener<V> valueListener;
	private DeletionListener<V> deletionListener;
	private List<ValueObserver<V>> observers = new ArrayList<>();

	public ObservableProperty() {
		T.call(this);
	}
	
	protected abstract Class<?> getValueType();

	@SuppressWarnings("unchecked")
	@Override
	public void loadFromJsonObject(JsonObject3c6 jsonObject) {
		
		Object jsonValue = jsonObject.get("value");
		
		Object value = Introspector.buildValueForType(getValueType(), jsonValue);
		
		this.value = (V) value;
	}

	@Override
	public JsonObject3c6 toJsonObject() {
		T.call(this);
		
		JsonObject3c6 result = JsonParser.jsonObject();
		
		result.setTypeName(this.getClass().getName());
		
		Object jsonValue = value;
		
		if(getValueType().equals(ModelValue.class)) {
			jsonValue = ((ModelValue) value).toJsonObject().toMap();
		}
		
		result.put("value", jsonValue);

		return result;
	}
	
	public ObservableProperty(V value) {
		T.call(this);
		
		this.value = value;
	}
	
	public V getValue() {
		T.call(this);
		
		return value;
	}

	protected void setValue(V value) {
		T.call(this);
		
		this.value = value;
	}
	
	
	public void get(ValueListener<V> valueListener) {
		T.call(this);
		
		valueListener.onValue(value);
	}

	public void onDeleted(DeletionListener<V> deletionListener) {
		// TODO
	}

	public void set(V newValue) {
		T.call(this);

		V oldValue = value;
		value = newValue;
		
		for(ValueObserver<V> observer : observers) {
			observer.onValueChanged(oldValue, newValue);
		}
	}
	
	public void observe(ValueObserver<V> observer) {
		T.call(this);

		this.observers.add(observer);
		observer.onValue(value);
	}

}
