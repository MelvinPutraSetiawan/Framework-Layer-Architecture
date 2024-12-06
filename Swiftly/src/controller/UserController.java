package controller;

import java.util.ArrayList;

import observer.Admin;
import observer.Trader;
import observer.User;

public class UserController {
	private static ArrayList<User> users = new ArrayList<>();
	
	public UserController() {
		
	}
	
	public void createAdmin() {
		users.add(new Admin(1, "Admin", "admin@gmail.com", "admin1234"));
		users.add(new Trader(2, "Trader", "trader@gmail.com", "trader1234"));
	}
	
	// Register User Checking
	public String registerUser(String name, String email, String password, String confirmPassword, Boolean atsChecked) {
		if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			return "All field must be filled!";
		}else if(email.contains("@")==false) {
			return "Email must contain '@'!";
		}else if(password.length() < 8) {
			return "Password must be more than 8 characters!";
		}else if(password.equals(confirmPassword)==false) {
			return "Password and Confirm Password must match!";
		}else if(!atsChecked) {
			return "Must accept term and condition";
		}
		for (User user : users) {
			if(user.getEmail().equals(email)) {
				return "Email has been used!";
			}
		}
		int id=1;
		if(users.size()>0) {
			users.get(users.size()-1).getId();
		}
		users.add(new Trader(id, name, email, password));
		return null;
	}
	
	// Login User
	public String loginUser(String email, String password) {
		if(email.isEmpty() || password.isEmpty()) {
			return "All field must be filled!";
		} else if(email.contains("@")==false) {
			return "Email must contain '@'!";
		}
		for (User user : users) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				user.login();
				return null;
			}
		}
		return "Invalid user and password!";
	}
	
	public User getUserById(int UserId)
	{
		for (User user : users) {
			if (user.getId()==UserId) {
				return user;
			}
		}
		return null;
	}
	public static ArrayList<User> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<User> users) {
		UserController.users = users;
	}
}
