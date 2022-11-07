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

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class DataPointViewModel extends DataPointModel implements ViewModel {
	private static final long serialVersionUID = 7911389583659316826L;

	public DataPointViewModel(int inputSize, double executionTimeSeconds) {
		super(inputSize, executionTimeSeconds);
		T.call(this);
	}

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		PerformanceGraphView graphView = (PerformanceGraphView) view;
		
		getObservableExecutionTimeSeconds().observe(new ValueObserver<Double>() {
			
			@Override
			public void onDeleted(Double lastValue) {
			}
			
			@Override
			public void onValue(Double value) {
			}
			
			@Override
			public void onValueChanged(Double oldValue, Double newValue) {
				T.call(this);
				graphView.setFinalExecutionTime(getId(), newValue);
			}
		});
		
	}

}
