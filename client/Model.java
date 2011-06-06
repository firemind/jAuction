import java.util.*;
//import gson;

class Model extends Observable {
  private Connection _connection;
  
  public Model() {
    
  }

  public void extractJson(String json) {
  }

  public void setConnection(Connection connection) {
    _connection = connection;
  }


  protected class CurrentUser {
    private int _id;
    private String _name;
    private int _money;
    private String _auth_id;

    public CurrentUser(){
      
    }

    public boolean authenticate(String username, String password) {
      return true; 
    }
  }
}
