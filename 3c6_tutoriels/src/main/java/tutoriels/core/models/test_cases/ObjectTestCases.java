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
package tutoriels.core.models.test_cases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class ObjectTestCases extends ClassTestCases {
	private static final long serialVersionUID = 5491422169374607423L;

	// XXX: String == MethodSignature.toString
	private Map<String, MethodTestCases> testCasesByMethod = new LinkedHashMap<>();
	
	private Object providedObject;

	public Map<String, MethodTestCases> getTestCasesByMethod() {
		return testCasesByMethod;
	}

	public void setTestCasesByMethod(Map<String, MethodTestCases> testCasesByMethod) {
		this.testCasesByMethod = testCasesByMethod;
	}

	public void validate(Object providedObject, ReportNodeViewModel report) {
		T.call(this);
		
		// XXX: memorize to validate method testcases
		this.providedObject = providedObject;

		validateClass(providedObject.getClass(), report);
	}

	@Override
	protected void validateMethod(Method method, ReportNodeViewModel report) {
		T.call(this);
		
		MethodTestCases methodTestCases = testCasesByMethod.get(Introspector.methodSignature(method).toString());
		
		if(methodTestCases != null) {
			
			//report.initializePerformanceGraph();

			methodTestCases.validate(providedObject, method, report);
			
		}else {

			report.setState(ValidationState.OK);
		}
	}

}
