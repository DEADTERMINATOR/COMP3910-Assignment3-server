package ca.bcit.infosys.cst3a.model;

import java.util.ArrayList;

public class Question {
	private int questionID;
	private String question;
	private ArrayList<Answer> answers;
	private Answer selectedAnswer;
	
	public int getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
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

	public Answer getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Answer selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	
}
