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
package ca.ntro.core.system.file;


import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ResourceAsStringPromise;

public abstract class ResourceLoader {
	
	private static ResourceLoader instance;
	
	public static void initialize(ResourceLoader instance) {
		ResourceLoader.instance = instance;
	}

	public static Promise<String> getResourceAsString(String resourcePath) {
		
		Promise<Resource> resourcePromise = getResource(resourcePath);
		
		return new ResourceAsStringPromise(resourcePromise);
	}
	
	public static Promise<Resource> getResource(String resourcePath) {
		
		Promise<Resource> result = null;
		
		try {
			
			result = instance.getResourcesImpl(resourcePath);
			
		}catch(NullPointerException e){

			System.out.println("[FATAL]" + ResourceLoader.class.getSimpleName() + " must be initialized");
			
		}
		
		return result;
	}
	
	protected abstract Promise<Resource> getResourcesImpl(String resourcePath);

}
