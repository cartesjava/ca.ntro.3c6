package atelier1_2_2;

import atelier1_2_2.vehicules.Vehicule;

public interface Accepteur {
	
	boolean accepterSiDeuxRoues(Vehicule vehicule);

	boolean accepterSiEconomique(Vehicule vehicule);

	boolean accepterSiMoto(Vehicule vehicule);

}
