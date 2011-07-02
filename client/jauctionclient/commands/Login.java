package jauctionclient.commands;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends ClientCommand {
	private JSONObject data;
	private String auth_id;
	
	public boolean parseJson(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			this.auth_id = this.data.getString("auth_key");
		} catch(JSONException e) {
			System.out.println("Bad Request" + json_object.toString());
			return false;
		}
		return true;
	}
  
	public void run() {
		getModel().getInputCommands().login(auth_id);
	}
}
