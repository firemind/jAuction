import java.util.*;

class User {
  private static HashMap<Integer, User> _all = new HashMap();
  private int _id;
  private String _name;

  private User(int id, String name) {
    this._id = id;
    this._name = name;
  }
  
  public static void add(int id, String name) {
    User user = new User(id, name);
    gAll().put(user.gId(), user);
  }

  public static HashMap<Integer, User> gAll()
  { return _all; }

  public int gId()
  { return _id; }

  public String gName()
  { return _name; }
}
