package com.trees.binarytreeproject.domain.question;

public abstract class AbstractTestQuestion {
    protected String questionText;
    protected int weight;

    public AbstractTestQuestion(String questionText, int weight) {
        this.questionText = questionText;
        this.weight = weight;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getWeight() {
        return weight;
    }


    public abstract boolean checkAnswer();
}
