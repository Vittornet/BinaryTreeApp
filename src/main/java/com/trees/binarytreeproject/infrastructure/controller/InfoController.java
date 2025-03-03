package com.trees.binarytreeproject.infrastructure.controller;

import com.trees.binarytreeproject.infrastructure.NavigationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ObservableValue;

import java.awt.Desktop;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

    @FXML
    public Hyperlink sourceLink;

    @FXML
    private AnchorPane infoRoot;

    @FXML
    private ListView<String> treeListView;

    @FXML
    private ScrollPane infoScrollPane;

    @FXML
    private VBox contentBox;

    @FXML
    private void backToMenu() {
        NavigationService.show("menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NavigationService.register("info", infoRoot);

        ObservableList<String> items = FXCollections.observableArrayList(
                "Бінарне дерево",
                "Червоно-чорне дерево",
                "Задачі"
        );
        treeListView.setItems(items);
        treeListView.getSelectionModel().select("Бінарне дерево");
        updateSourceLink("Бінарне дерево");
        loadPdfContent("/com/trees/binarytreeproject/pdf/binary_tree_info.pdf");

        treeListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldSelection, String newSelection) -> {
                    if (newSelection != null) {
                        updateSourceLink(newSelection);
                        if (newSelection.equals("Бінарне дерево")) {
                            loadPdfContent("/com/trees/binarytreeproject/pdf/binary_tree_info.pdf");
                        } else if (newSelection.equals("Червоно-чорне дерево")) {
                            loadPdfContent("/com/trees/binarytreeproject/pdf/red_black_tree_info.pdf");
                        } else if (newSelection.equals("Задачі")) {
                            loadPdfContent("/com/trees/binarytreeproject/pdf/binary_tree_tasks.pdf");
                        }
                    }
                }
        );
    }


    private void updateSourceLink(String treeType) {
        String url;
        if (treeType.equals("Бінарне дерево")) {
            url = "http://om.univ.kiev.ua/users_upload/15/upload/file/pr_lecture_23.pdf";
        } else if (treeType.equals("Червоно-чорне дерево")) {
            url = "http://om.univ.kiev.ua/users_upload/15/upload/file/pr_lecture_24.pdf";
        } else if (treeType.equals("Задачі")) {
            url = "http://cslibrary.stanford.edu/110/BinaryTrees.pdf";
        } else {
            url = "";
        }
        sourceLink.setText("Джерело інформації: " + url);
        sourceLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    private void loadPdfContent(String pdfFilePath) {
        try (InputStream is = getClass().getResourceAsStream(pdfFilePath);
             PDDocument document = PDDocument.load(is)) {

            PDFRenderer renderer = new PDFRenderer(document);
            int numPages = document.getNumberOfPages();

            if (contentBox == null) {
                contentBox = new VBox(10);
            } else {
                contentBox.getChildren().clear();
            }

            for (int i = 0; i < numPages; i++) {
                java.awt.image.BufferedImage bim = renderer.renderImageWithDPI(i, 150);
                Image fxImage = SwingFXUtils.toFXImage(bim, null);
                ImageView imageView = new ImageView(fxImage);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(infoScrollPane.getPrefWidth() - 20);
                contentBox.getChildren().add(imageView);
            }
            infoScrollPane.setContent(contentBox);
        } catch (IOException e) {
            infoScrollPane.setContent(null);
            showAlert("Помилка", "Не вдалося завантажити PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}