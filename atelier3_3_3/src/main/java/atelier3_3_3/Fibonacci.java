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
package atelier3_3_3;

import java.io.Serializable;

public abstract class Fibonacci implements Serializable {

	private static final long serialVersionUID = -8602186390344800134L;

	protected Fibonacci moinsUn;
	protected Fibonacci moinsDeux;

	protected int n;
	protected Long reponse;
	protected double nombreOr;

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Fibonacci getMoinsUn() {
		return moinsUn;
	}

	public void setMoinsUn(Fibonacci moinsUn) {
		this.moinsUn = moinsUn;
	}

	public Fibonacci getMoinsDeux() {
		return moinsDeux;
	}

	public void setMoinsDeux(Fibonacci moinsDeux) {
		this.moinsDeux = moinsDeux;
	}

	public double getNombreOr() {
		return nombreOr;
	}

	public void setNombreOr(double nombreOr) {
		this.nombreOr = nombreOr;
	}
	
	public Long getReponse() {
		return reponse;
	}

	public void setReponse(Long reponse) {
		this.reponse = reponse;
	}

	public abstract void construireGrapheRecursivement();

	public abstract void calculerReponseEtNombreOr();

}
