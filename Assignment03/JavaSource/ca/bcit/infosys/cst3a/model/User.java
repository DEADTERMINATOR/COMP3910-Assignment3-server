package ca.bcit.infosys.cst3a.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	private String studentNo;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	public User(String studentID, String username, String password, String firstName, String lastName) {
		this.studentNo = studentID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(){}
	
	@XmlElement(name = "studentNo")
	public String getStudentNo() {
		return studentNo;
	}
	
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
    @XmlElement(name = "username")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
    @XmlElement(name = "password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
    @XmlElement(name = "firstName")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
    @XmlElement(name = "lastName")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
