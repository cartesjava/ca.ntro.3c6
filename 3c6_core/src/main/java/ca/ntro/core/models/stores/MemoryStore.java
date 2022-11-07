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
package ca.ntro.core.models.stores;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.Model;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.debug.T;

public class MemoryStore extends ModelStoreSync {
	
	// XXX: we need ConcurrentHashMap as MemoryStore is accessed from different threads
	private Map<DocumentPath, JsonObject3c6> values = NtroCollections.concurrentHashMap(new HashMap<DocumentPath, JsonObject3c6>());
	
	private static MemoryStore instance = new MemoryStore();

	// XXX: must synchronize here as get can be called from multiple threads
	public synchronized static <M extends Model> M get(Class<M> modelClass, String modelId) {
		T.call(MemoryStore.class);

		M result = instance.getModel(modelClass, modelId);
		
		return result;
	}

	public synchronized static void clearStore() {
		T.call(MemoryStore.class);

		instance.values.clear();
	}

	@Override
	protected JsonObject3c6 getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		JsonObject3c6 result = values.get(documentPath);
		
		// XXX: creates a new JsonObject when null
		if(result == null) {
			
			result = JsonParser.jsonObject();

			values.put(documentPath, result);
		}

		return result;
	}

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <V extends Object> void setValue(ValuePath valuePath, V value) {
		T.call(this);
		
		JsonObject3c6 document = values.get(valuePath.extractDocumentPath());
		
		valuePath.updateObject(document, value);
	}

	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject3c6 jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		T.call(this);
	}


}
