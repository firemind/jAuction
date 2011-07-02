package jauctionclient.commands;
import jauctionclient.datamodel.CurrentUser;

import org.json.*;


public class SignUp extends ClientCommand {
	private JSONObject data;
	private Boolean success;
	
	public boolean parseJson(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			this.success = data.getBoolean("success");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	public void run() {
		CurrentUser cu = getModel().getCurrentUser();
		
		cu.setLogon(success);
	}
}
