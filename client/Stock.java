import java.util.*;

class Stock {
  private static HashMap<Integer, Stock> _all = new HashMap();
  private int _id;
  private int _amount;
  private int _resource_id;

  private Stock(int id, int amount, int resource_id) {
    this._id = id;
    this._amount = amount;
    this._resource_id = resource_id;
  }
  
  public static void add(int id, int amount, int resource_id) {
    Stock stock = new Stock(id, amount, resource_id);
    gAll().put(stock.gId(), stock);
  }

  public static HashMap<Integer, Stock> gAll()
  { return _all; }

  public int gId()
  { return _id; }

  public int gAmount()
  { return _amount; }

  public int gResourceId()
  { return _resource_id; }
}
