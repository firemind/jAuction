import java.util.*;

class Auction {
  private static HashMap<Integer, Auction> _all = new HashMap();
  private int _id;
  private int _user_id;
  private int _amount;
  private int _resource_id;
  private double _price;
  private int _timeleft_sec;

  private Auction(int id, int user_id, int amount, int resource_id, double price, int timeleft_sec) {
	_id = id;
	_user_id = user_id;
	_amount = amount;
	_resource_id = resource_id;
	_price = price;
	_timeleft_sec = timeleft_sec;
  }
  
  public static void add(int id, int user_id, int amount, int resource_id, double price, int timeleft_sec) {
    Auction auction = new Auction(id, user_id, amount, resource_id, price, timeleft_sec);
    gAll().put(auction.gId(), auction);
  }

  public static HashMap<Integer, Auction> gAll()
  { return _all; }

  public int gId()
  { return _id; }

  public int gUserId()
  { return _user_id; }

  public int gAmount()
  { return _amount; }

  public int gResourceId()
  { return _resource_id; }

  public double gPrice()
  { return _price; }

  public int gTimeleftSec()
  { return _timeleft_sec; }
}
