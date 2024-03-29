package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.enums.CaveObject;
import model.enums.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Test for the playerImpl Class.
 */
public class PlayerTest {
  private model.Player myPlayer;

  /**
   *Sets up a Cave Object for all the tests.
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
    myPlayer = new model.PlayerImpl("John",cave);
  }

  private void player(String name, model.Cave cave) {
    new model.PlayerImpl(name, cave);
  }

  /**
   * Bad create null name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate() {
    player(null, new model.CaveImpl(new model.Location(0,0)));
  }

  /**
   * Bad create null Cave.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate2() {
    player("Jack", null);
  }

  /**
   * Test the getters.
   */
  @Test
  public void getterTest() {
    Map<CaveObject, Integer> tres = myPlayer.getItems();
    assertEquals( 3, tres.get(CaveObject.CROOKEDARROW).intValue());

  }

  /**
   * Test picking up treasure and making sure you can't do it twice successfully.
   */
  @Test
  public void pickupTreasure() {
    assertTrue(myPlayer.search());
    Map<CaveObject, Integer> tres = myPlayer.getItems();
    assertEquals( 4, tres.get(CaveObject.CROOKEDARROW).intValue());

    assertFalse(myPlayer.search());
    Map<CaveObject, Integer> tres2 = myPlayer.getItems();
    assertEquals( 4, tres2.get(CaveObject.CROOKEDARROW).intValue());
  }

  /**
   * Test shooting the arrow.
   */
  @Test
  public void shootArrow() {
    Map<CaveObject, Integer> tres = myPlayer.getItems();
    assertEquals( 3, tres.get(CaveObject.CROOKEDARROW).intValue());
    myPlayer.shootArrow();
    tres = myPlayer.getItems();
    assertEquals( 2, tres.get(CaveObject.CROOKEDARROW).intValue());

    myPlayer.shootArrow();
    tres = myPlayer.getItems();
    assertEquals( 1, tres.get(CaveObject.CROOKEDARROW).intValue());

    myPlayer.shootArrow();
    tres = myPlayer.getItems();
    assertEquals( 0, tres.get(CaveObject.CROOKEDARROW).intValue());
  }

  /**
   * Test shooting the arrow.
   */
  @Test(expected = IllegalStateException.class)
  public void shootArrow2() {
    Map<CaveObject, Integer> tres = myPlayer.getItems();
    assertEquals( 3, tres.get(CaveObject.CROOKEDARROW).intValue());
    myPlayer.shootArrow();
    myPlayer.shootArrow();
    myPlayer.shootArrow();
    myPlayer.shootArrow();

  }


  /**
   * Making sure a player dies when entering a cave with a monster that isn't shot.
   */
  @Test
  public void dying() {
    for (int x = 0; x < 1000; x++) {
      model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
      newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
      model.Cave newCave2 = new model.CaveImpl(new model.Location(0, 5));
      newCave2.addConnection(Direction.SOUTH, new model.Location(1, 1));
      model.Monster m = new model.Otyugh();
      newCave2.addMonster(m);
      model.Player player2 = new model.PlayerImpl("Jack", newCave);
      player2.updateLocation(newCave2);
      assertFalse(player2.isAlive());
    }
  }


  /**
   * Making sure a player has approximately 50% to live when entering a cave with an injured
   * monster.
   */
  @Test
  public void escapeTest() {
    for (int y = 0; y < 1000; y++) {
      int count = 0;
      for (int x = 0; x < 1000; x++) {
        model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
        newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
        model.Cave newCave2 = new model.CaveImpl(new model.Location(0, 5));
        newCave2.addConnection(Direction.SOUTH, new model.Location(1, 1));
        model.Monster m = new model.Otyugh();
        m.shot();
        newCave2.addMonster(m);
        model.Player player2 = new model.PlayerImpl("Jack", newCave);
        player2.updateLocation(newCave2);
        if (player2.isAlive()) {
          count++;
          assertEquals(player2.getLocation(), newCave.getLocation());
        }
      }
      assertEquals(.5, (double) count / 1000, .1);
    }
  }

  /**
   * Test to make sure a player a has nothing happen to them when entering a cave with a dead
   * monster.
   */
  @Test
  public void deadMonster() {
    for (int x = 0; x < 1000; x++) {
      model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
      newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
      model.Cave newCave2 = new model.CaveImpl(new model.Location(0, 5));
      newCave2.addConnection(Direction.SOUTH, new model.Location(1, 1));
      model.Monster m = new model.Otyugh();
      m.shot();
      m.shot();
      newCave2.addMonster(m);
      model.Player player2 = new model.PlayerImpl("Jack", newCave);
      player2.updateLocation(newCave2);
      assertTrue(player2.isAlive());
      assertEquals(player2.getLocation(), newCave2.getLocation());
    }
  }

  /**
   * Player is dead error throw.
   */
  @Test(expected = IllegalStateException.class)
  public void deadShoot() {
    model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
    newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
    model.Cave newCave2 = new model.CaveImpl(new model.Location(0, 5));
    newCave2.addConnection(Direction.SOUTH, new model.Location(1, 1));
    model.Monster m = new model.Otyugh();
    newCave2.addMonster(m);
    model.Player player2 = new model.PlayerImpl("Jack", newCave);
    player2.updateLocation(newCave2);
    player2.shootArrow();
  }

  /**
   * Player is dead error throw.
   */
  @Test(expected = IllegalStateException.class)
  public void deadMove() {
    model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
    newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
    model.Cave newCave2 = new model.CaveImpl(new model.Location(0, 5));
    newCave2.addConnection(Direction.SOUTH, new model.Location(1, 1));
    model.Monster m = new model.Otyugh();
    newCave2.addMonster(m);
    model.Player player2 = new model.PlayerImpl("Jack", newCave);
    player2.updateLocation(newCave2);
    player2.updateLocation(newCave);
  }

  /**
   * Player is dead error throw.
   */
  @Test(expected = IllegalStateException.class)
  public void deadSearch() {
    model.Cave newCave = new model.CaveImpl(new model.Location(1, 1));
    newCave.addConnection(Direction.NORTH, new model.Location(0, 5));
    Cave newCave2 = new CaveImpl(new model.Location(0, 5));
    newCave2.addConnection(Direction.SOUTH, new Location(1, 1));
    Monster m = new Otyugh();
    newCave2.addMonster(m);
    model.Player player2 = new model.PlayerImpl("Jack", newCave);
    player2.updateLocation(newCave2);
    player2.search();
  }

}