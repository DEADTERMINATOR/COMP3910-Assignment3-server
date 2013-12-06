package ca.bcit.infosys.cst3a.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "score")
public class ScoreObject {
	private String token;
	private int week;
	private int quizID;
	
	@XmlElement(name = "answer")
	private List<Integer> answers;
	
	public ScoreObject(){}
	
	/**
	 * @return the token
	 */
	@XmlElement(name = "token")
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the week
	 */
	@XmlElement(name = "week")
	public int getWeek() {
		return week;
	}
	
	/**
	 * @param week the week to set
	 */
	public void setWeek(int week) {
		this.week = week;
	}
	
	/**
	 * @return the quizID
	 */
	@XmlElement(name = "quizID")
	public int getQuizID() {
		return quizID;
	}
	
	/**
	 * @param quizID the quizID to set
	 */
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	
	/**
	 * @return the userAnswers
	 */
	public List<Integer> getAnswers() {
		return answers;
	}
	
	/**
	 * @param userAnswers the userAnswers to set
	 */
	public void setUserAnswers(List<Integer> answers) {
		this.answers = answers;
	}
}
