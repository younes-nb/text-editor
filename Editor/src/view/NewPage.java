package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewPage extends VBox {

    private final TextField nameField;
    private final Button saveBtn;

    public NewPage() {

        Label nameLbl = new Label("Name : ");
        nameField = new TextField();

        HBox nameHBox = new HBox(nameLbl, nameField);
        nameHBox.setAlignment(Pos.CENTER);

        saveBtn = new Button("Save");

        this.getChildren().addAll(nameHBox, saveBtn);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.setPadding(new Insets(10));
    }

    public TextField getNameField() {
        return nameField;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }
}
