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

import ca.ntro.core.models.Model;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class LocalStore {
	
	private static ModelStoreSync instance;
	
	public static void initialize(ModelStoreSync instance) {
		LocalStore.instance = instance;
	}

	public static <M extends Model> M get(Class<M> modelClass, String modelId) {
		
		M result = null;
		
		try {
			
			result = (M) instance.getModel(modelClass, modelId);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	public static void close() {
		T.call(LocalStore.class);

		try {
			
			instance.close();
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
	}

}
