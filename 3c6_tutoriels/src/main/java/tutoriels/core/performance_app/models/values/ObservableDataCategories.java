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

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.models.DataCategoryViewModel;

public class ObservableDataCategories extends ObservableList<DataCategoryViewModel> {
	private static final long serialVersionUID = -6368903140498397238L;

	public ObservableDataCategories(List<DataCategoryViewModel> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return DataCategoryViewModel.class;
	}
}
