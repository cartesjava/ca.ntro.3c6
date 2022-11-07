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
package atelier1_2_3;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier1_2_3 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier1_2_3.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}
	

	public  Rouleur fournirMoto() {
		Rouleur moto = creerMoto();
		moto.rouler(10.0);
		
		return moto;
	}

	public  Rouleur fournirMobilette() {
		Rouleur mobilette = creerMobilette();
		mobilette.rouler(10.0);
		
		return mobilette;
	}

	public  Rouleur fournirAuto() {
		Rouleur auto = creerAuto();
		auto.rouler(10.0);
		
		return auto;
	}

	public  Rouleur fournirCamion() {
		Rouleur camion = creerCamion();
		camion.rouler(10.0);
		
		return camion;
	}
	
	public abstract Rouleur creerMoto();
	public abstract Rouleur creerMobilette();
	public abstract Rouleur creerAuto();
	public abstract Rouleur creerCamion();

}
