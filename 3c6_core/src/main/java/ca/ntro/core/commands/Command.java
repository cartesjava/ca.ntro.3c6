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
package ca.ntro.core.commands;

import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;
import ca.ntro.core.system.debug.T;

public abstract class Command {
	
	private Promise<CommandTarget> targetPromise;

	void setTargetPromise(Promise<CommandTarget> targetPromise) {
		this.targetPromise = targetPromise;
	}
	
	public void execute() {
		targetPromise.onValueOrError(new ValueListener<CommandTarget>() {
			
			@Override
			public void onValue(CommandTarget target) {
				T.call(this);
				
				executeImpl(target);

			}
		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
			}
		});
	}
	
	protected abstract void executeImpl(CommandTarget target);

}
