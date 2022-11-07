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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ExternalUpdateListener;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public class FilesStore extends ModelStoreSync {
	
	private final String DB_DIRECTORY = "db";

	@Override
	public void close() {
		T.call(this);

		// XXX: nothing to do. Files are closed after reading
	}

	@Override
	protected JsonObject3c6 getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		JsonObject3c6 result = null;
		
		if(ifResourceExists(documentPath)) {
			
			InputStream inputStream = resourceInputStream(documentPath);

			result = JsonParser.fromStream(inputStream);
			
		}else {
			
			result = JsonParser.jsonObject();
			
		}
		
		return result;
	}

	private boolean ifResourceExists(DocumentPath documentPath) {
		T.call(this);
		
		URL resourceURL = FilesStore.class.getResource(resourceName(documentPath));
		
		return resourceURL != null;
	}
	
	private String resourceName(DocumentPath documentPath) {
		T.call(this);

		Path filePath = Paths.get("/", DB_DIRECTORY, documentPath.getCollection(), documentPath.getId() + ".json");
		
		return filePath.toString();
	}

	private InputStream resourceInputStream(DocumentPath documentPath) {
		T.call(this);

		return FilesStore.class.getResourceAsStream(resourceName(documentPath));
	}

	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject3c6 jsonObject) {
		T.call(this);

		File resourceFile = getResourceFile(documentPath);
		
		try {

			FileWriter writer = new FileWriter(resourceFile);
			writer.write(JsonParser.toString(jsonObject));
			
			writer.close();

		} catch (IOException e) {
			
			Log.fatalError("Cannot write " + resourceFile.getPath(), e);
		}
	}


	private File getResourceFile(DocumentPath documentPath) {
		T.call(this);
		
		URL dbDirURL = FilesStore.class.getResource("/" + DB_DIRECTORY);
		
		Path dbDirPath = null;

		try {
			
			dbDirPath = Paths.get(dbDirURL.toURI());

		} catch (URISyntaxException e) {
			
			Log.fatalError("Cannot read db directory" + dbDirURL.toString(), e);
		}
		
		Path collectionDirPath = Paths.get(dbDirPath.toString(), documentPath.getCollection());
		
		File collectionDir = collectionDirPath.toFile();
		
		if(!collectionDir.exists()) {
			collectionDir.mkdir();
		}

		Path resourcePath = Paths.get(collectionDirPath.toString(), documentPath.getId() + ".json");
		
		return resourcePath.toFile();
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		T.call(this);
		// TODO Auto-generated method stub
		
	}
}
