package jauctionclient.commands;

import org.json.JSONObject;


public class SignUp extends ClientCommand {
	private Boolean success;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			this.success = getData().getBoolean("success");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.getOutputCommands().login(m.getCurrentUser().getName(), m.getCurrentUser().getPassword());
	}
}
