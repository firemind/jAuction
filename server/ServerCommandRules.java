package server;
import net.sf.json.JSONObject;

public interface ServerCommandRules {

	  public String name();
	  public String responseName();
	  public boolean parseJson(JSONObject data);
	  public JSONObject requestSpecification();
	  public JSONObject responseSpecification();
	  
}
