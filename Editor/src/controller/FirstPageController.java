package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Database;
import view.FirstPage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FirstPageController {

    private final FirstPage firstPage;
    private final ArrayList<Database> databases;
    private final Stage fistPageStage;

    public FirstPageController(ArrayList<Database> databases, Stage primaryStage) {

        fistPageStage = primaryStage;
        firstPage = new FirstPage();
        this.databases = databases;
        initButtons();
        addOldTextToTable();
    }

    private void initButtons() {

        firstPage.getNewBtn().setOnAction(event -> {

            NewPageController newPageController = new NewPageController(this);
            Stage newPageStage = new Stage();
            newPageStage.setScene(new Scene(newPageController.getNewPage()));
            newPageStage.setTitle("New Text");
            newPageStage.show();
        });

        firstPage.getOpenBtn().setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter =
                    new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setInitialDirectory(new File("./"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {

                TextArea textArea = new TextArea();
                try {

                    FileReader fileReader = new FileReader(file.getPath());
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
                    textArea.setText(text);
                    fileReader.close();
                    boolean exist = false;
                    Database fileDatabase = null;
                    for (Database database : databases) {

                        if (database.getName().equals(file.getName()) && database.getPath().equals(file.getPath())) {
                            fileDatabase = database;
                            exist = true;
                        }
                    }
                    if (!exist) {

                        LocalDate localDate = LocalDate.now();
                        LocalTime localTime = LocalTime.now();
                        fileDatabase = new Database(file.getName(), file.getPath(),
                                "#ffffff", localDate.toString(), localTime.toString(),
                                "-fx-font-weight: normal;", "-fx-font-style: normal;",
                                "-fx-font-size: 10pt;", "#000000");
                        this.addNewTextToTable(fileDatabase);
                    }

                    MainPageController newMainPageController =
                            new MainPageController(textArea, this, fileDatabase);
                    Stage mainPageStage = new Stage();
                    mainPageStage.setScene(new Scene(newMainPageController.getMainPage()));
                    mainPageStage.setTitle(file.getName());
                    mainPageStage.setHeight(800);
                    mainPageStage.setWidth(1000);
                    mainPageStage.show();
                } catch (IOException ioException) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong!");
                    alert.show();
                }
            }
        });

        firstPage.getTableView().setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {

                openMainPage();
            }
        });

        firstPage.getTableView().setOnKeyPressed(event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {

                openMainPage();
            }
        });

        firstPage.getDeleteBtn().setOnAction(event -> {

            Database database = firstPage.getTableView().getSelectionModel().getSelectedItem();
            if (database != null) {

                firstPage.getTableView().getItems().remove(database);
                databases.remove(database);
                saveData();
            }
        });

        firstPage.getExitBtn().setOnAction(event -> System.exit(0));
    }

    private void openMainPage() {

        Database database = firstPage.getTableView().getSelectionModel().getSelectedItem();
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
                MainPageController mainPageController = new MainPageController(textArea, this, database);
                Stage mainPageStage = new Stage();
                mainPageStage.setScene(new Scene(mainPageController.getMainPage()));
                mainPageStage.setTitle(database.getName());
                mainPageStage.setHeight(800);
                mainPageStage.setWidth(1000);
                firstPage.getScene().getWindow().hide();
                mainPageStage.show();
            } catch (IOException ioException){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Something went wrong!");
                alert.show();
            }
        }
    }

    public void addNewTextToTable(Database database) {

        firstPage.getTableView().getItems().add(database);
        databases.add(database);
        saveData();
    }

    public void addOldTextToTable() {

        firstPage.getTableView().getItems().addAll(databases);
    }

    public void saveData() {

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("Database");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.databases);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }
    }

    public boolean validName(String name,String path) {

        for (Database database : databases) {

            if (database.getName().equals(name) && database.getPath().equals(path)) {

                return false;
            }
        }
        return true;
    }

    public FirstPage getFirstPage() {
        return firstPage;
    }

    public ArrayList<Database> getDatabases() {
        return databases;
    }

    public Stage getFistPageStage() {
        return fistPageStage;
    }
}
