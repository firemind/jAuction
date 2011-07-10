package jauctionclient.commands;
import org.json.JSONObject;

public class Notify extends ClientCommand {
	private String message;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			message = getData().getString("message");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		m.setMessage(message);
		m.notifyObservers("newMessage");
	}
}
