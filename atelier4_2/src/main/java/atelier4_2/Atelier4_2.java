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
package atelier4_2;

import java.lang.reflect.Method;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.debug.T;
import tutoriels.core.app.Atelier;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.liste.ListeJava;
import tutoriels.liste.TesteurDeListe;
import tutoriels.validateurs.ValidateurListeJava;

public abstract class Atelier4_2 extends Atelier {

    static {

        new InitializerExercise().initialize(Atelier4_2.class);
        
    }

    @Override
    protected void performValidation() {
        T.call(this);
        
        String exerciseId = CurrentExercise.getId();

        ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
        validationReport.setReportTitle(exerciseId);
        validationReport.setExpectedNumberOfSubReports(3);
        
        addListSubReport(validationReport, "ListeTableau", "fournirListeTableau", false);
        addListSubReport(validationReport, "ListeChaineeSimple", "fournirListeChaineeSimple", true);
    }

    private void addListSubReport(ReportViewModel validationReport, String listName, String providerName, boolean ecrireGraphes) {
        ReportNodeViewModel subReport = ReportViewModel.newSubReport();
        validationReport.addSubReport(subReport);
        subReport.setTitle(listName);
        Method creerListeVide = Introspector.findMethodByName(this.getClass(), providerName);
        ValidateurListeJava.valider(subReport, this, creerListeVide, ecrireGraphes);
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
        return new TesteurAtelier4_2();
    }

    public abstract TesteurDeListe fournirTesteurDeListeSimple();
    public abstract TesteurDeListe fournirTesteurDeListeTableau();
    public abstract ListeJava<Character> fournirListeChaineeSimple();
    public abstract ListeJava<Character> fournirListeTableau();

}
