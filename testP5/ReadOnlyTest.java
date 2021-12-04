import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.ReadOnlyDungeon;
import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for the readOnly Model.
 */
public class ReadOnlyTest {
  private Dungeon dungeon;
  private ReadOnlyDungeon read;

  /**
   * Set up of the readOnly Dungeon.
   */
  @Before
  public void setUp() {
    dungeon = new DungeonImpl(6, 7, 43,
            true, 100, "Jack", true, 1);
    read = dungeon.makeReadOnly();
  }

  /**
   * General getters of the dungeon that remain unchanged no matter the moves taken.
   */
  @Test
  public void generalGetters() {
    assertEquals(6,read.getWidth());
    assertEquals(7,read.getHeight());
    assertEquals(43,read.getInterconnectivity());
    assertEquals(100,read.getItemPercent());
    assertEquals(1, read.getMonsterCount());
    assertTrue(read.getWrapping());
  }

  /**
   * Get the location of the player and check against the dungeons location and update location
   * making sure it changes as intended. Also checking size of the visits as well.
   */
  @Test
  public void locationGetter() {
    assertEquals(dungeon.getVisits(), read.getVisits());
    assertEquals(dungeon.getPlayerLocation(), read.getPlayerLoc());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(dungeon.getPlayerLocation(), read.getPlayerLoc());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(dungeon.getPlayerLocation(), read.getPlayerLoc());
    dungeon.movePlayer(Direction.WEST);
    assertEquals(dungeon.getPlayerLocation(), read.getPlayerLoc());
    assertEquals(dungeon.getVisits(), read.getVisits());
  }

  /**
   * Checking picking up items and the contents of the cave vs the player.
   */
  @Test
  public void itemGetters() {
    Cave cave = dungeon.getCave(dungeon.getPlayerLocation());
    assertEquals(cave.getItems().get(CaveObject.DIAMOND).intValue(), read.caveDiamond());
    assertEquals(cave.getItems().get(CaveObject.RUBY).intValue(), read.caveRuby());
    assertEquals(cave.getItems().get(CaveObject.SAPPHIRE).intValue(), read.caveSapphire());
    assertEquals(cave.getItems().get(CaveObject.CROOKEDARROW).intValue(), read.caveArrow());

    dungeon.movePlayer(Direction.SOUTH);
    cave = dungeon.getCave(dungeon.getPlayerLocation());
    assertEquals(cave.getItems().get(CaveObject.DIAMOND).intValue(), read.caveDiamond());
    assertEquals(cave.getItems().get(CaveObject.RUBY).intValue(), read.caveRuby());
    assertEquals(cave.getItems().get(CaveObject.SAPPHIRE).intValue(), read.caveSapphire());
    assertEquals(cave.getItems().get(CaveObject.CROOKEDARROW).intValue(), read.caveArrow());

    assertEquals(0, read.playerRuby());
    assertEquals(0, read.playerDiamond());
    assertEquals(3, read.playerArrow());
    assertEquals(0, read.playerSapphire());

    int d = cave.getItems().get(CaveObject.DIAMOND);
    int s = cave.getItems().get(CaveObject.SAPPHIRE);
    int r = cave.getItems().get(CaveObject.RUBY);
    int a = cave.getItems().get(CaveObject.CROOKEDARROW);
    dungeon.search();
    assertEquals(r, read.playerRuby());
    assertEquals(d, read.playerDiamond());
    assertEquals(3 + a, read.playerArrow());
    assertEquals(s, read.playerSapphire());

  }

  /**
   * Resting the dungeon test making sure everything works as it should.
   */
  @Test
  public void resetTest() {
    dungeon = new DungeonImpl(6, 7, 43,
            true, 100, "Jack", false, 1);
    read = dungeon.makeReadOnly();
    Location start = dungeon.getStart();
    assertFalse(read.caveDiamond() == 0 &&  read.caveSapphire()  == 0
            && read.caveRuby() == 0);
    dungeon.search();
    assertTrue(read.caveDiamond() == 0 &&  read.caveSapphire()  == 0
            && read.caveRuby() == 0);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.search();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.search();
    dungeon.movePlayer(Direction.EAST);
    dungeon.search();
    dungeon.movePlayer(Direction.EAST);
    dungeon.search();
    assertEquals(5, read.getVisits().size());
    assertNotEquals(read.getPlayerLoc(), dungeon.getStart());
    assertNotEquals(3, read.playerArrow());

    assertFalse(read.playerSapphire() == 0 &&  read.playerRuby()  == 0
            && read.playerDiamond() == 0);
    dungeon.reset();
    assertTrue(read.playerSapphire() == 0 &&  read.playerRuby()  == 0
            && read.playerDiamond() == 0);
    assertEquals(read.getPlayerLoc(), dungeon.getStart());
    assertEquals(3, read.playerArrow());
    assertEquals(start, read.getPlayerLoc());
    assertFalse(read.caveDiamond() == 0 &&  read.caveSapphire()  == 0
            && read.caveRuby() == 0);
    assertEquals(1, read.getVisits().size());
  }

  /**
   * Winning the game test making sure everything is set correctly.
   */
  @Test
  public void winningTest() {
    dungeon = new DungeonImpl(6, 7, 43,
            true, 100, "Jack", false, 1);
    read = dungeon.makeReadOnly();
    assertFalse(read.hasMonster());
    assertFalse(read.hasLivingMonster());
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(new Location(2,3 ), read.getPlayerLoc());
    dungeon.shoot(1, Direction.EAST);
    dungeon.shoot(1, Direction.EAST);
    assertEquals(1, read.playerArrow());
    dungeon.movePlayer(Direction.EAST);
    assertTrue(read.hasSolved());
    assertFalse(read.hasLost());
    assertTrue(read.hasMonster());
    assertFalse(read.hasLivingMonster());
  }

  /**
   * Dying test everything works correctly as well.
   */
  @Test
  public void dyingTest() {
    dungeon = new DungeonImpl(6, 7, 43,
          true, 100, "Jack", false, 1);
    read = dungeon.makeReadOnly();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertFalse(read.hasSolved());
    assertTrue(read.hasLost());
    assertTrue(read.hasMonster());
    assertTrue(read.hasLivingMonster());
  }
}


