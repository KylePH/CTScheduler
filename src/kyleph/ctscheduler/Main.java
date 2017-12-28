package kyleph.ctscheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainForm.fxml"));
        Parent form = loader.load();

        // Set the JavaFX form to accept drag and drop events.
        // This can't be done on the FXML form for some reason.
        // Allows onDragDropped event to be caught.
        form.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        // Send the HostServices for this application to the
        // controller class. HostServices is needed to preview
        // excel files and is part of the Application class.
        Controller controller = loader.getController();
        controller.setHostServices(getHostServices());

        Scene scene = new Scene(form, 600, 600);
        primaryStage.setTitle("ConTav Schedule Builder");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
