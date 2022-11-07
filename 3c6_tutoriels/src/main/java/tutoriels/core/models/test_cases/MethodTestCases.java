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
import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class MethodTestCases extends ModelValue {
	private static final long serialVersionUID = -7235780255880815953L;

	private String methodName;
	private List<MethodTestCase> testCases = new ArrayList<>();
	
	public MethodTestCases() {
		super();
		T.call(this);
	}

	public MethodTestCases(String methodName) {
		super();
		T.call(this);
		
		this.methodName = methodName;
	}

	public String getMethodName() {
		T.call(this);
		return methodName;
	}

	public void setMethodName(String methodName) {
		T.call(this);
		this.methodName = methodName;
	}
	
	public List<MethodTestCase> getTestCases() {
		T.call(this);

		return testCases;
	}

	public void setTestCases(List<MethodTestCase> testCases) {
		T.call(this);

		this.testCases = testCases;
	}
	
	public void add(MethodTestCase testCase) {
		T.call(this);
		
		testCases.add(testCase);
	}

	public void validate(Object providedObject, Method methodToTest, ReportNodeViewModel methodNode) {
		T.call(this);

		methodNode.setState(ValidationState.WAITING);
		methodNode.setExpectedNumberOfSubReports(testCases.size());
		
		if(testCases.isEmpty()) {
			
			methodNode.setState(ValidationState.OK);
			
		}else {

			for(MethodTestCase testCase : testCases) {

				// XXX: the will block if the method does not return
				testCase.validate(providedObject, methodToTest, methodNode);
			}
		}
	}
	
}
