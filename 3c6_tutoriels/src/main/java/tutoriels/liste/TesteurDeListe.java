/*
Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)

This file is part of Ntro, an application framework designed with teaching in mind.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
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
