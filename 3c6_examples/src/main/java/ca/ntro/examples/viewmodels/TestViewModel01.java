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
package ca.ntro.examples.viewmodels;


import ca.ntro.core.commands.CommandFactory;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.models.properties.stored.simple.StoredString;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import ca.ntro.examples.commands.change_text.ChangeText;
import ca.ntro.examples.commands.change_text.ChangeTextTarget;
import ca.ntro.examples.models.TestModel01;
import ca.ntro.examples.views.TestView01;

public class TestViewModel01 extends TestModel01 implements ViewModel {

	

	private int commandCounter = 0;

	public TestViewModel01() {
		super();
		T.call(this);
		
		// FIXME: make sure this is always called
		installCommandTargets();
	}


	private void installCommandTargets() {
		T.call(this);

		CommandFactory.installTarget(ChangeText.class, new ChangeTextTarget() {
			@Override
			public void changeText(String text) {
				T.call(this);
				
				commandCounter++;
				getStoredString().set("" + commandCounter + " " + text);
			}
		});
	}

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);

		getStoredString().observe(new ValueObserver<String>() {

			@Override
			public void onValue(String value) {
				T.call(this);

				((TestView01) view).displayText(value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				((TestView01) view).displayText(value);
			}
		});
		
	}
}
