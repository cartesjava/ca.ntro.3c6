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

import java.util.Random;

import tutoriels.liste.ListeJava;

public abstract class TesteurDeListeAbstrait implements TesteurDeListe {
	
	private static Random alea = new Random();

	@Override
	public void fairePlusieursAjouts(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.add('a');
		}
	}

	@Override
	public void fairePlusieursInsertionsAuDebut(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.insert(0, 'a');
		}
	}

	@Override
	public void fairePlusieursInsertionsAleatoires(ListeJava<Character> liste, int nombreOperations) {
		liste.insert(0, 'a');

		for(int i = 1; i < nombreOperations; i++) {
			liste.insert(alea.nextInt(liste.size()), 'a');
		}
	}

	
	@Override
	public void fairePlusieursModificationsAleatoires(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.set(alea.nextInt(liste.size()), 'a');
		}
	}

	@Override
	public void fairePlusieursRetraitsAleatoires(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.remove(alea.nextInt(liste.size()));
		}
	}

	@Override
	public void fairePlusieursRetraitsAuDebut(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.remove(0);
		}
	}

	@Override
	public void fairePlusieursRetraitsALaFin(ListeJava<Character> liste, int nombreOperations) {
		for(int i = 0; i < nombreOperations; i++) {
			liste.remove(liste.size()-1);
		}
	}

	@Override
	public ListeJava<Character> melangerLaListe(ListeJava<Character> liste) {
		ListeJava<Character> resultat = nouvelleListe();
		
		while(!liste.isEmpty()) {
			
			int positionAuHasard = alea.nextInt(liste.size());
			
			Character elementAuHasard = liste.get(positionAuHasard);
			
			resultat.add(elementAuHasard);
			
			liste.remove(positionAuHasard);
		}

		return resultat;
	}

	@Override
	public ListeJava<Character> melangerLaListeEfficace(ListeJava<Character> liste) {
		ListeJava<Character> resultat = nouvelleListe();
		
		
		while(resultat.size() < liste.size()) {
			
			Character elementAuHasard = null;
			int positionAuHasard = 0;
			
			while(elementAuHasard == null) {
				positionAuHasard = alea.nextInt(liste.size());
			    elementAuHasard = liste.get(positionAuHasard);
			}
			
			resultat.add(elementAuHasard);
			
			liste.set(positionAuHasard, null);
		}

		return resultat;
	}



}
