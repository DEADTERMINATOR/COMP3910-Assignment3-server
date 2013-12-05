package ca.bcit.infosys.cst3a.resources;

import java.util.ArrayList;
import java.util.List;

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
import ca.bcit.infosys.cst3a.model.ActiveUsers;
import ca.bcit.infosys.cst3a.model.Quiz;
import ca.bcit.infosys.cst3a.model.User;

@Path("/quizzes")
@Stateless
public class QuizResource {
	@EJB
	private QuizDatabase db;
	
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
	
	@POST
	@Path("/mark")
	@Consumes("application/xml")
	public String scoreQuiz(@QueryParam("token") String token, @QueryParam("week") int week, 
							@QueryParam("quizid") int quizID, @QueryParam("answerID") List<Integer> userAnswers) {
		boolean validUser = ActiveUsers.validateUser(token);
		if(!validUser) {
			return null;
		}
		User user = ActiveUsers.getLoggedInUser(token);
		int score = db.checkAnswers(week, user, userAnswers);
		int averageScore = db.getAverageScore(user);
		return "" + score + " " + averageScore;
	}
}
