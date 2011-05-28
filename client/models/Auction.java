package jAuction.models;

import java.util.*;

class Auction extends Observable {
  private static HashMap<Integer, Auction> _all = new HashMap();
  private int _id;
  private int _user_id;
  private int _amount;
  private int _resource_id;
  private double _price;
  private int _timeleft_sec;

  private Auction(int id, int user_id, int amount, int resource_id, double price, int timeleft_sec) {
    this._id = id;
    this._user_id = user_id;
    this._amount = amount;
    this._resource_id = resource_id;
    this._price = price;
    this._timeleft_sec = timeleft_sec;
  }
  
  public static void add(int id, int user_id, int amount, int resource_id, double price, int timeleft_sec) {
    Auction auction = new Auction(id, user_id, amount, resource_id, price, timeleft_sec);
    gAll().put(auction.gId(), auction);
  }

  public static HashMap gAll()
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
