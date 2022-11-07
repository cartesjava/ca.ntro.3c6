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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.PromiseSync;
import ca.ntro.core.system.debug.T;

public class CommandFactory {
	
	private static Map<Class<? extends Command>, CommandTarget> targets = new HashMap<>();

	// XXX: store every promise made that a target is coming
	private static Map<Class<? extends Command>, List<TargetPromise>> targetPromises = new HashMap<>();
	
	public static void installTarget(Class<? extends Command> commandType, CommandTarget target) {
		T.call(CommandFactory.class);
		
		// Save target for later
		targets.put(commandType, target);
		
		// Update promises for commands asked before the target was available
		if(targetPromises.containsKey(commandType)) {
			for(TargetPromise targetPromise : targetPromises.get(commandType)) {
				targetPromise.installTarget(target);
			}
		}
	}
	
	public static Command buildCommand(Class<? extends Command> commandType) {
		T.call(CommandFactory.class);
		
		Command command = null;

		try {

			command = commandType.newInstance();

		} catch (InstantiationException | IllegalAccessException e) {
			
			System.out.println("FATAL: cannot instantiate " + commandType.getSimpleName());
		}
		
		if(targets.containsKey(commandType)) {
			
			CommandTarget target = targets.get(commandType);
			command.setTargetPromise(new PromiseSync<CommandTarget>(target));
			
		}else {
			
			TargetPromise targetPromise = new TargetPromise();
			memoriseTargetPromise(commandType, targetPromise);
			command.setTargetPromise(targetPromise);
			
		}
		
		
		return command;
	}
	
	// FIXME: add a general Map<a,List<b>> type and utils 
	private static void memoriseTargetPromise(Class<? extends Command> commandType, TargetPromise targetPromise) {
		T.call(CommandFactory.class);

		if(targetPromises.containsKey(commandType)) {
			
			targetPromises.get(commandType).add(targetPromise);
			
		}else {
			
			List<TargetPromise> promises = new ArrayList<>();
			promises.add(targetPromise);
			targetPromises.put(commandType, promises);
		}
	}
	
	
	

}
