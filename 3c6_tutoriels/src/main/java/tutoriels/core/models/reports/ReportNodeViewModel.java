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
package tutoriels.core.models.reports;



import java.util.List;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.core.performance_app.views.PerformanceGraphView;
import tutoriels.core.views.CurrentNodeView;
import tutoriels.core.views.ReportNodeView;

@SuppressWarnings("serial")
public class ReportNodeViewModel extends ReportNodeModel implements ViewModel {
	
	private long nodeId;

	private ReportViewModel mainReport;

	private boolean useStateAsTitle = false;
	
	public ReportNodeViewModel(long nodeId) {
		super();
		T.call(this);

		this.nodeId = nodeId;
	}

	public void setMainReport(ReportViewModel mainReport) {
		T.call(this);
		
		this.mainReport = mainReport;
	}

	public void setState(ValidationState state) {
		T.call(this);
		
		super.setState(state);

		// for leaves, change title as well
		if(useStateAsTitle) {
			switch(state) {
				case WAITING:
					setTitle("en cours...");
					break;

				case TIMEOUT:
					setTitle("délai expiré");
					break;

				case CRASH:
					setTitle("plantage");
					break;

				case ERROR:
					setTitle("erreur");
					break;

				case OK:
					setTitle("ok");
					break;
			}
		}
	}

	@Override
	public void addSubReport(ReportNodeViewModel subReport) {
		T.call(this);

		subReport.setMainReport(mainReport);
		
		mainReport.registerReportNode(subReport);
		
		super.addSubReport(subReport);
	}

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		if(view instanceof ReportNodeView) {
			observeTitle((ReportNodeView) view);
			observeValidationState((ReportNodeView) view);
			observeSubReports((ReportNodeView) view);
			
		}else if(view instanceof CurrentNodeView) {

			observeHtmlPage((CurrentNodeView) view);
		}
	}

	private void observeHtmlPage(CurrentNodeView view) {
		T.call(this);
		
		getHtmlPage().observe(new ValueObserver<String>() {

			@Override
			public void onValue(String value) {
				view.displayHtml(value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String newValue) {
				view.displayHtml(newValue);
			}
		});
		
	}

	private void observeValidationState(ReportNodeView reportNodeView) {
		T.call(this);
		
		getState().observe(new ValueObserver<ValidationState>() {

			@Override
			public void onValue(ValidationState value) {
				T.call(this);
				
				reportNodeView.displayValidationState(value);
			}


			@Override
			public void onDeleted(ValidationState lastValue) {
				T.call(this);
				
			}

			@Override
			public void onValueChanged(ValidationState oldValue, ValidationState newValue) {
				T.call(this);

				reportNodeView.displayValidationState(newValue);
			}
		});
	}

	private void observeTitle(ReportNodeView view) {
		T.call(this);

		getTitle().observe(new ValueObserver<String>() {
			
			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);
				
				view.displayTitle(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String newValue) {
				T.call(this);

				view.displayTitle(newValue);
			}
		});
	}

	private void observeSubReports(ReportNodeView view) {
		
		getSubReports().removeAllObservers();
		
		getSubReports().observe(new ListObserver<ReportNodeViewModel>() {
			@Override
			public void onValueChanged(List<ReportNodeViewModel> oldValue, List<ReportNodeViewModel> newValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<ReportNodeViewModel> value) {
				T.call(this);
			}

			@Override
			public void onDeleted(List<ReportNodeViewModel> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, ReportNodeViewModel subReport) {
				T.call(this);

				ReportNodeView subView = view.insertChild(index, subReport.getId());
				subReport.observeAndDisplay(subView);
			}

			@Override
			public void onItemRemoved(int index, ReportNodeViewModel item) {
				T.call(this);

				view.removeChild(item.getId());
			}

			@Override
			public void onItemUpdated(int index, ReportNodeViewModel item) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public long getId() {
		T.call(this);
		
		return nodeId;
	}

	public boolean isUseStateAsTitle() {
		return useStateAsTitle;
	}

	public void setUseStateAsTitle(boolean useStateAsTitle) {
		this.useStateAsTitle = useStateAsTitle;
	}

	public void updateTestId(int testId) {
		String currentTitle = getTitle().getValue();
		String[] segments = currentTitle.split(" ");
		if(segments.length > 0) {
			String currentPrefix = segments[0];
			getTitle().set(String.format("%s (%02d)", currentPrefix, testId));
		}
	}

}
