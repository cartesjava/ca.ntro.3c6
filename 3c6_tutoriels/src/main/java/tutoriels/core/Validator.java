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
package tutoriels.core;



import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.debug.T;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.app.Exercise;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.test_cases.ProviderMethodsTestCases;

public class Validator {
	
	public static void validate(Exercise exercise) {
		T.call(Validator.class);
		
		String exerciseId = CurrentExercise.getId();

		// XXX: must ask for the ValidationReportViewModel. This is the actual Model
		// FIXME: this is not ideal, we should find a way to ask for the model and still get the viewmodel object
		ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, exerciseId);
		validationReport.setReportTitle(exerciseId);

		// XXX: close as soon as possible
		ProviderMethodsTestCases testCases = LocalStore.get(ProviderMethodsTestCases.class, exerciseId);
		LocalStore.close();
		
		testCases.validate(exercise, validationReport);
	
		/*

		// FIXME: should this be a list. We prefer to have objects in order
		Set<Object> testableObjects = TestCaseGenerator.testableObjects(exercise);
		

		for(Object testableObject : testableObjects) {
			
			if(testableObject != null) {

				testCases.validate(testableObject, validationReport);

			}else {

				ReportNodeViewModel nullReport = ReportViewModel.newSubReport();

				validationReport.addSubReport(nullReport);
				
				nullReport.setTitle("null");

				nullReport.setHtmlPage("Au moins une m??thode fournirXXX a retourn?? null");
				
				nullReport.setState(ValidationState.CRASH);
			}
			
		}

		// NOTE: in C++, we could generate the validation report in C++
		//       and still display it in Java
		 * 
		 * 
		 * 
		 */
	}


}
