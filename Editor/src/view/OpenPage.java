package view;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class OpenPage extends BorderPane {

    private final ListView<String> listView;

    public OpenPage(ListView<String> listView) {

        this.listView = listView;
        this.setCenter(listView);
    }

    public ListView<String> getListView() {
        return listView;
    }
}
