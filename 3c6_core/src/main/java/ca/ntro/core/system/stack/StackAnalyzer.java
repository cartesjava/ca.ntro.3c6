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
package ca.ntro.core.system.stack;

public abstract class StackAnalyzer {
	
	private static StackAnalyzer instance;
	
	public static void initialize(StackAnalyzer instance) {
		StackAnalyzer.instance = instance;
	}

	public static StackFrame getTracedFrame(String className, int stackOffset) {
		stackOffset++;
		
		StackFrame result = null;
		
		try {
			
			int finalStackOffset = instance.getInitialStackOffset() + stackOffset;
			result = instance.getTracedFrameImpl(className, finalStackOffset);
			
		}catch(NullPointerException e) {
			
			System.out.println("[FATAL]" + StackAnalyzer.class.getSimpleName() + " must be initialized");
			System.out.println("\ncaused by");
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected abstract StackFrame getTracedFrameImpl(String className, int finalStackOffset);
	protected abstract int getInitialStackOffset();

}
