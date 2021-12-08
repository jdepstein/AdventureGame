package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.enums.CaveObject;
import model.enums.Direction;
import model.enums.Smell;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Test for the Player Description Class.
 */
public class PlayerDescriptionTest {
  private model.Description description;

  /**
   *Sets up a Player Description Object for all the tests.
   */
  @Before
  public void setUp() {
    model.Cave cave = new model.CaveImpl(new model.Location(0,1));
    cave.addConnection(Direction.NORTH, new model.Location(1,1));
    cave.addConnection(Direction.SOUTH, new model.Location(2,1));
    cave.addConnection(Direction.EAST, new model.Location(3,2));
    cave.addConnection(Direction.WEST, new model.Location(5,1));
    cave.addTreasure(CaveObject.DIAMOND);
    cave.addTreasure(CaveObject.RUBY);
    cave.addArrow();
    model.Player player = new model.PlayerImpl("Jack",cave);
    description = new model.Description(player, cave);
  }

  /**
   * Null for the player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConstruct() {
    new model.Description(null,new model.CaveImpl(new model.Location(0,0)));
  }

  /**
   * Null for the cave.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConstruct2() {
    new model.Description(new model.PlayerImpl("Jack", new model.CaveImpl(new model.Location(0,0))),null);
  }

  /**
   * Test all the getters.
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
   * Test all the getters.
   */
  @Test
  public void caveType() {
    Cave cave = new model.CaveImpl(new model.Location(0,1));
    cave.addConnection(Direction.NORTH, new model.Location(1,1));
    cave.addConnection(Direction.SOUTH, new model.Location(2,1));
    cave.updateSmell(Smell.PUNGENT);
    model.Player player = new model.PlayerImpl("Jack",cave);
    model.Description des = new model.Description(player, cave);
    assertEquals("Tunnel",des.caveType());
    assertEquals("There's a Fowl Smell close by",des.getCaveSmell());

    cave = new model.CaveImpl(new model.Location(0,1));
    cave.addConnection(Direction.NORTH, new model.Location(1,1));
    cave.updateSmell(Smell.LIGHT);
    player = new model.PlayerImpl("Jack",cave);
    des = new model.Description(player, cave);
    assertEquals("Cave",des.caveType());
    assertEquals("There's a Slightly foul smell in the distance",des.getCaveSmell());

    cave = new CaveImpl(new model.Location(0,1));
    cave.addConnection(Direction.NORTH, new model.Location(1,1));
    cave.addConnection(Direction.SOUTH, new model.Location(2,1));
    cave.addConnection(Direction.EAST, new Location(3,2));
    player = new model.PlayerImpl("Jack",cave);
    des = new Description(player, cave);
    assertEquals(String.format("The %s seems normal", des.caveType()),des.getCaveSmell());
  }
}
