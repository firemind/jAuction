package server;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetAuctions extends ServerCommand {

	private int resource_id;
	
	GetAuctions(Connection con){
		super(con);
	}
	
	public String name(){
		return "get_auctions";
	}
	
	public JSONObject requestSpecification(){
		HashMap data = new HashMap();
		data.put("resource_id", "Integer");
		return super.specificationMapper("request", data);
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
		try {
			this.resource_id 	= data.getInt("resource_id");
		}catch(net.sf.json.JSONException e){
			con.badRequest();
			return false;
		}
		return true;
	}
	
	public void run(){
	  		HashMap data = new HashMap();
			ArrayList<HashMap> auctions = new ArrayList();
			for( Auction auction : con.getAuctionsByResourceId(resource_id)){
				HashMap auc1 = new HashMap();
				auc1.put("auction_id", con.getAuctions().indexOf(auction));
				auc1.put("resource_id", resource_id);
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