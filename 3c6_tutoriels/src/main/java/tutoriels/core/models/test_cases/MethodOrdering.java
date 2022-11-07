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
package tutoriels.core.models.test_cases;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodOrdering {
	
	public static List<String> desiredMethodOrder = new ArrayList<>();

	public static List<Method> reorderMethods(List<Method> methods) {
		List<Method> orderedMethods = new ArrayList<>();
		
		for(String methodName : desiredMethodOrder) {
			
			Method toAdd = null;
			
			for(Method candidate : methods) {
				if(candidate.getName().equals(methodName)) {
					
					toAdd = candidate;
					break;
				}
			}
			
			if(toAdd != null) {
				methods.remove(toAdd);
				orderedMethods.add(toAdd);
			}
		}

		orderedMethods.addAll(methods);
		
		return orderedMethods;
	}

}
