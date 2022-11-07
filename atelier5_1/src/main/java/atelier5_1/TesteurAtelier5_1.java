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
package atelier5_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.mappage.Cle;
import tutoriels.mappage.MapJava;
import tutoriels.mappage.TesteurDeMap;
import tutoriels.validateurs.ValidateurMapJava;

public class TesteurAtelier5_1 extends PerformanceTestsDriver {

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {

		TestParameters testsParameters = null;

		if(methodName.equals("fairePlusieursAjoutsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E3, (int) 1E4, 30, 10.0, true);

		} else if(methodName.equals("fairePlusieursModificationsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E3, (int) 1E4, 30, 2.0, true);

		} else if(methodName.equals("fairePlusieursRetraitsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*5, 30, 1.0, true);

		} else if(methodName.equals("accederAuClesDansOrdre")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*5, 30, 0.6, true);

		}

		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		List<Object> arguments = new ArrayList<>();

		if(methodName.equals("fairePlusieursAjoutsAleatoires")) {

			arguments.add(new HashMap<>());
			arguments.add(ValidateurMapJava.clesAleatoires(desiredInputSize));
			arguments.add(desiredInputSize);

		} else if(methodName.equals("fairePlusieursModificationsAleatoires") 
				|| methodName.equals("fairePlusieursRetraitsAleatoires")
				|| methodName.equals("accederAuClesDansOrdre") ) {
			
			String[] cles = ValidateurMapJava.clesAleatoires(desiredInputSize);

			arguments.add(ValidateurMapJava.mapAleatoire(cles));
			arguments.add(cles);
			arguments.add(desiredInputSize);
		}

		return arguments;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object> adaptArgumentsFor(Object providedObject, String methodName, List<Object> arguments) {
		
		List<Object> adaptedArguments;

		if(methodName.equals("fairePlusieursAjoutsAleatoires") 
				|| methodName.equals("fairePlusieursModificationsAleatoires") 
				|| methodName.equals("fairePlusieursRetraitsAleatoires") ) {
			
			adaptedArguments = new ArrayList<>();
			
			TesteurDeMap testeur = (TesteurDeMap) providedObject;
			
			MapJava<Cle<String>, Integer> mapAdapte = testeur.nouveauMap();
			Map<String, Integer> mapOriginal = (Map<String, Integer>) arguments.get(0);

			List<Cle<String>> cles = new ArrayList<>();
			
			for(Map.Entry<String, Integer> entry : mapOriginal.entrySet()) {
				Cle<String> cle = testeur.nouvelleCle(entry.getKey());

				mapAdapte.put(cle, entry.getValue());
				cles.add(cle);
			}
			
			adaptedArguments.add(mapAdapte);
			adaptedArguments.add(cles.toArray(new Cle[mapOriginal.size()]));
			adaptedArguments.add(arguments.get(2));

		} else if(methodName.equals("accederAuClesDansOrdre") ) {
			
			adaptedArguments = new ArrayList<>();
			
			TesteurDeMap testeur = (TesteurDeMap) providedObject;
			
			MapJava<Cle<String>, Integer> mapAdapte = testeur.nouveauMap();
			Map<String, Integer> mapOriginal = (Map<String, Integer>) arguments.get(0);
			
			for(Map.Entry<String, Integer> entry : mapOriginal.entrySet()) {
				Cle<String> cle = testeur.nouvelleCle(entry.getKey());
				mapAdapte.put(cle, entry.getValue());
			}

			adaptedArguments.add(mapAdapte);

		}else {
			
			adaptedArguments = arguments;
		}

		return adaptedArguments;
	}

	@Override
	public List<String> desiredMethodOrder() {
		return new ArrayList<String>();
	}
}
