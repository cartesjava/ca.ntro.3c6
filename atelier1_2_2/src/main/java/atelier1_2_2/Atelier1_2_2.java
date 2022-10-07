package atelier1_2_2;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;

public abstract class Atelier1_2_2 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier1_2_2.class);

	}

	@Override
	protected boolean siExecutable() {return false;}

	@Override
	protected void executer() {}
	
	public abstract Accepteur fournirAccepteur();
	public abstract Formateur fournirFormateur();


}
