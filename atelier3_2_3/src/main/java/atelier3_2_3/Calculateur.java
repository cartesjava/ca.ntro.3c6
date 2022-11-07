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
package atelier3_2_3;

import java.io.Serializable;

public abstract class Calculateur implements CalculateurFibonacci, Serializable {

	private static final long serialVersionUID = -2859226915654159163L;

	protected boolean siRecursif = true;
	protected int n;
	protected Fibonacci tete;

	public boolean getSiRecursif() {
		return siRecursif;
	}

	public void setSiRecursif(boolean siRecursif) {
		this.siRecursif = siRecursif;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Fibonacci getTete() {
		return tete;
	}

	public void setTete(Fibonacci tete) {
		this.tete = tete;
	}

	@Override
	public long calculerReponse(int n) {
		long reponse = -1;
		
		setN(n);

		construireGraphe();

		if(tete != null
				&& tete.getReponse() != null) {

			reponse = tete.getReponse();
		}
		
		return reponse;
	}

	protected abstract void construireGraphe();

}
