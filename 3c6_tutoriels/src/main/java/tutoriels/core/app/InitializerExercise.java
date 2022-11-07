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
