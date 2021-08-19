package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Database;
import view.OpenPage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenPageController {

    private final OpenPage openPage;
    private final MainPageController mainPageController;
    private final ArrayList<Database> databases;

    public OpenPageController(MainPageController mainPageController) {

        this.mainPageController = mainPageController;
        databases = mainPageController.getFirstPageController().getDatabases();
        openPage = new OpenPage(addTextToList());
        initCells();
    }

    private ListView<String> addTextToList() {

        ObservableList<String> texts = FXCollections.observableArrayList();
        for (Database database : databases) {

            texts.add(database.getName());
        }
        return new ListView<>(texts);
    }

    private void initCells() {

        openPage.getListView().setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {

                String selected = openPage.getListView().getSelectionModel().getSelectedItem();
                Database database = null;
                for (Database value : databases) {

                    if (value.getName().equals(selected)) {

                        database = value;
                    }
                }
                if (database != null) {

                    try {

                        FileReader fileReader = new FileReader(database.getPath());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String text = null;
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (text == null) {
                                text = line + "\n";
                            } else {
                                text = text.concat(line + "\n");
                            }
                        }
                        fileReader.close();
                        TextArea textArea = new TextArea();
                        textArea.setText(text);
                        MainPageController newMainPageController =
                                new MainPageController(textArea, mainPageController.getFirstPageController(), database);
                        Stage mainPageStage = new Stage();
                        mainPageStage.setScene(new Scene(newMainPageController.getMainPage()));
                        mainPageStage.setTitle(database.getName());
                        mainPageStage.setHeight(800);
                        mainPageStage.setWidth(1000);
                        openPage.getScene().getWindow().hide();
                        mainPageStage.show();
                    } catch (IOException ioException) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Something went wrong!");
                        alert.show();
                    }
                }
            }
        });
    }

    public OpenPage getOpenPage() {
        return openPage;
    }
}
