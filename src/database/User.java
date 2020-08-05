package database;

import com.google.gson.Gson;

public class User {
	
	private String username;
	private String password;
	private String name;
	private Permission permission = new Permission(true,true,true,true,true,true,true,true); 
	private Gson gson = new Gson();
	public User(String username, String password, String name) {
		// TODO Auto-generated constructor stub
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
	}
	public User(String username, String password, String name, Permission permission) {
		// TODO Auto-generated constructor stub
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.permission = permission;
	}
	
	public String getGson() {
		return this.gson.toJson(permission);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Permission getPermission() {
		return permission;
	}
	
	

}
