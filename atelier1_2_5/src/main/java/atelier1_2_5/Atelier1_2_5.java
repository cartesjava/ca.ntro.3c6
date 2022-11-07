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
package atelier1_2_5;

import ca.ntro.core.system.debug.T;
import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier1_2_5 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier1_2_5.class);
	}

	@Override
	protected boolean siExecutable() {
		T.call(this);
		return false;
	}

	@Override
	protected void executer() {
		T.call(this);
	}
	
	public Object fournirMoto() {
		return creerMoto(142.0);
	}

	public Object fournirMobilette() {
		return creerMobilette(14.2);
	}

	public Object fournirAuto() {
		return creerAuto(15.3);
	}

	public Object fournirCamion() {
		return creerCamion(124.0, 1500.2);
	}

	public abstract Object creerMoto(double kilometrage);
	public abstract Object creerMobilette(double kilometrage);
	public abstract Object creerAuto(double kilometrage);
	public abstract Object creerCamion(double kilometrage, double chargementEnKilos);

}
