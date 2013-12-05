package ca.bcit.infosys.cst3a.resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import ca.bcit.infosys.cst3a.model.Answer;
import ca.bcit.infosys.cst3a.model.Question;
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
	
	public Quiz getQuiz(int week, User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        boolean quizTaken = quizTaken(week, user);
        try {
        	try {
        		ArrayList<Question> questions = new ArrayList<Question>();
        		connection = data.getConnection();
        		statement = connection.prepareStatement("SELECT quizID FROM Quiz WHERE week = ?");
        		ResultSet quizResult = statement.executeQuery();
        		int quizID = quizResult.getInt("quizID");
        		statement.close();
        		statement = connection.prepareStatement("SELECT * FROM Question WHERE week = ?");
        		statement.setInt(1, week);
        		ResultSet questionResult = statement.executeQuery();
        		if(questionResult.next()) {
        			int questionID = questionResult.getInt("questionID");
        			String question = questionResult.getString("question");
        			ArrayList<Answer> answers = new ArrayList<Answer>();
        			statement = connection.prepareStatement("SELECT * FROM Answer WHERE questionID = ?");
        			ResultSet answerResult = statement.executeQuery();
        			if(answerResult.next()) {
        				int answerID = answerResult.getInt("answerID");
        				String answer = answerResult.getString("answer");
        				answers.add(new Answer(answerID, answer));
        			}
        			questions.add(new Question(questionID, question, answers));
        		}
        		if(!questions.isEmpty()) {
        			if(!quizTaken) {
        				return new Quiz(quizID, week, questions, -1, -1);
        			}
        			else {
        				statement = connection.prepareStatement("SELECT AVG(score) AS 'average' FROM UserQuiz WHERE username = ?");
        				statement.setString(1, user.getUsername());
        				ResultSet avgScoreResult = statement.executeQuery();
        				int avgScore = avgScoreResult.getInt("average");
        				statement = connection.prepareStatement("SELECT score FROM UserQuiz WHERE week = ?");
        				statement.setInt(1, week);
        				ResultSet scoreResult = statement.executeQuery();
        				int score = scoreResult.getInt("score");
        				return new Quiz(quizID, week, questions, score, avgScore);
        			}
        		}
        		return null;
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
            System.out.println("Error getting quiz");
            ex.printStackTrace();
            return null;
        }
	}
	
	public boolean quizTaken(int week, User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
            	connection = data.getConnection();
                statement = connection.prepareStatement("SELECT quizID FROM UserQuiz WHERE week = ?");
                statement.setInt(1, week);
                ResultSet results = statement.executeQuery();
                if(results.next()) {
                	return true;
                }
                return false;
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
            System.out.println("Error getting quiz");
            ex.printStackTrace();
            return false;
        }
	}
}
