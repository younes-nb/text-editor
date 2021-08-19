import controller.FirstPageController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {

        ArrayList<Database> databases;

        try {

            FileInputStream fileInputStream = new FileInputStream("Database");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            databases = (ArrayList<Database>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException ioException) {

            databases = new ArrayList<>();
        }
        FirstPageController firstPageController = new FirstPageController(databases, primaryStage);

        primaryStage.setScene(new Scene(firstPageController.getFirstPage()));
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Editor");
        primaryStage.show();
    }
}
