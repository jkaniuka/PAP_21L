import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

public class ComputerRandom extends Application {

  // Statyczny stage na potrzeby wywołania klasy z innego miejsca w programie
  static Stage classStage = new Stage();

  // Dodaję etykietę o powinności ruchu
  // Zaczyna człowiek i jest on "X"
  private Label moveInfo = new Label("Zaczyna X");

  // Utworzenie macierzy komórek 3x3 (obiekt klasy Board - plansza)
  private Board[][] board = new Board[3][3];

  // Rozróżnienie pomiędzy osobą, a komputerem
  private char comp = 'O';
  private char player = 'X';

  // Przycisk wyjścia z aplikacji => zamyka obecne okno (pozostawia Menu otwarte)
  // W dalszej implementacji będzie to "powrót" do poprzedniego ekranu
  Button button = new Button("Dalej");

  // Tablica przechowująca kolejne pozycje dostarczane przez algorytm
  // przeszukujący planszę
  int[][] sequence = new int[16][2];
  int position = 0;

  // Flaga poziomu gry
  String gameLevel;

  // Konstrukktor z parametrem poziom
  public ComputerRandom(String levelOfDifficulty) {
    gameLevel = levelOfDifficulty;

  }

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

    // Obsługa opuszczenia okna inaczej niż przez "x"
    button.setOnAction((ActionEvent event) -> {
      primaryStage.close();
    });
    borderPane.setBottom(button);
    BorderPane.setAlignment(button, Pos.CENTER);

    // Utworzenie Scene + tytuł
    Scene scene = new Scene(borderPane, 300, 350);
    borderPane.setStyle("-fx-background-color: #99ccff;");
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
      this.setOnMouseClicked(e -> humanMove());

      this.setOnMouseClicked(e -> {
        if (this.getToken() == ' ') {
          humanMove();
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

    // Lista potencjalnych kombinacji wartych blokady
    final List<String> mustBlock = Arrays.asList(new String[] { "XX ", "X X", " XX" });

    // Komputer losuje ( random () ) miejsce, gdzie może postawić znak
    private void machineTurn() {
      // Znacznik wykonania ruchu
      int allowLoop = 0;
      // Random używany do pierwszego ruchu komputera (pełni funkcję ziarna -> seed)

      // Iteratory kolumnowy i wierszowy
      int times = 0;
      int times2 = 0;

      if (gameLevel == "easy") {
        while (allowLoop == 0) {
          Random r = new Random();
          int i = r.nextInt(3);
          int j = r.nextInt(3);
          if (board[i][j].getToken() == ' ') {
            board[i][j].setToken(comp);
            sequence[position][0] = (i + 1);
            sequence[position][1] = (j + 1);
            position = position + 1;
            allowLoop = 1; // wyjście z pętli
          }
        }

      } else if (gameLevel == "medium") {
        // COLUMNS
        while (allowLoop == 0 & times < 3) {
          if (mustBlock.contains(new StringBuilder().append(board[0][times].getToken())
              .append(board[1][times].getToken()).append(board[2][times].getToken()).toString())) {
            for (int x = 0; x < 3; x++) {
              if (board[x][times].getToken() == ' ' & allowLoop == 0) {
                board[x][times].setToken(comp);
                sequence[position][0] = (x + 1);
                sequence[position][1] = (times + 1);
                position = position + 1;
                allowLoop = 1;
              }
            }
          }
          times = times + 1;
        }

        // ROWS
        while (allowLoop == 0 & times2 < 3) {
          if (mustBlock.contains(new StringBuilder().append(board[times2][0].getToken())
              .append(board[times2][1].getToken())
              .append(board[times2][2].getToken()).toString())) {
            for (int x = 0; x < 3; x++) {
              if (board[times2][x].getToken() == ' ' & allowLoop == 0) {
                board[times2][x].setToken(comp);
                sequence[position][0] = (times2 + 1);
                sequence[position][1] = (x + 1);
                position = position + 1;
                allowLoop = 1;
              }
            }
          }
          times2 = times2 + 1;
        }

        // Diagonal 1
        if (allowLoop == 0 & mustBlock.contains(new StringBuilder().append(board[0][0].getToken())
            .append(board[1][1].getToken()).append(board[2][2].getToken()).toString())) {
          for (int x = 0; x < 3; x++) {
            if (board[x][x].getToken() == ' ' & allowLoop == 0) {
              board[x][x].setToken(comp);
              sequence[position][0] = (x + 1);
              sequence[position][1] = (x + 1);
              position = position + 1;
              allowLoop = 1;
            }
          } // Diagonal 2
        } else if (allowLoop == 0 & allowLoop == 0 & mustBlock.contains(new StringBuilder()
            .append(board[2][0].getToken())
            .append(board[1][1].getToken())
            .append(board[0][2].getToken()).toString())) {
          if (board[2][0].getToken() == ' ') {
            board[2][0].setToken(comp);
            sequence[position][0] = (2 + 1);
            sequence[position][1] = (0 + 1);
            position = position + 1;
            allowLoop = 1;
          } else if (board[1][1].getToken() == ' ') {
            board[1][1].setToken(comp);
            sequence[position][0] = (1 + 1);
            sequence[position][1] = (1 + 1);
            position = position + 1;
            allowLoop = 1;
          } else if (board[0][2].getToken() == ' ') {
            board[0][2].setToken(comp);
            sequence[position][0] = (0 + 1);
            sequence[position][1] = (2 + 1);
            position = position + 1;
            allowLoop = 1;
          }
        }

        // Losowy ruch na początek
        while (allowLoop == 0) {
          Random r = new Random();
          int i = r.nextInt(3);
          int j = r.nextInt(3);
          if (board[i][j].getToken() == ' ') {
            board[i][j].setToken(comp);
            sequence[position][0] = (i + 1);
            sequence[position][1] = (j + 1);
            position = position + 1;
            allowLoop = 1;
          }
        }

      }

      if (gameWin(comp)) {
        try {
          BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 1);
          BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
              "Komputer", sequence);
        } catch (Exception e) {
          System.out.println(e);
        }

        moveInfo.setText(comp + " wygrywa");
        comp = ' ';

        // Obsługa komunikatu o rezultacie
        String family = "Helvetica";
        double size = 50;
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(10);
        textFlow.setLayoutY(10);
        Text text1 = new Text("Nie tym razem ...");
        text1.setFont(Font.font(family, FontWeight.BOLD, size));
        text1.setFill(Color.RED);
        textFlow.getChildren().addAll(text1);
        Stage mystage = new Stage();
        Group group = new Group(textFlow);
        Scene scene = new Scene(group, 430, 80, Color.SILVER);
        mystage.setTitle("Tic-Tac-Toe GAME");
        mystage.setScene(scene);
        mystage.show();

        // Okno samo się zamyka
        PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
        delay.setOnFinished(event -> mystage.close());
        delay.play();
      } else if (itsDraw()) {
        try {
          BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 2);
          BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
               "Komputer", sequence);
        } catch (Exception e) {
          System.out.println(e);
        }
        moveInfo.setText("Mamy remis");
        comp = ' ';

        // Obsługa komunikatu o rezultacie
        String family = "Helvetica";
        double size = 50;
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(10);
        textFlow.setLayoutY(10);
        Text text1 = new Text(" REMIS ");
        text1.setFont(Font.font(family, FontWeight.BOLD, size));
        text1.setFill(Color.FUCHSIA);
        textFlow.getChildren().addAll(text1);
        Stage mystage = new Stage();
        Group group = new Group(textFlow);
        Scene scene = new Scene(group, 200, 80, Color.LIGHTBLUE);
        mystage.setTitle("Tic-Tac-Toe GAME");
        mystage.setScene(scene);
        mystage.show();

        // Okno samo się zamyka
        PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
        delay.setOnFinished(event -> mystage.close());
        delay.play();

      }
    }

    // Obsługa kliknięcia człowieka
    private void humanMove() {
      System.out.println("Human");
      if (token == ' ' && player != ' ' && comp != ' ') {
        setToken(player);
        // Przy zapisie sekwencji numeracja pól planszy jest tożsama z indeksowaniem
        // elementów macierzy
        for (int q = 0; q < 3; q++) {
          for (int v = 0; v < 3; v++) {

            int[] tablica = { q + 1, v + 1 };
            if (board[q][v].getToken() == 'X' && !isInArray(tablica, sequence)) {
              sequence[position][0] = (q + 1);
              sequence[position][1] = (v + 1);
              position = position + 1;

            }
          }
        }

        // Sprawdzenie wygranej
        if (gameWin(player)) {

          try {
            BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 0);
            BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
                 "Komputer", sequence);
          } catch (Exception e) {
            System.out.println(e);
          }
          moveInfo.setText(player + " wygrywa - maszyna pokonana");
          player = ' ';

          // Obsługa komunikatu wygranej przez człowieka
          String family = "Helvetica";
          double size = 50;
          TextFlow textFlow = new TextFlow();
          textFlow.setLayoutX(10);
          textFlow.setLayoutY(10);
          Text text1 = new Text("Wygrałeś");
          text1.setFont(Font.font(family, FontWeight.BOLD, size));
          text1.setFill(Color.NAVY);
          textFlow.getChildren().addAll(text1);
          Stage mystage = new Stage();
          Group group = new Group(textFlow);
          Scene scene = new Scene(group, 250, 80, Color.LIMEGREEN);
          mystage.setTitle("Tic-Tac-Toe GAME");
          mystage.setScene(scene);
          mystage.show();

          // Okienko samo się zamyka
          PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
          delay.setOnFinished(event -> mystage.close());
          delay.play();

          // Remis
        } else if (itsDraw()) {

          try {
            BaseMenager.addResults(BaseMenager.connection, StartGameController.nicknameP1, 2);
            BaseMenager.addHistory(BaseMenager.connection, StartGameController.nicknameP1,
                 "Komputer", sequence);
          } catch (Exception e) {
            System.out.println(e);
          }
          moveInfo.setText("Remis");
          player = ' ';

          // Obsługa komunikatu o rezultacie
          String family = "Helvetica";
          double size = 50;
          TextFlow textFlow = new TextFlow();
          textFlow.setLayoutX(10);
          textFlow.setLayoutY(10);
          Text text1 = new Text(" REMIS ");
          text1.setFont(Font.font(family, FontWeight.BOLD, size));
          text1.setFill(Color.FUCHSIA);
          textFlow.getChildren().addAll(text1);
          Stage mystage = new Stage();
          Group group = new Group(textFlow);
          Scene scene = new Scene(group, 200, 80, Color.LIGHTBLUE);
          mystage.setTitle("Tic-Tac-Toe GAME");
          mystage.setScene(scene);
          mystage.show();

          // Okno samo się zamyka
          PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
          delay.setOnFinished(event -> mystage.close());
          delay.play();
          // Ruch maszyny
        } else {
          machineTurn();
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