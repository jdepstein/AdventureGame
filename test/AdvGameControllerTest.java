import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import adventuregame.AdvGameConsoleController;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import org.junit.Test;

import java.io.StringReader;


/**
 * Test cases for the tic-tac-toe controller, using mocks for readable and
 * appendable.
 */
public class AdvGameControllerTest {


  /**
   * Failing appendable test.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", true, 1);
    StringReader input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog);
    c.playGame(d);
  }


  /**
   * Null Model Test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badGame() {
    StringReader input = new StringReader("");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog);
    c.playGame(null);
  }

  /**
   * Null input Test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInput() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", true, 1);
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(null, gameLog);
    c.playGame(d);
  }

  /**
   * Null readable Test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badReader() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", true, 1);
    StringReader input = new StringReader("");
    AdvGameConsoleController c = new AdvGameConsoleController(input, null);
    c.playGame(d);
  }


  /**
   * Moving the player around and checking map states and message outputs readable Test. Have
   * some invalid and bad cmds mixed in a well to show the program will still work.
   */
  @Test
  public void movingRun() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("M T M N M W M S M hello M S M S V M S M M B M S "
            + "M E M N M N Jello M N M D M N M N M E 2.3 M W M E M E M S M S Q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("The cave Holds: \n"
                                           + "CrookedArrow: "));

    assertTrue(gameLog.toString().contains("You tried to move into a wall you are still "
            + "at your previous location"));
    assertTrue(gameLog.toString().contains("Connections Lead: \n"
                                          + "EAST\n"
                                          + "NORTH\n"
                                          + "SOUTH\n"
                                          + "WEST"));

    assertTrue(gameLog.toString().contains("Invalid move option T"));
    assertTrue(gameLog.toString().contains("Invalid move option M"));
    assertTrue(gameLog.toString().contains("Invalid move option hello"));
    assertTrue(gameLog.toString().contains("Invalid move option D"));
    assertTrue(gameLog.toString().contains("Unknown command V"));
    assertTrue(gameLog.toString().contains("Unknown command Jello"));
    assertTrue(gameLog.toString().contains("Unknown command 2.3"));
    assertTrue(gameLog.toString().contains("Unknown command B"));
    assertTrue(gameLog.toString().contains("You are in a Tunnel and, The Tunnel seems normal"));
    assertTrue(gameLog.toString().contains("You are in a Cave and, The Cave seems normal"));
    assertTrue(gameLog.toString().contains("You are in a Cave and, There's "
            + "a Slightly foul smell in the distance"));
    assertTrue(gameLog.toString().contains("You are in a Cave and, There's a Fowl Smell close by"));
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));

    assertTrue(gameLog.toString().contains("(--|--|SP|vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|=>) "
            + " (<=|--|  |vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|OE|vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  "
            + "(<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|=>) "
            + " (<=|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|--)  \n"));

    assertTrue(gameLog.toString().contains("(--|--| S|vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|=>) "
            + " (<=|--|  |vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|--)  \n"
            + "(--|^^| P|vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|OE|vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  "
            + "(<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|=>) "
            + " (<=|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|--)  \n"));

    assertTrue(gameLog.toString().contains("(--|--| S|vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|=>) "
            + " (<=|--|  |vv|=>)  (<=|--|  |vv|=>)  (<=|--|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^| P|vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>) "
            + " (<=|^^|OE|vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  "
            + "(<=|^^|  |vv|=>)  (<=|^^|  |vv|=>)  (<=|^^|  |vv|--)  \n"
            + "(--|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|=>) "
            + " (<=|^^|  |--|=>)  (<=|^^|  |--|=>)  (<=|^^|  |--|--)  \n"));

  }


}