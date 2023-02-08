import java.util.Arrays;
import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameBasic extends Application {

  // Ekran potrzebny do otwarcia gry z innego miejsca w aplikacji
  static Stage classStage = new Stage();

  // Dodaję etykietę o powinności ruchu
  private Label moveInfo;

  // Utworzenie macierzy komórek 3x3 (obiekt klasy Board - plansza)
  private Board[][] board = new Board[3][3];

  // Kto zaczyna?
  private char whoseTurn;

  // Tablica przechowująca kolejne pozycje dostarczane przez algorytm
  // przeszukujący planszę
  int[][] sequence = new int[16][2];
  int position = 0;

  /**
  * Konstruktor klasy - wybór figury jako parametru.
  */
  public GameBasic(String choose) {
    if (choose == "cross") {
      whoseTurn = 'X';
      moveInfo = new Label("Zaczyna X");
    } else if (choose == "circle") {
      whoseTurn = 'O';
      moveInfo = new Label("Zaczyna O");
    }
  }

  // Przycisk wyjścia z aplikacji => zamyka obecne okno (pozostawia Menu otwarte)
  // W dalszej implementacji będzie to "powrót" do poprzedniego ekranu
  Button button = new Button("Dalej");

  @Override
  public void start(Stage primaryStage) {

    classStage = primaryStage;
    button.getStylesheets().add("main.css");
    button.getStyleClass().add("custom-button");
    button.setMaxHeight(60);
    button.setMinHeight(60);
    button.setPrefHeight(60);

    // Ustrukturyzowanie komórek w 3 rzędy + 3 kolumny
    // GridPane wymusza strukturę "szachownicy"
    GridPane pane = new GridPane();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pane.add(board[i][j] = new Board(), j, i);
      }
    }

    // Border Pane = ułożenie ekranu (w centrum: gra + etykieta na dole i górze)
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
    moveInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    borderPane.setTop(moveInfo);

    // Obsługa opuszczenia aplikacji inaczej niż przez "x"
    button.setOnAction((ActionEvent event) -> {
      primaryStage.close(); // zamknięcie okna pośredniego
    });

    borderPane.setBottom(button);
    BorderPane.setAlignment(button, Pos.CENTER);
    borderPane.setStyle("-fx-background-color: #99ccff;");

    // Utworzenie Scene + tytuł
    Scene scene = new Scene(borderPane, 300, 350);
    primaryStage.setTitle("TIC-TAC-TOE");
    primaryStage.setScene(scene);
    // primaryStage.show();
  }

  /**
  * Sprawdzenie, czy istnieje już wygrana kombinacja.
  */
  public boolean gameWin(char sign) {

    // Sprawdzenia kolumnowe i wierszowe (6 przypadków)
    for (int i = 0; i < 3; i++) {
      if ((board[i][0].getToken() == sign && board[i][1].getToken() == sign 
          && board[i][2].getToken() == sign)
          || (board[0][i].getToken() == sign && board[1][i].getToken() == sign 
          && board[2][i].getToken() == sign)) {
        return true;
      }
    }

    // Przypadki szczególne = ułożenie na przekątnej planszy (2 przypadki)
    if ((board[0][0].getToken() == sign && board[1][1].getToken() == sign 
        && board[2][2].getToken() == sign)
        || (board[0][2].getToken() == sign && board[1][1].getToken() == sign 
        && board[2][0].getToken() == sign)) {
      return true;
    }
    return false;
  }

  /**
  * Remis - funkcja sprawdza czy plansza pełna, ale bez rozstrzygnięcia.
  */
  public boolean itsDraw() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j].getToken() == ' ') {
          return false;
        }
      }
    }
    return true;
  }

  // Klasa zgrupowanych komórek ( nasza plansza do gry )
  public class Board extends Pane {
    private char token = ' '; // token to albo x, albo o

    /**
    * Klasa Board (plansza) dziedziczy po Pane i pozwala ustrykturyzować komórki w planszę 3x3.
    */
    public Board() {
      setStyle("-fx-border-color: indigo");
      this.setPrefSize(800, 800);
      this.setOnMouseClicked(e -> {
        if (this.getToken() == ' ') {
          handleMouseClick();
        } else if (this.getToken() != ' ') {
          this.setDisable(true);
        }
      });
    }

    // Zwraca znak gracza z danego pola
    public char getToken() {
      return token;
    }


    /**
    * Na danym polu stawiany jest znacznik tzn. kółko albo krzyżyk.
    */
    public void setToken(char sign) {
      token = sign;

      if (token == 'X') {
        Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
        line1.endXProperty().bind(this.widthProperty().subtract(10));
        line1.endYProperty().bind(this.heightProperty().subtract(10));
        line1.setStroke(Color.RED);
        line1.setStrokeWidth(4.0);

        Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
        line2.startYProperty().bind(this.heightProperty().subtract(10));
        line2.endXProperty().bind(this.widthProperty().subtract(10));
        line2.setStroke(Color.RED);
        line2.setStrokeWidth(4.0);

        // Dodanie narysowanego znaku na ekran
        this.getChildren().addAll(line1, line2);
      } else if (token == 'O') {
        Ellipse ellipse = new Ellipse(this.getWidth() / 2,
            this.getHeight() / 2, this.getWidth() / 2 - 10,
            this.getHeight() / 2 - 10);
        ellipse.centerXProperty().bind(this.widthProperty().divide(2));
        ellipse.centerYProperty().bind(this.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
        ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
        ellipse.setStroke(Color.GREEN);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStrokeWidth(4.0);

        getChildren().add(ellipse); // Dodanie kółka do ekranu
      }
    }

    private void handleMouseClick() {
      // Pusta komórka -> wstawienie znacznika o/x
      if (token == ' ' && whoseTurn != ' ') {
        setToken(whoseTurn);
        for (int q = 0; q < 3; q++) {
          for (int v = 0; v < 3; v++) {

            int[] tablica = { q + 1, v + 1 };
            if (board[q][v].getToken() == 'X' && !isInArray(tablica, sequence)) {
              sequence[position][0] = (q + 1);
              sequence[position][1] = (v + 1);
              position = position + 1;

            }
            if (board[q][v].getToken() == 'O' && !isInArray(tablica, sequence)) {
              sequence[position][0] = (q + 1);
              sequence[position][1] = (v + 1);
              position = position + 1;

            }
          }
        }

        // Wygrana
        if (gameWin(whoseTurn)) {

          try {
            if (whoseTurn == 'X') {
              if (StartGameController.reverse) {
                BaseMenager.addTeamResults(BaseMenager.connection, StartGameController.nicknameP1,
                    StartGameController.nicknameP2, 1);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 1);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP2, 0);
              } else {
                BaseMenager.addTeamResults(BaseMenager.connection, StartGameController.nicknameP1,
                    StartGameController.nicknameP2, 0);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 0);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP2, 1);
              }
            } else {
              if (StartGameController.reverse) {
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 0);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP2, 1);
                BaseMenager.addTeamResults(BaseMenager.connection, StartGameController.nicknameP1,
                    StartGameController.nicknameP2, 0);
              } else {
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 1);
                BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP2, 0);
                BaseMenager.addTeamResults(BaseMenager.connection, StartGameController.nicknameP1,
                    StartGameController.nicknameP2, 1);
              }

            }
            BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
                StartGameController.nicknameP2, sequence);

          } catch (Exception e) {
            System.out.println(e);
          }

          moveInfo.setText(whoseTurn + " wygrywa");
          moveInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

          // Obsługa komunikatu o rezultacie
          String family = "Helvetica";
          double size = 50;
          TextFlow textFlow = new TextFlow();
          textFlow.setLayoutX(10);
          textFlow.setLayoutY(10);
          Text text1 = new Text(whoseTurn + " wygrywa");
          text1.setFont(Font.font(family, FontWeight.BOLD, size));
          text1.setFill(Color.LIME);
          textFlow.getChildren().addAll(text1);
          Stage mystage = new Stage();
          Group group = new Group(textFlow);
          Scene scene = new Scene(group, 300, 80, Color.LIGHTPINK);
          mystage.setTitle("Tic-Tac-Toe GAME");
          mystage.setScene(scene);
          mystage.show();

          // Okno samo się zamyka
          PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
          delay.setOnFinished(event -> mystage.close());
          delay.play();

          whoseTurn = ' ';
          // Remis
        } else if (itsDraw()) {

          try {
            BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 2);
            BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP2, 2);
            BaseMenager.addTeamResults(BaseMenager.connection, StartGameController.nicknameP1,
                StartGameController.nicknameP2, 2);
            BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
                StartGameController.nicknameP2, sequence);
          } catch (Exception e) {
            System.out.println(e);
          }
          moveInfo.setText("Remis :-(");
          moveInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

          // Obsługa komunikatu o rezultacie
          String family = "Helvetica";
          double size = 50;
          TextFlow textFlow = new TextFlow();
          textFlow.setLayoutX(10);
          textFlow.setLayoutY(10);
          Text text1 = new Text("Tym razem remis");
          text1.setFont(Font.font(family, FontWeight.BOLD, size));
          text1.setFill(Color.YELLOW);
          textFlow.getChildren().addAll(text1);
          Stage mystage = new Stage();
          Group group = new Group(textFlow);
          Scene scene = new Scene(group, 420, 80, Color.PALETURQUOISE);
          mystage.setTitle("Tic-Tac-Toe GAME");
          mystage.setScene(scene);
          mystage.show();

          // Okno samo się zamyka
          PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
          delay.setOnFinished(event -> mystage.close());
          delay.play();

          whoseTurn = ' ';
        } else {
          // Ruch przeciwnika
          whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
          moveInfo.setText("Teraz postaw " + whoseTurn);
          moveInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        }
      }
    }

    /**
    * Funkcja sprawdza, czy element już został dodany do historii ruchów.
    */
    public boolean isInArray(int[] sub, int[][] sup) {
      for (int i = 0; i < sup.length; i++) {
        if (Arrays.equals(sub, sup[i])) {
          return true;
        }
      }
      return false;
    }
  }

  // Uruchomienie okna gry
  public static void main(String[] args) {
    launch(args);
  }
}
