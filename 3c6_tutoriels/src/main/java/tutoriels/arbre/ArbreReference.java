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
package tutoriels.arbre;

import java.util.ArrayList;
import java.util.List;

public class ArbreReference<C extends Comparable<C>> extends Arbre<C> {

	private Noeud<C> racine;

	@Override
	public void ajouter(C valeur) {
		if(racine == null) {
			racine = new NoeudReference<C>(valeur);
		}else {
			racine.inserer(valeur);
		}
	}

	@Override
	public void retirer(C valeur) {

		if(racine != null && racine.siFeuille() && racine.getValeur().equals(valeur)) {

			racine = null;

		}else {

			Noeud<C> aRetirer = trouverNoeud(valeur);

			if(aRetirer != null) {
				aRetirer.seRetirer();
			}
		}
	}

	@Override
	public Noeud<C> racine() {
		return racine;
	}

	@Override
	public Noeud<C> trouverNoeud(C valeur) {
		Noeud<C> noeudCherche = null;

		if(racine != null) {
			noeudCherche = trouverNoeud(racine, valeur);
		}
		
		return noeudCherche;
	}
	
	private Noeud<C> trouverNoeud(Noeud<C> curseur, C valeur){
		Noeud<C> noeudCherche = null;

        if(curseur.getValeur() != null && curseur.getValeur().compareTo(valeur) == 0) {

        	noeudCherche = curseur;

        }else if(curseur.getEnfantGauche() != null && valeur.compareTo(curseur.getValeur()) < 0) {
        	
        	noeudCherche = trouverNoeud(curseur.getEnfantGauche(), valeur);

        }else if(curseur.getEnfantDroit() != null && valeur.compareTo(curseur.getValeur()) > 0) {

        	noeudCherche = trouverNoeud(curseur.getEnfantDroit(), valeur);

        }

        return noeudCherche;
	}

	@Override
	public List<Noeud<C>> tousLesNoeuds() {
		List<Noeud<C>> lesNoeuds = new ArrayList<>();

		if(racine != null) {
			lesNoeuds = tousLesNoeuds(racine);
		}
		
		return lesNoeuds;
	}
	
	private List<Noeud<C>> tousLesNoeuds(Noeud<C> curseur){
		List<Noeud<C>> lesNoeuds = new ArrayList<>();
		
		if(curseur.getEnfantGauche() != null) {
			lesNoeuds.addAll(tousLesNoeuds(curseur.getEnfantGauche()));
		}
		
		lesNoeuds.add(curseur);
		
		if(curseur.getEnfantDroit() != null) {
			lesNoeuds.addAll(tousLesNoeuds(curseur.getEnfantDroit()));
		}
		
		return lesNoeuds;
	}

	@Override
	public int nombreDeNoeuds() {
		return tousLesNoeuds().size();
	}
}
