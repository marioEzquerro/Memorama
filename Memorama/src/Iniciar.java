
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Mario Ezquerro
 *
 */
public class Iniciar extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/juego_memorama/Memorama.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("/sources/logo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
