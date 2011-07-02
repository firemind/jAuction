package jauctionclient.commands;


import org.json.*;


abstract public class ClientCommand {
	private static Model model;

	public boolean parseJson(JSONObject json_object) {
		return false;
	}
  
	public void run() { }

	public static Model getModel() { return ClientCommand.model; }

	public static void setModel(Model model) { ClientCommand.model = model; }
}
