package jauctionclient.commands;

import java.util.Hashtable;

public class ClientCommandFactory {
    private Hashtable<String, String> client_commands = new Hashtable<String, String>();
    
    public ClientCommandFactory(){
    	client_commands.put("get_commands", "GetCommands");
    	
    	client_commands.put("notify", "Notify");
    	client_commands.put("new_auction", "NewAuction");
    	client_commands.put("auction_sold", "AuctionSold");
    	client_commands.put("auction_removed", "AuctionRemoved");
    	client_commands.put("update_stock", "UpdateStock");
    	client_commands.put("update_money", "UpdateMoney");
    	
    	client_commands.put("login", "Login");
    	client_commands.put("signup", "SignUp");
    }
    
	public ClientCommand getCommand(String name){
		ClientCommand command = null;
		try {
		  if(this.client_commands.containsKey(name)){
			  Class commandClass = (Class) Class.forName("jauctionclient.commands." + client_commands.get(name));
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
