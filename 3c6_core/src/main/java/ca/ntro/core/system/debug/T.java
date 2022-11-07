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
package ca.ntro.core.system.debug;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class T {
	
	private static boolean isActive = true;
	

	public static void call(Object called) {
		if(!isActive) {return;}

		int stackOffset = 1;

		// XXX: instanceof Class ?  fails in JSweet
		// FIXME: refactor in a CalledObjectAnalyzer (Enclosing classes also different in JSweet)
		Class calledClass = null;
		if(called.getClass().getSimpleName().equals("Class")) {
			calledClass = (Class) called;
		}else {
			calledClass = called.getClass();
		}
		
		String className = calledClass.getSimpleName();

		traceCall(className, stackOffset);
	}

	
	private static void traceCall(String className, int stackOffset) {
		stackOffset++;

		StackFrame tracedFrame = StackAnalyzer.getTracedFrame(className, stackOffset);
		
		printTrace(tracedFrame);
	}
	
	private static void printTrace(StackFrame tracedFrame) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("#T.call | ");

		tracedFrame.printFrame(builder);

		System.out.println(builder.toString());
	}


	public static void here() {
		int stackOffset = 1;
		
		// FIXME: we don't want method name, only File and lineNumber
		traceCall("", stackOffset);
	}

	public static void values(Object... values) {
		
		// TODO: print also location of values
		
		StringBuilder builder = new StringBuilder();

		builder.append("#T.values | ");
		
		for(Object value : values) {
			
			// TODO: pretty print values
			ValueFormatter.format(builder, value);
			builder.append("\n");
		}
		
		System.out.println(builder.toString());
	}


	public static void setActive(boolean isActive) {
		T.isActive = isActive;
	}

}
