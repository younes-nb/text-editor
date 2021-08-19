package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Database;
import view.NewPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class NewPageController {

    private final NewPage newPage;
    private final FirstPageController firstPageController;

    public NewPageController(FirstPageController firstPageController) {

        newPage = new NewPage();
        this.firstPageController = firstPageController;
        initSaveBtn();

    }

    private void initSaveBtn() {

        newPage.getSaveBtn().setOnAction(event -> newMainPage());

        newPage.setOnKeyPressed(event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {

                newMainPage();
            }
        });
    }

    private void newMainPage() {

        String name = newPage.getNameField().getText();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        File file = new File(name + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(file.getPath());
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException ioException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong!");
            alert.show();
        }
        Database database = new Database(file.getName(), file.getPath(),
                "#ffffff", localDate.toString(), localTime.toString(),
                "-fx-font-weight: normal;", "-fx-font-style: normal;",
                "-fx-font-size: 10pt;", "#000000");
        firstPageController.addNewTextToTable(database);
        newPage.getScene().getWindow().hide();

        TextArea textArea = new TextArea();
        textArea.setStyle(database.getFontSize());
        MainPageController mainPageController = new MainPageController(textArea, firstPageController, database);
        Stage mainPageStage = new Stage();
        mainPageStage.setScene(new Scene(mainPageController.getMainPage()));
        mainPageStage.setTitle(database.getName());
        mainPageStage.setHeight(800);
        mainPageStage.setWidth(1000);
        firstPageController.getFirstPage().getScene().getWindow().hide();
        mainPageStage.show();

    }


    public NewPage getNewPage() {
        return newPage;
    }
}
