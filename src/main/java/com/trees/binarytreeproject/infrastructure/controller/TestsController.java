package com.trees.binarytreeproject.infrastructure.controller;

import com.trees.binarytreeproject.domain.question.AbstractTestQuestion;
import com.trees.binarytreeproject.domain.question.MultipleChoiceQuestion;
import com.trees.binarytreeproject.domain.question.TextInputQuestion;
import com.trees.binarytreeproject.domain.test.AbstractTreeTest;
import com.trees.binarytreeproject.domain.test.BinaryTreeTest;
import com.trees.binarytreeproject.domain.test.RedBlackTreeTest;
import com.trees.binarytreeproject.infrastructure.NavigationService;
import com.trees.binarytreeproject.infrastructure.render.question.MultipleChoiceQuestionRenderer;
import com.trees.binarytreeproject.infrastructure.render.question.QuestionRenderer;
import com.trees.binarytreeproject.infrastructure.render.question.TextInputQuestionRenderer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class TestsController implements Initializable {

    @FXML
    private BorderPane testsRoot;

    @FXML
    private ComboBox<String> testTypeComboBox;

    @FXML
    private VBox questionsContainer;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    private AbstractTreeTest currentTest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        NavigationService.register("tests", testsRoot);


        testTypeComboBox.getItems().addAll("Бінарне дерево", "Червоно-чорне дерево");
        testTypeComboBox.getSelectionModel().selectFirst();
        loadTest("Бінарне дерево");

        testTypeComboBox.setOnAction(e -> {
            String selected = testTypeComboBox.getValue();
            if (selected != null) {
                loadTest(selected);
            }
        });

        submitButton.setOnAction(e -> {
            int score = currentTest.getTotalScore();
            showResult(score);
        });
    }

    @FXML
    private void backToMenu() {
        NavigationService.show("menu");
    }


    private void loadTest(String testType) {
        questionsContainer.getChildren().clear();

        if (testType.equals("Бінарне дерево")) {
            currentTest = new BinaryTreeTest();
        } else if (testType.equals("Червоно-чорне дерево")) {
            currentTest = new RedBlackTreeTest();
        }

        int questionNumber = 1;
        for (AbstractTestQuestion question : currentTest.getQuestions()) {

            Label numberLabel = new Label(questionNumber + ". ");
            numberLabel.getStyleClass().add("question-number");


            QuestionRenderer renderer = getRendererForQuestion(question);
            Node questionNode = renderer.render(question);


            VBox questionBlock = new VBox(5);
            questionBlock.getChildren().addAll(numberLabel, questionNode);


            questionsContainer.getChildren().add(questionBlock);


            Separator separator = new Separator();
            questionsContainer.getChildren().add(separator);

            questionNumber++;
        }
    }

    private QuestionRenderer getRendererForQuestion(AbstractTestQuestion question) {
        if (question instanceof MultipleChoiceQuestion) {
            return new MultipleChoiceQuestionRenderer();
        } else if (question instanceof TextInputQuestion) {
            return new TextInputQuestionRenderer();
        }
        throw new IllegalArgumentException("Невідомий тип питання");
    }

    private void showResult(int score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("Твій результат:");
        alert.setContentText("Сумарна кількість балів: " + score);
        alert.showAndWait();
    }
}