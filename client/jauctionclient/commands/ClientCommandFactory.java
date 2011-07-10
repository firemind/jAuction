package jauctionclient.commands;

import java.util.Hashtable;

public class ClientCommandFactory {
    private Hashtable<String, String> client_commands = new Hashtable<String, String>();
    
    public ClientCommandFactory(){
    	//client_commands.put("get_commands", "GetCommands");
    	client_commands.put("login", Login.class.toString());
    	client_commands.put("signup", SignUp.class.toString());
    	client_commands.put("get_money", GetMoney.class.toString());
    	client_commands.put("get_stock", GetStock.class.toString());
    	client_commands.put("get_stock_all", GetStockAll.class.toString());
    	client_commands.put("get_resource", GetResource.class.toString());
    	client_commands.put("get_resource_all", GetResourceAll.class.toString());
    	client_commands.put("get_auctions", GetAuctions.class.toString());
    	client_commands.put("get_auctions_all", GetAuctionsAll.class.toString());
    	client_commands.put("buy_auction", BuyAuction.class.toString());
    	client_commands.put("cancel_auction", CancelAuction.class.toString());
    	client_commands.put("create_auction", CreateAuction.class.toString());
    	client_commands.put("get_my_auctions", GetMyAuctions.class.toString());
    	
    	client_commands.put("notify", Notify.class.toString());
    	client_commands.put("new_auction", NewAuction.class.toString());
    	client_commands.put("auction_sold", AuctionSold.class.toString());
    	client_commands.put("auction_remove", AuctionRemoved.class.toString());
    	client_commands.put("auction_removed", AuctionRemoved.class.toString());
    	client_commands.put("update_stock", UpdateStock.class.toString());
    	client_commands.put("update_money", UpdateMoney.class.toString());
    }
    
	public ClientCommand getCommand(String name){
		ClientCommand command = null;
		try {
		  if(this.client_commands.containsKey(name)){
			  Class commandClass = (Class) Class.forName(client_commands.get(name).replaceAll("^class ", ""));
				try {
					command = (ClientCommand) commandClass.newInstance();
				}catch(Exception e){
					System.out.println("Dynamic ClientCommand loading failed");
					e.printStackTrace();
				}
		  }else{
			  System.out.println("Unknown Command "+name);
		  }
		}catch(ClassNotFoundException e){
			System.out.println("Class not found");
			e.printStackTrace();
		}
		return command;
	}
	

    public Hashtable<String, String> getClientCommands() { return client_commands; }
}
