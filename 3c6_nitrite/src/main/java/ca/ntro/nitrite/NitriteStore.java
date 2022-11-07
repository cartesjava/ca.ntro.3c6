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
package ca.ntro.nitrite;


import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import static org.dizitart.no2.filters.Filters.eq;

import org.dizitart.no2.Cursor;

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ExternalUpdateListener;
import ca.ntro.core.system.debug.T;

public abstract class NitriteStore extends ModelStoreSync {
	
	protected abstract String getFileName();
	private Nitrite db = Nitrite.builder().filePath(getFileName()).openOrCreate();
	
	public NitriteStore() {
		super();
	}

	@Override
	public void close() {
		T.call(this);

		db.close();
	}
	
	@Override
	public JsonObject3c6 getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		addIndex(models);
		
		Cursor cursor = models.find(eq(ModelStore.MODEL_ID_KEY, documentPath.getId()));
		Document document  = cursor.firstOrDefault();
		
		JsonObject3c6 jsonObject = null;
		
		if(document != null) {

			jsonObject = (JsonObject3c6) document.get(ModelStore.MODEL_DATA_KEY);
			
			//System.out.println(jsonObject.toString());

		}else {

			// XXX: create document if none exists
			jsonObject = JsonParser.jsonObject();

			document = new Document();
			document.put(ModelStore.MODEL_ID_KEY, documentPath.getId());
			document.put(ModelStore.MODEL_DATA_KEY, jsonObject);

			models.insert(document);
		}
		
		return jsonObject;
	}


	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject3c6 jsonObject) {
		T.call(this);

		NitriteCollection models = db.getCollection(documentPath.getCollection());
		
		Document document = new Document();
		
		document.put(ModelStore.MODEL_ID_KEY, documentPath.getId());
		document.put(ModelStore.MODEL_DATA_KEY, jsonObject);
		
		
		models.update(eq(ModelStore.MODEL_ID_KEY, documentPath.getId()), document);
		
		db.commit();
	}
	

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		T.call(this);
		
		// XXX: nothing to do. A Nitrite store is NEVER updated from outside (never opened by two processes)
	}
	
	private void addIndex(NitriteCollection models) {
		T.call(this);
		
		// FÌXME: adding index freezes when the index already exists
		
		/*
		
		try {

			models.createIndex(ModelStore.MODEL_ID_KEY, indexOptions(IndexType.Unique));

		}catch(IndexingException e) {}
		*/
	}
	

}
