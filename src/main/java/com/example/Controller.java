package com.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller implements Initializable {

    @FXML
    WebView webView;

    WebEngine webEngine;

    @FXML
    Button buttonRefresh, buttonBackward, buttonForward;

    @FXML
    TextField textfieldURL;

    private final List<String> history = new ArrayList<>();
    private int currentIndex = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        textfieldURL.setOnAction(e -> {
            String urlText = textfieldURL.getText();
            if (!urlText.startsWith("http://") && !urlText.startsWith("https://")) {
                urlText = "https://" + urlText;
            }
            loadURL(urlText);
        });

        updateButtons();
    }

    private void loadURL(String url) {
        webEngine.load(url);
        textfieldURL.setText(url);

        if (currentIndex < history.size() - 1) {
            // Se não está no fim do histórico, remove o futuro a partir do currentIndex+1
            history.subList(currentIndex + 1, history.size()).clear();
        }

        // Adiciona novo url e atualiza currentIndex
        history.add(url);
        currentIndex = history.size() - 1;

        updateButtons();
    }

    @FXML
    private void refresh() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            webEngine.reload();
        }
    }

    @FXML
    private void backward() {
        if (currentIndex > 0) {
            currentIndex--;
            String url = history.get(currentIndex);
            webEngine.load(url);
            textfieldURL.setText(url);
            updateButtons();
        }
    }

    @FXML
    private void forward() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            String url = history.get(currentIndex);
            webEngine.load(url);
            textfieldURL.setText(url);
            updateButtons();
        }
    }

    private void updateButtons() {
        buttonBackward.setDisable(currentIndex <= 0);
        buttonForward.setDisable(currentIndex >= history.size() - 1);
    }
}
