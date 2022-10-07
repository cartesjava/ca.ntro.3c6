package atelier3_1_1;

public class Moto extends Vehicule {

	public Moto(double totalKilometres) {
		super(totalKilometres);
	}

	@Override
	protected String nomVehicule() {
		return "moto";
	}

}
