package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "ca.bcit.infosys.cst3a.model")
public class Question {
	/** The ID for the question */
	private int questionID;
	
	/** The actual question */
	private String question;

	/** The arraylist containing all the answers for the particular question */
	@XmlElement(name = "answersArray")
	private ArrayList<Answer> answersArray;

	/**
	 * No argument default constructor
	 */
    public Question(){}
    
    /**
     * Constructor
     * 
     * @param questionID - the question ID
     * @param question - the question
     * @param answersArray - the arraylist of answers for this question
     */
	public Question(int questionID, String question, ArrayList<Answer> answersArray) {
		this.questionID = questionID;
		this.question = question;
		this.answersArray = answersArray;
	}
	
	/**
	 * @return the questionID
	 */
    @XmlElement(name = "questionID")
	public int getQuestionID() {
		return questionID;
	}
	
    /**
     * @param questionID - the new questionID
     */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
	/**
	 * @return the question
	 */
    @XmlElement(name = "question")
	public String getQuestion() {
		return question;
	}
	
    /**
     * @param question - the new question
     */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * @return the answer array
	 */
	public ArrayList<Answer> getAnswersArray() {
		return answersArray;
	}
	
	/**
	 * @param answersArray - the new answer array
	 */
	public void setAnswers(ArrayList<Answer> answersArray) {
		this.answersArray = answersArray;
	}
}
