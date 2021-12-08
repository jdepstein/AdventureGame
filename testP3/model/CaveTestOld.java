package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.enums.CaveObject;
import model.enums.Direction;
import model.enums.Smell;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Test for the CaveImpl Class.
 */
public class CaveTestOld {
  private Cave myCave;

  /**
   *Sets up a Cave Object for all the tests.
   */
  @Before
  public void setUp() {
    this.myCave = new CaveImpl(new Location(0, 0));
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
   * Testing getters of a newly initialized Cave.
   */
  @Test
  public void gettersTest() {

    Map<model.enums.Direction, Location> dir = myCave.getDirections();
    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(model.enums.Direction.NORTH));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(model.enums.Direction.EAST));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(model.enums.Direction.WEST));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(model.enums.Direction.SOUTH));

    assertEquals("We had the cave location as 0,0", new Location(0, 0),
            myCave.getLocation());
    assertEquals(0, myCave.getLocation().getX());
    assertEquals(0, myCave.getLocation().getY());

    Map<model.enums.CaveObject, Integer> tres = myCave.getItems();
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(model.enums.CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Trying to add a connection with a null direction.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection1() {
    myCave.addConnection(null, new Location(2, 2));
  }

  /**
   * Trying to add a connection with a null location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection2() {
    myCave.addConnection(model.enums.Direction.NORTH, null);
  }

  /**
   * Trying to add a connection with a location same as the current cave.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badConnection3() {
    myCave.addConnection(model.enums.Direction.NORTH, new Location(0,0));
  }

  /**
   * Trying to add a connection with a direction that has already been set.
   */
  @Test(expected = IllegalArgumentException.class)
  public void overrideConnection() {
    myCave.addConnection(model.enums.Direction.NORTH, new Location(1,1));
    myCave.addConnection(model.enums.Direction.NORTH, new Location(1,2));
  }

  /**
   * Trying to add a connection with a location that has already been set.
   */
  @Test(expected = IllegalArgumentException.class)
  public void overrideConnection2() {
    myCave.addConnection(model.enums.Direction.NORTH, new Location(1,1));
    myCave.addConnection(model.enums.Direction.EAST, new Location(1, 1));
  }

  /**
   * Adding 4 locations then testing getter.
   */
  @Test
  public void connectionTest() {
    myCave.addConnection(model.enums.Direction.NORTH, new Location(1, 1));
    myCave.addConnection(model.enums.Direction.EAST, new Location(1, 0));
    myCave.addConnection(model.enums.Direction.SOUTH, new Location(0,1));
    myCave.addConnection(model.enums.Direction.WEST, new Location(1, 2));

    Map<model.enums.Direction, Location> dir = myCave.getDirections();
    assertEquals(new Location(1,1), dir.get(model.enums.Direction.NORTH));
    assertEquals(new Location(1,0), dir.get(model.enums.Direction.EAST));
    assertEquals(new Location(0,1), dir.get(model.enums.Direction.SOUTH));
    assertEquals(new Location(1,2), dir.get(model.enums.Direction.WEST));

  }

  /**
   * Trying to add a null treasure.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badTreasure() {
    myCave.addTreasure(null);
  }

  /**
   * Adding Treasure of all types and multiples to cave w/1.
   */
  @Test
  public void addTreasureTest() {
    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    Map<model.enums.CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    Map<model.enums.CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(model.enums.CaveObject.DIAMOND), tresAfter.get(model.enums.CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(model.enums.CaveObject.SAPPHIRE), tresAfter.get(model.enums.CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(model.enums.CaveObject.RUBY), tresAfter.get(model.enums.CaveObject.RUBY));
    assertEquals(2, tresAfter.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(model.enums.CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to tunnel show false.
   */
  @Test
  public void addTreasureTest2() {
    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(2,1));
    Map<model.enums.CaveObject, Integer> tresBefore = myCave.getItems();
    assertFalse(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    Map<model.enums.CaveObject, Integer> tresAfter = myCave.getItems();
    assertEquals(tresBefore.get(model.enums.CaveObject.DIAMOND), tresAfter.get(model.enums.CaveObject.DIAMOND));
    assertEquals(tresBefore.get(model.enums.CaveObject.SAPPHIRE), tresAfter.get(model.enums.CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(model.enums.CaveObject.RUBY), tresAfter.get(model.enums.CaveObject.RUBY));
    assertEquals(0, tresAfter.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals(0, tresAfter.get(model.enums.CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to cave w/3.
   */
  @Test
  public void addTreasureTest3() {
    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(3,1));
    myCave.addConnection(model.enums.Direction.EAST,new Location(4,1));
    Map<model.enums.CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    Map<model.enums.CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(model.enums.CaveObject.DIAMOND), tresAfter.get(model.enums.CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(model.enums.CaveObject.SAPPHIRE), tresAfter.get(model.enums.CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(model.enums.CaveObject.RUBY), tresAfter.get(model.enums.CaveObject.RUBY));
    assertEquals(2, tresAfter.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(model.enums.CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to cave w/4.
   */
  @Test
  public void addTreasureTest4() {
    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(5,1));
    myCave.addConnection(model.enums.Direction.EAST,new Location(3,1));
    myCave.addConnection(model.enums.Direction.WEST,new Location(2,1));
    Map<model.enums.CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    Map<model.enums.CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(model.enums.CaveObject.DIAMOND), tresAfter.get(model.enums.CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(model.enums.CaveObject.SAPPHIRE), tresAfter.get(model.enums.CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(model.enums.CaveObject.RUBY), tresAfter.get(model.enums.CaveObject.RUBY));
    assertEquals(2, tresAfter.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(model.enums.CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples.
   */
  @Test
  public void addTreasureTest5() {
    assertFalse(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.RUBY));

    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(0,2));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(model.enums.CaveObject.RUBY));

  }

  /**
   * Test removing and seeing if you get right amounts and check to see if the cave updates
   * correctly removing all the treasure.
   */
  @Test
  public void removeItemsTest() {
    Map<model.enums.CaveObject, Integer> removeNothing = myCave.removeItems();
    myCave.addConnection(model.enums.Direction.NORTH,new Location(0,1));
    assertEquals(0, removeNothing.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals( 0, removeNothing.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals( 0, removeNothing.get(model.enums.CaveObject.SAPPHIRE).intValue());
    assertEquals( 0, removeNothing.get(model.enums.CaveObject.CROOKEDARROW).intValue());

    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.SAPPHIRE);
    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.RUBY);
    myCave.addArrow();
    Map<model.enums.CaveObject, Integer> remove = myCave.removeItems();
    assertEquals(2, remove.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals( 1, remove.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals( 1, remove.get(model.enums.CaveObject.SAPPHIRE).intValue());
    assertEquals( 1, remove.get(model.enums.CaveObject.CROOKEDARROW).intValue());

    Map<model.enums.CaveObject, Integer> tres = myCave.getItems();
    assertEquals(0, tres.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.SAPPHIRE).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.CROOKEDARROW).intValue());

  }

  /**
   * Removing treasure from a tunnel should net 0 or a non-connected cave.
   */
  @Test
  public void removeTreasureTest2() {
    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.SAPPHIRE);
    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.RUBY);

    Map<model.enums.CaveObject, Integer> tres = myCave.removeItems();
    assertEquals(0, tres.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.SAPPHIRE).intValue());

    myCave.addConnection(model.enums.Direction.NORTH,new Location(1,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(1,2));
    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.SAPPHIRE);
    myCave.addTreasure(model.enums.CaveObject.DIAMOND);
    myCave.addTreasure(model.enums.CaveObject.RUBY);
    tres = myCave.removeItems();
    assertEquals(0, tres.get(model.enums.CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(model.enums.CaveObject.SAPPHIRE).intValue());

  }

  /**
   * Testing Equals nad hash work.
   */
  @Test
  public void samLocation() {
    assertFalse(this.myCave.sameLocation(cave(new Location(1, 1))));
    assertTrue(this.myCave.sameLocation(cave(new Location(0, 0))));
  }

  /**
   * Testing to string works.
   */
  @Test
  public void toStringTest() {
    assertEquals("(--|--|  |--|--)", myCave.toString());
    myCave.addConnection(model.enums.Direction.NORTH,new Location(1,1));
    myCave.addConnection(model.enums.Direction.SOUTH,new Location(1,2));
    assertEquals("(--|^^|  |vv|--)", myCave.toString());
    myCave.addConnection(model.enums.Direction.EAST, new Location(1,0));
    myCave.addConnection(model.enums.Direction.WEST, new Location(2,2));
    assertEquals("(<=|^^|  |vv|=>)", myCave.toString());
  }

  /**
   * Test all the new methods that were added in.
   */
  @Test
  public void newMethods() {
    assertEquals(0,myCave.getItems().get(model.enums.CaveObject.CROOKEDARROW).intValue());
    myCave.addArrow();
    assertEquals(1,myCave.getItems().get(model.enums.CaveObject.CROOKEDARROW).intValue());
    myCave.addArrow();
    myCave.addArrow();
    assertEquals(3,myCave.getItems().get(model.enums.CaveObject.CROOKEDARROW).intValue());
    myCave.removeItems();
    assertEquals(0,myCave.getItems().get(model.enums.CaveObject.CROOKEDARROW).intValue());

    assertEquals(model.enums.Smell.NONE ,myCave.getSmell());
    myCave.updateSmell(model.enums.Smell.LIGHT);
    assertEquals(model.enums.Smell.LIGHT ,myCave.getSmell());
    myCave.updateSmell(model.enums.Smell.PUNGENT);
    assertEquals(model.enums.Smell.PUNGENT ,myCave.getSmell());
    myCave.updateSmell(model.enums.Smell.NONE);
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
    this.myCave.addTreasure(model.enums.CaveObject.CROOKEDARROW);
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
    cave1.addConnection(model.enums.Direction.NORTH, new Location(1,0));
    cave1.addConnection(model.enums.Direction.SOUTH, new Location(2,0));
    assertFalse(cave1.addMonster(new Otyugh()));

  }

  /**
   * Making sure an arrow will add to tunnel.
   */
  @Test
  public void addArrowTunnel() {
    myCave.addConnection(model.enums.Direction.NORTH, new Location(1,1));
    myCave.addConnection(Direction.SOUTH, new Location(0,1));
    assertEquals(2, myCave.getDirections().values().size());
    myCave.addArrow();
    myCave.addArrow();
    assertEquals(2, myCave.getItems().get(CaveObject.CROOKEDARROW).intValue());
  }
}

