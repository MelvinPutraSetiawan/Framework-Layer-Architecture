package utilities;

import observer.User;

public class Session {
	private static User currentUser;
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User user) {
		currentUser = user;
		System.out.println("Logged in as : "+user.getName()+" | Email : "+user.getEmail());
	}
	
	public static void clearSession() {
		currentUser = null;
		System.out.println("Logout!");
	}
}
