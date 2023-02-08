//import niezbędnych bibliotek
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

//główna klasa pliku
public class MainController {

  @FXML
  private StackPane mainStackPane;

  /**
 * Wstrzyknięcie głównego Pana do kontrolera.
 */
  public void loadMenuScreen() {
    // ładowanie formatki fxml z pliku
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MenuScreen.fxml"));
    Pane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    MenuController menuController = loader.getController();
    menuController.setMainController(this);
    setScreen(pane);
  }

  /**
 * Metoda ładująca do głównego ekranu ekran podrzędny.
 */
  public void setScreen(Pane pane) {
    mainStackPane.getChildren().clear();
    mainStackPane.getChildren().add(pane);
  }

  // metoda wywoływana zaraz po konstruktorze kontrolera
  @FXML
  public void initialize() {

    loadMenuScreen();
  }

}
