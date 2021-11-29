package dungeon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for monsters.
 */
public class MonsterTest {

  /**
   * Test the generation of the Otyugh and makes ure everything updates accordingly
   * when one is
   * shot.
   */
  @Test
  public void generationTest() {
    Monster m = new Otyugh();
    assertFalse(m.isShot());
    assertFalse(m.isDead());
    m.shot();
    assertTrue(m.isShot());
    assertFalse(m.isDead());
    m.shot();
    assertTrue(m.isShot());
    assertTrue(m.isDead());
  }

  /**
   * Tests shooting a monster with too many arrows.
   */
  @Test(expected =  IllegalStateException.class)
  public void shotTooMany() {
    Monster m = new Otyugh();
    m.shot();
    m.shot();
    m.shot();
  }

}
