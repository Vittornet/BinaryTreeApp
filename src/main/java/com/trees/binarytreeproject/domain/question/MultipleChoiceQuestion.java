package com.trees.binarytreeproject.domain.question;

import java.util.List;

public class MultipleChoiceQuestion extends AbstractTestQuestion {
    private List<String> options;
    private int correctOptionIndex;
    private Integer userSelectedIndex = null;

    public MultipleChoiceQuestion(String questionText, int weight, List<String> options, int correctOptionIndex) {
        super(questionText, weight);
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setUserSelectedIndex(int index) {
        this.userSelectedIndex = index;
    }

    @Override
    public boolean checkAnswer() {
        return userSelectedIndex != null && userSelectedIndex == correctOptionIndex;
    }
}
