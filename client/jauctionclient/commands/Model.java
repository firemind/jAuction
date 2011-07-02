package jauctionclient.commands;

import jauctionclient.JSONEvent;
import jauctionclient.JSONListener;
import jauctionclient.datamodel.AllAuction;
import jauctionclient.datamodel.AllResource;
import jauctionclient.datamodel.AllStock;
import jauctionclient.datamodel.AllUser;
import jauctionclient.datamodel.Auction;
import jauctionclient.datamodel.CurrentUser;
import jauctionclient.datamodel.Resource;
import jauctionclient.datamodel.Stock;
import jauctionclient.datamodel.User;

import java.util.*;

import javax.swing.event.EventListenerList;

import org.json.*;

public class Model extends Observable {
	private final AllAuction all_auction = new AllAuction();
	private final AllResource all_resource = new AllResource();
	private final AllStock all_stock = new AllStock();
	private final AllUser all_user = new AllUser();
	private final CurrentUser current_user = CurrentUser.getInstance();
	
	private OutputCommands output_commands;
	private InputCommands input_commands;
	
	public Model() {
		ClientCommand.setModel(this);
		this.output_commands = new OutputCommands();
		this.input_commands = new InputCommands();
	}	

	public CurrentUser getCurrentUser() { return current_user; }

	public OutputCommands getOutputCommands() { return output_commands; }
	
	public InputCommands getInputCommands() { return input_commands; }
	

	public AllAuction getAllAuction() { return all_auction;}
	
	public AllResource getAllResource() { return all_resource; }

	public AllStock getAllStock() { return all_stock;}
	
	public AllUser getAllUser() { return all_user; }
	
	
	public class OutputCommands {
		private EventListenerList output_listeners = new EventListenerList(); 
		
		public OutputCommands(){
		}
		
		public void addJSONListener(JSONListener listener) { output_listeners.add(JSONListener.class, listener); }
		
		public void removeJSONListener(JSONListener listener) { output_listeners.remove(JSONListener.class, listener); }
		
		protected void notifyJSON(Map<String, Object> output) {
			JSONEvent event = new JSONEvent(this, new JSONObject(output));
			
			for ( JSONListener listener : output_listeners.getListeners( JSONListener.class ) ) 
			      listener.jsonReceived( event );
		}
		
		
		public void createAuction(long resource_id, int amount, int price, int duration){
			Map<String, Object> command = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "create_auction");
			command.put("data", data);
			
			data.put("resource_id", resource_id);
			data.put("amount", amount);
			data.put("price", price);
			data.put("duration", duration);
			
			notifyJSON(command);
		}
		
		
		public void getCommands(){
			Map<String, Object> command = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_commands");
			command.put("data", data);
			
			notifyJSON(command);
		}
		
		
		public void login(String username, String password){
			Map<String, Object> command = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "login");
			command.put("data", data);
			
			data.put("username", username);
			data.put("password", password);

			notifyJSON(command);
		}
		
		
		public void signUp(String username, String password){
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "signup");
			command.put("data", data);
			
			data.put("username", username);
			data.put("password", password);
			
			CurrentUser.getInstance().setName("name");

			notifyJSON(command);
		}
	}
	
	public class InputCommands implements JSONListener {
		private ClientCommandFactory factory;
		
		
		public InputCommands(){
			this.factory = new ClientCommandFactory();
		}
		
		
		@Override
		public void jsonReceived(JSONEvent e) {
			ClientCommand command;
			try {
				command = this.factory.getCommand(e.getJson().getString("command"));
				command.parseJson(e.getJson());
				command.run();
			} catch (JSONException je) {
				System.out.println("Bad Request:"+ e.getJson().toString());
			}
		}

		
		public void newAuction(Auction auction) {
			setChanged();
			getAllAuction().addInstance(auction);
			notifyObservers("allAuction");
		}
		

		public void login(String auth_id) {
			setChanged();
			current_user.setAuthId(auth_id);
			current_user.setLogon(true);
			notifyObservers("login");
		}


		public void auctionSold(Long auction_id) {
			setChanged();
			getAllAuction().removeInstance(auction_id);
			notifyObservers("allAuction");
		}


		public void notifyAuction(String message) {
			setChanged();
			System.out.println("message: "+ message);
			notifyObservers("newMessage");
		}
	}

}