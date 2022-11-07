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
package atelier3_1_2;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier3_1_2 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier3_1_2.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}
	
	public Object fournirTableauEntiers(){
		Object tableau = creerTableauEntiers(new Integer[] {54,1,5,43,6});
		return tableau;
	}

	public Object fournirTableauChaines(){
		Object tableau = creerTableauChaines(new String[] {"154","45","4","5","5"});
		return tableau;
	}

	public Object fournirTableauVehicules(){
		Object tableau = creerTableauVehicules(new Vehicule[] {new Auto(14.3), new Auto(3.53), new Moto(6.121), new Moto(34.3), new Auto(542.0)});
		return tableau;
	}

	public abstract Object creerTableauEntiers(Integer[] entiersInitiaux);
	public abstract Object creerTableauChaines(String[] chainesInitiales);
	public abstract Object creerTableauVehicules(Vehicule[] vehiculesInitiaux);

	public abstract Planteur fournirPlanteur();

}
