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
package tutoriels.core.performance_app.models.values;

import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import tutoriels.core.performance_app.TestParameters;

public class ObservableTestParameters extends ObservableProperty<TestParameters>{
	private static final long serialVersionUID = 6189533484500081758L;

	@Override
	protected Class<?> getValueType() {
		return TestParameters.class;
	}

}
