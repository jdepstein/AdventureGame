package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;
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

    Map<Direction, Location> dir = myCave.getDirections();
    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(Direction.NORTH));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(Direction.EAST));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(Direction.WEST));

    assertNull("A Cave is initialized with no connections in any direction",
            dir.get(Direction.SOUTH));

    assertEquals("We had the cave location as 0,0", new Location(0, 0),
            myCave.getLocation());
    assertEquals(0, myCave.getLocation().getX());
    assertEquals(0, myCave.getLocation().getY());

    Map<CaveObject, Integer> tres = myCave.getItems();
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(CaveObject.DIAMOND).intValue());
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(CaveObject.RUBY).intValue());
    assertEquals("A Cave is initialized with 0 for all treasure types", 0,
            tres.get(CaveObject.SAPPHIRE).intValue());
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
    myCave.addConnection(Direction.EAST, new Location(1, 1));
  }

  /**
   * Adding 4 locations then testing getter.
   */
  @Test
  public void connectionTest() {
    myCave.addConnection(Direction.NORTH, new Location(1, 1));
    myCave.addConnection(Direction.EAST, new Location(1, 0));
    myCave.addConnection(Direction.SOUTH, new Location(0,1));
    myCave.addConnection(Direction.WEST, new Location(1, 2));

    Map<Direction, Location> dir = myCave.getDirections();
    assertEquals(new Location(1,1), dir.get(Direction.NORTH));
    assertEquals(new Location(1,0), dir.get(Direction.EAST));
    assertEquals(new Location(0,1), dir.get(Direction.SOUTH));
    assertEquals(new Location(1,2), dir.get(Direction.WEST));

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
    myCave.addConnection(Direction.NORTH,new Location(0,1));
    Map<CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    Map<CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(CaveObject.DIAMOND), tresAfter.get(CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(CaveObject.SAPPHIRE), tresAfter.get(CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(CaveObject.RUBY), tresAfter.get(CaveObject.RUBY));
    assertEquals(2, tresAfter.get(CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to tunnel show false.
   */
  @Test
  public void addTreasureTest2() {
    myCave.addConnection(Direction.NORTH,new Location(0,1));
    myCave.addConnection(Direction.SOUTH,new Location(2,1));
    Map<CaveObject, Integer> tresBefore = myCave.getItems();
    assertFalse(myCave.addTreasure(CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(CaveObject.DIAMOND));
    Map<CaveObject, Integer> tresAfter = myCave.getItems();
    assertEquals(tresBefore.get(CaveObject.DIAMOND), tresAfter.get(CaveObject.DIAMOND));
    assertEquals(tresBefore.get(CaveObject.SAPPHIRE), tresAfter.get(CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(CaveObject.RUBY), tresAfter.get(CaveObject.RUBY));
    assertEquals(0, tresAfter.get(CaveObject.DIAMOND).intValue());
    assertEquals(0, tresAfter.get(CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to cave w/3.
   */
  @Test
  public void addTreasureTest3() {
    myCave.addConnection(Direction.NORTH,new Location(0,1));
    myCave.addConnection(Direction.SOUTH,new Location(3,1));
    myCave.addConnection(Direction.EAST,new Location(4,1));
    Map<CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    Map<CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(CaveObject.DIAMOND), tresAfter.get(CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(CaveObject.SAPPHIRE), tresAfter.get(CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(CaveObject.RUBY), tresAfter.get(CaveObject.RUBY));
    assertEquals(2, tresAfter.get(CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples to cave w/4.
   */
  @Test
  public void addTreasureTest4() {
    myCave.addConnection(Direction.NORTH,new Location(0,1));
    myCave.addConnection(Direction.SOUTH,new Location(5,1));
    myCave.addConnection(Direction.EAST,new Location(3,1));
    myCave.addConnection(Direction.WEST,new Location(2,1));
    Map<CaveObject, Integer> tresBefore = myCave.getItems();
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    assertTrue(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertTrue(myCave.addTreasure(CaveObject.DIAMOND));
    Map<CaveObject, Integer> tresAfter = myCave.getItems();
    assertNotEquals(tresBefore.get(CaveObject.DIAMOND), tresAfter.get(CaveObject.DIAMOND));
    assertNotEquals(tresBefore.get(CaveObject.SAPPHIRE), tresAfter.get(CaveObject.SAPPHIRE));
    assertEquals(tresBefore.get(CaveObject.RUBY), tresAfter.get(CaveObject.RUBY));
    assertEquals(2, tresAfter.get(CaveObject.DIAMOND).intValue());
    assertEquals(1, tresAfter.get(CaveObject.SAPPHIRE).intValue());
  }

  /**
   * Adding Treasure of all types and multiples.
   */
  @Test
  public void addTreasureTest5() {
    assertFalse(myCave.addTreasure(CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(CaveObject.RUBY));

    myCave.addConnection(Direction.NORTH,new Location(0,1));
    myCave.addConnection(Direction.SOUTH,new Location(0,2));
    assertFalse(myCave.addTreasure(CaveObject.DIAMOND));
    assertFalse(myCave.addTreasure(CaveObject.SAPPHIRE));
    assertFalse(myCave.addTreasure(CaveObject.RUBY));

  }

  /**
   * Test removing and seeing if you get right amounts and check to see if the cave updates
   * correctly removing all the treasure.
   */
  @Test
  public void removeItemsTest() {
    Map<CaveObject, Integer> removeNothing = myCave.removeItems();
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
    Map<CaveObject, Integer> remove = myCave.removeItems();
    assertEquals(2, remove.get(CaveObject.DIAMOND).intValue());
    assertEquals( 1, remove.get(CaveObject.RUBY).intValue());
    assertEquals( 1, remove.get(CaveObject.SAPPHIRE).intValue());
    assertEquals( 1, remove.get(CaveObject.CROOKEDARROW).intValue());

    Map<CaveObject, Integer> tres = myCave.getItems();
    assertEquals(0, tres.get(CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(CaveObject.SAPPHIRE).intValue());
    assertEquals( 0, tres.get(CaveObject.CROOKEDARROW).intValue());

  }

  /**
   * Removing treasure from a tunnel should net 0 or a non-connected cave.
   */
  @Test
  public void removeTreasureTest2() {
    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.SAPPHIRE);
    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.RUBY);

    Map<CaveObject, Integer> tres = myCave.removeItems();
    assertEquals(0, tres.get(CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(CaveObject.SAPPHIRE).intValue());

    myCave.addConnection(Direction.NORTH,new Location(1,1));
    myCave.addConnection(Direction.SOUTH,new Location(1,2));
    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.SAPPHIRE);
    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.RUBY);
    tres = myCave.removeItems();
    assertEquals(0, tres.get(CaveObject.DIAMOND).intValue());
    assertEquals( 0, tres.get(CaveObject.RUBY).intValue());
    assertEquals( 0, tres.get(CaveObject.SAPPHIRE).intValue());

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
    myCave.addConnection(Direction.NORTH,new Location(1,1));
    myCave.addConnection(Direction.SOUTH,new Location(1,2));
    assertEquals("(--|^^|  |vv|--)", myCave.toString());
    myCave.addConnection(Direction.EAST, new Location(1,0));
    myCave.addConnection(Direction.WEST, new Location(2,2));
    assertEquals("(<=|^^|  |vv|=>)", myCave.toString());
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

