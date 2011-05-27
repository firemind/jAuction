package server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User {
  private long id;
  private int money;
  private HashMap<Long, Integer> stock = new HashMap<Long, Integer>();
  private byte[] auth_key;
  User(long id, String username, String password){
    try {
	  MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	  digest.update((username + password).getBytes());
	  this.auth_key = digest.digest();
	 }catch(NoSuchAlgorithmException e){
       System.out.println("No Such Algorithm");
  	 }
	 this.id = id;
	 this.money = 5000;
	 long r_id1 = (long) 0;
	 long r_id2 = (long) 1;
	 long r_id3 = (long) 2;
	 this.stock.put(r_id1, 150);
	 this.stock.put(r_id2, 50);
	 this.stock.put(r_id3, 100);
  }
  
  public int getMoney(){
	  return this.money;
  }
  
  public int getStock(long resource_id){
	  if(this.stock.containsKey(resource_id)){
		  return this.stock.get(resource_id);
	  }else{
		  System.out.println("don't have stock "+resource_id);
		  return 0;
	  }
  }
  
  public String getAuthKey(){
	  return this.auth_key.toString();
  }

  public long getId(){
	  return this.id;
  }
}
