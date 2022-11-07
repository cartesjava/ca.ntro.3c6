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
package atelier1_2_1;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier1_2_1 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier1_2_1.class);
	}

	@Override
	protected boolean siExecutable() {return false;}
	
	@Override
	protected void executer() {}
	
	public abstract Object fournirMoto();
	public abstract Object fournirMobilette();
	public abstract Object fournirAuto();
	public abstract Object fournirCamion();
	public abstract Object fournirFourgonnette();


}
