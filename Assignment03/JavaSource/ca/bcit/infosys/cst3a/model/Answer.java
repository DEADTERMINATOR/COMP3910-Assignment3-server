package ca.bcit.infosys.cst3a.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for the Answer table.
 * 
 * @author Mark Ahmadi & Junko Yamamoto
 *
 */
@XmlRootElement(namespace = "ca.bcit.infosys.cst3a.model")
public class Answer {
	/** The ID of the answer */
	private int answerID;
	
	/** The actual answer */
	private String answer;
	
	/**
	 * No argument default constructor
	 */
	public Answer(){}
	
	/**
	 * Constructor
	 * 
	 * @param answerID - the answerID
	 * @param answer - the answer
	 */
	public Answer(int answerID, String answer) {
		this.answerID = answerID;
		this.answer = answer;
	}
	
	/**
	 * @return the answerID
	 */
    @XmlElement(name = "answerID")
	public int getAnswerID() {
		return answerID;
	}
	
    /**
     * @param answerID - the new answerID
     */
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	
	/**
	 * @return the answer
	 */
    @XmlElement(name = "answer")
	public String getAnswer() {
		return answer;
	}
	
    /**
     * @param answer - the new answer
     */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
