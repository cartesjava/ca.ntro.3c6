package tutoriels.core.app;

import java.io.File;
import java.nio.file.Paths;

import ca.ntro.app.NtroClientFx;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.debug.T;
import ca.ntro.java.InitializerJava;

public class InitializerExercise extends InitializerJava {
	
	static {
		
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");

		InitializerJava.disableTracingIfInJar();

	}
	
	public void initialize(Class<? extends Atelier> exerciseClass) {

		CurrentExercise.setId(exerciseClass);
		
		super.initialize();
		
		removeExistingGraphs();
		
		NtroClientFx.initialize();
		
		
	}

	private void removeExistingGraphs() {
		File graphsDirectory = Paths.get("_storage","graphs").toFile();
		if(graphsDirectory.exists()) {
			for(String graphFilename: graphsDirectory.list()) {
				File graphFile = Paths.get("_storage", "graphs", graphFilename).toFile();
				graphFile.delete();
			}
		}
	}

	@Override
	protected ModelStoreSync provideLocalStore() {
		T.call(this);

		return new NitriteStoreExercise();
		//return new FilesStore();
	}



}
