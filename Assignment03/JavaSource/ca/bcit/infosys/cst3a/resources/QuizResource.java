package ca.bcit.infosys.cst3a.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class QuizResource {
	private Database db = new Database();
	
	@GET
	@Path("/quizzes/{week_of_class}")
	public Quiz getQuiz(@PathParam("week_of_class") int week) {
		
	}
}
