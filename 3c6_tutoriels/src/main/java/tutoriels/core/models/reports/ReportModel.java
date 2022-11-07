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

import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.models.properties.observable.simple.ObservableString;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.stored.simple.StoredString;
import ca.ntro.core.system.debug.T;

public class ReportModel extends Model {
	
	// FIXME: this should be parametrized
	//        a model should exists w/oi
	private ReportNodeViewModel rootReportNode = ReportViewModel.newSubReport();
	
	@Override
	public void initializeStoredValues() {
		T.call(this);
		// XXX: none
	}
	
	public void setReportTitle(String title) {
		T.call(this);
		
		rootReportNode.setTitle(title);
	}

	public void addSubReport(ReportNodeViewModel subReport) {
		T.call(this);
		
		rootReportNode.addSubReport(subReport);
	}


	public ReportNodeViewModel getRootReportNode() {
		return rootReportNode;
	}

	public void setRootReportNode(ReportNodeViewModel rootReportNode) {
		this.rootReportNode = rootReportNode;
	}

}
