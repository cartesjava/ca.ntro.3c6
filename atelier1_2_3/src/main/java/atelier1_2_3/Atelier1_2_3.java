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
