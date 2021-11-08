import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import dungeon.Cave;
import dungeon.Description;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test for the DungeonImpl Class.
 */
public class DungeonTest {
  private Dungeon dungeonWrap;
  private Dungeon dungeonNoWrap;

  /**
   * Sets up a Dungeon Object for all the tests.
   */
  @Before
  public void setUp() {
    dungeonNoWrap = new DungeonImpl(6, 6, 25,
            false, 100, "Jack", true, 1);

    dungeonWrap = new DungeonImpl(6, 7, 43,
            true, 100, "Jack", true, 1);
  }

  private Dungeon dungeon(int w, int h, int i, boolean wrap, int treasure,
                          String name, boolean random, int monsterCount) {
    return new DungeonImpl(w, h, i, wrap, treasure, name, random, monsterCount);
  }

  /**
   * Width must be greater than or equal to 6.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badWidth() {
    dungeon(5, 6, 1, true, 25, "Jack", true, 1);
  }

  /**
   * Height must be greater than or equal to 5.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badHeight() {
    dungeon(6, 5, 1, true, 25, "Jack", true, 2);
  }

  /**
   * Interconnectivity must be greater than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInterconnectivity1() {
    dungeon(6, 6, -1, true, 25, "Jack", true, 3);
  }

  /**
   * on Non Wrapping To figure out the max connectivity multiply the
   * width - 1 * height - 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInterconnectivity2() {
    dungeon(6, 6, 5 * 5 + 1, false, 25, "Jack", true, 2);
  }

  /**
   * on Wrapping To figure out the max connectivity multiply the
   * width - 1 * height - 1 + width + height.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInterconnectivity3() {
    dungeon(6, 6, (6 * 6) + 2, true, 25, "Jack", true, 1);
  }

  /**
   * Null was passed for the name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badName() {
    dungeon(6, 6, 3, true, 25, null, true, 1);
  }


  /**
   * Treasure percent less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badTreasurePercent1() {
    dungeon(6, 6, 3, true, -1, "John", true, 2);
  }

  /**
   * Treasure percent greater than 100.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badTreasurePercent2() {
    dungeon(6, 6, 3, true, 101, "John", true, 3);
  }

  /**
   * Can't have 0 monsters.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badMonsterCount() {
    dungeon(6, 6, 3, true, 100, "John", true, 0);
  }


  /**
   * Can't have negative monsters.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badMonsterCount2() {
    dungeon(6, 6, 3, true, 100, "John", true, -1);
  }

  /**
   * Must have only 20% of total dungeon have monsters.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badMonsterCount3() {
    dungeon(6, 6, 3, true, 100, "John", true, 8);
  }


  /**
   * Test that arrows are added accordingly.
   */
  @Test
  public void arrowTest1() {
    int count = 0;
    for (int y = 0; y < dungeonWrap.getHeight(); y++) {
      for (int x = 0; x < dungeonWrap.getWidth(); x++) {
        Cave cave = dungeonWrap.getCave(new Location(x, y));
        if (cave.getItems().get(CaveObject.CROOKEDARROW) > 0) {
          count++;
        }
      }
    }
    assertEquals(42, count);
  }


  /**
   * Test that arrows are added accordingly.
   */
  @Test
  public void arrowTest2() {
    int count = 0;
    for (int y = 0; y < dungeonNoWrap.getHeight(); y++) {
      for (int x = 0; x < dungeonNoWrap.getWidth(); x++) {
        Cave cave = dungeonNoWrap.getCave(new Location(x, y));
        if (cave.getItems().get(CaveObject.CROOKEDARROW) > 0) {
          count++;
        }
      }
    }
    assertEquals(36, count);
  }

  /**
   * Test that arrows are added accordingly.
   */
  @Test
  public void arrowTest3() {
    Dungeon dung = dungeon(7, 8, 5, false, 25, "Jack", false, 1);
    int count = 0;
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        Cave cave = dung.getCave(new Location(x, y));
        if (cave.getItems().get(CaveObject.CROOKEDARROW) > 0) {
          count++;
        }
      }
    }
    assertEquals(14, count);
  }


  /**
   * Test that arrows are added accordingly.
   */
  @Test
  public void arrowTest4() {
    Dungeon dung = dungeon(7, 7, 5, false, 100, "Jack", false, 1);
    int count = 0;
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        Cave cave = dung.getCave(new Location(x, y));
        if (cave.getItems().get(CaveObject.CROOKEDARROW) > 0) {
          count++;
        }
      }
    }
    assertEquals(49, count);
  }

  /**
   * Test monster at the end.
   */
  @Test
  public void monsterEnd() {
    Dungeon dung;
    for (int x = 0; x < 700; x++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 1);
      Cave cave = dung.getCave(dung.getEnd());
      assertNotNull(cave.getMonster());
      assertFalse(cave.getMonster().isShot());
    }
  }

  /**
   * Test no monster at start.
   */
  @Test
  public void monsterStart() {
    Dungeon dung;
    for (int x = 0; x < 700; x++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 9);
      Cave cave = dung.getCave(dung.getStart());
      assertNull(cave.getMonster());
    }
  }

  /**
   * Test no monster at a Tunnel.
   */
  @Test
  public void monsterTunnel() {
    Dungeon dung;
    for (int z = 0; z < 700; z++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 9);
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Cave cave = dung.getCave(new Location(x, y));
          if (cave.getDirections().size() == 2) {
            assertNull(cave.getMonster());
          }
        }
      }
    }
  }

  /**
   * Test monster can have treasure in their cave.
   */
  @Test
  public void monsterTreasure() {
    Dungeon dung;
    for (int z = 0; z < 700; z++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 9);
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Cave cave = dung.getCave(new Location(x, y));
          if (cave.getMonster() != null) {
            int count = 0;
            for (CaveObject item : cave.getItems().keySet()) {
              if (item.getType().equals("treasure")) {
                count += cave.getItems().get(item);
              }
            }
            assertTrue(count > 0);
          }
        }
      }
    }
  }

  /**
   * Test monster can have Arrows in their cave.
   */
  @Test
  public void monsterArrow() {
    Dungeon dung;
    for (int z = 0; z < 700; z++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 9);
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Cave cave = dung.getCave(new Location(x, y));
          if (cave.getMonster() != null) {
            int count = 0;
            for (CaveObject item : cave.getItems().keySet()) {
              if (item.getType().equals("item")) {
                count += cave.getItems().get(item);
              }
            }
            assertTrue(count > 0);
          }
        }
      }
    }
  }


  /**
   * Test monster at the end.
   */
  @Test
  public void monsterTest() {
    Dungeon dung;
    for (int z = 0; z < 700; z++) {
      int count = 0;
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 6);
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Cave cave = dung.getCave(new Location(x, y));
          if (cave.getMonster() != null) {
            count++;
          }
        }
      }
      assertEquals(6, count);
    }
  }

  /**
   * Test monster at the end.
   */
  @Test
  public void smellTest() {
    Cave end = dungeonWrap.getCave(dungeonWrap.getEnd());
    List<Cave> first = new ArrayList<>();
    for (Location loc : end.getDirections().values()) {
      first.add(dungeonWrap.getCave(loc));
    }
    for (Cave cave : first) {
      assertEquals(Smell.PUNGENT, cave.getSmell());
      for (Location loc : cave.getDirections().values()) {
        if (!loc.equals(end.getLocation())) {
          assertEquals(Smell.LIGHT, dungeonWrap.getCave(loc).getSmell());
        }
      }
    }
  }

  /**
   * Testing smell.
   */
  @Test
  public void smellTest2() {
    Dungeon dung;
    for (int z = 0; z < 700; z++) {
      dung = dungeon(7, 7, 5, false, 100, "Jack", true, 6);
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Cave cave = dung.getCave(new Location(x, y));
          List<Cave> oneStep = new ArrayList<>();
          boolean gotSmell = false;
          for (Location loc : cave.getDirections().values()) {
            if (cave.getLocation() != loc) {
              if (dung.getCave(loc).getMonster() != null) {
                assertEquals(Smell.PUNGENT, cave.getSmell());
                gotSmell = true;
                break;
              } else {
                oneStep.add(dung.getCave(loc));
              }
            }
          }
          if (!gotSmell) {
            List<Location> locs = new ArrayList<>();
            for (Cave cur : oneStep) {
              for (Location loc : cur.getDirections().values()) {
                if (cave.getLocation() != loc) {
                  locs.add(loc);
                }
              }
            }
            locs = locs.stream().distinct().collect(Collectors.toList());
            int count = 0;
            for (Location loc : locs) {
              if (dung.getCave(loc).getMonster() != null) {
                count++;
              }
            }
            if (count == 0) {
              assertEquals(Smell.NONE, cave.getSmell());
            } else if (count == 1) {
              assertEquals(Smell.LIGHT, cave.getSmell());
            } else {
              assertEquals(Smell.PUNGENT, cave.getSmell());
            }
          }
        }
      }
    }
  }

  /**
   * Testing smell.
   */
  @Test
  public void smellTest3() {
    Dungeon dung = dungeon(6, 6, 25, false, 100, "Jack", false, 6);
    assertNotNull(dung.getCave(new Location(1, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(2, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(3, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(4, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(0, 1)).getMonster());
    assertNotNull(dung.getCave(new Location(3, 3)).getMonster());
    assertNull(dung.getCave(new Location(5, 0)).getMonster());
    int count = 0;
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        if (dung.getCave(new Location(x, y)).getMonster() != null) {
          count++;
        }
      }
    }
    assertEquals(6, count);
    assertEquals(Smell.PUNGENT, dung.getCave(new Location(2, 2)).getSmell());
  }


  /**
   * Test to make sure a player dies correctly.
   */
  @Test
  public void dying() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);
    assertNotNull(dung.getCave(new Location(3, 3)).getMonster());
    assertFalse(dung.getCave(new Location(3, 3)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(3, 3)).getMonster().isDead());
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasLost());
    assertFalse(dung.hasSolved());
    assertEquals(dung.getPlayerLocation(), dung.getEnd());
  }

  /**
   * Test to make sure a player dies correctly move after
   */
  @Test(expected = IllegalStateException.class)
  public void dying2() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasLost());
    dung.movePlayer(Direction.EAST);
  }

  /**
   * Test to make sure a player dies correctly shoot after
   */
  @Test(expected = IllegalStateException.class)
  public void dying3() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasLost());
    dung.shoot(3, Direction.NORTH);
  }

  /**
   * Test to make sure a player dies correctly search after
   */
  @Test(expected = IllegalStateException.class)
  public void dying4() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasLost());
    dung.search();
  }


  /**
   * Killing a Monster.
   */
  @Test
  public void shoot1() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.shoot(1, Direction.EAST));
    assertTrue(dung.shoot(1, Direction.EAST));
    dung.movePlayer(Direction.EAST);
    assertFalse(dung.hasLost());
    assertTrue(dung.hasSolved());
  }

  /**
   * shooting but no Monster.
   */
  @Test
  public void shoot2() {
    Dungeon dung = dungeon(6, 6, 25, false, 0,
            "jack", false, 1);
    assertFalse(dung.shoot(1, Direction.EAST));
    assertFalse(dung.shoot(1, Direction.EAST));
    dung.movePlayer(Direction.EAST);
    dung.search();
    Description des = dung.getPlayerDescription();
    assertTrue(des.getPlayerItems().contains("CrookedArrow: 1"));
  }

  /**
   * OverShooting a Monster.
   */
  @Test
  public void shoot3() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertNotNull(dung.getCave(new Location(3, 3)).getMonster());
    assertFalse(dung.shoot(2, Direction.EAST));
    assertFalse(dung.shoot(2, Direction.EAST));
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasLost());
    assertFalse(dung.hasSolved());
  }

  /**
   * shoot a Monster using wrap.
   */
  @Test
  public void shoot4() {
    Dungeon dung = dungeon(6, 6, 37, true, 33,
            "jack", false, 1);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    assertTrue(dung.shoot(3, Direction.WEST));
    assertTrue(dung.shoot(3, Direction.WEST));
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertFalse(dung.hasLost());
    assertTrue(dung.hasSolved());
  }


  /**
   * shoot a Monster using wrap max Distance.
   */
  @Test
  public void shoot5() {
    Dungeon dung = dungeon(8, 8, 65, true, 33,
            "jack", false, 1);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    assertTrue(dung.shoot(5, Direction.WEST));
    assertTrue(dung.shoot(5, Direction.WEST));
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertFalse(dung.hasLost());
    assertTrue(dung.hasSolved());
  }


  /**
   * shoot a Monster  noWrap max Distance.
   */
  @Test
  public void shoot6() {
    Dungeon dung = dungeon(9, 9, 64, false, 33,
            "jack", false, 1);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.shoot(5, Direction.NORTH));
    assertTrue(dung.shoot(5, Direction.NORTH));
    dung.movePlayer(Direction.NORTH);
    dung.movePlayer(Direction.NORTH);
    dung.movePlayer(Direction.NORTH);
    dung.movePlayer(Direction.NORTH);
    dung.movePlayer(Direction.NORTH);
    assertFalse(dung.hasLost());
    assertTrue(dung.hasSolved());
  }

  /**
   * Out of arrows.
   */
  @Test(expected = IllegalStateException.class)
  public void outOfArrows() {
    Dungeon dung = dungeon(6, 6, 25, false, 10, "jack", false, 1);
    dung.shoot(1, Direction.EAST);
    dung.shoot(1, Direction.EAST);
    dung.shoot(1, Direction.EAST);
    dung.shoot(1, Direction.EAST);
  }

  /**
   * shot directly into a wall west.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shootingIntoWall() {
    Dungeon dung = dungeon(6, 6, 25, false, 10, "jack", false, 1);
    dung.shoot(1, Direction.WEST);

  }

  /**
   * shot directly into a south.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shootingIntoWall2() {
    Dungeon dung = dungeon(6, 6, 25, false, 10, "jack", false, 1);
    dung.shoot(1, Direction.NORTH);

  }

  /**
   * See that smell updates when a monster dies.
   */
  @Test
  public void monsterDeadSmellUpdate() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.shoot(1, Direction.EAST));
    assertTrue(dung.shoot(1, Direction.EAST));
    dung.movePlayer(Direction.EAST);
    assertTrue(dung.hasSolved());
    assertFalse(dung.hasLost());
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        Cave cave = dung.getCave(new Location(x, y));
        assertEquals(Smell.NONE, cave.getSmell());
      }
    }
  }


  /**
   * Shooing around in a tunnel.
   */
  @Test
  public void shootAround() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 5);

    dung.search();
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.SOUTH);
    assertNotNull(dung.getCave(new Location(1, 0)).getMonster());
    assertTrue(dung.shoot(2, Direction.NORTH));
    System.out.println(dung.getCave(new Location(1, 0)).getMonster().isShot());
    assertTrue(dung.getCave(new Location(1, 0)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(1, 0)).getMonster().isDead());
    assertTrue(dung.shoot(2, Direction.NORTH));
    assertTrue(dung.getCave(new Location(1, 0)).getMonster().isDead());

    assertNotNull(dung.getCave(new Location(2, 0)).getMonster());
    assertFalse(dung.getCave(new Location(2, 0)).getMonster().isDead());
    assertTrue(dung.shoot(3, Direction.NORTH));
    assertTrue(dung.getCave(new Location(2, 0)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(2, 0)).getMonster().isDead());
    assertTrue(dung.shoot(3, Direction.NORTH));
    assertTrue(dung.getCave(new Location(2, 0)).getMonster().isDead());

    assertNotNull(dung.getCave(new Location(3, 0)).getMonster());
    assertFalse(dung.getCave(new Location(3, 0)).getMonster().isDead());
    assertTrue(dung.shoot(4, Direction.NORTH));
    assertTrue(dung.getCave(new Location(3, 0)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(3, 0)).getMonster().isDead());
    assertTrue(dung.shoot(4, Direction.NORTH));
    assertTrue(dung.getCave(new Location(3, 0)).getMonster().isDead());

    assertNotNull(dung.getCave(new Location(4, 0)).getMonster());
    assertFalse(dung.getCave(new Location(4, 0)).getMonster().isDead());
    assertTrue(dung.shoot(5, Direction.NORTH));
    assertTrue(dung.getCave(new Location(4, 0)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(4, 0)).getMonster().isDead());
    assertTrue(dung.shoot(5, Direction.NORTH));
    assertTrue(dung.getCave(new Location(4, 0)).getMonster().isDead());

    dung.movePlayer(Direction.NORTH);
    dung.movePlayer(Direction.NORTH);

    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertEquals(new Location(3, 0), dung.getPlayerLocation());
  }

  /**
   * Shooing Shows arrow won't curve  in a non tunnel.
   */
  @Test
  public void shootInto3WayCave() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 5);

    dung.search();
    dung.movePlayer(Direction.SOUTH);
    dung.movePlayer(Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertNotNull(dung.getCave(new Location(1, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(2, 0)).getMonster());
    assertNotNull(dung.getCave(new Location(3, 0)).getMonster());
    assertEquals(new Location(2, 1), dung.getPlayerLocation());
    assertTrue(dung.shoot(1, Direction.NORTH));
    assertTrue(dung.shoot(1, Direction.NORTH));
    assertTrue(dung.getCave(new Location(2, 0)).getMonster().isDead());
    assertFalse(dung.shoot(2, Direction.NORTH));
    assertFalse(dung.shoot(2, Direction.NORTH));
    assertFalse(dung.getCave(new Location(1, 0)).getMonster().isShot());
    assertFalse(dung.getCave(new Location(3, 0)).getMonster().isShot());

  }

  /**
   * 2 shots to kill.
   */
  @Test
  public void twoShotsToKill() {
    Dungeon dung;

    for (int x = 0; x < 600; x++) {
      dung = dungeon(6, 6, 25, false, 33,
              "jack", false, 5);
      dung.search();
      assertNotNull(dung.getCave(new Location(1, 0)).getMonster());
      assertTrue(dung.shoot(1, Direction.EAST));
      assertTrue(dung.getCave(new Location(1, 0)).getMonster().isShot());
      assertFalse(dung.getCave(new Location(1, 0)).getMonster().isDead());
      assertTrue(dung.shoot(1, Direction.EAST));
      assertTrue(dung.getCave(new Location(1, 0)).getMonster().isDead());
    }
  }

  /**
   * Making sure a player has approximately 50% to live when entering a cave with an injured
   * monster.
   */
  @Test
  public void escapeTest() {
    for (int y = 0; y < 500; y++) {
      int count = 0;
      Dungeon dung;
      for (int x = 0; x < 500; x++) {
        dung = dungeon(6, 6, 25, false, 33,
                "jack", false, 5);
        assertNotNull(dung.getCave(new Location(1, 0)).getMonster());
        assertTrue(dung.shoot(1, Direction.EAST));
        assertFalse(dung.getCave(new Location(1, 0)).getMonster().isDead());
        dung.movePlayer(Direction.EAST);
        if (!dung.hasLost()) {
          count++;
        }
      }
      assertEquals(.5, (double) count / 500, .1);
    }
  }
}






