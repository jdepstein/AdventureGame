import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.AdvGameConsoleController;
import model.Dungeon;
import model.DungeonImpl;
import model.Location;
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
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
  }


  /**
   * Null Model Test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badGame() {
    StringReader input = new StringReader("");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
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
    AdvGameConsoleController c = new AdvGameConsoleController(null, gameLog, false);
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
    AdvGameConsoleController c = new AdvGameConsoleController(input, null, false);
    c.playGame(d);
  }

  /**
   * Testing invalid cmds passed in then quitting.
   */
  @Test
  public void invalidCmds() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("F hello G 4.4 google PDP Q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Unknown command F"));
    assertTrue(gameLog.toString().contains("Unknown command hello"));
    assertTrue(gameLog.toString().contains("Unknown command G"));
    assertTrue(gameLog.toString().contains("Unknown command 4.4"));
    assertTrue(gameLog.toString().contains("Unknown command google"));
    assertTrue(gameLog.toString().contains("Unknown command PDP"));
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));
    assertEquals(new Location(0, 0), d.getPlayerLocation());


  }

  /**
   * Moving the player around and checking map states and message outputs readable Test. Have
   * some invalid and bad cmds mixed in a well to show the program will still work.
   */
  @Test
  public void oneMove() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("M S q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertEquals(new Location(0, 1), d.getPlayerLocation());
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));
    assertEquals(new Location(0, 1), d.getPlayerLocation());
  }

  /**
   * Trying to move a direction you cannot go.
   */
  @Test
  public void invalidMove() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("M G q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Connections Lead: \n"
                    + "EAST\n"
                    + "SOUTH"));
    assertTrue(gameLog.toString().contains("The cave Holds: \n"
                    + "CrookedArrow:"));
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?"));
    assertTrue(gameLog.toString().contains("Enter a Direction:"));
    assertTrue(gameLog.toString().contains("Invalid Direction G"));
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));
    assertEquals(new Location(0, 0), d.getPlayerLocation());
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
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
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

    assertTrue(gameLog.toString().contains("Invalid Direction T"));
    assertTrue(gameLog.toString().contains("Invalid Direction M"));
    assertTrue(gameLog.toString().contains("Invalid Direction hello"));
    assertTrue(gameLog.toString().contains("Invalid Direction D"));
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
    assertEquals(new Location(3, 2), d.getPlayerLocation());
    assertFalse(d.hasSolved());
  }

  /**
   * Picking up items in two caves then quitting.
   */
  @Test
  public void twoPickup() {
    Dungeon d =  new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("P M S P q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Connections Lead: \n"
            + "EAST\n"
            + "SOUTH"));
    assertTrue(gameLog.toString().contains("The cave Holds: \n"
            + "CrookedArrow:"));
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?"));
    assertTrue(gameLog.toString().contains("You found some items you now have: \n"
            + "CrookedArrow:"));


    assertTrue(gameLog.toString().contains("Diamonds")
            || gameLog.toString().contains("Sapphires") || gameLog.toString().contains("Rubies"));

    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));
    assertEquals(new Location(0, 1), d.getPlayerLocation());
    assertFalse(d.hasSolved());
  }

  /**
   * Picking up twice in the same cave/tunnel.
   */
  @Test
  public void pickupInvalid() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 1);
    StringReader input = new StringReader("P P q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);

    assertTrue(gameLog.toString().contains("You found some items you now have: \n"
            + "CrookedArrow:"));
    assertTrue(gameLog.toString().contains("You picked up but there was nothing to pick up"));
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));
    assertEquals(new Location(0, 0), d.getPlayerLocation());
    assertFalse(d.hasSolved());

  }

  /**
   * Picking up in an empty  cave/tunnel.
   */
  @Test
  public void pickupInvalid2() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 0, "Jack", false, 1);
    StringReader input = new StringReader("P M S P q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);

    assertFalse(gameLog.toString().contains("You found some items you now have:"));
    assertTrue(gameLog.toString().contains("You picked up but there was nothing to pick up"));
    assertTrue(gameLog.toString().contains("Quit the dungeon So no items were collected"));

    assertEquals(new Location(0, 1), d.getPlayerLocation());
    assertFalse(d.hasSolved());
  }


  /**
   * Shooting at a monster to kill it.
   */
  @Test
  public void shootHitTwice() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("P S 1 E S 1 E q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You shoot and arrow and hear a loud roar in the distance\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You shoot and arrow and hear a loud roar in the distance\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected"));
    assertTrue(d.getCave(new Location(1, 0)).getMonster().isDead());

  }

  /**
   * Shot into a wall.
   */
  @Test
  public void shootIntoWall() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("S 1 W S 1 N q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "Tried to shoot an arrow directly into a wall\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "Tried to shoot an arrow directly into a wall\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected"));
  }

  /**
   * Shot into and there was no contact.
   */
  @Test
  public void shooNothingsThereWall() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("S 1 S q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You Shoot an arrow into darkness and hear nothing\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected\n"));

  }

  /**
   * Shooting into the same cave that was previously a hit and noting that now you are not getting
   * hit.
   */
  @Test
  public void shootAfterYouKill() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("P S 1 E S 1 E S 1 E q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You shoot and arrow and hear a loud roar in the distance\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You shoot and arrow and hear a loud roar in the distance\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You Shoot an arrow into darkness and hear nothing\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected\n"));
  }

  /**
   * Shot into and there was no contact.
   */
  @Test
  public void overshooting() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("S 2 E S 3 E q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You Shoot an arrow into darkness and hear nothing\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Enter a Distance 1-5: \n"
            + "Enter a Direction:\n"
            + "You Shoot an arrow into darkness and hear nothing\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected\n"));
  }

  /**
   * Doing all the invalid options when shooting an arrow.
   */
  @Test
  public void invalidShooting() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 6);
    StringReader input = new StringReader("P S 1 W S 1 N S W S 2.2 S 7 E S "
            + "hello S .4 S 4 5 S 4 B S 4 hello S 4 V q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("Invalid Direction 5"));
    assertTrue(gameLog.toString().contains("Invalid Direction B"));
    assertTrue(gameLog.toString().contains("Invalid Direction hello"));
    assertTrue(gameLog.toString().contains("Invalid Direction V"));
    assertTrue(gameLog.toString().contains("Invalid distance to shoot 7"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got W"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got 2.2"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got hello"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got .4"));
    assertTrue(gameLog.toString().contains("Tried to shoot an arrow directly into a wall"));

    d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 6);
    input = new StringReader("S 1 E S 1 E S 1 E S 1 E q");
    gameLog = new StringBuilder();
    c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("You have no arrows to shoot"));
  }

  /**
   * Moved into a cave with a monster and died.
   */
  @Test
  public void dying() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("M E");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertEquals(new Location(1, 0), d.getPlayerLocation());
    assertTrue(gameLog.toString().contains("Eaten by a Monster Comp Comp"));
    assertTrue(d.hasLost());

  }

  /**
   * Shot into a cave with a monster then attempted to move into that cve with the injured monster.
   */
  @Test
  public void possibleEscape() {
    int count = 0;
    for (int x = 0; x < 500; x++) {
      Dungeon d = new DungeonImpl(6, 6, 25,
              false, 100, "Jack", false, 2);
      StringReader input = new StringReader("S 1 E M E q");
      Appendable gameLog = new StringBuilder();
      AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
      c.playGame(d);
      assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
              + "Enter a Distance 1-5: \n"
              + "Enter a Direction:\n"
              + "You shoot and arrow and hear a loud roar in the distance\n"
              + "Move, Pickup, or Shoot (M-P-S)?\n"));
      if (d.getPlayerLocation().equals(new Location(0, 0))) {
        assertTrue(gameLog.toString().contains("You Narrowly escaped a Otyugh and "
                + "returned to your previous location"));
        assertFalse(d.hasLost());
        count++;
      } else {
        assertTrue(gameLog.toString().contains("Eaten by a Monster Comp Comp"));
        assertTrue(d.hasLost());
      }
    }
    assertEquals((double) count / 500, .5, .1);

  }

  /**
   * Moving the player to the end and having them win.
   */
  @Test
  public void winningTest() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("M S M S M S M E M E S 1 E S 1 E M E q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(gameLog.toString().contains("You found the decaying corpse of a"
            + " Otyugh that has been hit by two arrows"));

    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "You Have reached the end of the Dungeon type Q or q to leave\n"
            + "You Can still Explore but you run the risk of dying or not finding your way back\n"
            + "You have made it through the dungeon through your travels you collected: \n"
            + "CrookedArrow: 1"));

    assertTrue(d.hasSolved());
    assertFalse(d.hasLost());
  }

  /**
   * Testing a dungeon run with shooting picking up and finishing it. Also, with invalid cmd
   * as well as arguments mixed in as well and the error messages that you would get for
   * move that are not aloud.
   */
  @Test
  public void everythingTogether() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", false, 2);
    StringReader input = new StringReader("W S 1 N P M G S 6 E M S M W P hello P M S P S H S 2 "
            + "N shoot S 2 N P M S M P P M E P p S 2 X M E P S B S 2.1 S 1 E P PDP S 1"
            + " E P M E P quit q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);

    assertTrue(gameLog.toString().contains("Unknown command W"));
    assertTrue(gameLog.toString().contains("Unknown command hello"));
    assertTrue(gameLog.toString().contains("Unknown command shoot"));
    assertTrue(gameLog.toString().contains("Unknown command p"));
    assertTrue(gameLog.toString().contains("Unknown command PDP"));
    assertTrue(gameLog.toString().contains("Unknown command quit"));
    assertTrue(gameLog.toString().contains("Invalid Direction G"));
    assertTrue(gameLog.toString().contains("Invalid Direction X"));
    assertTrue(gameLog.toString().contains("Invalid Direction P"));
    assertTrue(gameLog.toString().contains("Invalid distance to shoot 6"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got H"));
    assertTrue(gameLog.toString().contains("Expected an Integer but got 2.1"));
    assertTrue(gameLog.toString().contains("Tried to shoot an arrow directly into a wall"));
    assertTrue(gameLog.toString().contains("Move, Pickup, or Shoot (M-P-S)?\n"
            + "You Have reached the end of the Dungeon type Q or q to leave\n"
            + "You Can still Explore but you run the risk of dying or not finding your way back\n"
            + "You have made it through the dungeon through your travels you collected: \n"));
    assertTrue(gameLog.toString().contains("You tried to move into a wall you are still "
            + "at your previous location"));
    assertTrue(gameLog.toString().contains("You picked up but there was nothing to pick up"));


    assertTrue(d.hasSolved());
    assertFalse(d.hasLost());
    assertTrue(d.getCave(new Location(1, 0)).getMonster().isDead());
    assertTrue(d.getCave(new Location(3, 3)).getMonster().isDead());
    assertEquals(new Location(3, 3), d.getPlayerLocation());
  }

  /**
   *Testing the different situations of smell being 1 away 2 away from 1 being 2 always from 2 and
   * being more than 2 away from.
   */
  @Test
  public void smellNotificationsTest() {
    Dungeon d = new DungeonImpl(6, 6, 25,
            false, 0, "Jack", false, 7);
    StringReader input = new StringReader("S 1 S S 1 S M S M S M S M N M E M E q");
    Appendable gameLog = new StringBuilder();
    AdvGameConsoleController c = new AdvGameConsoleController(input, gameLog, false);
    c.playGame(d);
    assertTrue(d.getCave(new Location(0, 1)).getMonster().isDead());
    assertTrue(gameLog.toString().contains(
              "You are in a Tunnel and, There's a Fowl Smell close by\n"
            + "The cave Holds: \n"
            + "CrookedArrow: 12\n"
            + "Connections Lead: \n"
            + "EAST\n"
            + "SOUTH\n"));

    assertTrue(gameLog.toString().contains(
              "You are in a Cave and, There's a Slightly foul smell in the distance\n"
            + "The cave Holds: \n"
            + "Connections Lead: \n"
            + "EAST\n"
            + "NORTH\n"
            + "SOUTH\n"));

    assertTrue(gameLog.toString().contains(
            "You are in a Cave and, The Cave seems normal\n"
            + "The cave Holds: \n"
            + "Connections Lead: \n"
            + "EAST\n"
            + "NORTH\n"
            + "SOUTH\n"));


    assertTrue(gameLog.toString().contains(
              "You are in a Cave and, There's a Fowl Smell close by\n"
            + "The cave Holds: \n"
            + "Connections Lead: \n"
            + "EAST\n"
            + "NORTH\n"
            + "SOUTH\n"
            + "WEST\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Quit the dungeon So no items were collected"));
  }

}