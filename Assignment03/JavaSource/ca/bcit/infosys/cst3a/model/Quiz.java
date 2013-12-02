package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

public class Quiz {
	private int quizID;
	private int weekNo;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private int score;
	
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
	
	
}
