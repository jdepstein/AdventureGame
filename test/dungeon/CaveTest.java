package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * Test for the CaveImpl Class.
 */
public class CaveTest {
  private Cave myCave;

  /**
   *Sets up a Cave Object for all the tests.
   */
  @Before
  public void setUp() {
    this.myCave = new CaveImpl(new Location(0,0));
  }

  private Cave cave(Location loc) {
    return new CaveImpl(loc);
  }

  /**
   * Should get an error if null is passed for location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badLocation() {
    cave(null);
  }


  /**
   * Trying to add a connection with a null direction.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection1() {
    myCave.addConnection(null, new Location(2,2));
  }

  /**
   * Trying to add a connection with a null location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection2() {
    myCave.addConnection(Direction.NORTH, null);
  }

  /**
   * Trying to add a connection with a location same as the current cave.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection3() {
    myCave.addConnection(Direction.NORTH, new Location(0,0));
  }

  /**
   * Trying to add a connection with a direction that has already been set.
   */
  @Test(expected = IllegalArgumentException.class)
  public void overrideConnection() {
    myCave.addConnection(Direction.NORTH, new Location(1,1));
    myCave.addConnection(Direction.NORTH, new Location(1,2));
  }

  /**
   * Trying to add a connection with a location that has already been set.
   */
  @Test(expected = IllegalArgumentException.class)
  public void overrideConnection2() {
    myCave.addConnection(Direction.NORTH, new Location(1,1));
    myCave.addConnection(Direction.EAST, new Location(1,1));
  }

  /**
   * Test removing and seeing if you get right amounts and check to see if the cave updates
   * correctly removing all the treasure.
   */
  @Test
  public void removeItemsTest() {
    HashMap<CaveObject, Integer> removeNothing = myCave.removeItems();
    myCave.addConnection(Direction.NORTH,new Location(0,1));
    assertEquals(0, removeNothing.get(CaveObject.DIAMOND).intValue());
    assertEquals( 0, removeNothing.get(CaveObject.RUBY).intValue());
    assertEquals( 0, removeNothing.get(CaveObject.SAPPHIRE).intValue());
    assertEquals( 0, removeNothing.get(CaveObject.CROOKEDARROW).intValue());

    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.SAPPHIRE);
    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.RUBY);
    myCave.addArrow();
    HashMap<CaveObject, Integer> remove = myCave.removeItems();
    assertEquals(2, remove.get(CaveObject.DIAMOND).intValue());
    assertEquals( 1, remove.get(CaveObject.RUBY).intValue());
    assertEquals( 1, remove.get(CaveObject.SAPPHIRE).intValue());
    assertEquals( 1, remove.get(CaveObject.CROOKEDARROW).intValue());

    HashMap<CaveObject, Integer> tres = myCave.getItems();
    assertEquals(0, tres.get(CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(CaveObject.SAPPHIRE).intValue());
    assertEquals( 0, tres.get(CaveObject.CROOKEDARROW).intValue());

  }


  /**
   * Test all the new methods that were added in.
   */
  @Test
  public void newMethods() {
    assertEquals(0,myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());
    myCave.addArrow();
    assertEquals(1,myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());
    myCave.addArrow();
    myCave.addArrow();
    assertEquals(3,myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());
    myCave.removeItems();
    assertEquals(0,myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());

    assertEquals(Smell.NONE ,myCave.getSmell());
    myCave.updateSmell(Smell.LIGHT);
    assertEquals(Smell.LIGHT ,myCave.getSmell());
    myCave.updateSmell(Smell.PUNGENT);
    assertEquals(Smell.PUNGENT ,myCave.getSmell());
    myCave.updateSmell(Smell.NONE);
    assertEquals(Smell.NONE ,myCave.getSmell());

    assertNull(myCave.getMonster());
    myCave.addMonster(new Otyugh());
    assertNotNull(myCave.getMonster());
  }

  /**
   * Test the null smell.
   */
  @Test(expected = IllegalArgumentException.class)
    public void nullSmell() {
    this.myCave.updateSmell(null);
  }

  /**
   * Test the null monster.
   */
  @Test(expected = IllegalArgumentException.class)
  public void notTreasure() {
    this.myCave.addTreasure(CaveObject.CROOKEDARROW);
  }

  /**
   * Test the null monster.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullMonster() {
    this.myCave.addMonster(null);
  }

  /**
   * Adding more than one monster or into a tunnel return false.
   */
  @Test
  public void monsterAdding() {
    this.myCave.addMonster(new Otyugh());
    assertFalse(this.myCave.addMonster(new Otyugh()));

    Cave cave1 = cave(new Location(0,0));
    cave1.addConnection(Direction.NORTH, new Location(1,0));
    cave1.addConnection(Direction.SOUTH, new Location(2,0));
    assertFalse(cave1.addMonster(new Otyugh()));

  }

  /**
   * Making sure an arrow will add to tunnel.
   */
  @Test
  public void addArrowTunnel() {
    myCave.addConnection(Direction.NORTH, new Location(1,1));
    myCave.addConnection(Direction.SOUTH, new Location(0,1));
    assertEquals(2, myCave.getDirections().values().size());
    myCave.addArrow();
    myCave.addArrow();
    assertEquals(2, myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());
  }
}

