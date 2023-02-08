
//import niezbędnych bibliotek
import java.sql.ResultSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


//główna klasa pliku
public class StartGameController {

  // stworzenie pola klasy MainController
  private MainController mainController;

  @FXML
  private ToggleButton g1;

  @FXML
  private ToggleGroup group1;

  @FXML
  private ToggleButton g2;

  @FXML
  private ToggleButton g3;

  @FXML
  private ToggleButton g4;

  @FXML
  private TextField nick;

  public static String nicknameP1;
  public static String nicknameP2;
  public static Boolean reverse = false;
  public static int no;

  // wywołanie powrotu do Menu
  @FXML
  void backToMenu() {
    mainController.loadMenuScreen();
  }

  // Gra multiplayer
  @FXML
  void gameMulti() {

    try {

      // Sprawdzenie gracza w bazie danych
      nicknameP1 = checkPlayer(nick.getText());

      // Liczba gier do rozegrania
      no = noGames(group1);

      TextInputDialog dialog = new TextInputDialog("Podaj nick drugiego gracza");
      dialog.setTitle("Muliplayer game");
      dialog.setHeaderText("Podaj nick drugiego gracza");
      dialog.getDialogPane().getStylesheets().add("myDialogs.css");
      dialog.getDialogPane().getStyleClass().add("dialog-pane");
      dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);

      dialog.showAndWait();

      String result = dialog.getResult();
      nicknameP2 = checkPlayer(result);

      // Pobranie teamu z bazy danych
      ResultSet r = BaseMenager.getTeam(BaseMenager.connection, nicknameP1, nicknameP2);

      // Jeśli team nie istnieje
      if (!r.next()) {

        // Pobranie teamu z bazy danych dla odwrotnej kolejności graczy
        r = BaseMenager.getTeam(BaseMenager.connection, nicknameP2, nicknameP1);
        //Jeśli nigdy nie było rozgrywki pomiędzy danymi graczami
        if (!r.next()) {
          // Dodanie teamu do bazy danych
          BaseMenager.addNewTeam(BaseMenager.connection, nicknameP1, nicknameP2);
        } else {
          // Zapamiętanie informacji o odwrotnej kolejności graczy
          reverse = true;
          String string = nicknameP1;
          nicknameP1 = nicknameP2;
          nicknameP2 = string;
        }
      }

      // Interfejs wyboru kształtu figury do gry

      BorderPane bp = new BorderPane();
      bp.setPadding(new Insets(10, 20, 10, 20));

      // TOP
      Text header = new Text("Kółko czy krzyżyk?\n");
      bp.setTop(header);
      header.setStyle("-fx-font-family: 'Alegreya';" + "-fx-font-size: 45px;");
      BorderPane.setAlignment(header, Pos.CENTER);
      bp.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));

      // LEFT
      // Guzik z ikoną krzyżyka
      ImageView imageView = new ImageView(
          "https://banner2.cleanpng.com/20180328/toe/kisspng-mitchell-aluminium-american-red-cross-symbol-clip-wrong-5abc6250e6c9b6.5732349715222953769453.jpg");
      imageView.setFitHeight(50);
      imageView.setFitWidth(50);
      Button buttonCross = new Button("", imageView);
      bp.setLeft(buttonCross);

      // RIGHT
      // Guzik z ikoną kółka
      ImageView imageView2 = new ImageView(
          "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Blue_circle_for_diabetes.svg/480px-Blue_circle_for_diabetes.svg.png");
      imageView2.setFitHeight(50);
      imageView2.setFitWidth(50);
      Button buttonCircle = new Button("", imageView2);
      bp.setRight(buttonCircle);
      Stage primaryStage = new Stage();
      // Obsługa wyboru figury
      buttonCross.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          for (int i = 0; i < no; i++) {
            GameBasic game = new GameBasic("cross");
            primaryStage.close();
            game.start(GameBasic.classStage);
            GameBasic.classStage.showAndWait();
          }
        }
      });

      buttonCircle.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

          for (int i = 0; i < no; i++) {
            GameBasic game = new GameBasic("circle");
            primaryStage.close();
            game.start(GameBasic.classStage);
            GameBasic.classStage.showAndWait();
          }
        }
      });

      // CENTER
      Text description = new Text("   Tryb Multiplayer\n osoba wybierająca figurę zaczyna\n");
      description.setStyle("-fx-font-family: 'Alegreya';" + "-fx-font-size: 18px;");
      description.setTextAlignment(TextAlignment.CENTER);
      BorderPane.setAlignment(description, Pos.CENTER);
      bp.setCenter(description);

      // BOTTOM
      // Przycisk cofania
      Button returnToMenu = new Button("Powrót do MENU");
      returnToMenu.setStyle("-fx-font-family: 'Alegreya';" 
          + "-fx-font-size: 20px;" + "-fx-background-color: transparent;");

      // Obsługa wciśnięcia przycisku
      returnToMenu.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          primaryStage.close();
        }
      });
      BorderPane.setAlignment(returnToMenu, Pos.CENTER);
      bp.setBottom(returnToMenu);

      Scene scene = new Scene(bp, 400, 400);
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (Exception e) {
      System.out.println(e);
    }

  }

  @FXML
  void startGame() {

    try {

      // Sprawdzenie gracza w bazie danych
      nicknameP1 = checkPlayer(nick.getText());

      // Liczba gier do rozegrania
      no = noGames(group1);

      // Okno wyboru poziomu trudności
      Stage primaryStage = new Stage();
      primaryStage.setTitle("Wybór trybu gry");

      ChoiceBox<String> choiceBox = new ChoiceBox<>();
      choiceBox.getItems().add("Easy");
      choiceBox.getItems().add("Medium");
      choiceBox.getItems().add("Expert");
      choiceBox.getStylesheets().add("main.css");
      choiceBox.getStyleClass().add(".choice-box");

      // Potwierdzenie wyboru trybu gry
      Button buttonConfirmation = new Button("Rozpocznij grę");
      buttonConfirmation.wrapTextProperty().setValue(true);
      buttonConfirmation.getStylesheets().add("main.css");
      buttonConfirmation.getStyleClass().add("custom-button");
      buttonConfirmation.setMaxHeight(40);
      buttonConfirmation.setMinHeight(40);
      buttonConfirmation.setPrefHeight(40);

      // Obsługa wciśnięcia przycisku
      buttonConfirmation.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String value = (String) choiceBox.getValue();

          for (int i = 0; i < no; i++) {
            if (value == null) {
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.getDialogPane().getStylesheets().add("myDialogs.css");
              alert.getDialogPane().getStyleClass().add("dialog-pane");
              alert.setContentText("Wybierz poziom trudności");
              alert.show();

            } else if (value == "Easy") {
              ComputerRandom game = new ComputerRandom("easy");
              primaryStage.close();
              game.start(ComputerRandom.classStage);
              ComputerRandom.classStage.showAndWait();

            } else if (value == "Medium") {
              ComputerRandom game = new ComputerRandom("medium");
              primaryStage.close();
              game.start(ComputerRandom.classStage);
              ComputerRandom.classStage.showAndWait();

            } else if (value == "Expert") {
              MiniMax game = new MiniMax();
              primaryStage.close();
              game.start(MiniMax.classStage);
              MiniMax.classStage.showAndWait();
            }
          }

        }
      });

      Button buttonExit = new Button("Cofnij");
      buttonExit.wrapTextProperty().setValue(true);
      buttonExit.getStylesheets().add("main.css");
      buttonExit.getStyleClass().add("custom-button");
      buttonExit.setMaxHeight(40);
      buttonExit.setMinHeight(40);
      buttonExit.setPrefHeight(40);

      // Obsługa wciśnięcia przycisku
      buttonExit.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          primaryStage.close();
        }
      });

      // Opis gry

      TextFlow textFlow = new TextFlow();
      textFlow.setLayoutX(10);
      textFlow.setLayoutY(10);

      Text easyDescription = new Text(
          "Easy - najprostszy poziom gry. Komputer wybiera miejsce"
          + " postawienia kółka w sposób (pseudo)losowy.\n");
      Text mediumDescription = new Text(
          "Medium - komputer będzie blokować Twoje ruchy potencjalnie prowadzące do wygranej.\n");
      Text expertDescription = new Text(
          "Expert - komputer planuje swoje ruchy w oparciu o heurystyczny," 
          + "rekursywny algorytm MINI-MAX.\n");
      easyDescription.setWrappingWidth(400);
      mediumDescription.setWrappingWidth(400);
      expertDescription.setWrappingWidth(400);
      String family = "Alegreya";
      double size = 20;

      Text text1 = new Text("Wybierz poziom trudności");
      text1.setFont(Font.font(family, FontWeight.BOLD, size));
      text1.setFill(Color.BLACK);
      textFlow.getChildren().addAll(text1);
      Text textSpace1 = new Text("");
      Text textSpace2 = new Text("");
      VBox vbox = new VBox(text1, easyDescription, mediumDescription,
          expertDescription, textSpace1, textSpace2,
          choiceBox, buttonConfirmation, buttonExit);
      vbox.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
      vbox.setAlignment(Pos.BASELINE_CENTER);
      vbox.setStyle("-fx-font-family: 'Alegreya';" + "-fx-font-size: 15px;");
      Scene scene = new Scene(vbox, 400, 400);
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (Exception e) {

      System.out.println(e);
    }
  }

  @FXML
  void startSpeedRun() {

    try {
      // Liczba gier
      no = noGames(group1);

      // Sprawdzenie gracza w bazie danych
      nicknameP1 = checkPlayer(nick.getText());

      // Ekran z animacja "biegnącego czasu"
      Stage mystage = new Stage();
      mystage.setTitle("Tic-Tac-Toe GAME");

      Button startSpeedRun = new Button("Zaczynam grę");
      startSpeedRun.wrapTextProperty().setValue(true);
      startSpeedRun.getStylesheets().add("main.css");
      startSpeedRun.getStyleClass().add("custom-button");


      ImageView image = new ImageView("http://caccioppoli.com/Animated%20gifs/Clock/RUNNING.gif");
      image.setFitHeight(200);
      image.setFitWidth(200);

      String family = "Alegreya";
      double size = 20;
      TextFlow textFlow = new TextFlow();
      textFlow.setLayoutX(10);
      textFlow.setLayoutY(10);
      Text text1 = new Text("Gotowy na prawdziwe wyzwanie?");

      text1.setFont(Font.font(family, FontWeight.BOLD, size));
      text1.setFill(Color.BLACK);
      textFlow.getChildren().addAll(text1);
      Text textSpace1 = new Text("");
      VBox vbox = new VBox(textSpace1, image, text1, startSpeedRun);
      vbox.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
      vbox.setAlignment(Pos.BASELINE_CENTER);

      Scene scene2 = new Scene(vbox, 400, 400);
      mystage.setScene(scene2);
      mystage.show();



      // Interfejs suwaka

      // Obsługa suwaka

      Label label = new Label("Wybierz limit czasu na wykonanie ruchu");
      label.setStyle("-fx-font-family: 'Alegreya';" + "-fx-font-size: 15px;");

      Label l = new Label(" ");
      l.setStyle("-fx-font-family: 'Alegreya';" + "-fx-font-size: 15px;");
      l.setTextFill(Color.BLACK);

      // Utworzenie suwaka
      Slider slider = new Slider();

      // Limity
      slider.setMin(1);
      slider.setMax(10);
      slider.setValue(5);
      slider.setMajorTickUnit(1);
      slider.setShowTickLabels(true);
      slider.setShowTickMarks(true);
      slider.setBlockIncrement(1);

      // Klasa reprezentująca limit czasu na wykonanie ruchu
      class MyTime {
        int timeForMove = 5; // defaultowy czas na ruch to 5 sekund
      }
      // Obiekt gamelimit przechowuję dane o czasie -> potrzebne w dalszej części
      // programu

      MyTime gamelimit = new MyTime();

      // Adding Listener
      slider.valueProperty().addListener(new ChangeListener<Number>() {

        public void changed(
            ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          l.setText("Wybrany czas to : " + newValue.intValue() + " sekund");
          gamelimit.timeForMove = newValue.intValue();
        }
      });

      // Komunikat o zasadach gry

 
      TextFlow textFlow2 = new TextFlow();
      textFlow2.setLayoutX(10);
      textFlow2.setLayoutY(10);
      Text text12 = new Text("W tej wersji gry musisz znać swoje możliwości. "
          + "Wybierz, ile czasu dajesz sobie na wykonanie ruchu. "
          + "Po rozpoczęciu gry to Ty zaczynasz - maszyna odpowie od razu. "
          + "Potem masz tyle czasu na wykonie ruchu, ile wybrałeś. "
          + "Jeżeli nie wykonasz ruchu, komputer przejmie inicjatywę i"
          + " stracisz swoją kolej. Bądź szybki i powodzenia.\n\n");
      String family2 = "Alegreya";
      double size2 = 14;
      text12.setFont(Font.font(family2, size2));
      text12.setFill(Color.BLACK);
      textFlow2.getChildren().addAll(text12);

      // Komunikat domyslny limit
      Text text22 = new Text("Domyślny limit to 5.0 sekund");
      text22.setFont(Font.font(family2, FontWeight.BOLD, 15));
      text22.setFill(Color.BLACK);
      textFlow2.getChildren().addAll(text22);

      // create a VBox
      VBox root = new VBox();
      root.setPadding(new Insets(20));
      root.setSpacing(10);
      root.getChildren().addAll(label, slider, l, textFlow2);
      root.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
      Button startTheGame = new Button("Kliknij, aby zacząć");
      startTheGame.getStylesheets().add("main.css");
      startTheGame.getStyleClass().add("custom-button");
      Stage sliderStage = new Stage();

      // Obsługa wciśnięcia przycisku
      startTheGame.setOnAction(new EventHandler<ActionEvent>() {

        @Override
      public void handle(ActionEvent e) {
        

          for (int i = 0; i < no; i++) {
            SpeedRun game = new SpeedRun(gamelimit.timeForMove);
            sliderStage.close();
            game.start(SpeedRun.classStage);
            SpeedRun.classStage.showAndWait();
          }
          SpeedRun game = new SpeedRun(gamelimit.timeForMove);
          game.start(SpeedRun.classStage);
        }
      });
      root.getChildren().add(startTheGame);
      sliderStage.setTitle("Set time limit");

      Scene scene2Slider = new Scene(root, 400, 400);
      sliderStage.setScene(scene2Slider);

      // ekran z animacją - przycisk otwiera slider
      startSpeedRun.setOnAction(new EventHandler<ActionEvent>() {

        @Override
      public void handle(ActionEvent event) {
          sliderStage.show();
          mystage.close();

        }
      });
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  // metoda wywoływana zaraz po konstruktorze kontrolera
  @FXML
  void initialize() {

  }
  
  /**
 * Sprawdzemnie gracza w bazie danych.
 */
  public String checkPlayer(String nickname) throws Exception {

    ResultSet r = null;

    // wymuszenie podania nicku gracza
    if (nickname.isEmpty()) {

      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.getDialogPane().getStylesheets().add("myDialogs.css");
      alert.getDialogPane().getStyleClass().add("dialog-pane");
      alert.setContentText("Wprowadź nick gracza");
      alert.show();
      throw new Exception("Empty nick");
    }

    // Sprawdzenie czy gracz jest nowy
    Boolean newUser = true;
    r = BaseMenager.getStats(BaseMenager.connection);

    while (r.next()) {
      if (nickname.equals(r.getString("NICK"))) {
        newUser = false;
      }

    }

    // jeśli gracz już istnieje w bazie danych
    if (newUser == false) {

      ButtonType btnType = showAlert(
          AlertType.CONFIRMATION, "info", "Gracz z takim nickiem już istnieje. Czy to ty?");

      // jeśli gracz nie chce grać pod podanym nickiem
      if (btnType == ButtonType.NO) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getDialogPane().getStylesheets().add("myDialogs.css");
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        alert.setContentText("Wprowadź nowy inny nick");
        alert.showAndWait();

        throw new Exception("Choose new nick");

      }

    } else {

      // jeśli gracz nie istnieje utwórz nowego
      BaseMenager.addNewPlayer(BaseMenager.connection, nickname);

    }
    return nickname;
  }

  /**
 * Wyświetlenie alertu.
 */
  public static ButtonType showAlert(Alert.AlertType alertType, String title, String message) {

    Alert alert = new Alert(alertType, title, ButtonType.YES, ButtonType.NO);
    alert.getDialogPane().getStylesheets().add("myDialogs.css");
    alert.getDialogPane().getStyleClass().add("dialog-pane");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

    return alert.getResult();
  }

  /**
 * Zwrócenie ilości gier do rozgrywki na podstaweie zaznaczonego przycisku.
 */
  public static int noGames(ToggleGroup group) {

    ToggleButton selectedToggleButton = (ToggleButton) group.getSelectedToggle();
    String id = selectedToggleButton.getId();
    if (id.equals("g1")) {
      return 1;
    } else if (id.equals("g2")) {
      return 2;
    } else if (id.equals("g3")) {
      return 3;
    } else if (id.equals("g4")) {
      return 4;
    } else {
      return 0;
    }

  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

}
