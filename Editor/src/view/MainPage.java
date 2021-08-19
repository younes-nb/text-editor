package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainPage extends BorderPane {

    private final MenuItem newItem, openItem,openFileItem, closeItem, saveItem, saveAsItem, quitItem,
            undoItem, redoItem, cutItem, copyItem, pasteItem, deleteItem;
    private final ToggleButton boldTBtn, italicTBtn;
    private final ComboBox<String> fontSizeBox;
    private final ColorPicker fontCP;
    private final ColorPicker backGroundCP;
    private final TextArea textArea;


    public MainPage(TextArea textArea, String fontBold, String fontItalic,
                    String fontSize, String fontColor, String backgroundColor) {

        newItem = new MenuItem("New");
        openItem = new MenuItem("Open...");
        openFileItem = new MenuItem("Open File");
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        closeItem = new MenuItem("Close");
        saveItem = new MenuItem("Save");
        saveAsItem = new MenuItem("Save As...");
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        quitItem = new MenuItem("Quit");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newItem, openItem,openFileItem ,separatorMenuItem1,
                closeItem, saveItem, saveAsItem, separatorMenuItem2, quitItem);

        undoItem = new MenuItem("Undo");
        redoItem = new MenuItem("Redo");
        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
        cutItem = new MenuItem("Cut");
        copyItem = new MenuItem("Copy");
        pasteItem = new MenuItem("Paste");
        deleteItem = new MenuItem("Delete");

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(undoItem, redoItem, separatorMenuItem3,
                cutItem, copyItem, pasteItem, deleteItem);

        MenuBar menuBar = new MenuBar(fileMenu, editMenu);

        boldTBtn = new RadioButton("B");
        boldTBtn.setStyle("-fx-font-size: 12pt;" + "-fx-font-weight: bold;");

        if (fontBold.equals("-fx-font-weight: bold;")) {

            boldTBtn.setSelected(true);
        }


        italicTBtn = new RadioButton("I");
        italicTBtn.setStyle("-fx-font-style: italic;" + "-fx-font-size: 12pt;");

        if (fontItalic.equals("-fx-font-style: italic;")) {

            italicTBtn.setSelected(true);
        }


        Label fontSizeLbl = new Label("Font Size");
        String[] fontSizes = {"Small", "Medium", "Large"};

        fontSizeBox = new ComboBox<>(FXCollections.observableArrayList(fontSizes));
        switch (fontSize) {

            case "-fx-font-size: 10pt;":

                fontSizeBox.getSelectionModel().select("Small");
                break;

            case "-fx-font-size: 20pt;":

                fontSizeBox.getSelectionModel().select("Medium");
                break;

            case "-fx-font-size: 30pt;":

                fontSizeBox.getSelectionModel().select("Large");
                break;
        }

        HBox fontSizeHBox = new HBox(fontSizeLbl, fontSizeBox);
        fontSizeHBox.setSpacing(5);

        HBox row_1HBox = new HBox(boldTBtn, italicTBtn, fontSizeHBox);
        row_1HBox.setSpacing(68);
        row_1HBox.setPadding(new Insets(10));

        Label fontColorLbl = new Label("Font Color");
        fontCP = new ColorPicker(Color.web(fontColor));
        HBox fontColorHBox = new HBox(fontColorLbl, fontCP);
        fontColorHBox.setSpacing(5);

        Label backGroundColorLbl = new Label("Back Ground Color");
        backGroundCP = new ColorPicker(Color.web(backgroundColor));
        HBox backGroundColorHBox = new HBox(backGroundColorLbl, backGroundCP);
        backGroundColorHBox.setSpacing(5);

        HBox row_2HBox = new HBox(fontColorHBox, backGroundColorHBox);
        row_2HBox.setSpacing(25);
        row_2HBox.setPadding(new Insets(10));

        VBox vBox = new VBox(menuBar, row_1HBox, row_2HBox);

        textArea.setStyle(fontBold + fontItalic + fontSize +
                "-fx-text-fill: " + fontColor + ";" + "-fx-control-inner-background:" + backgroundColor + ";");

        this.textArea = textArea;
        this.setTop(vBox);
        this.setCenter(this.textArea);

    }

    public TextArea getTextArea() {
        return textArea;
    }

    public MenuItem getNewItem() {
        return newItem;
    }

    public MenuItem getOpenItem() {
        return openItem;
    }

    public MenuItem getOpenFileItem() {
        return openFileItem;
    }

    public MenuItem getCloseItem() {
        return closeItem;
    }

    public MenuItem getSaveItem() {
        return saveItem;
    }

    public MenuItem getSaveAsItem() {
        return saveAsItem;
    }

    public MenuItem getQuitItem() {
        return quitItem;
    }

    public MenuItem getUndoItem() {
        return undoItem;
    }

    public MenuItem getRedoItem() {
        return redoItem;
    }

    public MenuItem getCutItem() {
        return cutItem;
    }

    public MenuItem getCopyItem() {
        return copyItem;
    }

    public MenuItem getPasteItem() {
        return pasteItem;
    }

    public MenuItem getDeleteItem() {
        return deleteItem;
    }

    public ToggleButton getBoldTBtn() {
        return boldTBtn;
    }

    public ToggleButton getItalicTBtn() {
        return italicTBtn;
    }

    public ComboBox<String> getFontSizeBox() {
        return fontSizeBox;
    }

    public ColorPicker getFontCP() {
        return fontCP;
    }

    public ColorPicker getBackGroundCP() {
        return backGroundCP;
    }

}
