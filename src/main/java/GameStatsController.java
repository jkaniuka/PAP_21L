
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
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStatsController {

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
  private Button statsBtn;

  // wywołanie powrotu do menu
  @FXML
  void backToMenu() {
    mainController.loadMenuScreen();

  }

  // Przejście do historii gier
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

    // zwrócenie instancji kontrolera tworzonego podczas ładowania formatki
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

    // // zwrócenie instancji kontrollera tworzonego podczas ładowania formatki
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

    //Wyświetlanie statystyk meczy

    try {

      // Pobranie statystyk najlepszych meczy
      r = BaseMenager.getBestTeamStats(BaseMenager.connection);
      int i = 1;

      // Wyświetlanie wszystkich otrzymanych informacji
      while (r.next()) {

        Label label1 = new Label(r.getString("NICK_P1"));

        label1.setFont(Font.font("Alegreya", 15));

        final Tooltip tooltip = new Tooltip();
        tooltip.setText("\nIlość wygranych: " + r.getString("WIN_P1") 
            + "\nIlość przegranych: " + r.getString("LOSE_P1")
            + "\nIlość remisów: " + r.getString("DRAW_P1"));
        tooltip.setShowDelay(Duration.seconds(1));
        label1.setTooltip(tooltip);
        grid.add(label1, 0, i);

        Text t3 = new Text("-");
        t3.setFont(Font.font("Alegreya", 15));
        grid.add(t3, 1, i);

        Label label2 = new Label(r.getString("NICK_P2"));

        label2.setFont(Font.font("Alegreya", 15));

        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText("\nIlość wygranych: " + r.getString("WIN_P2") + "\nIlość przegranych: "
            + r.getString("LOSE_P2") + "\nIlość remisów: " + r.getString("DRAW_P2"));
        tooltip2.setShowDelay(Duration.seconds(1));
        label2.setTooltip(tooltip2);

        grid.add(label2, 2, i);

        i = i + 1;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
