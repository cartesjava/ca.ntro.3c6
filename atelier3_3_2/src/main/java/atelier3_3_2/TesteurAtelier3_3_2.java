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
package atelier3_3_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;

public class TesteurAtelier3_3_2 extends PerformanceTestsDriver {

	private static Random random = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		
		TestParameters testsParameters = null;

		if(methodName.equals("trier")) {
			
			testsParameters = new TestParameters((int) 1E3, (int) 1E4 * 3, 30, 15.0, true);
		}

		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		List<Object> arguments = new ArrayList<>();
		
		if(methodName.equals("trier")) {

			Tableau<Integer> tableau = randomIntegerArray(desiredInputSize);
			arguments.add(tableau);

		}

		return arguments;
	}

	private Tableau<Integer> randomIntegerArray(int size){
		Integer[] result = new Integer[size];
		
		for(int i = 0; i < size; i++) {
			result[i] = random.nextInt(size);
		}
		
		return new MonTableau<>(result);
	}

	@Override
	public List<Object> adaptArgumentsFor(Object providedObject, String methodName, List<Object> arguments) {
		return arguments;
	}

	@Override
	public List<String> desiredMethodOrder() {
		return new ArrayList<>();
	}

}
