package ca.bcit.infosys.cst3a.resources;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.bcit.infosys.cst3a.access.UserDatabase;
import ca.bcit.infosys.cst3a.model.ActiveUsers;
import ca.bcit.infosys.cst3a.model.User;

import java.security.SecureRandom;
import java.math.BigInteger;

@Path("/")
@Stateless
public class UserResource {
	@EJB
	private UserDatabase db;
	private String token = null;
	
	@GET
	@Path("/userlogin")
	@Produces("text/xml")
	public Response getUser(@QueryParam("username") String username, @QueryParam("password") String password) {
		User user = db.getUser(username);
		if(user != null) {
			boolean validUser = db.validate(user, password);
			if(validUser) {
				token = nextSessionId();
				ActiveUsers.addLoggedInUser(token, user);
				return Response.status(200).entity(token).build();
			}
		}
		return Response.status(403).build();
	}

	@POST
	@Path("/newuser")
	@Consumes("application/xml")
	public String newUser(User user) {
		boolean successful = db.add(user);
		if(successful) {
			return "success";
		}
		return "failure";
	}

	@GET
	@Path("/logout")
	@Produces("text/xml")
	public void logout(@QueryParam("token") String token) {
		ActiveUsers.removeLoggedInUser(token);
	}
	
	public String nextSessionId() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}
