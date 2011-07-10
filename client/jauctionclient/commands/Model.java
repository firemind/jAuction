package jauctionclient.commands;

import jauctionclient.connection.Connection;
import jauctionclient.connection.JSONEvent;
import jauctionclient.connection.JSONListener;
import jauctionclient.datamodel.AllAuction;
import jauctionclient.datamodel.AllResource;
import jauctionclient.datamodel.AllStock;
import jauctionclient.datamodel.AllUser;
import jauctionclient.datamodel.Auction;
import jauctionclient.datamodel.CurrentUser;
import jauctionclient.datamodel.DataObjectEvent;
import jauctionclient.datamodel.DataObjectListener;
import jauctionclient.datamodel.Resource;
import jauctionclient.datamodel.Stock;
import jauctionclient.datamodel.User;

import java.io.IOException;
import java.net.ConnectException;
import java.util.*;

import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;

import org.json.*;

public class Model extends Observable {
	private final AllAuction all_auction = new AllAuction();
	private final AllAuction all_my_auction = new AllAuction();
	private final AllResource all_resource = new AllResource();
	private final AllStock all_stock = new AllStock();
	private final AllUser all_user = new AllUser();
	private final CurrentUser current_user = CurrentUser.getInstance();
	private final Integer maximal_auction_price = 10000;
	private final Queue<String> messages = new LinkedList<String>();
	private Connection connection;
	private String host;
	private Integer port;
	
	private OutputCommands output_commands;
	private InputCommands input_commands;
	
	public Model() {
		ClientCommand.setModel(this);
		this.output_commands = new OutputCommands();
		this.input_commands = new InputCommands();
		
		all_auction.addDataObjectListener(output_commands); 
		all_my_auction.addDataObjectListener(output_commands);
		all_resource.addDataObjectListener(output_commands);
		all_stock.addDataObjectListener(output_commands);
		all_user.addDataObjectListener(output_commands);
	}

	public CurrentUser getCurrentUser() { return current_user; }

	public OutputCommands getOutputCommands() { return output_commands; }
	
	public InputCommands getInputCommands() { return input_commands; }
	

	public AllAuction getAllAuction() { return all_auction; }
	
	public AllAuction getAllMyAuction() { return all_my_auction; }
	
	public AllResource getAllResource() { return all_resource; }

	public AllStock getAllStock() { return all_stock; }

	public AllUser getAllUser() { return all_user; }
	

	public int getMaximalAuctionPrice() {
		return maximal_auction_price;
	}
	
	
	public boolean setConnection(String host, Integer port) {
		if(connection == null) 
		{
			try {
				this.connection = new Connection(host, port);
				connection.addJSONListener(this.getInputCommands());
				getOutputCommands().addJSONListener(connection);
	
				this.host = host;
				this.port = port;
				return true;
			} catch (ConnectException e) {
				System.out.println("Connection refused");
			} catch (IOException e) {
				System.out.println("Connection failed");
				e.printStackTrace();
			}
		}
		connection = null;
		return false;
	}

	public String getHost() {
		return host;
	}
	
	public Integer getPort() {
		return port;
	}

	public DefaultTableModel getAllStockTable() {
		Object[][] stock = new Object[getAllStock().size()][5];
		Object[] tableHeader = {
				"Resource",
				"Amount"
		};
		
		int i = 0;
		for(Stock s : getAllStock().getAll().values()){
			if(getAllResource().get(s.getResourceId()) == null) {
				stock[i][0] = "-";
			} else {
				stock[i][0] = getAllResource().get(s.getResourceId()).getName();
			}
			stock[i][1] = s.getAmount();
			i++;
		}
		
		return new NoEditableTableModel(stock, tableHeader);
	}
	
	public DefaultTableModel getAllResourceTable() {
		Object[] tableHeader = {
				"Resource",
				"Name"
		};
		Object[][] resource = getAllResource().getAllTable();		
		
		return new NoEditableTableModel(resource, tableHeader);
	}
	
	public DefaultTableModel getAllAuctionTable() {
		Object[][] auction = new Object[getAllAuction().size()][5];
		Object[] tableHeader = {
				"Id",
				"Resource",
				"Amount",
				"Price",
				"Timeleft"
		};
		
		auction = new Object[getAllAuction().size()][5];
		
		int i = 0;
		for(Auction auc : getAllAuction().getAll().values()){
			auction[i][0] = auc.getId();
			if(getAllResource().get(auc.getResourceId()) == null) {
				auction[i][1] = "-";
			} else {
				auction[i][1] = getAllResource().get(auc.getResourceId()).getName();
			}
			auction[i][2] = auc.getAmount();
			auction[i][3] = auc.getPrice();
			auction[i][4] = auc.getTimeleftSec();
			i++;
		}

		return new NoEditableTableModel(auction, tableHeader);
	}
	
	public DefaultTableModel getAllMyAuctionTable() {
		Object[][] auction = new Object[getAllMyAuction().size()][5];
		Object[] tableHeader = {
				"Id",
				"Resource",
				"Amount",
				"Price",
				"Timeleft"
		};
		
		int i = 0;
		for(Auction auc : getAllMyAuction().getAll().values()){
			auction[i][0] = auc.getId();
			if(getAllResource().get(auc.getResourceId()) == null) {
				auction[i][1] = "-";
			} else {
				getAllResource().get(auc.getResourceId()).getName();
			}
			auction[i][2] = auc.getAmount();
			auction[i][3] = auc.getPrice();
			auction[i][4] = auc.getTimeleftSec();
			i++;
		}

		return new NoEditableTableModel(auction, tableHeader);
	}
	
	public class NoEditableTableModel extends DefaultTableModel{
		public NoEditableTableModel(Object[][] content, Object[] header) {
			super(content, header);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) { return false; }
	};

	
	protected void setMessage(String message) { this.messages.add(message); }

	public String getMessage() { return messages.poll(); }

	protected void setModelChanged(){
		setChanged();
	}
	
	public Resource[] getAllStockArray() {
		Resource[] resources = new Resource[getAllStock().size()];
		int i = 0;
		for(Resource r : getAllResource().getAll().values()){
			resources[i++] = r;
		}
		
		return resources;
	}
	
	
	/*------------------------------------------------------------------------------------------------*/
	

	public class OutputCommands implements DataObjectListener {
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
		
		@Override
		public void getDataObject(DataObjectEvent e) {
			String class_name = e.getClassName();
			if (class_name.equals("Auction")) {
				getAllAuction();
			} else if (class_name.equals("Resource")) {
				getResource(e.getId());
			} else if (class_name.equals("Stock")) {
				getStock(e.getId());
			}
		}
		
		/*Commands*/
		
		
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
			
			current_user.setName(username);
			current_user.setPassword(password);

			notifyJSON(command);
		}
		
		
		public void signUp(String username, String password){
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "signup");
			command.put("data", data);
			
			data.put("username", username);
			data.put("password", password);
			
			current_user.setName(username);

			notifyJSON(command);
		}
		
		public void getMoney(){
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_money");
			command.put("data", data);
			
			data.put("auth_key", current_user.getAuthId());

			notifyJSON(command);
		}
		
		public void getStock(long resource_id) {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_stock");
			command.put("data", data);
			
			data.put("resource_id", resource_id);
			data.put("auth_key", current_user.getAuthId());

			notifyJSON(command);			
		}
		
		public void getStockAll() {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_stock_all");
			command.put("data", data);
			
			data.put("auth_key", current_user.getAuthId());

			notifyJSON(command);			
		}
		
		public void getResource(long resource_id) {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_resource");
			command.put("data", data);
			
			data.put("resource_id", resource_id);

			notifyJSON(command);
		}
		
		public void getResourceAll(){
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_resource_all");
			command.put("data", data);

			notifyJSON(command);			
		}

//		public void getAuction(long auction_id) {
//			HashMap<String, Object> command = new HashMap<String, Object>();
//			HashMap<String, Object> data = new HashMap<String, Object>();
//			
//			command.put("command", "get_auctions");
//			command.put("data", data);
//			
//			data.put("auction_id", auction_id);
//
//			notifyJSON(command);
//		}	
		
		public void getAuctionsAll() {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "get_auctions_all");
			command.put("data", data);
			
			data.put("auth_key", current_user.getAuthId());
			
			notifyJSON(command);			
		}
		
		public void buyAuction(long auction_id) {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "buy_auction");
			command.put("data", data);
			
			data.put("auction_id", auction_id);
			data.put("auth_key", current_user.getAuthId());

			notifyJSON(command);			
		}
		
		public void cancelAuction(long auction_id) {
			HashMap<String, Object> command = new HashMap<String, Object>();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "cancel_auction");
			command.put("data", data);
			
			data.put("auction_id", auction_id);
			data.put("auth_key", current_user.getAuthId());

			notifyJSON(command);			
		}
		
//		public void getMyAuctions() {
//			HashMap<String, Object> command = new HashMap<String, Object>();
//			HashMap<String, Object> data = new HashMap<String, Object>();
//			
//			command.put("command", "get_my_auctions");
//			command.put("data", data);
//			
//			data.put("auth_key", current_user.getAuthId());
//
//			notifyJSON(command);			
//		}
		
		public void createAuction(long resource_id, int amount, int price, long duration){
			Map<String, Object> command = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			
			command.put("command", "create_auction");
			command.put("data", data);
			
			data.put("auth_key", current_user.getAuthId());
			data.put("resource_id", resource_id);
			data.put("amount", amount);
			data.put("price", price);
			data.put("duration", duration);
			
			notifyJSON(command);
			
		}

		public void getStartUpData() {
			getMoney();
			getResourceAll();
			getStockAll();
			getAuctionsAll();
		}
	}
	
	public class InputCommands implements JSONListener {
		private final ClientCommandFactory factory = new ClientCommandFactory();
		
		
		protected InputCommands(){
		}
		
		@Override
		public void jsonReceived(JSONEvent e) {
			ClientCommand command;
			try {
				command = this.factory.getCommand(e.getJson().getString("command"));
				if(command.parseInput(e.getJson())) {
					command.run();
				} else {
					System.out.println("Bad command: "+e.getJson().getString("command"));
				}
			} catch (JSONException je) {
				System.out.println("Bad Request:"+ e.getJson().toString());
			}
		}		
	}

}