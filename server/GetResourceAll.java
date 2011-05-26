package server;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetResourceAll extends ServerCommand {
	
	
	GetResourceAll(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_resource_all";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		return super.specificationMapper("request", null);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		ArrayList<HashMap> resources = new ArrayList();
		HashMap res1 = new HashMap();
		res1.put("resource_id", "Integer");
		res1.put("name", "String");
		resources.add(res1);
		data.put("resources", resources);

		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		return true;
	}
	
	public void run(){
	  		HashMap data = new HashMap();
			ArrayList<HashMap> resources = new ArrayList();
			for( Resource resource : con.getResources()){
				int resource_id =  con.getResources().indexOf(resource);
				HashMap res = new HashMap();
		  		res.put("resource_id", resource_id);
		  		res.put("name", resource.getName());
		  		resources.add(res);
			}
			data.put("resources", resources);
	  		con.respond(this.responseName(), data);
	}
}