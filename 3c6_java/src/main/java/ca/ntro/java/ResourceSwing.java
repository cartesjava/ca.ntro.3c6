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
package ca.ntro.java;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.PromiseSync;
import ca.ntro.core.system.file.Resource;

public class ResourceSwing implements Resource {
	
	private InputStream stream;
	
	public ResourceSwing(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public Promise<String> getString() {

		Promise<String> result = null;
		
		StringBuilder builder = new StringBuilder();
		
		InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
		
		int c;
		try {

			while((c = reader.read()) > 0) {
				builder.append((char) c);
			}

			result = new PromiseSync<String>(builder.toString());

		} catch (Exception e) {
			
			result = new PromiseSync<String>(null);

		} 
		
		return result;
	}

}
