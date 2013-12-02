package ca.bcit.infosys.cst3a.resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import ca.bcit.infosys.cst3a.model.Quiz;
import ca.bcit.infosys.cst3a.model.User;

@Stateless
public class Database {
	/** The data source associated with the database. */
	@Resource(mappedName = "java:jboss/datasources/quizServer")
	private DataSource data;
	
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
                	User user = new User(result.getString("studentID"), result.getString("username"),
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
	
	public boolean validate(User user, String password) {
		if(user.getPassword().equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean add(User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
                connection = data.getConnection();
                User duplicateUser = getUser(user.getUsername());
                if(duplicateUser == null) {
                	statement = connection.prepareStatement("INSERT INTO User "
                			+ "VALUES (?, ?, ?, ?, ?");
                	statement.setString(1, user.getStudentID());
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
	
/*	public Quiz getQuiz(int week) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
                connection = data.getConnection();
                statement = connection.prepareStatement("SELECT * FROM Question q JOIN Answer a "
                		+ "ON q.questionID = a.questionID WHERE q.weekNo = ?");
                statement.setInt(1, week);
                ResultSet result = statement.executeQuery();
                if(result.next()) {
                	
                }
        }
	}*/
}
