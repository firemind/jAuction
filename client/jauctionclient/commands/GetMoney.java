package jauctionclient.commands;

import org.json.JSONObject;

public class GetMoney extends ClientCommand {
	private Integer money;
	
	@Override
	public boolean parseInput(JSONObject json_object) {
		try {
			super.parseInput(json_object);
			money = getData().getInt("money");
		} catch(Exception e) {
			return false;
		}
		return true;
	}
  
	@Override
	public void run() {
		Model m = getModel();
		m.setModelChanged();
		m.getCurrentUser().setMoney(money);
		m.notifyObservers("updateMoney");
	}
}
