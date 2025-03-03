package com.trees.binarytreeproject.infrastructure.render.question;

import com.trees.binarytreeproject.domain.question.AbstractTestQuestion;
import com.trees.binarytreeproject.domain.question.TextInputQuestion;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TextInputQuestionRenderer implements QuestionRenderer {

    @Override
    public Node render(AbstractTestQuestion question) {
        if (!(question instanceof TextInputQuestion)) {
            throw new IllegalArgumentException("Невірний тип питання для TextInputQuestionRenderer");
        }
        TextInputQuestion tiq = (TextInputQuestion) question;
        VBox container = new VBox(5);
        Label qLabel = new Label(tiq.getQuestionText());
        TextField answerField = new TextField();
        answerField.setPromptText("Введіть відповідь");
        answerField.textProperty().addListener((obs, oldVal, newVal) -> tiq.setUserAnswer(newVal));
        container.getChildren().addAll(qLabel, answerField);
        return container;
    }
}
