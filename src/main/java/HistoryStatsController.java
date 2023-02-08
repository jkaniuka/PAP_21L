
import java.io.IOException;
import java.sql.ResultSet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class HistoryStatsController {

  private MainController mainController;

  @FXML
  private BorderPane borderPane;

  @FXML
  private GridPane grid;

  @FXML
  private Button menuBtn;

  @FXML
  private Button chartBtn;

  @FXML
  private Button gameStatsBtn;

  @FXML
  private Button statsBtn;

  int position = 1;

  // wywołanie powrotu do menu
  @FXML
  void backToMenu() {
    mainController.loadMenuScreen();

  }

  // otwarcie okna statystyk meczowych
  @FXML
  void toGameStats(ActionEvent event) {
    // ładowanie formatki fxml z pliku
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameStatsScreen.fxml"));
    BorderPane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    GameStatsController gameStatsController = loader.getController();
    gameStatsController.setMainController(mainController);
    mainController.setScreen(pane);
  }

  @FXML
  void showChart() {

    // Wykres kołowy statystyk na podstawie DB

    ResultSet r = null;

    try {

      r = BaseMenager.getStats(BaseMenager.connection);
      int winChart = 0;
      int loseChart = 0;
      int drawChart = 0;

      while (r.next()) {

        winChart = winChart + Integer.parseInt(r.getString("WIN"));
        loseChart = loseChart + Integer.parseInt(r.getString("LOSE"));
        drawChart = drawChart + Integer.parseInt(r.getString("DRAW"));
      }

      Stage primaryStage = new Stage();
      primaryStage.setTitle("Wykres kołowy statystyk");

      PieChart pieChart = new PieChart();

      PieChart.Data slice1 = new PieChart.Data("Wygrane", winChart);
      PieChart.Data slice2 = new PieChart.Data("Przegrane", loseChart);
      PieChart.Data slice3 = new PieChart.Data("Remisy", drawChart);

      pieChart.getData().add(slice1);
      pieChart.getData().add(slice2);
      pieChart.getData().add(slice3);

      // Przycisk powrotu do Menu
      Button backToStatsMenu = new Button("Cofnij");
      backToStatsMenu.getStylesheets().add("main.css");
      backToStatsMenu.getStyleClass().add("custom-button");

      // Obsługa wciśnięcia przycisku
      backToStatsMenu.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          primaryStage.close();
        }
      });

      VBox vbox = new VBox(pieChart, backToStatsMenu);
      vbox.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
      vbox.setAlignment(Pos.BOTTOM_RIGHT);

      Scene scene = new Scene(vbox, 400, 400);

      primaryStage.setScene(scene);

      primaryStage.show();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  // otwarcie okna statystyk graczy
  @FXML
  void showStats(ActionEvent event) {
    // ładowanie formatki fxml z pliku
    FXMLLoader loader1 = new FXMLLoader(this.getClass().getResource("StatsScreen.fxml"));
    BorderPane pane = null;
    try {
      pane = loader1.load();
    } catch (IOException e) {
      e.printStackTrace();

    }

    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    StatsController statsController = loader1.getController();
    statsController.setMainController(mainController);
    mainController.setScreen(pane);

  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  // metoda wywoływana zaraz po konstruktorze kontrolera
  @FXML
  void initialize() {

    ResultSet r = null;

    // Wyświetlanie historii rozgrywek

    try {

      // Pobranie historii
      r = BaseMenager.getHistory(BaseMenager.connection);
      int i = 1;

      // Wyświetlanie 10 ostatnich rozgrywek
      while (r.next() && i < 10) {
        int[][] sequence = new int[16][2];
        Text t = new Text(r.getString("NICK_P1"));
        t.setFont(Font.font("Alegreya", 15));
        ResultSet rs = null;

        // Pobranie szczegółowych informacji o meczu
        rs = BaseMenager.getSpecifiedHistory(BaseMenager.connection, r);

        // Zapisanie sekwencji ruchów do tabeli
        while (rs.next()) {
          for (int j = 1; j < 17; j++) {
            String str = "M" + j;
            sequence[j - 1][0] = rs.getInt(str) / 10;
            sequence[j - 1][1] = rs.getInt(str) % 10;
          }
        }
        // Inocjalizacja onka historii rozgrywki
        Stage statsStage = new Stage();
        statsStage.initStyle(StageStyle.UNDECORATED);

        // Inicjalizacja cyklu czasowego 
        Timeline timeline = new Timeline();
        timeline.setCycleCount(16);

        // Obsluga wyświetlania historii po najechaniu na nick
        t.hoverProperty().addListener(
            (ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {

              // GridPane pokazujący planszę gry
              GridPane gpane = new GridPane();
              statsStage.setScene(new Scene(gpane));
              gpane.setGridLinesVisible(true);
              final int numCols = 3;
              final int numRows = 3;
              for (int k = 0; k < numCols; k++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / numCols);
                gpane.getColumnConstraints().add(colConst);
              }
              for (int k = 0; k < numRows; k++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / numRows);
                gpane.getRowConstraints().add(rowConst);
              }
              gpane.setStyle("-fx-background-color: #7cb0df;");
              gpane.setPrefHeight(100);
              gpane.setPrefWidth(100);

              gpane.setAlignment(Pos.CENTER);

              Text tx = new Text("x");
              tx.setFont(Font.font("Arial", 35));
              GridPane.setHalignment(tx, HPos.CENTER);

              // Wyświetlenie pierwszego postawionego znaku
              gpane.add(tx, sequence[0][1] - 1, sequence[0][0] - 1, 1, 1);

              statsStage.show();

              // Obsługa wyświetlania dalszej historii po upływie zadanego czasu
              KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> {
                if (sequence[position][0] != 0 && position < 16) {

                  if (position % 2 == 1) {
                    Text sign = new Text("o");
                    sign.setFont(Font.font("Arial", 35));
                    GridPane.setHalignment(sign, HPos.CENTER);
                    gpane.add(sign, sequence[position ][1] - 1, sequence[position][0] - 1, 1, 1);
                  } else {
                    Text sign = new Text("x");
                    sign.setFont(Font.font("Arial", 35));
                    GridPane.setHalignment(sign, HPos.CENTER);
                    gpane.add(sign, sequence[position][1] - 1, sequence[position][0] - 1, 1, 1);
                  }
                }
                position = position + 1;
              });
              timeline.getKeyFrames().add(keyFrame);
              timeline.play();

              // Obsługa zakończenia pokazywania historii
            } else {
              position = 1;
              timeline.getKeyFrames().clear();
              timeline.stop();
              statsStage.hide();
            }
          });
        grid.add(t, 0, i);

        Text t2 = new Text(r.getString("NICK_P2"));
        t2.setTextAlignment(TextAlignment.CENTER);
        t2.setFont(Font.font("Alegreya", 15));
        grid.add(t2, 1, i);

        Text t3 = new Text(r.getString("DATE"));
        t3.setFont(Font.font("Alegreya", 15));
        grid.add(t3, 2, i);
        i = i + 1;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
