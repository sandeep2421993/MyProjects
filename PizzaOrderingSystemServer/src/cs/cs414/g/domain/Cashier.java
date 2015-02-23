package cs.cs414.g.domain;

import java.util.Map;

public class Cashier {

	public String name;
	PizzaStore store;
	public Map<String,String> loginUidPwd;

	public Cashier(String name, PizzaStore pizzaStore) {
		this.name = name;
		this.store = pizzaStore;
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
	
	public Map<String, String> getLoginUidPwd() {
		return loginUidPwd;
	}

	public void setLoginUidPwd(Map<String, String> loginUidPwd) {
		this.loginUidPwd = loginUidPwd;
	}
	
}
