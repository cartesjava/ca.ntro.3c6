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
package ca.ntro.core.system.log;

import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class Log {

	public static void warning(String[] messages) {

	}

	public static void error(String[] messages) {

	}

	
	public static void fatalError(String message, Exception... causedBy) {
		int currentDepth = 1;
		StackFrame tracedFrame = StackAnalyzer.getTracedFrame(null, currentDepth);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("#FATAL | " );
		
		builder.append(message);

		builder.append(" (" );

		tracedFrame.printSourceLocation(builder);

		builder.append(")" );

		
		if(causedBy != null && causedBy.length > 0) {

			builder.append("\ncaused by\n");
			System.out.println(builder.toString());

			causedBy[0].printStackTrace();
			
		}else {

			System.out.println(builder.toString());
			
		}
		
		
		AppCloser.close();
	}

}
