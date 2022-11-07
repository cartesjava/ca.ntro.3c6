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

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.system.debug.T;

public abstract class ModelStoreAsync extends ModelStore {

	public <M extends Model> Promise<M> getModel(Class<M> modelClass, String modelId){
		T.call(this);
		
		Promise<M> modelPromise = null;
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), modelId);
		
		Promise<JsonObject3c6> promiseJsonObject = getJsonObject(documentPath);
		
		modelPromise = new ModelPromise<M>(modelClass, modelId, this, promiseJsonObject);
		
		return modelPromise;
	}

	protected abstract Promise<JsonObject3c6> getJsonObject(DocumentPath documentPath);

	protected abstract void saveJsonObject(JsonObject3c6 jsonObject, DocumentPath documentPath);
	


}
