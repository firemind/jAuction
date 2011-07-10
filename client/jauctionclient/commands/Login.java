package jauctionclient.commands;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends ClientCommand {
	private JSONObject data;
	private Long id;
	private String auth_id;
	private boolean success;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			this.data = json_object.getJSONObject("data");
			
			if(data.get("auth_key") == JSONObject.NULL){
				success = false;
			} else{
				success = true;
				this.auth_id = data.getString("auth_key");
				this.id = data.getLong("user_id");
			}
		} catch(JSONException e) {
			System.out.println("Bad Request" + json_object.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
  

	@Override
	public void run() {
		Model m = getModel();
		if(success) {
			m.setModelChanged();
			m.getCurrentUser().setAuthId(auth_id);
			m.getCurrentUser().setId(id);
			m.getCurrentUser().setLogon(true);
			m.getOutputCommands().getStartUpData();
			m.notifyObservers("login");
		} else {
			m.getOutputCommands().signUp(m.getCurrentUser().getName(), m.getCurrentUser().getPassword());
		}
	}
}
