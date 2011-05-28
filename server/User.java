package server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User {
  private long id;
  private long money;
  private HashMap<Long, Long> stock = new HashMap<Long, Long>();
  private byte[] auth_key;
  private String username;
  private String password;
  
  User(long id, String username, String password){
    try {
	  MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	  digest.update((username + password).getBytes());
	  this.auth_key = digest.digest();
	 }catch(NoSuchAlgorithmException e){
       System.out.println("No Such Algorithm");
  	 }
	 this.username = username;
	 this.password = password;
	 this.id = id;
	 this.money = 5000;
	 long r_id1 = (long) 0;
	 long r_id2 = (long) 1;
	 long r_id3 = (long) 2;
	 long v_id1 = (long) 150;
	 long v_id2 = (long) 50;
	 long v_id3 = (long) 100;
	 this.stock.put(r_id1, v_id1);
	 this.stock.put(r_id2, v_id2);
	 this.stock.put(r_id3, v_id3);
  }
  
  public long getMoney(){
	  return this.money;
  }
  
  public long getStock(long resource_id){
	  if(this.stock.containsKey(resource_id)){
		  return this.stock.get(resource_id);
	  }else{
		  System.out.println("don't have stock "+resource_id);
		  return 0;
	  }
  }
  
  public void loseMoney(long amount){
	  this.money -= amount;
  }
  
  public void addStock(long res_id, long amount){
	  if(this.stock.containsKey(res_id)){
		  long prev_amount = this.stock.get(res_id);
		  this.stock.put(res_id, prev_amount+amount);
	  }else{
		  this.stock.put(res_id, amount);
	  }
  }
  
  public boolean authenticate(String u, String p){
	  if(u.equals(this.username) && p.equals(this.password)){
		  return true;
	  }else{
		  return false;
	  }
	  
  }
  
  public String getAuthKey(){
	  return this.auth_key.toString();
  }

  public long getId(){
	  return this.id;
  }
}
