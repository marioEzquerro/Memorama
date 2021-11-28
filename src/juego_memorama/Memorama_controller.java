package juego_memorama;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Mario Ezquerro
 */
public class Memorama_controller implements Initializable {

    @FXML
    private Text time;
    @FXML
    private Text vidas;
    @FXML
    private TilePane tilePane;
    @FXML
    private BorderPane anteriorBP;
    
    private String anteriorClick = "";
    private static int aciertos = 0;
    private static int clicks = 0;
    private int nVidas = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vidas.setText("" + nVidas);
        startGame();
    }

    public void diaDeHoy() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        time.setText(sdf.format(cal.getTime()));
    }

    public void startGame() {
        diaDeHoy();
        tilePane.getChildren().clear();

        ArrayList<String> array_imgs = aleatorizarImagenes();
        ImageView imgElemento = new ImageView();

        for (int i = 0; i < 12; i++) {
            BorderPane carta = new BorderPane();

            // colocamos elementos en carta
            imgElemento = setImage("/sources/blanco.png");

            carta.setStyle(
                    "-fx-padding: 10;"
                    + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;"
                    + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-border-color: black;"
            );
            carta.setCenter(imgElemento);
            carta.setPadding(new Insets(12, 14, 0, 14));
            clickEnCarta(carta, array_imgs.get(i));
            tilePane.getChildren().addAll(carta);
        }
    }

    // cargar imagen
    private ImageView setImage(String src) {
        ImageView fotoElemento = new ImageView();
        fotoElemento.setFitWidth(65);
        fotoElemento.setFitHeight(65);
        Image image = new Image(getClass().getResource(src).toString());
        fotoElemento.setImage(image);
        return fotoElemento;
    }

    private ArrayList<String> aleatorizarImagenes() {
        ArrayList<String> imagenes = new ArrayList();
        for (int i = 1; i <= 6; i++) {
            imagenes.add("img" + i + ".png");
            imagenes.add("img" + i + ".png");
        }
        Collections.shuffle(imagenes);
        return imagenes;
    }

    public void clickEnCarta(BorderPane bp, String nombre) {
        bp.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                return;
            }
            
            bp.setCenter(setImage("/sources/" + nombre));
            clicks++;
            
            if (clicks == 2) {
                if (!nombre.equals(anteriorClick)) {
                    nVidas--;
                    vidas.setText("" + nVidas);
                    ocultarBP(bp);
                    ocultarBP(anteriorBP);
                } else {
                    aciertos++;
                }
                clicks = 0;
            }
            
            // check final del juego por victoria o derrota
            if (aciertos == 6) {
                restart("¡GANASTE, ENHORABUENA!");
                return;
            }
            
            if (nVidas == 0 ) {
                restart("¡Fallaste! vuelve a intentarlo");
                return;
            }

            // cambiamos el bp para el siguiente click
            anteriorClick = nombre;
            anteriorBP = bp;
        });
    }

    public void ocultarBP(BorderPane bp) {
        FadeTransition ft = new FadeTransition(Duration.millis(700), bp.getCenter());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
    
    public void restart(String msg) {
        nVidas = 5;
        aciertos = 0;
        vidas.setText("" + nVidas);
        mostrarMenuResultadoJuego(msg);
        startGame();
    }
    
     public static void mostrarMenuResultadoJuego(String estado) {
        Stage stage = new Stage();

        Text desc = new Text();
        desc.setText(estado);

        StackPane root = new StackPane();
        root.getChildren().add(desc);

        Scene scene = new Scene(root, 200, 70);
        stage.setScene(scene);
        stage.show();
    }
}
