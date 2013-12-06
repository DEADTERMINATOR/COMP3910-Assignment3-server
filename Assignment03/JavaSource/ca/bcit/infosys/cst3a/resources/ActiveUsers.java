package ca.bcit.infosys.cst3a.resources;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;

import ca.bcit.infosys.cst3a.model.User;

@ApplicationScoped
public class ActiveUsers {
	/** A map containing all the currently valid tokens and the users they belong to. */
	private static HashMap<String, User> loggedIn = new HashMap<String, User>();
	
	public ActiveUsers(){}
	
	/**
	 * Gets the map of logged in users.
	 * 
	 * @return the map of logged in users.
	 */
	public static HashMap<String, User> getLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * Puts a token and the user it was assigned to into the map.
	 * 
	 * @param token - the randomly generated token
	 * @param user - the user the token was assigned to.
	 */
	public static void addLoggedInUser(String token, User user) {
		loggedIn.put(token, user);
	}
	
	/**
	 * Gets a user from the map using a specified token.
	 * 
	 * @param token - the token to use to retrieve the user
	 * @return the user if the retrieval was successful, or null if it was not
	 */
	public static User getLoggedInUser(String token) {
		User user = loggedIn.get(token);
		if(user != null) {
			return user;
		}
		return null;
	}
	
	/**
	 * Removes a user and their token from the map.
	 * 
	 * @param token - the token to be removed
	 */
	public static void removeLoggedInUser(String token) {
		loggedIn.remove(token);
	}
	
	/**
	 * Validates if the given token exists in the map (therefore it is a valid token assigned
	 * to a user).
	 * 
	 * @param token - the token to check
	 * @return true if the token is valid, false if it is not
	 */
	public static boolean validateUser(String token) {
		if(loggedIn.containsKey(token)) {
			return true;
		}
		return false;
	}
}
