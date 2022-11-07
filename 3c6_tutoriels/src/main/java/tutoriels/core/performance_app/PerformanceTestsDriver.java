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
package tutoriels.core.performance_app;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.debug.T;
import tutoriels.core.TestCaseGenerator;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.app.Exercise;
import tutoriels.core.performance_app.models.PerformanceTestsViewModel;

public abstract class PerformanceTestsDriver {

	public abstract TestParameters getTestParametersFor(String providerMethodPrefix, String methodName);

	public abstract List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize);

	public abstract List<Object> adaptArgumentsFor(Object providedObject, String methodName, List<Object> arguments);
	
	public <P extends PerformanceTestsDriver, E extends Exercise> void testPerformance(Exercise exercise) {
		T.call(TestCaseGenerator.class);

		PerformanceTestsViewModel performanceTests = MemoryStore.get(PerformanceTestsViewModel.class, CurrentExercise.getId());

		for(Map.Entry<String, List<Method>> entry : providerMethodsByPrefix(exercise).entrySet()) {
			
			String providerMethodPrefix = entry.getKey();
			List<Method> providerMethods = entry.getValue();
			
			performanceTests.testPerformance(providerMethodPrefix, providerMethods, exercise, this);
		}

	}
	
	static Map<String, List<Method>> providerMethodsByPrefix(Exercise exercise){
		
		Map<String, List<Method>> providerMethods = new HashMap<>();
		
		for(Method method : TestCaseGenerator.providerMethods(exercise)) {
			
			String prefix = exercise.providerMethodPrefix(method);
			
			List<Method> methods = providerMethods.get(prefix);
			if(methods == null) {
				methods = new ArrayList<>();
				providerMethods.put(prefix, methods);
			}
			
			methods.add(method);
		}
		
		return providerMethods;
	}

	public abstract List<String> desiredMethodOrder();

}
