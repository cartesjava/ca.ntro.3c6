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
package ca.ntro.core.models;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ValuePath;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

/**
 * 
 * The properties of a Model must be:
 * - Java values: String, Double, List<String>, etc.
 * - User-defined classes that extend Value
 * 
 * @author mbergeron
 *
 */
public abstract class Model extends JsonObjectIO {
	
	
	private String id;
	private ModelStore modelStore;
	
	public void save() {
		T.call(this);
		
		modelStore.saveJsonObject(documentPath(), toJsonObject());
	}

	public void updateOnceFrom(ModelStoreAsync store, String modelId, UpdateListener listener) {
		
		
	}

	public void synchronizeWith(ModelStore store, String modelId) {
		// XXX: only synchronizes Synchronized properties/map/list
		
		
	}
	
	
	private DocumentPath documentPath() {
		return new DocumentPath(getClass().getSimpleName(), id);
	}
	
	// XXX: cannot be protected as it should
	//      not be used by subclasses
	void setId(String id) {
		T.call(this);
		
		this.id = id;
	}

	void setOrigin(ModelStore origin) {
		T.call(this);
		
		this.modelStore = origin;
	}
	
	public abstract void initializeStoredValues();

	public void connectStoredValues() {
		T.call(this);
		
		// FIXME: this inserts the wrong value in a StoredString
		//        (it inserts a StoredString instead of a String)
		
		for(Method getter : Introspector.userDefinedGetters(this)) {
			
			String fieldName = Introspector.fieldNameForGetter(getter);
			
			Object fieldValue = null;
			
			try {

				fieldValue = getter.invoke(this);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				Log.fatalError("[Model] Cannot invoke getter " + getter.getName(), e);
				
			}
			
			if(fieldValue instanceof StoreConnectable) {
				
				ValuePath valuePath = ValuePath.collection(this.getClass().getSimpleName()).documentId(id).field(fieldName);
				
				((StoreConnectable) fieldValue).connectToStore(valuePath, modelStore);
			}
		}
	}

	

}
