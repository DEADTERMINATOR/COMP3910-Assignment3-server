package ca.bcit.infosys.cst3a.resources;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ca.bcit.infosys.cst3a.access.QuizDatabase;
import ca.bcit.infosys.cst3a.model.Quiz;
import ca.bcit.infosys.cst3a.model.ScoreObject;
import ca.bcit.infosys.cst3a.model.User;

/**
 * Provides interaction and communication with the client side with regards to functionality
 * related to the quizzes.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@Path("/quizzes")
@Stateless
public class QuizResource {
	/** Provides CRUD operations for this class. */
	@EJB
	private QuizDatabase db;
	
	/**
	 * Returns the quiz given a specified week after the user's session is validated by the
	 * provided token.
	 * 
	 * @param week - the week to retrieve the quiz for.
	 * @param token - the randomly generated token used to identify the user's session
	 * @return the quiz for the given week, or null if the session was not validated
	 */
	@GET
	@Path("{week}")
	@Produces("application/xml")
	public Quiz getQuiz(@PathParam("week") int week, @QueryParam("token") String token) {
		boolean validUser = ActiveUsers.validateUser(token);
		if(validUser) {
			User user = ActiveUsers.getLoggedInUser(token);
			if(user != null) {
				return db.getQuiz(week, user);
			}
		}
		return null;
	}
	
	/**
	 * Validates the user's session using the token and scores the quiz for a given week 
	 * based on the answers the user provided.
	 * 
	 * @param so - a object containing the token, the week number, the quizID, and the list of
	 * user answers
	 * @return a string containing both the score for the current quiz and the average score for
	 * the user across all taken quizzes
	 */
	@POST
	@Path("/mark")
	@Consumes("application/xml")
	public String scoreQuiz(ScoreObject so) {
		boolean validUser = ActiveUsers.validateUser(so.getToken());
		if(!validUser) {
			return null;
		}
		User user = ActiveUsers.getLoggedInUser(so.getToken());
		int score = db.checkAnswers(so.getWeek(), user, so.getAnswers());
		int averageScore = db.getAverageScore(user);
		return score + " " + averageScore;
	}
}
