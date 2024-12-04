package model;

public class User {
	private int id, money;
	private String name, email, password;
	
	public User(int id, int money, String name, String email, String password) {
		this.id = id;
		this.money = money;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
