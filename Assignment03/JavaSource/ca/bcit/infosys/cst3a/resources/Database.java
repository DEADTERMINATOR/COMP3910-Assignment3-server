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
		PreparedStatement quizStatement = null;
		PreparedStatement questionStatement = null;
		PreparedStatement answerStatement = null;
		PreparedStatement avgStatement = null;
		PreparedStatement userQuizStatement = null;
        Connection connection = null;
        boolean quizTaken = quizTaken(week, user);
        try {
        	try {
        		ArrayList<Question> questions = new ArrayList<Question>();
        		connection = data.getConnection();
        		quizStatement = connection.prepareStatement("SELECT quizID FROM Quiz WHERE week = ?");
        		quizStatement.setInt(1, week);
        		ResultSet quizResult = quizStatement.executeQuery();
        		int quizID = quizResult.getInt("quizID");
        		quizStatement.close();
        		questionStatement = connection.prepareStatement("SELECT * FROM Question WHERE week = ?");
        		questionStatement.setInt(1, week);
        		ResultSet questionResult = questionStatement.executeQuery();
        		questionStatement.close();
        		while(questionResult.next()) {
        			int questionID = questionResult.getInt("questionID");
        			String question = questionResult.getString("question");
        			ArrayList<Answer> answers = new ArrayList<Answer>();
        			answerStatement = connection.prepareStatement("SELECT * FROM Answer WHERE questionID = ?");
        			answerStatement.setInt(1, questionID);
        			ResultSet answerResult = answerStatement.executeQuery();
        			while(answerResult.next()) {
        				int answerID = answerResult.getInt("answerID");
        				String answer = answerResult.getString("answer");
        				answers.add(new Answer(answerID, answer));
        			}
        			questions.add(new Question(questionID, question, answers));
        			answerStatement.close();
        		}
        		if(!questions.isEmpty()) {
        			if(!quizTaken) {
        				return new Quiz(quizID, week, questions, -1, -1);
        			}
        			else {
        				avgStatement = connection.prepareStatement("SELECT AVG(score) AS 'average' FROM UserQuiz WHERE username = ?");
        				avgStatement.setString(1, user.getUsername());
        				ResultSet avgScoreResult = avgStatement.executeQuery();
        				avgScoreResult.next();
        				int avgScore = avgScoreResult.getInt("average");
        				avgStatement.close();
        				userQuizStatement = connection.prepareStatement("SELECT score FROM UserQuiz WHERE week = ?");
        				userQuizStatement.setInt(1, week);
        				ResultSet scoreResult = userQuizStatement.executeQuery();
        				scoreResult.next();
        				int score = scoreResult.getInt("score");
        				return new Quiz(quizID, week, questions, score, avgScore);
        			}
        		}
        		return null;
        	}
            finally {
                if(quizStatement != null) {
                    quizStatement.close();
                }
                if(questionStatement != null) {
                	questionStatement.close();
                }
                if(answerStatement != null) {
                	answerStatement.close();
                }
                if(avgStatement != null) {
                	avgStatement.close();
                }
                if(userQuizStatement != null) {
                	userQuizStatement.close();
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
                statement = connection.prepareStatement("SELECT week FROM UserQuiz WHERE week = ?");
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
            System.out.println("Error checking if the quiz was taken");
            ex.printStackTrace();
            return false;
        }
	}
}
