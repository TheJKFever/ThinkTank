package Helper;
import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Helper {
	private static JSONParser JSON = new JSONParser();
	
	public static String Jsonify(String type, Object data) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("data", data);
		return JSONToString(obj);
	}
	
	public static String Jsonify(long timestamp, String type, Object data) {
		JSONObject obj = new JSONObject();
		obj.put("timestamp", timestamp);
		obj.put("type", type);
		obj.put("data", data);
		return JSONToString(obj);
	}

	public static String Jsonify(String type, Object data, boolean result) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("data", data);
		obj.put("result", result);
		return JSONToString(obj);
	}
	
	public static String Jsonify(long timestamp, String type, Object data, boolean result) {
		JSONObject obj = new JSONObject();
		obj.put("timestamp", timestamp);
		obj.put("type", type);
		obj.put("data", data);
		obj.put("result", result);
		return JSONToString(obj);
	}

	private static String JSONToString(JSONObject obj) {
		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}

	public static JSONObject parse(String data) {
		try {
			return (JSONObject)JSON.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
