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

import ca.ntro.core.Factory;
import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;
import ca.ntro.core.system.debug.T;

public class ModelPromise<M extends Model> implements Promise<M>{
	
	private Class<M> modelClass;
	private Promise<JsonObject3c6> jsonObjectPromise;
	private String modelId;
	private ModelStore store;
	
	public ModelPromise(Class<M> modelClass, String modelId, ModelStore store, Promise<JsonObject3c6> jsonObjectPromise) {
		T.call(this);
		this.modelClass = modelClass;
		this.modelId = modelId;
		this.jsonObjectPromise = jsonObjectPromise;
		this.store = store;
	}

	@Override
	public void onValueOrError(ValueListener<M> valueListener, ErrorListener errorListener) {
		T.call(this);
		
		jsonObjectPromise.onValueOrError(new ValueListener<JsonObject3c6>() {

			@Override
			public void onValue(JsonObject3c6 jsonObject) {
				T.call(this);
				
				M model = Factory.newInstance(modelClass);
				
				model.setId(modelId);
				
				model.setOrigin(store);
				
				if(!jsonObject.isEmpty()) {
					
					model.loadFromJsonObject(jsonObject);
					
				}

				model.connectStoredValues();
				
				valueListener.onValue(model);
			}
		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
				errorListener.onError();
				
			}
		});
		
	}

}
