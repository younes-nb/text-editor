package controller;

import javafx.scene.control.Alert;
import model.Database;
import view.NewPage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SaveAsPageController {

    private final NewPage newPage;
    private final MainPageController mainPageController;

    public SaveAsPageController(MainPageController mainPageController) {

        this.mainPageController = mainPageController;
        newPage = new NewPage();
        initSavaBtn();
    }

    private void initSavaBtn() {

        newPage.getSaveBtn().setOnAction(event -> {

            String name = newPage.getNameField().getText();
            File file = new File(name + ".txt");

            try {

                FileWriter fileWriter = new FileWriter(file.getPath());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(mainPageController.getMainPage().getTextArea().getText());
                bufferedWriter.close();
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                Database database = new Database(name, mainPageController.getMainPage().getTextArea().getText(),
                        mainPageController.setBackGroundColor(), localDate.toString(), localTime.toString(),
                        mainPageController.setBold(), mainPageController.setItalic(),
                        mainPageController.setFontSize(), mainPageController.setFontColor());

                mainPageController.getFirstPageController().addNewTextToTable(database);
                newPage.getScene().getWindow().hide();
            } catch (IOException ioException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong!");
                alert.show();
            }
        });
    }

    public NewPage getNewPage() {
        return newPage;
    }
}
