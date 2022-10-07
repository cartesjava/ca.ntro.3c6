package ca.ntro.core.json;

import java.io.InputStream;
import java.util.Map;

import ca.ntro.core.Factory;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class JsonParser {
	
	private static JsonParser instance;
	
	public static void initialize(JsonParser instance) {
		T.call(JsonParser.class);
		JsonParser.instance = instance;
	}
	
	protected abstract JsonObject3c6 jsonObjectImpl(); 

	public static JsonObject3c6 jsonObject() {
		T.call(JsonParser.class);
		
		JsonObject3c6 result = null;
		
		try {
			
			result = instance.jsonObjectImpl();
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}
	
	
	protected abstract JsonObject3c6 fromStringImpl(String jsonString);

	public static JsonObject3c6 fromString(String jsonString) {
		T.call(JsonParser.class);

		JsonObject3c6 result = null;
		
		try {
			
			result = instance.fromStringImpl(jsonString);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	protected abstract JsonObject3c6 fromStreamImpl(InputStream jsonStream);

	public static JsonObject3c6 fromStream(InputStream jsonStream) {
		T.call(JsonParser.class);

		JsonObject3c6 result = null;
		
		try {
			
			result = instance.fromStreamImpl(jsonStream);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}


	protected abstract String toStringImpl(JsonObject3c6 jsonObject);

	public static String toString(JsonObject3c6 jsonObject) {
		T.call(JsonParser.class);

		String result = null;
		
		try {
			
			result = instance.toStringImpl(jsonObject);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(JsonParser.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	public static boolean isUserDefined(Object jsonValue) {
		T.call(JsonParser.class);
		
		return userDefinedTypeName(jsonValue) != null;
	}
	
	@SuppressWarnings("unchecked")
	private static String userDefinedTypeName(Object jsonValue) {
		T.call(JsonParser.class);
		
		String result = null;

		try {
			
			if(jsonValue != null) {
				
				Map<String, Object> jsonMap = (Map<String, Object>) jsonValue;
				
				result = (String) jsonMap.get(JsonObject3c6.TYPE_KEY);
			}

		}catch(ClassCastException e) {}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public static Object buildUserDefined(Object jsonValue) {
		T.call(JsonParser.class);
		
		String typeName = userDefinedTypeName(jsonValue);
		
		Class<?> typeClass = Introspector.getClassFromName(typeName);

		Object userDefinedObject = Factory.newInstance(typeClass);
		
		try {
			
			((JsonObjectIO) userDefinedObject).loadFromJsonObject(new JsonObject3c6((Map<String, Object>) jsonValue));
			
		}catch(ClassCastException e) {}

		return userDefinedObject;
	}


	
}
