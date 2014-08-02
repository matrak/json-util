package mrak.utils.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
	
	public static<T> T get(JSONObject json, Class<T> target) {
		return get(json, target, Config.DEFAULT_CONFIG);
	}
	
	public static<T> T get(JSONObject json, Class<T> target, Config config) {
		
		T instance;
		try {
			instance = target.newInstance();
		} catch (Exception e) {
			throw new Error("Could not create new instance for: " + target);
		}
		
		Field[] fields = target.getDeclaredFields();
		for(Field f : fields) {
			
			if(f.getAnnotation(Ignore.class) != null) {
				continue;
			}

			String jsonFieldName;
			Name name = f.getAnnotation(Name.class);
			if(name != null) {
				jsonFieldName = name.value();
			}
			else {
				jsonFieldName = config.convertCamelCaseToLCaseUnderscore() ? toUnderscoreName(f.getName()) : f.getName();
			}

			Class<?> fieldClass = f.getType();
			f.setAccessible(true);
			
			if(isCollection(f)) 
			{
				Class<?> listElemType = getCollectionType(f);
				JSONArray array = getJSONArray(json, jsonFieldName, config);
				List<Object> list = (array != null) ? new ArrayList<>(array.length()) : null;
				
				for(int index = 0; (array != null && index < array.length()); index++) {
					JSONObject next = array.getJSONObject(index);
					Object listObj = get(next, listElemType, config);
					list.add(listObj);
				}

				setFieldValue(f, instance, list);
			}
			else 
			{
				if(!setFieldValue(f, instance, json, fieldClass, jsonFieldName)) {
					JSONObject next = getJSONObject(json, jsonFieldName, config);
					Object object = get(next, fieldClass, config);
					setFieldValue(f, instance, object);
				}
			}
		}
		return instance;
	}
	
	private static JSONObject getJSONObject(JSONObject obj, String key, Config conf) {
		if(obj.has(key)) {
			return obj.getJSONObject(key);
		}
		else {
			if(conf.throwExceptionOnMissingFieldsInJSON()) {
				throw new Error("Missing field: " + key);
			}
			else {
				return null;
			}
		}
	}
	
	private static JSONArray getJSONArray(JSONObject obj, String key, Config conf) {
		if(obj.has(key)) {
			return obj.getJSONArray(key);
		}
		else {
			if(conf.throwExceptionOnMissingFieldsInJSON()) {
				throw new Error("Missing field: " + key);
			}
			else {
				return null;
			}
		}	
	}
	
	private static boolean isCollection(Field f) {
		// TODO better java.lang.Iterable?
		return Collection.class.isAssignableFrom(f.getType());
	}
	
	private static Class<?> getCollectionType(Field f) {
		Type genericFieldType = f.getGenericType();
		if(genericFieldType instanceof ParameterizedType){
		    ParameterizedType aType = (ParameterizedType) genericFieldType;
		    Type[] fieldArgTypes = aType.getActualTypeArguments();
		    if(fieldArgTypes.length == 1) {
		    	return (Class<?>) fieldArgTypes[0];
		    }
		    else {
		    	throw new Error();
		    }
		}
		throw new Error();
	}
	
	private static boolean setFieldValue(Field f, Object instance, Object val) {
		try {
			f.set(instance, val);
			return true;
		}
		catch(Exception e) {
			String exp = "Could not set value %s for field %s in %s";
			throw new Error(String.format(exp, val, f.getName(), instance.getClass().getSimpleName()));
		}
	}
	
	private static boolean setFieldValue(Field field, Object bean, JSONObject obj, Class<?> clazz, String key) {
		
		Object value = null;
		try {
			
			if(clazz == Boolean.class) {
				value = obj.getBoolean(key) ? Boolean.TRUE : Boolean.FALSE;
			}
			
			else if(clazz == String.class) {
				value = obj.getString(key);
			}
			
			else if(clazz == Double.class) {
				value = new Double(obj.getDouble(key));
			}
			
			else if(clazz == Long.class) {
				value = new Long(obj.getLong(key));
			}
			
			else if(clazz == Integer.class) {
				value = new Integer(obj.getInt(key));
			}
			
		}
		catch (JSONException e) {
			String exp = "Could not obtain value from json for key %s for field %s of type %s";
			throw new Error(String.format(exp, key, field.getName(), clazz.getSimpleName()));	
		}
		
		return (value != null) ? setFieldValue(field, bean, value) : false;

	}
	
	public static String toUnderscoreName(String camelCase) {
		if(camelCase == null) {
			return null;
		}
		else {
			StringBuilder b = new StringBuilder();
			for(int c = 0; c < camelCase.length(); c++) {
				char cc = camelCase.charAt(c);
				if(Character.isUpperCase(cc)) {
					if(c > 0) {
						b.append('_');
					}					
					b.append(Character.toLowerCase(cc));
				}
				else {
					b.append(cc);
				}
			}
			return b.toString();
		}
	}
	
	public static String toUnderscoreName_v1(String camelCase) {
		String regex = "([A-Z][a-z]+)";
		String replacement = "$0_";
		return camelCase.replaceAll(regex, replacement);
	}
	
	public static String toUnderscoreName_v2(String camelCase) {
		String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
		return camelCase.replaceAll(regex, replacement);
	}	
	
}
