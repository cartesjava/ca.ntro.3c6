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
package tutoriels.core.performance_app.models;

import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableDouble;
import ca.ntro.core.system.debug.T;

public class DataPointModel extends Model {
	private static final long serialVersionUID = 7834919545798734024L;
	
	private int inputSize;
	private ObservableDouble observableExecutionTimeSeconds = new ObservableDouble(0.0);
	private int id;
	
	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
	}
	
	public DataPointModel(int inputSize, double executionTimeSeconds) {
		super();
		T.call(this);
		this.inputSize = inputSize;
		this.observableExecutionTimeSeconds = new ObservableDouble(executionTimeSeconds);
	}

	public int getInputSize() {
		return inputSize;
	}
	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}
	
	public void setExecutionTime(double executionTimeSeconds) {
		T.call(this);
		this.observableExecutionTimeSeconds.set(executionTimeSeconds);
	}

	public ObservableDouble getObservableExecutionTimeSeconds() {
		return observableExecutionTimeSeconds;
	}

	public void setObservableExecutionTimeSeconds(ObservableDouble observableExecutionTimeSeconds) {
		this.observableExecutionTimeSeconds = observableExecutionTimeSeconds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
