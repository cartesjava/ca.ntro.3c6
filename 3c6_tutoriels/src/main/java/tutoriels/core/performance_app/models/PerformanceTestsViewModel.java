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


import java.util.List;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.MainPerformanceAppView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class PerformanceTestsViewModel extends PerformanceTestsModel implements ViewModel {
	private static final long serialVersionUID = -2849937081961960593L;

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		MainPerformanceAppView mainView = (MainPerformanceAppView) view;
		
		getPerformanceGraphs().observe(new ListObserver<PerformanceGraphViewModel>() {
			
			@Override
			public void onItemRemoved(int index, PerformanceGraphViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, PerformanceGraphViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, PerformanceGraphViewModel item) {
				T.call(this);

				PerformanceGraphView graphView = mainView.addGraphView(item.getTitle());
				item.observeAndDisplay(graphView);
			}
			
			@Override
			public void onDeleted(List<PerformanceGraphViewModel> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<PerformanceGraphViewModel> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<PerformanceGraphViewModel> oldValue, List<PerformanceGraphViewModel> newValue) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}

	
	
}
