package server.ServerCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import server.Connection;
import server.Resource;

import net.sf.json.JSONObject;

public class GetStockAll extends ServerCommand {
	
	private String auth_key;
	
	GetStockAll(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_stock_all";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("auth_key", "String");
		return super.specificationMapper("request", data);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		ArrayList<HashMap> stocks = new ArrayList();
		HashMap stock1 = new HashMap();
		stock1.put("resource_id", "Integer");
		stock1.put("amount", "Integer");
		stocks.add(stock1);
		data.put("stocks", stocks);
		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		try {
			this.auth_key 		= data.getString("auth_key");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
	  	if(authenticate(auth_key)){
	  		HashMap data = new HashMap();
			ArrayList<HashMap> stocks = new ArrayList();
			Iterator it = con.jAuctionServer.getResources().keySet().iterator();
	    	while(it.hasNext()) {
	    		Resource res  = (Resource) con.jAuctionServer.getResources().get(it.next());
				HashMap stock = new HashMap();
		  		stock.put("resource_id", res.getId());
		  		stock.put("amount", this.con.user.getStock(res.getId()));
		  		stocks.add(stock);
			}
			data.put("stocks", stocks);
			
	  		con.respond(this.responseName(), data);
	  	}
	}
}