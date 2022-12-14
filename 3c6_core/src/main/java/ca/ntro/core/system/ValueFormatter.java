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
package ca.ntro.core.system;

import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class ValueFormatter {
	
	private static ValueFormatter instance;
	private static boolean isHtml = false;
	
	public static void initialize(ValueFormatter instance) {
		T.call(ValueFormatter.class);
		
		ValueFormatter.instance = instance;
	}

	public static void setIsHtml(boolean isHtml){
		T.call(ValueFormatter.class);
		
		ValueFormatter.isHtml = isHtml;
	}
	
	public abstract void formatImpl(StringBuilder builder, boolean isHtml, Object... values);

	public static void format(StringBuilder builder, Object... values) {
		T.call(ValueFormatter.class);
		
		try {
			
			instance.formatImpl(builder, isHtml, values);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(ValueFormatter.class.getSimpleName() + " must be initialized");
			
		}
	}
	

}
