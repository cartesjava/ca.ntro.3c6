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
package tutoriels.core.app;

import java.io.File;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.Validator;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.core.models.test_cases.ProviderMethodsTestCases;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.swing.PerformanceApp;
import tutoriels.core.swing.ValidationApp;

public abstract class Atelier extends Exercise {
	
	private final String PREFIX = "fournir";
	private final String SUFFIX_PATTERN = "[A-Z][a-z]*$";
	private final Pattern suffixPattern = Pattern.compile(SUFFIX_PATTERN);
	
	@Override
	public boolean isAProviderMethod(Method method) {
		T.call(this);
		
		return method.getName().startsWith(PREFIX);
	}

	@Override
	public String providerMethodSuffix(Method method) {

		String methodName = method.getName();

		Matcher matcher = suffixPattern.matcher(methodName);
		matcher.find();

		String suffix = "";

		try {

			suffix = matcher.group(0);

		}catch(IllegalStateException e) {
			
			Log.fatalError("No match found in " + method.getName(), e);
		}

		
		return suffix;
	}

	@Override
	public String providerMethodPrefix(Method method) {

		String methodName = method.getName();
		methodName = methodName.replace(PREFIX, "");
		
		String suffix = providerMethodSuffix(method);
		
		methodName = methodName.replace(suffix, "");
		
		return methodName;
	}
	
	protected abstract void executer();
	protected abstract boolean siExecutable();

	public void valider() {
		T.call(this);
		
		checkIfDatabaseExists();

		launchValidationApp();
		performValidation();

		//PerformanceTestsDriver driver = createPerformanceTestsDriver();
		//testPerformance(driver);
		
	}
	
	protected void performValidation() {
		T.call(this);

		Validator.validate(this);
	}

	private void launchValidationApp() {
		T.call(this);

		new ValidationApp(new MainWindowClosedListener() {

			@Override
			public void mainWindowClosed() {
				T.call(this);
				
				ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
				validationReport.getRootReportNode().getState().get(new ValueListener<ValidationState>() {

					@Override
					public void onValue(ValidationState value) {
						T.call(this);
						reagirEtatValidation(value);
					}
				});
			}
		});
	}

	protected void checkIfDatabaseExists() {
		T.call(this);

		// XXX: close as soon as possible
		ProviderMethodsTestCases testCases = LocalStore.get(ProviderMethodsTestCases.class, CurrentExercise.getId());
		LocalStore.close();
		
		if(testCases.isEmpty()) {
			T.setActive(false);
			
			String dbFileName = CurrentExercise.getId() + ".db";

			// XXX: delete file in case it was created
			File dbFile = new File(dbFileName);
			dbFile.delete();
			
			System.err.println("Base de donn??es manquante ou corrompue.\n\n >  SVP re-t??l??charger " + dbFileName + " et\n >  placer le fichier ?? la racine du projet" );
			
			AppCloser.close();
			System.exit(0);
		}
	}
	
	private void reagirEtatValidation(ValidationState etat) {
		T.call(this);
		
		if(etat == ValidationState.OK /*&& siExecutable()*/) {
			//System.out.println("[La validation est r??ussie. L'application va maintenant s'ex??cuter.]\n");

			PerformanceTestsDriver driver = createPerformanceTestsDriver();
			
			if(driver != null) {
				
				System.out.println("[La validation est r??ussie. Les tests de performance vont maintenant s'ex??cuter]");
				testPerformance(driver);
				
			}else {

				System.out.println("[La validation est r??ussie]");
			}
			
			
			//executer();
		/*
		}else if(siExecutable()) {
			System.err.println("La validation est ??chou??e. L'application ne va pas s'ex??cuter. D??sol??.");
		}*/
		}else{
			System.err.println("[La validation est ??chou??e. Les tests de performance ne seront pas ex??cut??s]");
			AppCloser.close();
		}
		
	}
	
	private void testPerformance(PerformanceTestsDriver driver) {
		T.call(this);

		cleanUpAsMuchAsPossible();

		launchPerformanceApp();
		

		driver.testPerformance(this);
	}
	
	private void cleanUpAsMuchAsPossible() {
		T.call(this);

		MemoryStore.clearStore();
		System.gc();
	}

	private void launchPerformanceApp() {
		T.call(this);

		new PerformanceApp(new MainWindowClosedListener() {
			@Override
			public void mainWindowClosed() {
				T.call(this);
				AppCloser.close();
			}
		});
	}
	
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return null;
	}
}
