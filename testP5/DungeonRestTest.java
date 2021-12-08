import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import model.Cave;
import model.Dungeon;
import model.DungeonImpl;
import model.Location;
import model.enums.Direction;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for the DungeonImpl Class.
 */
public class DungeonRestTest {
  private Dungeon dungeon;

  /**
   * Sets up the dungeon.
   */
  @Before
  public void setUp() {
    dungeon = new DungeonImpl(6, 7, 43,
            true, 100, "Jack", true, 1);
  }

  /**
   * Tests all the new getters that were added on to the dungeon.
   */
  @Test
  public void newGetters() {
    assertEquals(1, dungeon.getVisits().size());
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(3, dungeon.getVisits().size());
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(3, dungeon.getVisits().size());

    assertEquals(1, dungeon.getMonsterCount());
    assertTrue(dungeon.getWrapping());
    assertEquals(100, dungeon.getItemPercent());
    assertEquals(43, dungeon.getInterconnectivity());
    assertNotEquals(dungeon.getPlayerLocation(), dungeon.getStart());
    dungeon.reset();
    assertEquals(1, dungeon.getVisits().size());
    assertEquals(1, dungeon.getMonsterCount());
    assertEquals(100, dungeon.getItemPercent());
    assertEquals(43, dungeon.getInterconnectivity());
    assertEquals(dungeon.getPlayerLocation(), dungeon.getStart());
  }

  /**
   * The reset method for the dungeon test.
   */
  @Test
  public void resetMethod() {
    Location locS = dungeon.getStart();
    Location locE = dungeon.getEnd();
    Cave start = dungeon.getCave(locS);
    dungeon.search();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(4, dungeon.getVisits().size());
    dungeon.reset();
    assertEquals(locS, dungeon.getStart());
    assertEquals(locE, dungeon.getEnd());
    assertEquals(1, dungeon.getVisits().size());
    assertTrue(dungeon.getVisits().contains(locS));
    assertEquals(dungeon.getCave(dungeon.getStart()).getItems(), start.getItems());
  }
}
