package com.trees.binarytreeproject.domain.test;

import com.trees.binarytreeproject.domain.question.AbstractTestQuestion;

import java.util.List;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class AbstractTreeTest {

    private final List<AbstractTestQuestion> questions;


    protected AbstractTreeTest(String questionsFilePath) {
        this.questions = loadQuestions(questionsFilePath);
    }

    public List<AbstractTestQuestion> getQuestions() {
        return questions;
    }

    public int getTotalScore() {
        int score = 0;
        for (AbstractTestQuestion q : questions) {
            if (q.checkAnswer()) {
                score += q.getWeight();
            }
        }
        return score;
    }


    private static List<AbstractTestQuestion> loadQuestions(String filePath) {
        List<AbstractTestQuestion> questions = new ArrayList<>();
        try (InputStream is = AbstractTreeTest.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 4) {
                    System.err.println("Невірний формат рядка: " + line);
                    continue;
                }
                String type = parts[0].trim();
                String questionText = parts[1].trim();
                int weight = Integer.parseInt(parts[2].trim());

                if ("MCQ".equalsIgnoreCase(type)) {
                    if (parts.length < 5) {
                        System.err.println("Невірний формат MCQ питання: " + line);
                        continue;
                    }
                    String[] options = parts[3].split(";");
                    int correctIndex = Integer.parseInt(parts[4].trim());
                    questions.add(new com.trees.binarytreeproject.domain.question.MultipleChoiceQuestion(
                            questionText, weight, java.util.Arrays.asList(options), correctIndex));
                } else if ("TEXT".equalsIgnoreCase(type)) {
                    String correctAnswer = parts[3].trim();
                    questions.add(new com.trees.binarytreeproject.domain.question.TextInputQuestion(
                            questionText, weight, correctAnswer));
                } else {
                    System.err.println("Невідомий тип питання: " + type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}
