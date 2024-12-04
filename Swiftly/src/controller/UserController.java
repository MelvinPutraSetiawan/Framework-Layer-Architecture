package controller;

import java.util.ArrayList;

import model.User;

public class UserController {
	private static ArrayList<User> users = new ArrayList<>();
	
	public UserController() {
		
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
		return null;
	}
}
