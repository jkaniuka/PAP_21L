
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GameLogicTest {

  // Symulowany obszar planszy do testów (jako tablica znaków)
  char[][] boardPicture = { { 'X', 'X', 'X' }, { 'O', ' ', 'O' }, { ' ', 'O', ' ' } };

  // Symulowana plansza dla algorytmu MINIMAX
  char[][] miniMaxBoardPicture = { { 'X', 'O', 'X' }, { 'O', 'O', 'X' }, { ' ', ' ', ' ' } };

  
  /** Test wyszukiwania sytuacji remisu na planszy.*/
  @Test
  public void itsDrawTest() {

    assertEquals(false,GameLogic.itsDraw());

  }


  /** Test wykrywania zakończenia rozgrywki na skutek wygranej.*/
  @Test
  public void gameWinTest() {
  
    // X nie powinien wygrać, poniważ są 3 kółka w rzędzie.
    assertEquals(false,GameLogic.gameWin('O'));
  }

  /** Test przeszukiwania tablicy zawierającej ruchy z przeszłości
   *  - nie może dojść do kilkukrotnego dodania tej samej pozycji.*/
  @Test
  public void isInArrayTest() {

    // Nowa pozycja do dodania
    int[] tablica = { 2, 2 };

    // Dotychczasowa zawartość tablicy
    int[][] sequence = {{1,1}, {2,2}, {0,2}};
  
    assertEquals(true,GameLogic.isInArray(tablica,sequence));
  }

  
  /** Test konwersji tablicy jako obiektu klasy Board na tablicę znaków.*/
  @Test
  public void boardPictureTest() {
    
    assertArrayEquals(GameLogic.boardSituation(), boardPicture);
  }

  /** Test wykrywania potencjalnej wygranej w nasepnych chwilach ( dla alg. MiniMax).*/
  @Test
  public void winCheckTest() {
    
    assertEquals(+1,GameLogic.winCheck(boardPicture));
  }


  /** Test działania funkcji MINIMAX. Opis poniżej.*/
  @Test
  public void miniMaxTest() {
    // Gracz chce maksymalizować wynik.
    // Po postawieniu X na pozycji [3,1], a komputer na [3,2] to gracz przegrywa - wynik (-1)
    // Jeżeli komputer przeoczy szansę i postawi 'o' na [3,3] to gra jest nierozstrzygnięta.
    // Wtedy gracz nie ma wyboru i musi postawić znak na polu [3,2]
    // Jest to sytuacja remisowa - odpowiada jej wyniki 0
    
  
    assertEquals(0,GameLogic.minimax(miniMaxBoardPicture, 3, true));
  }
  
}