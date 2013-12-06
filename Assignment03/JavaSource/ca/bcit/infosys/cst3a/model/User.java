package ca.bcit.infosys.cst3a.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	/** The user's student number */
	private String studentNo;
	
	/** The user's username */
	private String username;
	
	/** The user's password */
	private String password;
	
	/** The user's first name */
	private String firstName;
	
	/** The user's last name */
	private String lastName;
	
	/**
	 * No argument default constructor
	 */
	public User(){}
	
	/**
	 * Constructor
	 * 
	 * @param studentID - the student ID
	 * @param username - the username
	 * @param password - the password
	 * @param firstName - the first name
	 * @param lastName - the last name
	 */
	public User(String studentID, String username, String password, String firstName, String lastName) {
		this.studentNo = studentID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * @return the studentNo
	 */
	@XmlElement(name = "studentNo")
	public String getStudentNo() {
		return studentNo;
	}
	
	/**
	 * @param studentNo - the new studentNo
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	
	/**
	 * @return the username
	 */
    @XmlElement(name = "username")
	public String getUsername() {
		return username;
	}
	
    /**
     * @param username - the new username
     */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
    @XmlElement(name = "password")
	public String getPassword() {
		return password;
	}
	
    /**
     * @param password - the new password
     */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the firstName
	 */
    @XmlElement(name = "firstName")
	public String getFirstName() {
		return firstName;
	}
	
    /** 
     * @param firstName - the new firstName
     */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
    @XmlElement(name = "lastName")
	public String getLastName() {
		return lastName;
	}
	
    /**
     * @param lastName - the new lastName
     */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
