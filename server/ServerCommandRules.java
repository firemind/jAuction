package server;
import net.sf.json.JSONObject;

public interface ServerCommandRules {

	  public String name();
	  public boolean parseJson(JSONObject data);
	  public void call();
}
