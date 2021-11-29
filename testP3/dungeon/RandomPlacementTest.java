package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the RandomPlacement Class.
 */
public class RandomPlacementTest  {
  private RandomPlacement rand;

  /**
   * Sets up a random.
   */
  @Before
  public void setUp() {
    rand = new RandomPlacement(7,6);
  }


  /**
   * rolling test.
   */
  @Test
  public void rollTest() {
    Location y;
    for (int x = 0; x < 500; x++) {
      y = rand.roll();
      assertTrue(y.getX() >= 0);
      assertTrue(y.getX() < 7);
      assertTrue(y.getY() >= 0);
      assertTrue(y.getY() < 6);
    }
    y = rand.rollNotRandom(true);
    assertEquals(0,y.getX());
    assertEquals(0,y.getY());
    y = rand.rollNotRandom(false);
    assertEquals(3,y.getX());
    assertEquals(3,y.getY());
  }


}