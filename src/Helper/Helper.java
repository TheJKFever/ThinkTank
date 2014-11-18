package Helper;
import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;


public class Helper {


	public static String Jsonify(String type, Object data) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("data", data);

		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toString();
	}

	public static String Jsonify(String type, Object data, boolean result) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("data", data);
		obj.put("result", result);

		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toString();

	}
}
