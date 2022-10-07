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
