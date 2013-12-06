package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "score")
public class ScoreObject {
	private String token;
	private int week;
	private int quizID;
	
	@XmlElementWrapper(name = "userAnswers")
	@XmlElement(name = "answer")
	private ArrayList<Integer> answers;
	
	public ScoreObject(){}
	
	public ScoreObject(String token, int week, int quizID, ArrayList<Integer> answers) {
		this.token = token;
		this.week = week;
		this.quizID = quizID;
		this.answers = answers;
	}
	
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
	public ArrayList<Integer> getAnswers() {
		return answers;
	}
	
	/**
	 * @param userAnswers the userAnswers to set
	 */
	public void setUserAnswers(ArrayList<Integer> answers) {
		this.answers = answers;
	}
}
