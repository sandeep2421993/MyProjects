package cs.cs414.g.domain;

import java.util.Map;

public class StoreManager {

	public String name;
	PizzaStore store;
	public Map<String,String> loginUidPwd;

	public StoreManager(String managerName, PizzaStore theStore) {
		this.name=managerName;
		this.store=theStore;
	}
	
	public Map<String, String> getLoginUidPwd() {
		return loginUidPwd;
	}

	public void setLoginUidPwd(Map<String, String> loginUidPwd) {
		this.loginUidPwd = loginUidPwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PizzaStore getStore() {
		return store;
	}
	public void setStore(PizzaStore store) {
		this.store = store;
	}
}
