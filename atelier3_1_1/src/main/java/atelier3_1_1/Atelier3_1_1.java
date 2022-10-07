package atelier3_1_1;

import ca.ntro.core.system.debug.T;
import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier3_1_1 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier3_1_1.class);
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
