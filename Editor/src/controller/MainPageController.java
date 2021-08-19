package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Database;
import view.MainPage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Stack;

public class MainPageController {

    private final MainPage mainPage;
    private final FirstPageController firstPageController;
    private final Database database;
    private final Stack<String> undoStack = new Stack<>();
    private final Stack<String> redoStack = new Stack<>();

    public MainPageController(TextArea textArea, FirstPageController firstPageController, Database database) {

        this.firstPageController = firstPageController;
        mainPage = new MainPage(textArea, database.getFontBold(), database.getFontItalic(),
                database.getFontSize(), database.getFontColor(), database.getBackgroundColor());
        this.database = database;
        initEditMenu();
        initTextOptions();
        initFileMenu();

    }

    private void initFileMenu() {

        mainPage.getNewItem().setOnAction(event -> {

            NewPageController newPageController = new NewPageController(firstPageController);
            Stage newPageStage = new Stage();
            newPageStage.setScene(new Scene(newPageController.getNewPage()));
            newPageStage.setTitle("New Text");
            newPageStage.show();
        });

        mainPage.getNewItem().setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        mainPage.getOpenItem().setOnAction(event -> {

            OpenPageController openPageController = new OpenPageController(this);
            Stage openPageStage = new Stage();
            openPageStage.setScene(new Scene(openPageController.getOpenPage()));
            openPageStage.setTitle("Open Text");
            openPageStage.show();
        });

        mainPage.getOpenItem().setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        mainPage.getOpenFileItem().setOnAction(event -> {

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
                    for (Database database : firstPageController.getDatabases()) {

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
                        firstPageController.addNewTextToTable(fileDatabase);
                    }

                    MainPageController newMainPageController =
                            new MainPageController(textArea, firstPageController, fileDatabase);
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

        mainPage.getSaveItem().setOnAction(event -> {

            for (int i = 0; i < firstPageController.getDatabases().size(); i++) {

                if (firstPageController.getDatabases().get(i).getName().equals(database.getName())) {

                    try {
                        FileWriter fileWriter = new FileWriter(database.getPath());
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(mainPage.getTextArea().getText());
                        bufferedWriter.close();
                        firstPageController.getDatabases().get(i).setFontBold(setBold());
                        firstPageController.getDatabases().get(i).setFontItalic(setItalic());
                        firstPageController.getDatabases().get(i).setFontSize(setFontSize());
                        firstPageController.getDatabases().get(i).setFontColor(setFontColor());
                        firstPageController.getDatabases().get(i).setBackgroundColor(setBackGroundColor());
                        firstPageController.getDatabases().get(i).setDate(LocalDate.now().toString());
                        firstPageController.getDatabases().get(i).setTime(LocalTime.now().toString());
                        firstPageController.saveData();
                    } catch (IOException ioException) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Something went wrong!");
                        alert.show();
                    }
                    break;
                }
            }
        });

        mainPage.getSaveItem().setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        mainPage.getSaveAsItem().setOnAction(event -> {

            SaveAsPageController saveAsPageController = new SaveAsPageController(this);
            Stage saveAsPageStage = new Stage();
            saveAsPageStage.setScene(new Scene(saveAsPageController.getNewPage()));
            saveAsPageStage.setTitle("Save As");
            saveAsPageStage.show();
        });

        mainPage.getCloseItem().setOnAction(event -> {

            mainPage.getScene().getWindow().hide();
            if (!firstPageController.getFistPageStage().isShowing()) {

                firstPageController.getFistPageStage().show();
            }
        });

        mainPage.getQuitItem().setOnAction(event -> System.exit(0));
    }

    private void initEditMenu() {

        mainPage.getUndoItem().setOnAction(event -> undo());
        mainPage.getUndoItem().setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

        mainPage.getRedoItem().setOnAction(event -> redo());
        mainPage.getRedoItem().setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        mainPage.getCutItem().setOnAction(event -> mainPage.getTextArea().cut());
        mainPage.getCutItem().setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        mainPage.getCopyItem().setOnAction(event -> mainPage.getTextArea().copy());
        mainPage.getCopyItem().setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        mainPage.getPasteItem().setOnAction(event -> mainPage.getTextArea().paste());
        mainPage.getPasteItem().setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        mainPage.getDeleteItem().setOnAction(event -> {
            IndexRange indexRange = mainPage.getTextArea().getSelection();
            mainPage.getTextArea().deleteText(indexRange);
        });
        mainPage.getDeleteItem().setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

    }

    private void initTextOptions() {

        mainPage.getBoldTBtn().setOnAction(event ->
                mainPage.getTextArea().setStyle(setBold() + setItalic() + setFontSize() +
                        "-fx-text-fill: " + setFontColor() + ";" + "-fx-control-inner-background:" + setBackGroundColor() + ";"));

        mainPage.getItalicTBtn().setOnAction(event ->
                mainPage.getTextArea().setStyle(setItalic() + setBold() + setFontSize() +
                        "-fx-text-fill: " + setFontColor() + ";" + "-fx-control-inner-background:" + setBackGroundColor() + ";"));

        mainPage.getFontSizeBox().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                mainPage.getTextArea().setStyle(setFontSize() + setBold() + setItalic() +
                        "-fx-text-fill: " + setFontColor() + ";" + "-fx-control-inner-background:" + setBackGroundColor() + ";"));

        mainPage.getFontCP().valueProperty().addListener((observable, oldValue, newValue) ->
                mainPage.getTextArea().setStyle("-fx-text-fill: " + setFontColor() + ";" + setFontSize() +
                        setBold() + setItalic() + "-fx-control-inner-background:" + setBackGroundColor() + ";"));

        mainPage.getBackGroundCP().valueProperty().addListener((observable, oldValue, newValue) ->
                mainPage.getTextArea().setStyle("-fx-text-fill: " + setFontColor() + ";" + setFontSize() +
                        setBold() + setItalic() + "-fx-control-inner-background:" + setBackGroundColor() + ";"));

        mainPage.getTextArea().setOnKeyTyped(event ->
                undoStack.push(mainPage.getTextArea().getText()));
    }

    public void undo() {

        undoAndRedo(undoStack, redoStack);
    }

    public void redo() {

        undoAndRedo(redoStack, undoStack);
    }

    private void undoAndRedo(Stack<String> redoStack, Stack<String> undoStack) {
        if (!redoStack.isEmpty()) {

            String previous = redoStack.pop();
            mainPage.getTextArea().setText(previous);
            undoStack.push(previous);
        }
    }

    public String setBold() {

        if (mainPage.getBoldTBtn().isSelected()) {

            return "-fx-font-weight: bold;";
        } else {

            return "-fx-font-weight: normal;";
        }
    }

    public String setItalic() {

        if (mainPage.getItalicTBtn().isSelected()) {

            return "-fx-font-style: italic;";
        } else {

            return "-fx-font-style: normal;";
        }
    }

    public String setFontSize() {


        switch (mainPage.getFontSizeBox().getSelectionModel().getSelectedIndex()) {

            case 1:
                return "-fx-font-size: 20pt;";


            case 2:
                return "-fx-font-size: 30pt;";

            default:
                return "-fx-font-size: 10pt;";
        }
    }

    public String setFontColor() {

        String color = mainPage.getFontCP().getValue().toString().replaceFirst("0x", "#");
        color = color.substring(0, 7);

        return color;
    }

    public String setBackGroundColor() {

        String color = mainPage.getBackGroundCP().getValue().toString().replaceFirst("0x", "#");
        color = color.substring(0, 7);

        return color;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public FirstPageController getFirstPageController() {
        return firstPageController;
    }

}
