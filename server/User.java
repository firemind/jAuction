package server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ArrayList;

public class User {
  private int money;
  private ArrayList<Integer> stock = new ArrayList();
  private byte[] auth_key;
  User(String username, String password){
    try {
	  MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	  digest.update((username + password).getBytes());
	  this.auth_key = digest.digest();
	 }catch(NoSuchAlgorithmException e){
       System.out.println("No Such Algorithm");
  	 }
	 this.money = 5000;
	 this.stock.add(0, 5);
	 this.stock.add(1, 10);
  }
  
  public int getMoney(){
	  return this.money;
  }
  
  public int getStock(int resource_id){
	  if(resource_id >= 0 && this.stock.size() > resource_id){
		  return this.stock.get(resource_id);
	  }else{
		  return 0;
	  }
  }
  
  public String getAuthKey(){
	  return this.auth_key.toString();
  }
}
