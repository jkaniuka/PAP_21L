import java.io.IOException;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StatsController {

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
  private Button histStatsBtn;

  @FXML
  private Button gameStatsBtn;

  // wywołanie powrotu do Menu
  @FXML
  void backToMenu() {
    mainController.loadMenuScreen();

  }

  // Przejście do ekranu z historią rozgrywek
  @FXML
  void toHistoryStats(ActionEvent event) {
    // ładowanie formatki fxml z pliku
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("HistoryStatsScreen.fxml"));
    BorderPane pane = null;
    try {
      pane = loader.load();
    } catch (IOException e) {
      e.printStackTrace();

    }

    // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
    HistoryStatsController historyStatsController = loader.getController();
    historyStatsController.setMainController(mainController);
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
      backToStatsMenu.setOnAction(new EventHandler<ActionEvent>() {
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

  // Otwarcie okna statystyk meczowych
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

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  // metoda wywoływana zaraz po konstruktorze kontrolera
  @FXML
  void initialize() {

    ResultSet r = null;
    //Wyświetlanie statystyk graczy
    try {

      // Pobranie statystyk najlepszych graczy
      r = BaseMenager.getBestStats(BaseMenager.connection);
      int i = 1;

      // Wyświetlanie wszystkich otrzymanych informacji
      while (r.next()) {

        Text t = new Text(r.getString("NICK"));
        t.setFont(Font.font("Alegreya", 15));
        grid.add(t, 0, i);

        Text t2 = new Text(r.getString("WIN"));
        t2.setTextAlignment(TextAlignment.CENTER);
        t2.setFont(Font.font("Alegreya", 15));
        grid.add(t2, 1, i);

        Text t3 = new Text(r.getString("LOSE"));
        t3.setFont(Font.font("Alegreya", 15));
        grid.add(t3, 2, i);

        Text t4 = new Text(r.getString("DRAW"));
        t4.setFont(Font.font("Alegreya", 15));
        grid.add(t4, 3, i);

        i = i + 1;
      }
    } catch (Exception e) {

      System.out.println(e);
    }
  }
}
