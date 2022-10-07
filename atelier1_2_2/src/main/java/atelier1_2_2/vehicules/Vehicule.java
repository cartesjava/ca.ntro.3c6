package atelier1_2_2.vehicules;

import ca.ntro.core.models.properties.ModelValue;

public class Vehicule extends ModelValue {
	
	public double consomationLitresParKilometre() {
		return 0.0;
	}
	
	public double litresEssenceConsomes(double kilometres) {
		return kilometres * consomationLitresParKilometre();
	}

	public int nombreDeRoues() {
		return 0;
	}

}
