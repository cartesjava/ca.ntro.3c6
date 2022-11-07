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
package atelier3_3_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;

public class TesteurAtelier3_3_3 extends PerformanceTestsDriver {

	private static Random random = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		
		TestParameters testsParameters = null;

		if(methodName.equals("calculerReponse")) {
			
			//testsParameters = new TestParameters((int) 1E3, (int) 1E4, 30, 35.0, true);
			testsParameters = new TestParameters((int) 1E5, (int) 1E7, 30, 0.5, true);
			//testsParameters = new TestParameters((int) 1E7, (int) 1E8, 30, 2.0, true);
		}

		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		List<Object> arguments = new ArrayList<>();
		
		if(methodName.equals("calculerReponse")) {

			arguments.add(desiredInputSize);

		}

		return arguments;
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
