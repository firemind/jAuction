package server.ServerCommands;

import java.util.Hashtable;

import server.Connection;

public class ServerCommandFactory {
    private Hashtable<String, String> serverCommands = new Hashtable<String, String>();
    
    public Hashtable<String, String> getServerCommands(){
    	return this.serverCommands;
    }
    
    public ServerCommandFactory(){
    	serverCommands.put("login", "Login");
    	serverCommands.put("signup", "Signup");
    	serverCommands.put("get_commands", "GetCommands");
    	serverCommands.put("get_money", "GetMoney");
    	serverCommands.put("get_stock", "GetStock");
    	serverCommands.put("get_stock_all", "GetStockAll");
    	serverCommands.put("get_resource", "GetResource");
    	serverCommands.put("get_resource_all", "GetResourceAll");
    	serverCommands.put("get_auctions", "GetAuctions");
    	serverCommands.put("get_auctions_all", "GetAuctionsAll");
    	serverCommands.put("create_auction", "CreateAuction");
    	serverCommands.put("buy_auction", "BuyAuction");
    	serverCommands.put("cancel_auction", "CancelAuction");
    	serverCommands.put("quit", "Quit");
    }
	public ServerCommand getCommand(String name, Connection con){

		ServerCommand sc = null;
		try {
		  if(serverCommands.containsKey(name)){
			  Class cla = (Class) Class.forName("server.ServerCommands."+serverCommands.get(name));
				try {
					sc = (ServerCommand) cla.getDeclaredConstructor(con.getClass()).newInstance(con);
				}catch(Exception e){
					System.out.println("Dynamic ServerCommand loading failed");
					e.printStackTrace();
				}
		  }else{
			  System.out.println("Unknown Command "+name);
		  }
		}catch(ClassNotFoundException e){
			System.out.println("Class not found");
			e.printStackTrace();
		}
		return sc;
	}
}
