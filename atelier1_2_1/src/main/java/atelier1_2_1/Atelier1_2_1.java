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
