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
package ca.ntro.core.models.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.StoreConnectable;
import ca.ntro.core.models.stores.ValuePath;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;


/**
 * Every user-defined value inside a model
 * must be a subclass of ModelValue
 * 
 * @author mbergeron
 *
 */
public abstract class ModelValue extends JsonObjectIO implements StoreConnectable {

	@Override
	public void connectToStore(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);

		connectSubValues(valuePath, modelStore);
	}

	private void connectSubValues(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);
		
		for(Method getter : Introspector.userDefinedGetters(this)) {
			
			String fieldName = Introspector.fieldNameForGetter(getter);
			
			Object fieldValue = null;
			
			try {

				fieldValue = getter.invoke(this);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				Log.fatalError("[ModelValue] Cannot invoke getter " + getter.getName(), e);
				
			}
			
			if(fieldValue instanceof StoreConnectable) {
				
				valuePath.addFieldName(fieldName);
				
				((StoreConnectable) fieldValue).connectToStore(valuePath, modelStore);
			}
		}
	}
}
