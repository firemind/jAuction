package server.ServerCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import server.Auction;
import server.Connection;

import net.sf.json.JSONObject;

public class GetAuctionsAll extends ServerCommand {

	
	GetAuctionsAll(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_auctions_all";
	}
	
	public JSONObject requestSpecification(){
		return super.specificationMapper("request", null);
	}
	
	public JSONObject responseSpecification(){
		HashMap data = new HashMap();
		ArrayList<HashMap> auctions = new ArrayList();
		HashMap auc1 = new HashMap();
		auc1.put("auction_id", "Integer");
		auc1.put("resource_id", "Integer");
		auc1.put("amount", "Integer");
		auc1.put("user_id", "Integer");
		auc1.put("price", "Integer");
		auc1.put("timeleft_sec", "Integer");
 
		auctions.add(auc1);
		data.put("auctions", auctions);

		return super.specificationMapper("response", data);
	}
	
	public boolean parseJson(JSONObject data){
		return true;
	}
	
	public void run(){
	  		HashMap data = new HashMap();
			ArrayList<HashMap> auctions = new ArrayList();
	    	Iterator it = con.jAuctionServer.getAuctions().keySet().iterator();
	    	while(it.hasNext()) {
	    		Auction auc = con.jAuctionServer.getAuctions().get(it.next());
				HashMap auc1 = new HashMap();
				auc1.put("auction_id", auc.getId());
				auc1.put("resource_id", auc.getResource().getId());
				auc1.put("amount", auc.getAmount());
				auc1.put("user_id", auc.getUser().getId());
				auc1.put("price", auc.getPrice());
				auc1.put("timeleft_sec", auc.getTimeleftSec());
		  		auctions.add(auc1);
			}
			data.put("auctions", auctions);
	  		con.respond(this.responseName(), data);
	}
}