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
package ca.ntro.core.app;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.file.ResourceLoader;
import ca.ntro.core.system.stack.StackAnalyzer;

public abstract class Initializer {
	
	public void initialize() {

		StackAnalyzer.initialize(provideStackAnalyzer());
		AppCloser.initialize(provideAppCloser());
		ResourceLoader.initialize(provideResourceLoader());
		LocalStore.initialize(provideLocalStore());
		JsonParser.initialize(provideJsonParser());
		Introspector.initialize(provideIntrospector());
		ValueFormatter.initialize(provideValueFormatter());
		NtroCollections.initialize(provideNtroCollections());
	}
	
	protected abstract StackAnalyzer provideStackAnalyzer();
	protected abstract AppCloser provideAppCloser();
	protected abstract ResourceLoader provideResourceLoader();
	protected abstract ModelStoreSync provideLocalStore();
	protected abstract JsonParser provideJsonParser();
	protected abstract Introspector provideIntrospector();
	protected abstract ValueFormatter provideValueFormatter();
	protected abstract NtroCollections provideNtroCollections();
	

}
