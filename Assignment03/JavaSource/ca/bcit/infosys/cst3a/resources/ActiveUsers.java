package ca.bcit.infosys.cst3a.resources;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;

import ca.bcit.infosys.cst3a.model.User;

@ApplicationScoped
public class ActiveUsers {
	private static HashMap<String, User> loggedIn = new HashMap<String, User>();
	
	public ActiveUsers(){}
	
	public static HashMap<String, User> getLoggedIn() {
		return loggedIn;
	}
	
	public static void addLoggedInUser(String token, User user) {
		loggedIn.put(token, user);
	}
	
	public static User getLoggedInUser(String token) {
		User user = loggedIn.get(token);
		if(user != null) {
			return user;
		}
		return null;
	}
	
	public static void removeLoggedInUser(String token) {
		loggedIn.remove(token);
	}
	
	public static boolean validateUser(String token) {
		if(loggedIn.containsKey(token)) {
			return true;
		}
		return false;
	}
}
