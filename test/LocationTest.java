import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import dungeon.Location;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the Location Class.
 */
public class LocationTest {
  private Location location;
  private Location location2;

  /**
   * Sets up a Location Object for all the tests.
   */
  @Before
  public void setUp() {
    location = new Location(3,3);
    location2 = new Location(0,0);
  }

  private Location loc(int x, int y) {
    return new Location(x,y);
  }

  /**
   * Can't have negative x.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badLocation1() {
    loc(-1,0);
  }

  /**
   * Can't have negative y.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badLocation2() {
    loc(1,-1);
  }

  /**
   * Can't have negative x and y.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badLocation3() {
    loc(-1,-1);
  }

  /**
   * Distance test.
   */
  @Test
  public void distanceTest() {
    assertEquals(6, location.getDistance(location2));
    assertEquals(6, location2.getDistance(location));
    assertEquals(0, location.getDistance(location));
    assertEquals(0, location2.getDistance(location2));
    Location location3 = loc(5,3);
    assertEquals(2, location.getDistance(location3));
    assertEquals(2, location3.getDistance(location));
    assertEquals(8, location3.getDistance(location2));
    assertEquals(8, location2.getDistance(location3));
  }

  /**
   * Distance test.
   */
  @Test
  public void distanceWrapTest() {
    assertEquals(4, location.wrappingDistance(location2, 4,4));
    assertEquals(4, location2.wrappingDistance(location, 4,4));

  }

  /**
   * Test the Equal method.
   */
  @Test
  public void equalsTest() {
    assertEquals(location,location);
    assertEquals(location2,location2);
    assertNotEquals(location,location2);
    assertNotEquals(location2,location);

    Location location3 = loc(0,0);
    assertNotEquals(location,location3);
    assertNotEquals(location3,location);
    assertEquals(location2,location3);
    assertEquals(location3,location2);

    Location location4 = loc(3,3);
    assertNotEquals(location2,location4);
    assertNotEquals(location4,location2);
    assertEquals(location4,location);
    assertEquals(location,location4);
  }

  /**
   * Test the HashCode method.
   */
  @Test
  public void hashTest() {
    assertNotEquals(location.hashCode(), location2.hashCode());
    assertNotEquals(location2.hashCode(), location.hashCode());

    Location location3 = loc(3,3);
    assertEquals(location.hashCode(), location3.hashCode());
    assertEquals(location3.hashCode(), location.hashCode());
  }

  /**
   * Test the ToString method.
   */
  @Test
  public void toStringTest() {
    assertEquals("(3,3)", location.toString());
    assertEquals("(0,0)", location2.toString());
  }
}