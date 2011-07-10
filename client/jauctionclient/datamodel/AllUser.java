package jauctionclient.datamodel;

import java.util.HashMap;

public class AllUser extends AbstractModelObject{
	private final HashMap<Long, User> all_users = new HashMap<Long, User>();
	
	public void addInstance(User user) {
		all_users.put(user.getId(), user);
		firePropertyChange("allUser", null, all_users);
	}
	
	public void addInstance(Long id, String name) {
		User user = new User(id, name);
		all_users.put(user.getId(), user);
		firePropertyChange("allUser", null, all_users);
	}

	public void removeInstance(Long id) {
		all_users.remove(id);
		firePropertyChange("allUser", null, all_users);
	}
	
	public Integer size(){
		return all_users.size();
	}
	
	public User get(Long id) {
		if(!all_users.containsKey(id)){
			requestDataObject(new DataObjectEvent(this, id, "User"));
			return null;
		}
		return all_users.get(id);
	}	

	public HashMap<Long, User> getAll() { return all_users; }
}
