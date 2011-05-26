package server;

import java.util.ArrayList;
import java.util.HashMap;

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
			for( Auction auction : con.getAuctions()){
				HashMap auc1 = new HashMap();
				auc1.put("auction_id", con.getAuctions().indexOf(auction));
				auc1.put("resource_id", con.getResources().indexOf(auction.getResource()));
				auc1.put("amount", auction.getAmount());
				auc1.put("user_id", con.getUsers().indexOf(auction.getUser()));
				auc1.put("price", auction.getPrice());
				auc1.put("timeleft_sec", auction.getTimeleftSec());
		  		auctions.add(auc1);
			}
			data.put("auctions", auctions);
	  		con.respond(this.responseName(), data);
	}
}