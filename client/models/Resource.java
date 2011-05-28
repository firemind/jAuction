package jAuction.models;

import java.util.*;

class Resource extends Observable {
  private static HashMap<Integer, Resource> _all = new HashMap();
  private int _id;
  private String _name;

  private Resource(int id, String name) {
    this._id = id;
    this._name = name;
  }
  
  public static void add(int id, String name) {
    Resource resource = new Resource(id, name);
    gAll().put(resource.gId(), resource);
  }

  public static HashMap gAll()
  { return _all; }

  public int gId()
  { return _id; }

  public String gName()
  { return _name; }
}
