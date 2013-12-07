package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for the Quiz table.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@XmlRootElement(name = "quiz")
public class Quiz {
	/** The ID for the quiz */
	private int quizID;
	
	/** The week number of the quiz */
	private int weekNo;
	
	/** The score for the quiz */
	private int score;
	
	/** The average score across all quizzes */
	private int averageScore;
	
	/** The arraylist containing all the questions for a particular quiz */
    private ArrayList<Question> questions;
    
    /**
	 * No argument default constructor
	 */
    public Quiz(){}

    /**
     * Constructor
     * 
     * @param quizID - the quiz ID
     * @param weekNo - the week number
     * @param questions - the arraylist of questions for the quiz
     * @param score - the score the user received for this quiz, or -1 if not taken
     * @param averageScore - the average score across all taken quizzes
     */
	public Quiz(int quizID, int weekNo, ArrayList<Question> questions, int score, int averageScore) {
		this.quizID = quizID;
		this.weekNo = weekNo;
		this.questions = questions;
		this.score = score;
		this.averageScore = averageScore;
	}
	
	/**
	 * @return the quizID
	 */
	@XmlElement(name = "quizID")
	public int getQuizID() {
		return quizID;
	}
	
	/**
	 * @param quizID - the new quizID
	 */
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	
	/**
	 * @return the weekNo
	 */
	public int getWeekNo() {
		return weekNo;
	}
	
	/**
	 * @param weekNo - the new weekNo
	 */
	public void setWeekNo(int weekNo) {
		this.weekNo = weekNo;
	}
	
	/**
	 * @return the questions arraylist
	 */
    @XmlElement(name = "questions")
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
    /**
     * @param questions - the new questions arraylist
     */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	/**
	 * @return the score
	 */
    @XmlElement(name = "score")
	public int getScore() {
		return score;
	}
	
    /**
     * @param score - the new score
     */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the average score
	 */
	@XmlElement(name = "average")
	public int getAverageScore() {
		return averageScore;
	}
	
	/**
	 * @param averageScore - the new average score
	 */
	public void setAverageScore(int averageScore) {
		this.averageScore = averageScore;
	}
}
