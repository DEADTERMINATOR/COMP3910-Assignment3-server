package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

public class Quiz {
	private int quizID;
	private int weekNo;
	private ArrayList<Question> questions;
	private int score;
	private int averageScore;
	
	public Quiz(int quizID, int weekNo, ArrayList<Question> questions, int score, int averageScore) {
		this.quizID = quizID;
		this.weekNo = weekNo;
		this.questions = questions;
		this.score = score;
		this.averageScore = averageScore;
	}
	
	public int getQuizID() {
		return quizID;
	}
	
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	
	public int getWeekNo() {
		return weekNo;
	}
	
	public void setWeekNo(int weekNo) {
		this.weekNo = weekNo;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getAverageScore() {
		return averageScore;
	}
	
	public void setAverageScore(int averageScore) {
		this.averageScore = averageScore;
	}
}
