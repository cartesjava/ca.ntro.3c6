package atelier3_1_2;

public class Auto extends Vehicule {

	public Auto(double totalKilometres) {
		super(totalKilometres);
	}

	@Override
	protected String nomVehicule() {
		return "auto";
	}

}
