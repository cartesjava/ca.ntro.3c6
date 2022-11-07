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

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.system.debug.T;

public class ValuePath {
	
	private String collection;
	private String documentId;
	private List<String> fieldPath = new ArrayList<>();
	
	private ValuePath(String collection) {
		this.collection = collection;
		
	}
	
	public static ValuePath collection(String collection) {
		T.call(ValuePath.class);
		return new ValuePath(collection);
	}
	
	public ValuePath documentId(String documentId) {
		T.call(this);
		this.documentId = documentId;
		return this;
	}
	
	public ValuePath field(String... fieldPath) {
		T.call(this);
		
		for(String fieldName : fieldPath) {
			this.fieldPath.add(fieldName);
		}

		return this;
	}
	
	public DocumentPath extractDocumentPath() {
		T.call(this);
		return new DocumentPath(collection, documentId);
	}

	public void addFieldName(String fieldName) {
		T.call(this);
		
		this.fieldPath.add(fieldName);
	}

	public Object updateObject(JsonObject3c6 storedObject, Object value) {
		T.call(this);
		
		return storedObject.updateValue(fieldPath, value);
	}

	public Object getValueFrom(JsonObject3c6 storedObject) {
		T.call(this);

		return storedObject.getValue(fieldPath);
	}

}
