package server;

import junit.framework.TestCase;

import jauctionclient.commands.Model;

import java.net.Socket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.io.*;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

public class JAuctionServerTest extends TestCase implements Observer{
    
	jauctionclient.commands.Model m = new jauctionclient.commands.Model();
	protected void setUp() throws Exception {
		if(!m.hasConnection())
		  m.setConnection("localhost", 4444);
		  m.addObserver(this);
	}

	protected void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	public void testMain() {
	}
	
	public void testSignupAndLogin(){
		assertFalse(m.getCurrentUser().isLogon());
		m.getOutputCommands().signUp("mark", "secret");
		m.getOutputCommands().login("mark", "secret");
		while(m.getCurrentUser().getId() == null){}
		
	}
	
	public void testGetCommands() {
		m.getOutputCommands().getCommands();
	}

	public void testLoginAndGetMoney(){
		assertEquals((long)m.getCurrentUser().getMoney(), 0);
        m.getOutputCommands().getMoney();
	}


	public void testLoginAndGetStock(){
		long resource_id = 1;
		assertNull(m.getAllStock().get(resource_id));
        m.getOutputCommands().getStock(resource_id);
	}	

	public void testLoginAndGetStockAll(){
		long resource_id = 2;
		assertNull(m.getAllStock().get(resource_id));
        m.getOutputCommands().getStockAll();
	}	
	
	public void testGetResourceAll() {
		assertEquals((long)m.getAllResource().size(), 0);
        m.getOutputCommands().getResourceAll();
	}	

	public void testGetAllAuctions() {
		assertEquals((long)m.getAllAuction().size(), 0);
        m.getOutputCommands().getAuctionsAll();
	}	

	
	
	public void testLoginAndCreateAuction() {
		assertEquals(0, (long)m.getAllMyAuction().size());
        m.getOutputCommands().createAuction(1, 1, 1, 20);
	}		
	
	public void testGetResource() {
		m.getOutputCommands().getResource(1);
	}		
	
	public void testResponses(){
		while(m.hasConnection() && !m.connectionClosed()){
			
		}
	}
	
	public void update(Observable obs, Object obj){
		if (obj.equals("login")){
			assertTrue(m.getCurrentUser().isLogon());
		}
		if(obj.equals("get_money")){
			assertNotNull(m.getCurrentUser().getMoney());
		}
		if(obj.equals("get_stock")){
	        assertNotNull(m.getAllStock().get((long)1));
		}
		if(obj.equals("get_stock_all")){
	        assertNotNull(m.getAllStock().get((long)2));
		}
		if(obj.equals("get_resource_all")){
			assertTrue(m.getAllResource().size() > 0);
		}
		if(obj.equals("get_auctions_all")){
			assertTrue(m.getAllAuction().size() > 0);
		}
		if(obj.equals("create_auction")){
			System.err.println("test");
			assertEquals(1, (long)m.getAllMyAuction().size());
			m.getOutputCommands().Quit();
		}
		System.err.println(obj);
	}
}
