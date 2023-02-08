//import niezbędnych bibliotek
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

//główna klasa pliku
public class MenuController {

  // stworzenie pola klasy MainController
  private MainController mainController;

  // wstrzyknięcie przycisków do kontrollera
  @FXML
  private Button graBtn;

  @FXML
  private Button howToBtn;

  @FXML
  private Button endBtn;

  // obsługa akcji przycisków
  @FXML
  void koniec(ActionEvent event) throws Exception {

    // Inicjalizacja ekranu końcowego
    Stage exitStage = new Stage();
    exitStage.setTitle("Zamykanie aplikacji...");
    exitStage.setHeight(435);
    exitStage.setWidth(418);

    Button buttonYes = new Button("TAK");
    buttonYes.wrapTextProperty().setValue(true);
    buttonYes.getStylesheets().add("main.css");
    buttonYes.getStyleClass().add("custom-button");

    Button buttonNo = new Button("NIE");
    buttonNo.wrapTextProperty().setValue(true);
    buttonNo.getStylesheets().add("main.css");
    buttonNo.getStyleClass().add("custom-button");

    ImageView image = new ImageView("https://upload.wikimedia.org/wikipedia/commons/d/da/TicTacToe-152374698XOp.gif");
    image.setFitHeight(200);
    image.setFitWidth(200);

    // Napis główny TIC-TAC-TOE
    String family = "Alegreya";
    double size = 20;
    TextFlow textFlow = new TextFlow();
    textFlow.setLayoutX(10);
    textFlow.setLayoutY(10);
    Text text1 = new Text("Czy na pewno chcesz wyjść?");
    final Text textSpace1 = new Text("");
    final Text textSpace2 = new Text("");
    text1.setFont(Font.font(family,FontWeight.BOLD, size));
    text1.setFill(Color.BLACK);
    textFlow.getChildren().addAll(text1);   
    VBox vbox = new VBox(textSpace1,image,text1,buttonYes,textSpace2,buttonNo);
    vbox.setBackground(new Background(new BackgroundFill(Color.rgb(153, 204, 255), 
        CornerRadii.EMPTY, Insets.EMPTY)));
    vbox.setAlignment(Pos.BASELINE_CENTER);

    Scene scene2 = new Scene(vbox, 400, 400);
    exitStage.setScene(scene2);
    exitStage.show();

    // Obsługa wciśnięcia przycisku
    buttonYes.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event)  {
        try {
          BaseMenager.closeConnection();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        Platform.exit();
      }
    });

    // Obsługa wciśnięcia przycisku
    buttonNo.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        exitStage.close();
      }
    });
  }



  // Przejście do ekranu z instrukcjami
  @FXML
  void howTo(ActionEvent event) {

    VBox root = new VBox();
    root.setPadding(new Insets(20));
    root.setSpacing(10);
    root.setBackground(new Background(new BackgroundFill(Color.rgb(153, 204, 255),
        CornerRadii.EMPTY, Insets.EMPTY)));

    final String family = "Alegreya";
    final double size = 14;
    TextFlow textFlow = new TextFlow();
    textFlow.setLayoutX(10);
    textFlow.setLayoutY(10);
    final Text text1 = new Text("INSTRUKCJA \n\n " 
        + "Kółko i krzyżyk (TIC-TAC-TOE) to bardzo prosta gra.\n "
        + "Jeżeli nigdy o niej nie słyszałeś - kliknij w link poniżej: \n");

    Hyperlink linkeng = new Hyperlink();
    linkeng.setText("Instrukcja [ENG]");

    // Obsługa naciśnięcia linku
    linkeng.setOnAction(e -> {
      if (Desktop.isDesktopSupported()) {
        try {
          Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Tic-tac-toe"));
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (URISyntaxException e1) {
          e1.printStackTrace();
        }
      }
    });

    Hyperlink linkpol = new Hyperlink();
    linkpol.setText("Instrukcja [POL]");

    // Obsługa naciśnięcia linku
    linkpol.setOnAction(e -> {
      if (Desktop.isDesktopSupported()) {
        try {
          Desktop.getDesktop().browse(new URI("https://pl.wikipedia.org/wiki/K%C3%B3%C5%82ko_i_krzy%C5%BCyk"));
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (URISyntaxException e1) {
          e1.printStackTrace();
        }
      }
    });

    text1.setFont(Font.font(family, size));
    text1.setFill(Color.BLACK);
    textFlow.getChildren().addAll(text1);

    // Grafika instrukcji
    ImageView image = new ImageView("https://icon-library.com/images/manual-icon/manual-icon-23.jpg");
    image.setFitHeight(80);
    image.setFitWidth(80);
    VBox root2 = new VBox();
    root2.setPadding(new Insets(20));
    root2.setSpacing(10);
    root2.setBackground(new Background(new BackgroundFill(
        Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    root2.getChildren().addAll(image);
    root2.setAlignment(Pos.BASELINE_CENTER);

    Button returnButton = new Button("Powrót do MENU");
    returnButton.getStylesheets().add("main.css");
    returnButton.getStyleClass().add("custom-button");
    Stage howToStage = new Stage();
    
    // Obsługa wciśnięcia przycisku
    returnButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        howToStage.close();
      }
    });
    // Vbox (kontener) na napisy, przyciski, oraz grafikę
    VBox rootSumUp = new VBox();
    rootSumUp.setPadding(new Insets(20));
    rootSumUp.setSpacing(10);
    rootSumUp.setBackground(new Background(new BackgroundFill(
        Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));

    root.getChildren().addAll(text1, linkeng, linkpol);
    root.getChildren().add(returnButton);
    rootSumUp.getChildren().addAll(root,root2);
    howToStage.setTitle("Instrukcja");
    Scene howToScene = new Scene(rootSumUp, 400, 400);
    howToStage.setScene(howToScene);
    howToStage.show();
  }

  // Przejście do ekranu wyboru gier
  @FXML
  void openApplication(ActionEvent event) {
    // ładowanie nowej formatki fxml z pliku
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StartGameScreen.fxml"));
    Pane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();

    }
    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    StartGameController startGameController = loader.getController();
    startGameController.setMainController(mainController);

    // przekazanie Pane'a do MainControllera
    mainController.setScreen(pane);

  }

  // otwarcie okna statystyk
  @FXML
  void showStats(ActionEvent event) {
    // ładowanie formatki fxml z pliku
    FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("StatsScreen.fxml"));
    BorderPane pane = null;
    try {
      pane = loader2.load();
    } catch (IOException e) {
      e.printStackTrace();

    }

    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    StatsController statsController = loader2.getController();
    statsController.setMainController(mainController);
    mainController.setScreen(pane);

  }

  // metoda wywoływana zaraz po konstruktorze kontrolera
  @FXML
  void initialize() {

  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }
}
