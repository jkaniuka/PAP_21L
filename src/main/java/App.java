
//import niezbędnych bibliotek

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

//główna klasa pliku
public class App extends Application {


  @Override
  // uruchamianie pierwszego okna programu
  public void start(Stage primaryStage) {
    try {
      Stage mystage = new Stage();
      mystage.setHeight(400);
      mystage.setWidth(400);
  
      // Nawiązanie połączenia z bazą danych
      BaseMenager.connect();

      // Stylistyka ekranu powitalnego

      Button button = new Button("START");
      button.wrapTextProperty().setValue(true);
      button.getStylesheets().add("main.css");
      button.getStyleClass().add("main-button");
  
      // Pobranie grafiki
      ImageView image = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/1024px-Tic_tac_toe.svg.png");
      image.setFitHeight(200);
      image.setFitWidth(200);
  
      // Napis główny TIC-TAC-TOE
      String family = "Alegreya";
      double size = 50;
      TextFlow textFlow = new TextFlow();
      textFlow.setLayoutX(10);
      textFlow.setLayoutY(10);
      Text text1 = new Text("TIC-TAC-TOE");
      text1.setFont(Font.font(family,FontWeight.BOLD, size));
      text1.setFill(Color.BLACK);
      textFlow.getChildren().addAll(text1);   
  
      VBox vbox = new VBox(text1,image,button);
      vbox.setBackground(new Background(new BackgroundFill(
          Color.rgb(153, 204, 255),CornerRadii.EMPTY, Insets.EMPTY)));
      vbox.setAlignment(Pos.BASELINE_CENTER);
  
      Scene scene2 = new Scene(vbox, 400, 600);
      mystage.setScene(scene2);
      mystage.show();

      // ładowanie formatki fxml z pliku
      FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MainScreen.fxml"));
      StackPane stackPane = loader.load();

      // Obsługa wciśnięcia przycisku
      button.setOnAction(event -> {
        Scene scene = new Scene(stackPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe GAME");
        primaryStage.show();
        mystage.close();
      });
    } catch (Exception e) {
      System.out.println(e);
    }
  }


  public static void main(String[] args) {
    launch(args);
  }
}