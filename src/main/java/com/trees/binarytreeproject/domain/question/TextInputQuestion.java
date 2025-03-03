package com.trees.binarytreeproject.domain.question;

public class TextInputQuestion extends AbstractTestQuestion {
    private String correctAnswer;
    private String userAnswer = "";

    public TextInputQuestion(String questionText, int weight, String correctAnswer) {
        super(questionText, weight);
        this.correctAnswer = correctAnswer;
    }

    public void setUserAnswer(String answer) {
        this.userAnswer = answer;
    }

    @Override
    public boolean checkAnswer() {
        return userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
}
