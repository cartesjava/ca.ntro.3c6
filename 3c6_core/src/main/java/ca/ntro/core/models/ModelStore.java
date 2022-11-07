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
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ValuePath;

public abstract class ModelStore {
	
	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);

	protected abstract void saveJsonObject(DocumentPath documentPath, JsonObject3c6 jsonObject);


}
