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
package atelier3_3_2;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.performance_app.PerformanceTestsDriver;

public abstract class Atelier3_3_2 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier3_3_2.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}

	@Override
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return new TesteurAtelier3_3_2();
	}
	
	public abstract <C extends Comparable<C>> Trieur<C> fournirTrieurNaif();

	public abstract <C extends Comparable<C>> Trieur<C> fournirTrieurJdk();

	public abstract <C extends Comparable<C>> Trieur<C> fournirTrieurEfficace();

}
