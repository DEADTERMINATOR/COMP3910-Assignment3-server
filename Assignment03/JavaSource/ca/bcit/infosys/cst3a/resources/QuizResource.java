package ca.bcit.infosys.cst3a.resources;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ca.bcit.infosys.cst3a.model.Quiz;

/*@Path("/")
@Stateless
public class QuizResource {
	@EJB
	private Database db;
	
	@GET
	@Path("/quizzes/{week_of_class}")
	@Produces("application/xml")
	public Quiz getQuiz(@PathParam("week_of_class") int week) {
		return db.getQuiz(week);
	}
}*/
