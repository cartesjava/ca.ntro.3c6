package ca.ntro.java.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.core.json.JsonObject3c6;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.debug.T;

public class JsonParserJava extends JsonParser {

	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	protected JsonObject3c6 jsonObjectImpl() {
		T.call(this);
		return new JsonObject3c6(new HashMap<String, Object>());
	}

	@Override
	protected JsonObject3c6 fromStringImpl(String jsonString) {
		T.call(this);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = gson.fromJson(jsonString, HashMap.class);
		
		return new JsonObject3c6(jsonMap);
	}

	@Override
	protected JsonObject3c6 fromStreamImpl(InputStream jsonStream) {
		T.call(this);
		
		InputStreamReader jsonReader = new InputStreamReader(jsonStream);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = gson.fromJson(jsonReader, HashMap.class);
		
		return new JsonObject3c6(jsonMap);
	}

	@Override
	protected String toStringImpl(JsonObject3c6 jsonObject) {
		T.call(this);
		return gson.toJson(jsonObject.toMap());
	}
}
