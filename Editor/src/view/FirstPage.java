package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Database;

public class FirstPage extends BorderPane {

    private final TableView<Database> tableView;
    private final Button newBtn, openBtn, deleteBtn, exitBtn;

    public FirstPage() {

        newBtn = new Button("New");
        newBtn.setMaxWidth(Double.MAX_VALUE);
        openBtn = new Button("Open File");
        openBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn = new Button("Delete");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn = new Button("Exit");
        exitBtn.setMaxWidth(Double.MAX_VALUE);

        VBox vBox = new VBox(newBtn, openBtn, deleteBtn, exitBtn);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10, 20, 0, 10));
        vBox.setAlignment(Pos.TOP_CENTER);

        TableColumn<Database, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Database, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Database, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Database,String> pathCol = new TableColumn<>("Path");
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

        tableView = new TableView<>();
        tableView.getColumns().addAll(nameCol, dateCol, timeCol,pathCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.setCenter(tableView);
        this.setLeft(vBox);
    }

    public TableView<Database> getTableView() {
        return tableView;
    }

    public Button getNewBtn() {
        return newBtn;
    }

    public Button getOpenBtn() {
        return openBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Button getExitBtn() {
        return exitBtn;
    }
}
