package jauctionclient.commands;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class ClientCommand {
	private static Model model;
	private JSONObject data;

	public boolean parseInput(JSONObject json_object) throws JSONException {
		setData(json_object.getJSONObject("data"));
		return true;
	}
  
	public void run() { }
	
	public static String getCommand() {
		return "";
	}
	
	public JSONObject prepareOutput() {
		return new JSONObject();
	}

	public static Model getModel() {
		return ClientCommand.model; 
	}

	public static void setModel(Model model) { 
		ClientCommand.model = model; 
	}

	protected void setData(JSONObject data) {
		this.data = data;
	}

	protected JSONObject getData() {
		return data;
	}

}
