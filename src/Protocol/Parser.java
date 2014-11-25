package Protocol;

import java.util.logging.Level;

import org.json.simple.JSONObject;

import Exceptions.PortNotAvailableException;
import Helper.Helper;

public class Parser {

	public void received(String data) {
// TODO Parse all possible messages
		JSONObject jsonData = Helper.parse(data);
		String type = (String)jsonData.get("type");
		switch(type) {
		// consider making this {"type": "command", "data": "new game"...
		// instead of {"type": "new game", ...
			case "new game": 
			int portOfNewGame;
			try {
				portOfNewGame = newGame();
				send(Helper.Jsonify("response", portOfNewGame, true));
			} catch (PortNotAvailableException pnae) {
				send(Helper.Jsonify("response", pnae.getMessage(), false));
			}
			default:
				logger.log(Level.INFO, "Parse error. did not understand message: " + data);
		}
	}
}
