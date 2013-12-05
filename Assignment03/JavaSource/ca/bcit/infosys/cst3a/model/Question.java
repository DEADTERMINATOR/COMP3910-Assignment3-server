package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "ca.bcit.infosys.cst3a.model")
public class Question {
	private int questionID;
	private String question;

    @XmlElement(name = "answersArray")
	private ArrayList<Answer> answers;

    public Question(){}
    
	public Question(int questionID, String question, ArrayList<Answer> answers) {
		this.questionID = questionID;
		this.question = question;
		this.answers = answers;
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
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
}
