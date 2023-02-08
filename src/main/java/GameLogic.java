import java.util.Arrays;
import javafx.scene.layout.Pane;


// Klasa grupująca wszystkie metody odpowiedzialne za logikę gry
// Nie zawiera elementów pakietu JavaFX takich jak pokazywanie Stage'a itd. - wygodne dla testowania
// Została stworzona wyłącznie na potrzeby testów jednostkowych z JUnit

public class GameLogic {

  // Utworzenie obiektu klasy testowej
  static GameLogic game = new GameLogic();

  // Znaczniki gracza oraz maszyny
  private static char comp = 'O';
  private static final char player = 'X';

  // Stworzenie planszy do gry o wymiarze 3x3
  private static Board[][] board = new Board[3][3];

  
  // Metoda inicjalizująca symulowaną zajętość pól na planszy
  static Board[][] createStructure() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = new Board();
      }
    }
    board[0][0].token = 'X';
    board[0][1].token = 'X';
    board[0][2].token = 'X';
    board[1][0].token = 'O';
    board[1][1].token = ' ';
    board[1][2].token = 'O';
    board[2][0].token = ' ';
    board[2][1].token = 'O';
    board[2][2].token = ' ';

    return board;
  }

  // Klasa reprezentująca planszę
  public static class Board extends Pane {
    private char token = ' '; // token to albo x, albo o

    /**
    * Klasa Board (plansza) dziedziczy po Pane i pozwala ustrykturyzować komórki w planszę 3x3.
    */
    public Board() {

    }

    // Zwraca znak gracza z danego pola
    public char getToken() {
      return token;
    }
  }  

  /**
  * Sprawdzanie istnienia wygranej kombinacji w trybach Multi, Basic, Speedrun.
  */
  public static boolean gameWin(char sign) {

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
  * Plansza zajęta bez rozstrzygnięcia.
  */
  public static boolean itsDraw() {
    board = createStructure();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j].getToken() == ' ') {
          return false;
        }
      }
    }
    return true;
  }


  /**
  * Funkcja sprawdza, czy element już został dodany do historii ruchów.
  */
  public static boolean isInArray(int[] sub, int[][] sup) {
    for (int i = 0; i < sup.length; i++) {
      if (Arrays.equals(sub, sup[i])) {
        return true;
      }
    }
    return false;
  }

  /**
  * Funkcja dokonująca konwersji planszy (jako obiektu klasy Board) na tablicę znaków.
  */
  public static char[][] boardSituation() {
    board = createStructure();
    char[][] boardPicture = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        boardPicture[i][j] = board[i][j].getToken();
      }
    }
    return boardPicture;
  }


  /**
  * Funkcja sprawdzająca potencjalną wygraną w ruchu k+1 w algorytmie MiniMax.
  */
  public static int winCheck(char[][] myBoard) {
    // Sprawdzenie wierszowe
    for (int i = 0; i < 3; i++) {
      if (myBoard[i][0] == myBoard[i][1] && myBoard[i][1] == myBoard[i][2]) {
        if (myBoard[i][0] == player) {
          return +1; // +1 za wygraną komputera
        } else if (myBoard[i][0] == comp) {
          return -1; // -1 za przegraną komputera = wygraną graza
        }
      }
    }

    // Sprawdzenie kolumnowe
    for (int j = 0; j < 3; j++) {
      if (myBoard[0][j] == myBoard[1][j] && myBoard[1][j] == myBoard[2][j]) {
        if (myBoard[0][j] == player) {
          return +1;
        } else if (myBoard[0][j] == comp) {
          return -1;
        }
      }
    }

    // Diagonalie
    if (myBoard[0][0] == myBoard[1][1] && myBoard[1][1] == myBoard[2][2]) {
      if (myBoard[0][0] == player) {
        return +1;
      } else if (myBoard[0][0] == comp) {
        return -1;
      }
    }

    if (myBoard[0][2] == myBoard[1][1] && myBoard[1][1] == myBoard[2][0]) {
      if (myBoard[0][2] == player) {
        return +1;
      } else if (myBoard[0][2] == comp) {
        return -1;
      }
    }
    return 0;
  }

  /**
  * Właściwy algorytm heurystyczny MiniMax.
  */
  public static int minimax(char[][] board, int depth, Boolean maximizingPlayer) {
    int score = winCheck(board);

    if (score == 1 || score == -1) {
      return score;
    }
    if (itsDraw() == false) {
      return 0;
    }

    if (maximizingPlayer) {
      // Zgodnie z teorią matematyczną +/- Infinity
      int bestValue = -100000;
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          // Sprawdzenie, czy komórka pusta
          if (board[i][j] == ' ') {
            // Symulacja ruchu
            board[i][j] = player;

            // Rekursywne wywołanie algorytmu MINIMAX
            bestValue = Math.max(bestValue, minimax(board, depth + 1, !maximizingPlayer));

            // Cofam ruch po przeliczeniu algorytmem MiniMax
            board[i][j] = ' ';
          }
        }
      }
      return bestValue;

    } else {
      int bestValue = 100000;
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {

          if (board[i][j] == ' ') {
            board[i][j] = 'O';
            bestValue = Math.min(bestValue, minimax(board, depth + 1, !maximizingPlayer));
            board[i][j] = ' ';
          }
        }
      }
      return bestValue;
    }
  }
}


