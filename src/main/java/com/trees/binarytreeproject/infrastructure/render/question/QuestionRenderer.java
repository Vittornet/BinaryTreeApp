package com.trees.binarytreeproject.infrastructure.render.question;

import com.trees.binarytreeproject.domain.question.AbstractTestQuestion;
import javafx.scene.Node;

public interface QuestionRenderer {
    Node render(AbstractTestQuestion question);
}
