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
package atelier4_1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.liste.ListeJava;
import tutoriels.liste.TesteurDeListe;

public class TesteurAtelier4_1 extends PerformanceTestsDriver {
		
	private static final Random alea = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		if(!providerMethodPrefix.equals("TesteurDeListe")) return null;

		TestParameters testsParameters = null;

		if(methodName.equals("fairePlusieursAjouts")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);

		} else if(methodName.equals("fairePlusieursInsertionsAuDebut")) {

			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);

		} else if(methodName.equals("fairePlusieursInsertionsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.00, true);

		} else if(methodName.equals("fairePlusieursModificationsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.00, false);

		} else if(methodName.equals("fairePlusieursRetraitsAleatoires")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);

		} else if(methodName.equals("fairePlusieursRetraitsAuDebut")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);

		} else if(methodName.equals("fairePlusieursRetraitsALaFin")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);

		} else if(methodName.equals("melangerLaListe") || methodName.equals("melangerLaListeEfficace")) {
			
			testsParameters = new TestParameters((int) 1E4, (int) 1E4*3, 30, 10.0, true);
			//testsParameters = new TestParameters((int) 1E4, (int) 1E5, 30, 15.0, true);
		}

		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		
		List<Object> arguments = new ArrayList<>();

		if(!providerMethodPrefix.equals("TesteurDeListe")) return arguments;

		if(methodName.equals("fairePlusieursAjouts")
				|| methodName.equals("fairePlusieursInsertionsAuDebut")
				|| methodName.equals("fairePlusieursInsertionsAleatoires")) {
			arguments.add(new ArrayList<>());
			arguments.add(desiredInputSize);

		} else if(methodName.equals("fairePlusieursModificationsAleatoires") 
				|| methodName.equals("fairePlusieursRetraitsAleatoires")
				|| methodName.equals("fairePlusieursRetraitsALaFin")
				|| methodName.equals("fairePlusieursRetraitsAuDebut")) {
			arguments.add(listeAleatoire(desiredInputSize));
			arguments.add(desiredInputSize);
		}else {
			arguments.add(listeAleatoire(desiredInputSize));
		}

		return arguments;
	}
	
	private List<Character> listeAleatoire(int taille){
		List<Character> liste = new LinkedList<>();
		
		for(int i = 0; i < taille; i++) {
			liste.add((char) (97 + alea.nextInt(26)));
		}
		
		return liste;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> adaptArgumentsFor(Object providedObject, String methodName, List<Object> arguments) {
		
		List<Object> adaptedArguments;

		if(methodName.equals("fairePlusieursAjouts") 
				|| methodName.equals("fairePlusieursInsertionsAleatoires") 
				|| methodName.equals("fairePlusieursInsertionsAuDebut") 
				|| methodName.equals("fairePlusieursModificationsAleatoires") 
				|| methodName.equals("fairePlusieursRetraitsAuDebut") 
				|| methodName.equals("fairePlusieursRetraitsALaFin") 
				|| methodName.equals("fairePlusieursRetraitsAleatoires")) {
			
			adaptedArguments = new ArrayList<>();
			
			TesteurDeListe testeur = (TesteurDeListe) providedObject;
			
			ListeJava<Character> listeAdaptee = testeur.nouvelleListe();
			List<Character> listeOriginale = (List<Character>) arguments.get(0);
			
			for(Character i : listeOriginale) {
				listeAdaptee.add(i);
			}
			
			adaptedArguments.add(listeAdaptee);
			adaptedArguments.add(arguments.get(1));

		}else if(methodName.equals("melangerLaListe") || methodName.contentEquals("melangerLaListeEfficace")) {

			adaptedArguments = new ArrayList<>();
			
			TesteurDeListe testeur = (TesteurDeListe) providedObject;
			
			ListeJava<Character> listeAdaptee = testeur.nouvelleListe();
			List<Character> listeOriginale = (List<Character>) arguments.get(0);
			
			for(Character i : listeOriginale) {
				listeAdaptee.add(i);
			}
			
			adaptedArguments.add(listeAdaptee);

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
