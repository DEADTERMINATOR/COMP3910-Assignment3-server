package ca.bcit.infosys.cst3a.resources;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ca.bcit.infosys.cst3a.access.UserDatabase;
import ca.bcit.infosys.cst3a.model.User;

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Provides interaction and communication with the client side with regards to functionality
 * related to the user.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@Path("/")
@Stateless
public class UserResource {
	/** Provides CRUD operations for this class. */
	@EJB
	private UserDatabase db;
	
	/** The randomly generated token provided to the client side when a user logs in. This
	 *  token allows for a particular user's session to be uniquely identified amongst all other
	 *  users and their sessions. */
	private String token = null;
	
	/**
	 * Attempts to get a user given a username and password provided by the client side to log them in.
	 * Returns a successful response of failure response depending on the result.
	 * 
	 * @param username - the username provided by the client side
	 * @param password - the password provided by the client side
	 * @return a status 200 response with the token attached if the log in is successful, a status 403
	 * response if it is not
	 */
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

	/**
	 * Takes a potential new user created by the register function on the client side and attempts to 
	 * add it to the database.
	 * 
	 * @param user - the potential new user to try and add
	 * @return a string denoting whether the attempted addition was successful or not
	 */
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

	/**
	 * Called when the client side makes a logout request for a user. Destroys the randomly
	 * generated token provided at the beginning of the session.
	 * 
	 * @param token - the randomly generated token provided to a particular user's session
	 */
	@GET
	@Path("/logout")
	@Produces("text/xml")
	public void logout(@QueryParam("token") String token) {
		ActiveUsers.removeLoggedInUser(token);
	}
	
	/**
	 * Generates a random token used to identify a particular user's session.
	 * 
	 * @return the randomly generated token
	 */
	public String nextSessionId() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}
