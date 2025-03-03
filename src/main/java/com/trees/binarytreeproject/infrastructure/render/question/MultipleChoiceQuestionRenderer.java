package com.trees.binarytreeproject.infrastructure.render.question;

import com.trees.binarytreeproject.domain.question.AbstractTestQuestion;
import com.trees.binarytreeproject.domain.question.MultipleChoiceQuestion;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MultipleChoiceQuestionRenderer implements QuestionRenderer {

    @Override
    public Node render(AbstractTestQuestion question) {
        if (!(question instanceof MultipleChoiceQuestion)) {
            throw new IllegalArgumentException("Невірний тип питання для MultipleChoiceQuestionRenderer");
        }
        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
        VBox container = new VBox(5);
        Label qLabel = new Label(mcq.getQuestionText());
        container.getChildren().add(qLabel);

        ToggleGroup toggleGroup = new ToggleGroup();
        int index = 0;
        for (String option : mcq.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(toggleGroup);
            final int currentIndex = index;
            rb.setOnAction(e -> mcq.setUserSelectedIndex(currentIndex));
            container.getChildren().add(rb);
            index++;
        }
        return container;
    }
}
