import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class BaseMenager {

  private static final String USERNAME = "33095650_tictactoe";
  private static final String PASSWORD = "PAP21LKK";
  private static final String CONN =
      "jdbc:mysql://serwer2026623.home.pl/33095650_tictactoe?serverTimezone=Europe/Warsaw";

  public static Connection connection;

  /**
 * Połączenie z bazą danych.
 */
  public static void connect() throws SQLException {
    try {
      connection = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
    } catch (SQLException e) {
      showDbAlert(AlertType.ERROR, "Błąd bazy danych", "Połączenie z bazą danych się nie powiodło");
      throw new IllegalStateException("Błąd połaczenia z bazą danych");
    }
  }

  /**
 * Zamknięcie połączenia z bazą danych.
 */
  public static void closeConnection() throws SQLException {

    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
 * Dodanie nowego gracza do bazy danych.
 */
  public static void addNewPlayer(Connection con, String nick) throws SQLException {

    try {
      String sql = "INSERT INTO USERS VALUES(null,?, 0, 0, 0)";
      PreparedStatement statement = con.prepareStatement(sql);
      statement.setString(1, nick);
      statement.addBatch();
      statement.executeBatch();

    } catch (SQLException e) {

      showDbAlert(AlertType.WARNING, "Błąd dodania nowego gracza", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd dodania gracza do bazy danych");
    }
  }

  /**
 * Dodanie nowewgo zespołu do bazy danych.
 */
  public static void addNewTeam(Connection con, String nick1, String nick2) throws SQLException {

    try {
      String sql = "INSERT INTO MULTIGAME VALUES(null,?, 0, 0, 0, ?, 0, 0, 0)";
      PreparedStatement statement = con.prepareStatement(sql);
      statement.setString(1, nick1);
      statement.setString(2, nick2);
      statement.addBatch();
      statement.executeBatch();

    } catch (SQLException e) {
      showDbAlert(AlertType.WARNING, "Błąd dodania nowego teamu", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd dodania teamu do bazy danych");
    }
  }

  /**
 * Dodanie wyników rozgrywki do bazy danych.
 */
  public static void addResults(Connection con, String nick, Integer result) throws SQLException {

    try {

      String sql = "SELECT * FROM USERS WHERE nick = ?";

      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, nick);
      stmt.addBatch();
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        if (result == 0) {
          int wins = rs.getInt("WIN");
          wins = wins + 1;
          String sqlOut = "UPDATE USERS SET WIN = ? WHERE nick = ?";
          PreparedStatement statement = con.prepareStatement(sqlOut);
          statement.setInt(1, wins);
          statement.setString(2, nick);
          statement.addBatch();
          statement.executeBatch();

        } else if (result == 1) {
          int loses = rs.getInt("LOSE");
          loses = loses + 1;
          String sqlOut = "UPDATE USERS SET LOSE = ? WHERE nick = ?";
          PreparedStatement statement = con.prepareStatement(sqlOut);
          statement.setInt(1, loses);
          statement.setString(2, nick);
          statement.addBatch();
          statement.executeBatch();

        } else {
          int draws = rs.getInt("DRAW");
          draws = draws + 1;

          String sqlOut = "UPDATE USERS SET DRAW = ? WHERE nick = ?";
          PreparedStatement statement = con.prepareStatement(sqlOut);
          statement.setInt(1, draws);
          statement.setString(2, nick);
          statement.addBatch();
          statement.executeBatch();
        }

      }

    } catch (SQLException e) {
      showDbAlert(AlertType.WARNING, "Błąd zapisu statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd zapisu statystyk");
    }
  }


  /**
 * Dodanie wyników rozgrywki w trybie multiplayer do bazy danych.
 */
  public static void addTeamResults(
       Connection con, String nick1, String nick2, Integer result) throws SQLException {

    try {

      String sql = "SELECT * FROM MULTIGAME WHERE nick_p1 = ? AND nick_p2 = ?";

      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, nick1);
      stmt.setString(2, nick2);
      stmt.addBatch();
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        // wygrał gracz p1
        if (result == 0) {
          int winsP1 = rs.getInt("WIN_P1");
          winsP1 = winsP1 + 1;
          String sqlP1 = "UPDATE MULTIGAME SET WIN_P1 = ? WHERE nick_p1 = ? AND nick_p2 = ?";
          PreparedStatement statementP1 = con.prepareStatement(sqlP1);
          statementP1.setInt(1, winsP1);
          statementP1.setString(2, nick1);
          statementP1.setString(3, nick2);
          statementP1.addBatch();
          statementP1.executeBatch();

          int loseP2 = rs.getInt("LOSE_P2");
          loseP2 = loseP2 + 1;
          String sqlP2 = "UPDATE MULTIGAME SET LOSE_P2 = ? WHERE nick_p2 = ? AND nick_p1 = ?";
          PreparedStatement statementP2 = con.prepareStatement(sqlP2);
          statementP2.setInt(1, loseP2);
          statementP2.setString(2, nick2);
          statementP2.setString(3, nick1);
          statementP2.addBatch();
          statementP2.executeBatch();

          // wygrał gracz 2
        } else if (result == 1) {

          int winsP2 = rs.getInt("WIN_P2");
          winsP2 = winsP2 + 1;
          String sqlP2 = "UPDATE MULTIGAME SET WIN_P2 = ? WHERE nick_p2 = ? AND nick_p1 = ?";
          PreparedStatement statementP2 = con.prepareStatement(sqlP2);
          statementP2.setInt(1, winsP2);
          statementP2.setString(2, nick2);
          statementP2.setString(3, nick1);
          statementP2.addBatch();
          statementP2.executeBatch();

          int loseP1 = rs.getInt("LOSE_P1");
          loseP1 = loseP1 + 1;
          String sqlP1 = "UPDATE MULTIGAME SET LOSE_P1 = ? WHERE nick_p1 = ? AND nick_p2 = ?";
          PreparedStatement statementP1 = con.prepareStatement(sqlP1);
          statementP1.setInt(1, loseP1);
          statementP1.setString(2, nick1);
          statementP1.setString(3, nick2);
          statementP1.addBatch();
          statementP1.executeBatch();

        } else {
          int drawP1 = rs.getInt("DRAW_P1");
          drawP1 = drawP1 + 1;
          String sqlP1 = "UPDATE MULTIGAME SET DRAW_P1 = ? WHERE nick_p1 = ? AND nick_p2 = ?";
          PreparedStatement statementP1 = con.prepareStatement(sqlP1);
          statementP1.setInt(1, drawP1);
          statementP1.setString(2, nick1);
          statementP1.setString(3, nick2);
          statementP1.addBatch();
          statementP1.executeBatch();

          int drawP2 = rs.getInt("DRAW_P2");
          drawP2 = drawP2 + 1;
          String sqlP2 = "UPDATE MULTIGAME SET DRAW_P2 = ? WHERE nick_p2 = ? AND nick_p1 = ?";
          PreparedStatement statementP2 = con.prepareStatement(sqlP2);
          statementP2.setInt(1, drawP2);
          statementP2.setString(2, nick2);
          statementP2.setString(3, nick1);
          statementP2.addBatch();
          statementP2.executeBatch();
        }

      }

    } catch (SQLException e) {
      showDbAlert(AlertType.WARNING, "Błąd zapisu statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd zapisu statystyk");
    }
  }

  /**
 * Dodanie histori rozgrywki do bazy danych.
 */
  public static void addHistory(
       Connection con, String nick1, String nick2, int[][] array) throws SQLException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    try {
      String sql = "INSERT INTO HISTORY VALUES("
          + "null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = con.prepareStatement(sql);
      statement.setString(1, nick1);
      statement.setString(2, nick2);
      statement.setString(3, dtf.format(now));
      statement.setInt(4, getInt(array[0]));
      statement.setInt(5, getInt(array[1]));
      statement.setInt(6, getInt(array[2]));
      statement.setInt(7, getInt(array[3]));
      statement.setInt(8, getInt(array[4]));
      statement.setInt(9, getInt(array[5]));
      statement.setInt(10, getInt(array[6]));
      statement.setInt(11, getInt(array[7]));
      statement.setInt(12, getInt(array[8]));
      statement.setInt(13, getInt(array[9]));
      statement.setInt(14, getInt(array[10]));
      statement.setInt(15, getInt(array[11]));
      statement.setInt(16, getInt(array[12]));
      statement.setInt(17, getInt(array[13]));
      statement.setInt(18, getInt(array[14]));
      statement.setInt(19, getInt(array[15]));
      statement.addBatch();

      

      statement.executeBatch();

    } catch (SQLException e) {

      showDbAlert(AlertType.WARNING, "Błąd zapisu histori", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd zapisu histori");
    }
  }

  /**
 * Pobranie z bazy danych statystyk.
 */
  public static ResultSet getStats(Connection con) throws SQLException {

    try {
      String query = "SELECT * FROM USERS";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu statystyk");

    }
  }

  /**
 * Pobranie z bazy danych najlepszych statystyk.
 */
  public static ResultSet getBestStats(Connection con) throws SQLException {

    try {
      String query = "SELECT * FROM USERS ORDER BY WIN DESC LIMIT 10";



      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu statystyk");
    }
  }

  /**
 *Pobranie z bazy danych informacji o rozegranych grach multiplayer.
 */
  public static ResultSet getTeamStats(Connection con) throws SQLException {
    try {
      String query = "SELECT * FROM MULTIGAME";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu statystyk");

    }
  }

  /**
 *Pobranie z bazy danych informacji o rozegranych grach multiplayer.
 */
  public static ResultSet getBestTeamStats(Connection con) throws SQLException {
    try {
      String query = "SELECT * FROM MULTIGAME ORDER BY (WIN_P1 + DRAW_P1 + LOSE_P1) DESC LIMIT 10";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu statystyk");

    }
  }

  /**
 * Odczytanie z bazy danych nicków graczy.
 */
  public static ResultSet getNicks(Connection con) throws SQLException {
    try {
      String query = "SELECT NICK FROM USERS";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania nicków", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu bazy danych");

    }
  }


  /**
 * Odczytanie pełnych informacji o graczach w meczach.
 */
  public static ResultSet getTeam(Connection con, String nick1, String nick2) throws SQLException {
    try {
      String query = "SELECT * FROM MULTIGAME WHERE NICK_P1 = ? AND NICK_P2 = ?";

      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, nick1);
      stmt.setString(2, nick2);
      stmt.addBatch();

      ResultSet rs = stmt.executeQuery();

      return rs;

    } catch (SQLException e) {

      showDbAlert(AlertType.WARNING, "Błąd pobrania statystyk", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu statystyk");

    }
  }

  /**
 * Odczytanie z bazy danych historii meczy.
 */
  public static ResultSet getHistory(Connection con) throws SQLException {
    try {
      String query = "SELECT NICK_P1, NICK_P2, DATE FROM HISTORY ORDER BY ID DESC";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania historii", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu bazy danych");

    }
  }

  /**
 * Pobranie z bazy danych historii wybranego meczu.
 */
  public static ResultSet getSpecifiedHistory(Connection con, ResultSet r) throws SQLException {
    try {
      String query = "SELECT * FROM HISTORY WHERE DATE = ?";

      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, r.getString("DATE"));
      stmt.addBatch();

      ResultSet rs = stmt.executeQuery();

      return rs;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania historii", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu bazy danych");

    }
  }

  /**
 * Odczytanie z bazy danych najlepszych 3 graczy.
 */
  public static ResultSet getTop3(Connection con) throws SQLException {
    try {
      String query = "SELECT NICK FROM USERS ORDER BY WIN DESC";

      Statement stmt = con.createStatement();

      ResultSet r;
      r = stmt.executeQuery(query); // wykonanie kwerendy i przeslanie wynikow do obiektu ResultSet

      return r;

    } catch (SQLException e) {
      System.out.println(e);
      showDbAlert(AlertType.WARNING, "Błąd pobrania nicków", "Spróbuj ponownie później.");
      throw new IllegalStateException("Błąd odczytu bazy danych");

    }
  }

  /**
 * Wyświetlanie komunikatu o błędach.
 */
  public static void showDbAlert(Alert.AlertType alertType, String title, String message) {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.getDialogPane().getStylesheets().add("src/test/myDialogs.css");
    alert.getDialogPane().getStyleClass().add("dialog-pane");
    alert.setHeaderText(title);
    alert.setContentText(message);
    alert.show();
  }

  /**
 * Zwrócenie pozycji w formie int.
 */
  public static int getInt(int[] arr) {

    if (arr != null) {
      int ij = arr[0] * 10 + arr[1];
      return ij;

    } else {
      return 0;

    }
  }
}