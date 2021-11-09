package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * driver.Test for the Player Description Class.
 */
public class PlayerDescriptionTest {
  private Description description;

  /**
   *Sets up a Player Description Object for all the tests.
   */
  @Before
  public void setUp() {
    Cave cave = new CaveImpl(new Location(0,1));
    cave.addConnection(Direction.NORTH, new Location(1,1));
    cave.addConnection(Direction.SOUTH, new Location(2,1));
    cave.addConnection(Direction.EAST, new Location(3,2));
    cave.addConnection(Direction.WEST, new Location(5,1));
    cave.addTreasure(CaveObject.DIAMOND);
    cave.addTreasure(CaveObject.RUBY);
    cave.addArrow();
    Player player = new PlayerImpl("Jack",cave);
    description = new Description(player, cave);
  }

  /**
   * Null for the player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConstruct() {
    new Description(null,new CaveImpl(new Location(0,0)));
  }

  /**
   * Null for the cave.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConstruct2() {
    new Description(new PlayerImpl("Jack", new CaveImpl(new Location(0,0))),null);
  }

  /**
   * driver.Test all the getters.
   */
  @Test
  public void gettersTest() {
    assertEquals("Jack", description.getName());

    List<String> treasurePlayer = description.getPlayerItems();
    assertEquals(4, treasurePlayer.size());
    assertTrue(treasurePlayer.contains("Diamonds: 0"));
    assertTrue(treasurePlayer.contains("Rubies: 0"));
    assertTrue(treasurePlayer.contains("Sapphires: 0"));
    assertTrue(treasurePlayer.contains("CrookedArrow: 3"));

    List<String> treasureCave = description.getCaveItems();
    assertEquals(4, treasureCave.size());
    assertTrue(treasureCave.contains("Diamonds: 1"));
    assertTrue(treasureCave.contains("Rubies: 1"));
    assertTrue(treasureCave.contains("Sapphires: 0"));
    assertTrue(treasureCave.contains("CrookedArrow: 1"));

    List<String> playerDirections = description.getCaveDirections();
    assertEquals(4, playerDirections.size());
    assertTrue(playerDirections.contains("NORTH"));
    assertTrue(playerDirections.contains("SOUTH"));
    assertTrue(playerDirections.contains("EAST"));
    assertTrue(playerDirections.contains("WEST"));

    assertEquals("Cave",description.caveType());
  }

  /**
   * driver.Test all the getters.
   */
  @Test
  public void caveType() {
    Cave cave = new CaveImpl(new Location(0,1));
    cave.addConnection(Direction.NORTH, new Location(1,1));
    cave.addConnection(Direction.SOUTH, new Location(2,1));
    cave.updateSmell(Smell.PUNGENT);
    Player player = new PlayerImpl("Jack",cave);
    Description des = new Description(player, cave);
    assertEquals("Tunnel",des.caveType());
    assertEquals("There's a Fowl Smell close by",des.getCaveSmell());

    cave = new CaveImpl(new Location(0,1));
    cave.addConnection(Direction.NORTH, new Location(1,1));
    cave.updateSmell(Smell.LIGHT);
    player = new PlayerImpl("Jack",cave);
    des = new Description(player, cave);
    assertEquals("Cave",des.caveType());
    assertEquals("There's a Slightly foul smell in the distance",des.getCaveSmell());

    cave = new CaveImpl(new Location(0,1));
    cave.addConnection(Direction.NORTH, new Location(1,1));
    cave.addConnection(Direction.SOUTH, new Location(2,1));
    cave.addConnection(Direction.EAST, new Location(3,2));
    player = new PlayerImpl("Jack",cave);
    des = new Description(player, cave);
    assertEquals(String.format("The %s seems normal", des.caveType()),des.getCaveSmell());
  }
}
