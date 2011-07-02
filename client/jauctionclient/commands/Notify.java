package jauctionclient.commands;
import org.json.*;

public class Notify extends ClientCommand {
	private JSONObject data;
	private String message;
	
	public boolean parseJson(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			this.message = this.data.getString("message");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	public void run() {
		getModel().getInputCommands().notifyAuction(message);
	}
}
