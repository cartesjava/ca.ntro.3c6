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

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.commands.CommandFactory;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.commands.select_node.SelectNode;
import tutoriels.core.commands.select_node.SelectNodeTarget;
import tutoriels.core.models.reports.values.ObservableReportNode;
import tutoriels.core.views.ValidationAppMainView;

public class ReportViewModel extends ReportModel implements ViewModel {
	private static final long serialVersionUID = -5886933278386557673L;

	private static long nextNodeId = 0l;
	
	public synchronized static ReportNodeViewModel newSubReport() {
		T.call(ReportViewModel.class);
		
		ReportNodeViewModel reportNode = new ReportNodeViewModel(nextNodeId);

		nextNodeId++;
		
		return reportNode;
	}


	private Map<Long, ReportNodeViewModel> reportNodes = new HashMap<>();
	
	private ObservableReportNode currentNode = new ObservableReportNode(null);
	
	public ReportViewModel() {
		super();
		T.call(this);
		
		initializeRootReport();
		
		// FIXME: this should be called by Ntro
		installCommandTargets();
	}

	private void initializeRootReport() {
		T.call(this);

		getRootReportNode().setMainReport(this);
		registerReportNode(getRootReportNode());
	}
	
	public void registerReportNode(ReportNodeViewModel reportNode) {
		T.call(this);
		
		reportNodes.put(reportNode.getId(), reportNode);
	}
	

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		ValidationAppMainView mainView = (ValidationAppMainView) view;
		
		getRootReportNode().observeAndDisplay(mainView.getRootReportView());
		
		currentNode.observe(new ValueObserver<ReportNodeViewModel>() {

			@Override
			public void onValue(ReportNodeViewModel value) {
				if(value != null) {
					value.observeAndDisplay(mainView.getCurrentNodeView());
				}
			}

			@Override
			public void onDeleted(ReportNodeViewModel lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(ReportNodeViewModel oldValue, ReportNodeViewModel newValue) {
				if(newValue != null) {
					newValue.observeAndDisplay(mainView.getCurrentNodeView());
				}
			}
		});
	}

	public void installCommandTargets() {
		T.call(this);

		CommandFactory.installTarget(SelectNode.class, new SelectNodeTarget() {
			@Override
			public void selectNode(long nodeId) {
				T.call(this);
				
				selectReportNode(nodeId);
			}
		});
	}
	
	private void selectReportNode(long nodeId) {
		T.call(this);
		
		ReportNodeViewModel reportNode = reportNodes.get(nodeId);
		
		if(reportNode != null) {
			currentNode.set(reportNode);
		}
	}

	public void setExpectedNumberOfSubReports(int expectedNumberOfSubReports) {
		T.call(this);

		getRootReportNode().setExpectedNumberOfSubReports(expectedNumberOfSubReports);
	}

}
