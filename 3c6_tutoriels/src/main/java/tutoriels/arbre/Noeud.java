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

public abstract class Noeud<C extends Comparable<C>> {

	protected C valeur;
	protected Noeud<C> enfantGauche;
	protected Noeud<C> enfantDroit;

	private Noeud<C> parent;

	public C getValeur() {
		return valeur;
	}

	public void setValeur(C valeur) {
		this.valeur = valeur;
	}

	public Noeud<C> getEnfantGauche() {
		return enfantGauche;
	}

	public void setEnfantGauche(Noeud<C> enfantGauche) {
		this.enfantGauche = enfantGauche;
	}

	public Noeud<C> getEnfantDroit() {
		return enfantDroit;
	}

	public void setEnfantDroit(Noeud<C> enfantDroit) {
		this.enfantDroit = enfantDroit;
	}

	public Noeud<C> parent() {
		return parent;
	}

	public void enregistrerParent(Noeud<C> parent) {
		this.parent = parent;
	}

	public Noeud(C valeur) {
		setValeur(valeur);
	}

	public Noeud(C valeur, Noeud<C> parent) {
		setValeur(valeur);
		enregistrerParent(parent);
	}

	protected abstract Noeud<C> nouveauNoeud(C valeur, Noeud<C> parent);
	protected abstract Noeud<C> nouveauNoeud(C valeur);

	public abstract void inserer(C valeur);
	
	protected int hauteur(Noeud<C> noeud) {
		int hauteur = 0;
		
		if(noeud != null) {
		
			int hauteurGauche = hauteur(noeud.getEnfantGauche());
			int hauteurDroite = hauteur(noeud.getEnfantDroit());
			
			hauteur =  1 + Math.max(hauteurGauche, hauteurDroite);
		}

		return hauteur;
	}

	protected void equilibrer() {

		int differenceHauteurs = hauteur(getEnfantGauche()) - hauteur(getEnfantDroit());
		
		if(differenceHauteurs >= 2) {
			
			rotationGaucheDroite();
			
		}else if(differenceHauteurs <= -2) {

			rotationDroiteGauche();

		}else if(parent() != null){
			parent().equilibrer();
		}
	}

	private void rotationGaucheDroite(){
		C prochaineValeurRacine = getEnfantGauche().getValeur();
		C prochaineValeurDroite = getValeur();
		Noeud<C> prochainEnfantGauche = getEnfantGauche().getEnfantGauche();
		Noeud<C> prochainEnfantDroiteGauche = getEnfantGauche().getEnfantDroit();
		Noeud<C> prochainEnfantDroiteDroite = getEnfantDroit();
		
		setValeur(prochaineValeurRacine);
		setEnfantDroit(nouveauNoeud(prochaineValeurDroite, this));
		setEnfantGauche(prochainEnfantGauche);
		if(prochainEnfantGauche != null) {
			prochainEnfantGauche.enregistrerParent(this);
		}

		getEnfantDroit().setEnfantGauche(prochainEnfantDroiteGauche);
		if(prochainEnfantDroiteGauche != null) {
			prochainEnfantDroiteGauche.enregistrerParent(getEnfantDroit());
		}
		getEnfantDroit().setEnfantDroit(prochainEnfantDroiteDroite);
		if(prochainEnfantDroiteDroite != null) {
			prochainEnfantDroiteDroite.enregistrerParent(getEnfantDroit());
		}
	}

	private void rotationDroiteGauche(){
		C prochaineValeurRacine = getEnfantDroit().getValeur();
		C prochaineValeurGauche = getValeur();
		Noeud<C> prochainEnfantDroit = getEnfantDroit().getEnfantDroit();
		Noeud<C> prochainEnfantGaucheDroite = getEnfantDroit().getEnfantGauche();
		Noeud<C> prochainEnfantGaucheGauche = getEnfantGauche();
		
		setValeur(prochaineValeurRacine);
		setEnfantGauche(nouveauNoeud(prochaineValeurGauche, this));
		setEnfantDroit(prochainEnfantDroit);
		if(prochainEnfantDroit != null) {
			prochainEnfantDroit.enregistrerParent(this);
		}

		getEnfantGauche().setEnfantDroit(prochainEnfantGaucheDroite);
		if(prochainEnfantGaucheDroite != null) {
			prochainEnfantGaucheDroite.enregistrerParent(getEnfantGauche());
		}
		getEnfantGauche().setEnfantGauche(prochainEnfantGaucheGauche);
		if(prochainEnfantGaucheGauche != null) {
			prochainEnfantGaucheGauche.enregistrerParent(getEnfantGauche());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<ul>");
		if(getEnfantDroit() != null) {
		builder.append(getEnfantDroit());
		}
		builder.append("<li>");
		builder.append(getValeur());
		builder.append("</li>");
		if(getEnfantGauche() != null) {
			builder.append(getEnfantGauche());
		}
		builder.append("</ul>");
		
		return builder.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		boolean siEgal = false;
		
		if(o instanceof Noeud) {
			siEgal = siEgal((Noeud<C>) o);
		}
		
		return siEgal;
	}
	
	private boolean siEgal(Noeud<C> n) {
		boolean siEgal = false;
		
		if(getValeur() != null && getValeur().equals(n.getValeur())) {

			boolean gaucheEgal = false;
			boolean droiteEgal = false;
			
			if(getEnfantGauche() != null && getEnfantGauche().equals(n.getEnfantGauche())) {
				gaucheEgal = true;
			}else if(getEnfantGauche() == null && n.getEnfantGauche() == null) {
				gaucheEgal = true;
			}
			
			if(getEnfantDroit() != null && getEnfantDroit().equals(n.getEnfantDroit())) {
				droiteEgal = true;
			}else if(getEnfantDroit() == null && n.getEnfantDroit() == null) {
				droiteEgal = true;
			}

			siEgal = gaucheEgal && droiteEgal;
			
		}else if(getValeur() == null && n.getValeur() == null) {

			siEgal = true;
		}

		return siEgal;
	}

	public boolean siFeuille() {
		return getEnfantDroit() == null && getEnfantGauche() == null;
	}
	
	private void retirerEnfant(Noeud<C> aRetirer) {
		if(getEnfantDroit() == aRetirer) {
			
			setEnfantDroit(null);
			
		}else if(getEnfantGauche() == aRetirer) {
			
			setEnfantGauche(null);
		}
	}

	public void seRetirer() {
		if(siFeuille() && parent() != null){

			parent().retirerEnfant(this);

		}else {

			remplacerParDernierEnfant();

		}
	}
	
	private void remplacerParDernierEnfant() {
		
		Noeud<C> aRetirer = null;
		
		if(getEnfantDroit() != null && hauteur(getEnfantDroit()) > hauteur(getEnfantGauche())) {
			
			aRetirer = getEnfantDroit().enfantLePlusAGauche();
			
		}else if(getEnfantGauche() != null){

			aRetirer = getEnfantGauche().enfantLePlusADroite();
			
		}
		
		if(aRetirer != null) {

			setValeur(aRetirer.getValeur());
			aRetirer.seRetirer();
		}
	}
	
	private Noeud<C> enfantLePlusADroite() {
		Noeud<C> lePlusADroite = this;
		
		if(getEnfantDroit() != null) {
			
			lePlusADroite = getEnfantDroit().enfantLePlusADroite();
		}
		
		return lePlusADroite;
	}

	private Noeud<C> enfantLePlusAGauche() {

		Noeud<C> lePlusAGauche = this;
		
		if(getEnfantGauche() != null) {

			lePlusAGauche = getEnfantGauche().enfantLePlusAGauche();
		}
		
		return lePlusAGauche;
	}

}
