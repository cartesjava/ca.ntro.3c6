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
package atelier5_3_2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.app.Atelier;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.mappage.TesteurDeMap;
import tutoriels.validateurs.ValidateurMapJava;

public abstract class Atelier5_3_2 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier5_3_2.class);
	}

	@Override
	protected void performValidation() {
		T.call(this);
		
		String exerciseId = CurrentExercise.getId();

		ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
		validationReport.setReportTitle(exerciseId);

		validationReport.setExpectedNumberOfSubReports(3);

		addMapSubReport(validationReport, "MapHash", "fournirTesteurDeMapHash", false);
		addMapSubReport(validationReport, "MapArbre", "fournirTesteurDeMapArbre", true);
	}

	private Object providedObjetForProviderMethodByName(String providerMethodName) {

		Object providedObjet = null;
		
		Method providerMethod = Introspector.findMethodByName(this.getClass(), providerMethodName);
		
		try {

			providedObjet =  providerMethod.invoke(this);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			Log.fatalError("Impossible d'appeler " + providerMethodName, e);
		}
		
		return providedObjet;
	}

	private void addMapSubReport(ReportViewModel validationReport, String mapName, String providerName, boolean ecrireGraphes) {
		ReportNodeViewModel subReport = ReportViewModel.newSubReport();
		validationReport.addSubReport(subReport);

		subReport.setTitle(mapName);

		TesteurDeMap testeur = (TesteurDeMap) providedObjetForProviderMethodByName(providerName);

		int tailleCleMin = 1;
		int tailleCleMax = 5;
		ValidateurMapJava.valider(subReport, mapName, testeur, ecrireGraphes, tailleCleMin, tailleCleMax);
	}

	protected void checkIfDatabaseExists() {
		T.call(this);
		// XXX: nothing, we do not want a database
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}

	@Override
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return new TesteurAtelier5_3_2();
	}

	public abstract TesteurDeMap fournirTesteurDeMapHash();
	public abstract TesteurDeMap fournirTesteurDeMapArbre();

}
