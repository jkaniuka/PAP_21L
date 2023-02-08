
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class BaseMenegerTest {

  /** Test połączenia z bazą danych.*/
  @Test
  public void connectTest() throws SQLException {
    BaseMenager.connect();

    assertNotEquals(null, BaseMenager.getStats(BaseMenager.connection));
  }

  /**Test pobrania nicku z bazy danych.*/
  @Test 
  public void nickTest() throws SQLException {

    BaseMenager.connect();

    ResultSet r = null;
    r = BaseMenager.getNicks(BaseMenager.connection);

    r.next();
    assertEquals("USER1", r.getString("NICK"));

    r.next();
    assertEquals("USER2", r.getString("NICK"));
  }

  /**Test pobrania nicku z bazy danych dla meczy.*/
  @Test 
  public void nickTeamTest() throws SQLException {

    BaseMenager.connect();

    ResultSet r = null;
    r = BaseMenager.getTeamStats(BaseMenager.connection);
    r.next();

    assertEquals("user15", r.getString("NICK_P1"));
    assertEquals("user16", r.getString("NICK_P2"));
  }


  /**Test pobrania statystyk z bazy danych.*/
  @Test 
  public void statsTest() throws SQLException {

    BaseMenager.connect();

    ResultSet r = null;
    r = BaseMenager.getStats(BaseMenager.connection);
    r.next();

    assertEquals("USER1", r.getString("NICK"));
    assertEquals(1, r.getInt("WIN"));
    assertEquals(5, r.getInt("LOSE"));
    assertEquals(7, r.getInt("DRAW"));
  }


  /**Test pobrania statystyk meczowych z bazy danych.*/
  @Test 
  public void statsTeamTest() throws SQLException {

    BaseMenager.connect();

    ResultSet r = null;
    r = BaseMenager.getTeamStats(BaseMenager.connection);
    r.next();
    r.next();
    r.next();
    assertEquals("db_test1", r.getString("NICK_P1"));
    assertEquals("db_test2", r.getString("NICK_P2"));
    assertEquals(3, r.getInt("WIN_P1"));
    assertEquals(0, r.getInt("LOSE_P1"));
    assertEquals(0, r.getInt("DRAW_P1"));

    assertEquals(0, r.getInt("WIN_P2"));
    assertEquals(3, r.getInt("LOSE_P2"));
    assertEquals(0, r.getInt("DRAW_P2"));

  }


  /**Test pobrania informacji o meczu z bazy danych.*/
  @Test 
  public void teamTest() throws SQLException {

    BaseMenager.connect(); 

    ResultSet r = null;
    r = BaseMenager.getTeam(BaseMenager.connection, "db_test1", "db_test2");
    r.next();
    assertEquals("db_test1", r.getString("NICK_P1"));
    assertEquals("db_test2", r.getString("NICK_P2"));
    assertEquals(3, r.getInt("WIN_P1"));
    assertEquals(0, r.getInt("LOSE_P1"));
    assertEquals(0, r.getInt("DRAW_P1"));

    assertEquals(0, r.getInt("WIN_P2"));
    assertEquals(3, r.getInt("LOSE_P2"));
    assertEquals(0, r.getInt("DRAW_P2"));

  }

  /**Test funkcji przeliczającej pozycje.*/
  @Test 
  public void positionTest() throws SQLException {

    int[][] sequence = {
      {1,3},
      {3,1}, 
      {2,2}
    };
    BaseMenager.getInt(sequence[0]);

    assertEquals(13, BaseMenager.getInt(sequence[0]));
    assertEquals(31, BaseMenager.getInt(sequence[1]));
    assertEquals(22, BaseMenager.getInt(sequence[2]));
  }


  
}