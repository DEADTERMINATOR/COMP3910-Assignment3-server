package ca.bcit.infosys.cst3a.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import ca.bcit.infosys.cst3a.model.User;

/**
 * Provides CRUD operations for the UserResource class.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@Stateless
public class UserDatabase {
	/** The data source associated with the database. */
	@Resource(mappedName = "java:jboss/datasources/quizServer")
	private DataSource data;
	
	/**
	 * Gets the user given a specified username.
	 * 
	 * @param username - the user's username used to retrieve the user
	 * @return the user specified by the username
	 */
	public User getUser(String username) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
                connection = data.getConnection();
                statement = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
                statement.setString(1, username);
                ResultSet result = statement.executeQuery();
                if(result.next()) {
                	User user = new User(result.getString("studentNo"), result.getString("username"),
                			result.getString("password"), result.getString("firstName"), result.getString("lastName"));
                	return user;
                }
                else {
                	return null;
                }
            } finally {
            	if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving users");
            ex.printStackTrace();
            return null;
        }
	}
	
	/**
	 * Validates that the password entered on the client side for the user matches the one stored
	 * in the database.
	 * 
	 * @param user - the user trying to login
	 * @param password - the password provided by the user
	 * @return true if the password entered is correct, false if it is not
	 */
	public boolean validate(User user, String password) {
		if(user.getPassword().equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds a user to the database when they register, provided that the username for this
	 * potential new user does not already exist. If the username does not exist, the new user
	 * is added, if if does then the new user is not added.
	 * 
	 * @param user - the user to add
	 * @return true if the addition was successful, false if it was not.
	 */
	public boolean add(User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
                connection = data.getConnection();
                User duplicateUser = getUser(user.getUsername());
                if(duplicateUser == null) {
                	statement = connection.prepareStatement("INSERT INTO User VALUES (?, ?, ?, ?, ?)");
                	statement.setString(1, user.getStudentNo());
                	statement.setString(2, user.getUsername());
                	statement.setString(3, user.getPassword());
                	statement.setString(4, user.getFirstName());
                	statement.setString(5, user.getLastName());
                	statement.executeUpdate();
                	return true;
                }
                else {
                	return false;
                }
            }
            finally {
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("Error adding users");
            ex.printStackTrace();
            return false;
        }
    }
}
