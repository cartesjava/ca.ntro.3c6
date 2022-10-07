package atelier1_2_4;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier1_2_4 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier1_2_4.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}
	
	public abstract Object fournirMoto();
	public abstract Object fournirMobilette();
	public abstract Object fournirAuto();
	public abstract Object fournirCamion();

}
