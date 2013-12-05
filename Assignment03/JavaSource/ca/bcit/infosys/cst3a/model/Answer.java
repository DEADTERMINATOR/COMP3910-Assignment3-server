package ca.bcit.infosys.cst3a.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "ca.bcit.infosys.cst3a.model")
public class Answer {
	private int answerID;
	private String answer;
	
	public Answer(int answerID, String answer) {
		this.answerID = answerID;
		this.answer = answer;
	}
	
    @XmlElement(name = "answerID")
	public int getAnswerID() {
		return answerID;
	}
	
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	
    @XmlElement(name = "answer")
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
