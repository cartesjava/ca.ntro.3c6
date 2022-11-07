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
package tutoriels.core.performance_app;

import ca.ntro.core.system.debug.T;

public class TestParameters {
	
	private int minInputSize;
	private int maxInputSize;
	private int desiredSamples;
	private double estimatedMaxExecutionTime;
	private boolean animate;

	public TestParameters(int minInputSize, int maxInputSize, int numberOfSamples, double estimatedMaxExecutionTime, boolean animate) {
		super();
		T.call(this);
		this.minInputSize = minInputSize;
		this.maxInputSize = maxInputSize;
		this.desiredSamples = numberOfSamples;
		this.estimatedMaxExecutionTime = estimatedMaxExecutionTime;
		this.animate = animate;
	}

	public int getMinInputSize() {
		return minInputSize;
	}

	public void setMinInputSize(int minInputSize) {
		this.minInputSize = minInputSize;
	}

	public int getMaxInputSize() {
		return maxInputSize;
	}

	public void setMaxInputSize(int maxInputSize) {
		this.maxInputSize = maxInputSize;
	}
	
	public int getDesiredSamples() {
		return desiredSamples;
	}

	public void setDesiredSamples(int desiredSamples) {
		this.desiredSamples = desiredSamples;
	}

	public double getEstimatedMaxExecutionTime() {
		return estimatedMaxExecutionTime;
	}

	public void setEstimatedMaxExecutionTime(double estimatedMaxExecutionTime) {
		this.estimatedMaxExecutionTime = estimatedMaxExecutionTime;
	}
	
	public boolean getAnimate() {
		return animate;
	}
}
