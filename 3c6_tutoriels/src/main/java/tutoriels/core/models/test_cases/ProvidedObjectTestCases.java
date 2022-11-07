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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.TestCaseGenerator;

public class ProvidedObjectTestCases extends ObjectTestCases {
	private static final long serialVersionUID = 4069522587121288447L;

	void generateTestCases(String providerMethodName, Object providedObject, TestCaseGenerator testCaseGenerator) {
		T.call(this);
		
		generateTestCases(providedObject);

		generateMethodTestCases(providerMethodName, providedObject, testCaseGenerator);
	}

	private void generateMethodTestCases(String providerMethodName, Object providedObject, TestCaseGenerator testCaseGenerator) {
		T.call(this);

		Set<String> processedMethodNames = new HashSet<>();
		
		List<Method> methodsToTest = Introspector.userDefinedMethodsFromObject(providedObject);
		
		methodsToTest = MethodOrdering.reorderMethods(methodsToTest);

		for(Method methodToTest : methodsToTest) {

			String methodName = methodToTest.getName();
			
			if(!processedMethodNames.contains(methodName)) {
				
				processedMethodNames.add(methodName);

				generateMethodTestCases(providerMethodName, providedObject, testCaseGenerator, methodToTest, methodName);
			}
		}
	}

	private void generateMethodTestCases(String providerMethodName, Object providedObject, TestCaseGenerator testCaseGenerator,
			Method methodToTest, String methodName) {
		T.call(this);
		
		if(!isMethodTestableHere(methodToTest)) {
			return;
		}

		MethodTestCases methodTestCases = new MethodTestCases(methodName);
		getTestCasesByMethod().put(Introspector.methodSignature(methodToTest).toString(), methodTestCases);
		
		List<List<Object>> listOfInputArguments = testCaseGenerator.generateMultipleInputArguments(providerMethodName, providedObject, methodToTest);
		
		for(List<Object> arguments : listOfInputArguments) {
			
			Object returnValue = null;
			
			try {
				
				if(providedObject instanceof CloneableObject) {
					providedObject = ((CloneableObject) providedObject).cloneObject();
				}

				returnValue = methodToTest.invoke(providedObject, MethodTestCase.argumentArray(methodToTest, arguments));

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				Log.fatalError("Cannot invoke methodToTest " + methodToTest, e);
			}
			
			methodTestCases.add(new MethodTestCase(methodName, arguments, returnValue));
		}
	}

	private boolean isMethodTestableHere(Method methodToTest) {
		T.call(this);
		
		boolean isMethodTestable = true;
		
		isMethodTestable = isMethodTestable && Modifier.isPublic(methodToTest.getModifiers());
		
		isMethodTestable = isMethodTestable && !methodToTest.getReturnType().equals(void.class);
		
		return isMethodTestable;
	}
}
