package ca.bcit.infosys.cst3a.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import ca.bcit.infosys.cst3a.model.Answer;
import ca.bcit.infosys.cst3a.model.Question;
import ca.bcit.infosys.cst3a.model.Quiz;
import ca.bcit.infosys.cst3a.model.User;

/**
 * Provides CRUD operations for the QuizResource class.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@Stateless
public class QuizDatabase {
	/** The data source associated with the database. */
	@Resource(mappedName = "java:jboss/datasources/quizServer")
	private DataSource data;
	
	/**
	 * Gets a quiz given a specified week and user. If the quiz has been taken, the quiz is returned
	 * with the score stored in the database for the specified user, as well as the current average
	 * score across all quizzes. If the quiz has not been taken, the quiz is returned with a value
	 * of -1 for both the score and average score. This allows the client side to differentiate between
	 * the two cases and react accordingly.
	 * 
	 * @param week - the week of the quiz to retrieve
	 * @param user - the user who is logged in to take the quiz
	 * @return the quiz for the specified week, with either the score and average score, or values of -1
	 * for both of those values.
	 */
	public Quiz getQuiz(int week, User user) {
		PreparedStatement quizStatement = null;
		PreparedStatement questionStatement = null;
		PreparedStatement answerStatement = null;
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
        		quizResult.next();
        		int quizID = quizResult.getInt("quizID");
        		questionStatement = connection.prepareStatement("SELECT * FROM Question WHERE week = ?");
        		questionStatement.setInt(1, week);
        		ResultSet questionResult = questionStatement.executeQuery();
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
        		}
        		if(!questions.isEmpty()) {
        			if(!quizTaken) {
        				return new Quiz(quizID, week, questions, -1, -1);
        			}
        			else {
        				int avgScore = getAverageScore(user);
        				userQuizStatement = connection.prepareStatement("SELECT score FROM UserQuiz WHERE week = ? AND userName = ?");
        				userQuizStatement.setInt(1, week);
        				userQuizStatement.setString(2, user.getUsername());
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
	
	/**
	 * Checks if a quiz for a specified week has been taken previously for the specified user.
	 * 
	 * @param week - the week of the quiz to retrieve
	 * @param user - the user who is logged in to take the quiz
	 * @return true if the quiz has been taken, false if it has not.
	 */
	public boolean quizTaken(int week, User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
            	connection = data.getConnection();
                statement = connection.prepareStatement("SELECT week FROM UserQuiz WHERE week = ? AND userName = ?");
                statement.setInt(1, week);
                statement.setString(2, user.getUsername());
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
	
	/**
	 * Checks the answers provided by the user for a quiz's questions versus the correct answers
	 * stored in the database and determines the score for the quiz.
	 * 
	 * @param week - the week of the quiz to mark
	 * @param user - the user who is logged in to take the quiz
	 * @param userAnswers - the list of answers provided by the user
	 * @return the score
	 */
	public int checkAnswers(int week, User user, ArrayList<Integer> userAnswers) {
		int score = 0;
		PreparedStatement answerStatement = null;
		PreparedStatement questionStatement = null;
        Connection connection = null;
        try {
            try {
            	connection = data.getConnection();
                answerStatement = connection.prepareStatement("SELECT questionID FROM Answer WHERE answerID = ?");
                questionStatement = connection.prepareStatement("SELECT answerID FROM Question WHERE questionID = ?");
                for(Integer answer: userAnswers) {
                	answerStatement.setInt(1, answer);
                	ResultSet answerSet = answerStatement.executeQuery();
                	answerSet.next();
                	int questionID = answerSet.getInt("questionID");
                	questionStatement.setInt(1, questionID);
                	ResultSet correctAnswer = questionStatement.executeQuery();
                	correctAnswer.next();
                	int correctAnswerID = correctAnswer.getInt("answerID");
                	if(answer == correctAnswerID) {
                		score++;
                	}
                }
                insertScore(week, user, score);
                return score;
            }
            finally {
            	if(answerStatement != null) {
            		answerStatement.close();
            	}
            	if(questionStatement != null) {
            		questionStatement.close();
            	}
            	if(connection != null) {
            		connection.close();
            	}
            }
        }
        catch (SQLException ex) {
        	System.out.println("Error checking the answers");
        	ex.printStackTrace();
        	return -1;
        }
	}
	
	/**
	 * Inserts the score determined in checkAnswers into the database for the provided user.
	 * 
	 * @param week - the week of the quiz to retrieve
	 * @param user - the user who is logged in to take the quiz
	 * @param score - the score for the quiz
	 */
	private void insertScore(int week, User user, int score) {
		PreparedStatement statement = null;
        Connection connection = null;
        try {
            try {
                connection = data.getConnection();
                statement = connection.prepareStatement("INSERT INTO UserQuiz VALUES(?, ?, ?)");
                statement.setString(1, user.getUsername());
                statement.setInt(2, week);
                statement.setInt(3, score);
                statement.executeUpdate();
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
        	System.out.println("Error updating score");
        	ex.printStackTrace();
        }
	}
	
	/**
	 * Determines the average score across all taken quizzes for the specified user.
	 * 
	 * @param user - the user to get the average of
	 * @return the average score
	 */
	public int getAverageScore(User user) {
		PreparedStatement statement = null;
        Connection connection = null;
        int avgScore = -1;
        try {
            try {
            	connection = data.getConnection();
            	statement = connection.prepareStatement("SELECT AVG(score) AS 'average' FROM UserQuiz WHERE username = ?");
				statement.setString(1, user.getUsername());
				ResultSet avgScoreResult = statement.executeQuery();
				avgScoreResult.next();
				avgScore = avgScoreResult.getInt("average");
				return avgScore;
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
        	System.out.println("Error getting the average score");
        	ex.printStackTrace();
        	return -1;
        }
	}
}
