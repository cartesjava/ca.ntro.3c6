package atelier4_1;

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
import tutoriels.liste.ValidateurListeJava;

public abstract class Atelier4_1 extends Atelier {

    static {

        new InitializerExercise().initialize(Atelier4_1.class);
        
    }

    @Override
    protected void performValidation() {
        T.call(this);
        
        String exerciseId = CurrentExercise.getId();

        ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
        validationReport.setReportTitle(exerciseId);
        validationReport.setExpectedNumberOfSubReports(3);
        
        addListSubReport(validationReport, "ListeChaineeSimple", "fournirListeChaineeSimple");
        addListSubReport(validationReport, "ListeChaineeDouble", "fournirListeChaineeDouble");
        addListSubReport(validationReport, "ListeArray", "fournirListeArray");
    }

    private void addListSubReport(ReportViewModel validationReport, String listName, String providerName) {
        ReportNodeViewModel subReport = ReportViewModel.newSubReport();
        validationReport.addSubReport(subReport);
        subReport.setTitle(listName);
        Method creerListeVide = Introspector.findMethodByName(this.getClass(), providerName);
        ValidateurListeJava.valider(subReport, this, creerListeVide);
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
        return new TesteurAtelier4_1();
    }

    public abstract TesteurDeListe fournirTesteurDeListeSimple();
    public abstract TesteurDeListe fournirTesteurDeListeDouble();
    public abstract TesteurDeListe fournirTesteurDeListeArray();
    public abstract ListeJava<Character> fournirListeChaineeSimple();
    public abstract ListeJava<Character> fournirListeChaineeDouble();
    public abstract ListeJava<Character> fournirListeArray();

}
