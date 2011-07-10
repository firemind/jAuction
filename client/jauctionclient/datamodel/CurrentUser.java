package jauctionclient.datamodel;

public class CurrentUser extends DataObject {
	private static CurrentUser current_user;
	private Boolean is_logon = false;
	private String name = "";
	private Integer money = 0;
	private String auth_id = "";
	private String password = "";
	
	private CurrentUser(){	
	}

	public static CurrentUser getInstance(){
		if(current_user == null){
			current_user = new CurrentUser();
		}
		return current_user;
	}
	
	public void setId(Long id) {
		super.setId(id);
	}
	

	public Boolean isLogon() {
		return is_logon; 
	}
	public void setLogon(Boolean is_logon) {
		this.is_logon = is_logon; 
		firePropertyChange("logon", null, is_logon);
	}

	
	public String getName() { 
		return name; 
	}
	public void setName(String name) { 
		String oldName = this.name;
		this.name = name; 
		firePropertyChange("name", oldName, this.name);
	}

	
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		Integer oldMoney = this.money;
		this.money = money;
		firePropertyChange("name", oldMoney, this.money);
	}

	
	public String getAuthId() {
		return auth_id; 
	}
	public void setAuthId(String auth_id) { 
		String oldAuthId = this.auth_id;
		this.auth_id = auth_id; 
		firePropertyChange("authId", oldAuthId, this.auth_id);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}