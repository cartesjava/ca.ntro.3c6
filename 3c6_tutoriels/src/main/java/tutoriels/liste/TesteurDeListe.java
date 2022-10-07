package tutoriels.liste;

import tutoriels.liste.ListeJava;

public interface TesteurDeListe {
	
	ListeJava<Character> nouvelleListe();
	
	void fairePlusieursAjouts(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursInsertionsAuDebut(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursInsertionsAleatoires(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursModificationsAleatoires(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursRetraitsAleatoires(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursRetraitsAuDebut(ListeJava<Character> liste, int nombreOperations);

	void fairePlusieursRetraitsALaFin(ListeJava<Character> liste, int nombreOperations);

	ListeJava<Character> melangerLaListe(ListeJava<Character> liste);

	ListeJava<Character> melangerLaListeEfficace(ListeJava<Character> liste);

}
