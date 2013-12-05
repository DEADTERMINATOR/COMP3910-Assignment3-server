package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "quiz")
public class Quiz {
	private int quizID;
	private int weekNo;
	private int score;
	private int averageScore;
    private ArrayList<Question> questions;

	public Quiz(int quizID, int weekNo, ArrayList<Question> questions, int score, int averageScore) {
		this.quizID = quizID;
		this.weekNo = weekNo;
		this.questions = questions;
		this.score = score;
		this.averageScore = averageScore;
	}

	public Quiz(){}
	
	@XmlElement(name = "quizID")
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
	
    @XmlElement(name = "questions")
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
    @XmlElement(name = "score")
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	@XmlElement(name = "average")
	public int getAverageScore() {
		return averageScore;
	}
	
	public void setAverageScore(int averageScore) {
		this.averageScore = averageScore;
	}
}
