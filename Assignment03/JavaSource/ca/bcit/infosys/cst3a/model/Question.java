package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "ca.bcit.infosys.cst3a.model")
public class Question {
	private int questionID;
	private String question;

	@XmlElement(name = "answersArray")
	private ArrayList<Answer> answersArray;

    public Question(){}
    
	public Question(int questionID, String question, ArrayList<Answer> answersArray) {
		this.questionID = questionID;
		this.question = question;
		this.answersArray = answersArray;
	}
    @XmlElement(name = "questionID")
	public int getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
    @XmlElement(name = "question")
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public ArrayList<Answer> getAnswersArray() {
		return answersArray;
	}
	
	public void setAnswers(ArrayList<Answer> answersArray) {
		this.answersArray = answersArray;
	}
}
