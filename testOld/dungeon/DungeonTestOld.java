package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * driver.Test for the DungeonImpl Class.
 */
public class DungeonTestOld {
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
   * Make sure the dungeon is not solved and make sure that the sizes are set correctly.
   */
  @Test
  public void initialization() {
    assertEquals(6, dungeonNoWrap.getHeight());
    assertEquals(6, dungeonNoWrap.getWidth());
    assertEquals(7, dungeonWrap.getHeight());
    assertEquals(6, dungeonWrap.getWidth());

    assertFalse(dungeonWrap.hasSolved());
    assertFalse(dungeonNoWrap.hasSolved());
    Dungeon dungeon1;
    Dungeon dungeon2;
    for (int x = 0; x < 500; x++) {
      dungeon1 = dungeon(6, 6, 5, false, 25, "John", true, 1);
      dungeon2 = dungeon(6, 7, 10, true, 25, "John", true, 1);
      assertEquals(dungeon1.getStart(), dungeon1.getPlayerLocation());
      assertEquals(dungeon2.getStart(), dungeon2.getPlayerLocation());
      assertTrue(dungeon1.getStart().getDistance(dungeon1.getEnd()) >= 5);
      assertTrue(dungeon2.getStart().wrappingDistance(dungeon2.getEnd(),
              dungeon2.getWidth() - 1, dungeon2.getHeight() - 1) >= 5);
      assertEquals(dungeonWrap.getStart(), dungeonWrap.getPlayerLocation());
      assertEquals(dungeonNoWrap.getStart(), dungeonNoWrap.getPlayerLocation());
    }
  }

  /**
   * We have full connectivity of the graph show that if a move can't happen it will
   * just keep the player in the same location.
   */
  @Test
  public void movingThePlayerNoWrap() {
    while (dungeonNoWrap.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX() - 1,
              dungeonNoWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.WEST);
        dungeonNoWrap.shoot(1, Direction.WEST);
      }
      dungeonNoWrap.movePlayer(Direction.WEST);
    }
    while (dungeonNoWrap.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
              dungeonNoWrap.getPlayerLocation().getY() - 1);
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.NORTH);
        dungeonNoWrap.shoot(1, Direction.NORTH);
      }
      dungeonNoWrap.movePlayer(Direction.NORTH);
    }
    assertEquals(new Location(0, 0), dungeonNoWrap.getPlayerLocation());

    dungeonNoWrap.movePlayer(Direction.WEST);
    assertEquals(new Location(0, 0), dungeonNoWrap.getPlayerLocation());

    dungeonNoWrap.movePlayer(Direction.NORTH);
    assertEquals(new Location(0, 0), dungeonNoWrap.getPlayerLocation());
  }

  /**
   * We have full connectivity of the graph show that wrapping works.
   */
  @Test
  public void movingThePlayerWrap() {
    while (dungeonWrap.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dungeonWrap.getPlayerLocation().getX() - 1,
              dungeonWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonWrap.getEnd())) {
        dungeonWrap.shoot(1, Direction.WEST);
        dungeonWrap.shoot(1, Direction.WEST);
      }
      dungeonWrap.movePlayer(Direction.WEST);
    }
    while (dungeonWrap.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dungeonWrap.getPlayerLocation().getX(),
              dungeonWrap.getPlayerLocation().getY() - 1);
      if (loc.equals(dungeonWrap.getEnd())) {
        dungeonWrap.shoot(1, Direction.NORTH);
        dungeonWrap.shoot(1, Direction.NORTH);
      }
      dungeonWrap.movePlayer(Direction.NORTH);
    }
    assertEquals(new Location(0, 0), dungeonWrap.getPlayerLocation());

    Location loc = new Location(dungeonWrap.getWidth() - 1,
            dungeonWrap.getPlayerLocation().getY());
    if (loc.equals(dungeonWrap.getEnd())) {
      dungeonWrap.shoot(1, Direction.WEST);
      dungeonWrap.shoot(1, Direction.WEST);
    }
    dungeonWrap.movePlayer(Direction.WEST);

    assertEquals(new Location(5, 0), dungeonWrap.getPlayerLocation());
    dungeonWrap.movePlayer(Direction.EAST);
    assertEquals(new Location(0, 0), dungeonWrap.getPlayerLocation());
    dungeonWrap.movePlayer(Direction.NORTH);


    loc = new Location(dungeonWrap.getPlayerLocation().getX(),
            dungeonWrap.getHeight() - 1);
    if (loc.equals(dungeonWrap.getEnd())) {
      dungeonWrap.shoot(1, Direction.NORTH);
      dungeonWrap.shoot(1, Direction.NORTH);
    }
    assertEquals(new Location(0, 6), dungeonWrap.getPlayerLocation());
    dungeonWrap.movePlayer(Direction.SOUTH);
    assertEquals(new Location(0, 0), dungeonWrap.getPlayerLocation());
  }

  /**
   * Null move.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badMove1() {
    dungeonNoWrap.movePlayer(null);
  }

  /**
   * Null move.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badMove2() {
    dungeonWrap.movePlayer(null);
  }

  /**
   * We have full connectivity so in a no-wrapping there should be no treasure in coroners.
   */
  @Test
  public void treasureHuntNoWrap() {
    while (dungeonNoWrap.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX() - 1,
              dungeonNoWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.WEST);
        dungeonNoWrap.shoot(1, Direction.WEST);
      }
      dungeonNoWrap.movePlayer(Direction.WEST);
    }
    while (dungeonNoWrap.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
              dungeonNoWrap.getPlayerLocation().getY() - 1);
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.NORTH);
        dungeonNoWrap.shoot(1, Direction.NORTH);
      }
      dungeonNoWrap.movePlayer(Direction.NORTH);
    }
    assertEquals(new Location(0, 0), dungeonNoWrap.getPlayerLocation());
    dungeonNoWrap.search();

    String diamonds = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(0);
    String rubies = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(1);
    String sapphires = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(2);
    assertEquals("Diamonds: 0", diamonds);
    assertEquals("Rubies: 0", rubies);
    assertEquals("Sapphires: 0", sapphires);


    String diamondsPrev = diamonds;
    String rubiesPrev = rubies;
    String sapphiresPrev = sapphires;
    Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX() + 1,
            dungeonNoWrap.getPlayerLocation().getY());
    if (loc.equals(dungeonNoWrap.getEnd())) {
      dungeonNoWrap.shoot(1, Direction.EAST);
      dungeonNoWrap.shoot(1, Direction.EAST);
    }
    dungeonNoWrap.movePlayer(Direction.EAST);
    while (dungeonNoWrap.getPlayerLocation().getX() != dungeonNoWrap.getWidth() - 1) {
      assertTrue(dungeonNoWrap.search());

      diamonds = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(0);
      rubies = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(1);
      sapphires = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(2);
      assertFalse(diamonds.equals(diamondsPrev) && rubies.equals(rubiesPrev)
              && sapphires.equals(sapphiresPrev));

      diamondsPrev = diamonds;
      rubiesPrev = rubies;
      sapphiresPrev = sapphires;

      assertFalse(dungeonNoWrap.search());
      loc = new Location(dungeonNoWrap.getPlayerLocation().getX() + 1,
              dungeonNoWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.EAST);
        dungeonNoWrap.shoot(1, Direction.EAST);
      }

      dungeonNoWrap.movePlayer(Direction.EAST);
    }
    dungeonNoWrap.search();
    assertEquals(diamondsPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(0));
    assertEquals(rubiesPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(1));
    assertEquals(sapphiresPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(2));


    loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
            dungeonNoWrap.getPlayerLocation().getY() + 1);
    if (loc.equals(dungeonNoWrap.getEnd())) {
      dungeonNoWrap.shoot(1, Direction.SOUTH);
      dungeonNoWrap.shoot(1, Direction.SOUTH);
    }
    dungeonNoWrap.movePlayer(Direction.SOUTH);
    while (dungeonNoWrap.getPlayerLocation().getY() != dungeonNoWrap.getHeight() - 1) {
      assertTrue(dungeonNoWrap.search());
      assertFalse(dungeonNoWrap.search());
      loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
              dungeonNoWrap.getPlayerLocation().getY() + 1);
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.SOUTH);
        dungeonNoWrap.shoot(1, Direction.SOUTH);
      }

      dungeonNoWrap.movePlayer(Direction.SOUTH);
      diamonds = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(0);
      rubies = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(1);
      sapphires = dungeonNoWrap.getPlayerDescription().getPlayerItems().get(2);
      diamondsPrev = diamonds;
      rubiesPrev = rubies;
      sapphiresPrev = sapphires;


    }
    dungeonNoWrap.search();
    assertEquals(diamondsPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(0));
    assertEquals(rubiesPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(1));
    assertEquals(sapphiresPrev, dungeonNoWrap.getPlayerDescription().getPlayerItems().get(2));

  }

  /**
   * We have full connectivity so in a wrapping there should be treasure in every location.
   */
  @Test
  public void treasureHuntWrap() {
      while (dungeonWrap.getPlayerLocation().getX() != 0) {
        Location loc = new Location(dungeonWrap.getPlayerLocation().getX() - 1,
                dungeonWrap.getPlayerLocation().getY());
        if (loc.equals(dungeonWrap.getEnd())) {
          dungeonWrap.shoot(1, Direction.WEST);
          dungeonWrap.shoot(1, Direction.WEST);
        }
        dungeonWrap.movePlayer(Direction.WEST);
      }

      while (dungeonWrap.getPlayerLocation().getY() != 0) {
        Location loc = new Location(dungeonWrap.getPlayerLocation().getX(),
                dungeonWrap.getPlayerLocation().getY() - 1);
        if (loc.equals(dungeonWrap.getEnd())) {
          dungeonWrap.shoot(1, Direction.NORTH);
          dungeonWrap.shoot(1, Direction.NORTH);
        }
        dungeonWrap.movePlayer(Direction.NORTH);
      }

      assertEquals(new Location(0, 0), dungeonWrap.getPlayerLocation());
      assertTrue(dungeonWrap.search());

      String diamonds = dungeonWrap.getPlayerDescription().getPlayerItems().get(0);
      String rubies = dungeonWrap.getPlayerDescription().getPlayerItems().get(1);
      String sapphires = dungeonWrap.getPlayerDescription().getPlayerItems().get(2);
      String diamondsPrev = diamonds;
      String rubiesPrev = rubies;
      String sapphiresPrev = sapphires;

      Location loc = new Location(dungeonWrap.getPlayerLocation().getX() + 1,
              dungeonWrap.getPlayerLocation().getY());
      if (loc.getX() == dungeonWrap.getWidth()) {
        loc = new Location(0,
                dungeonWrap.getPlayerLocation().getY());
      }
      if (loc.equals(dungeonWrap.getEnd())) {
        dungeonWrap.shoot(1, Direction.EAST);
        dungeonWrap.shoot(1, Direction.EAST);
      }
      dungeonWrap.movePlayer(Direction.EAST);
      while (dungeonWrap.getPlayerLocation().getX() != dungeonWrap.getWidth() - 1) {
        assertTrue(dungeonWrap.search());

        diamonds = dungeonWrap.getPlayerDescription().getPlayerItems().get(0);
        rubies = dungeonWrap.getPlayerDescription().getPlayerItems().get(1);
        sapphires = dungeonWrap.getPlayerDescription().getPlayerItems().get(2);
        assertFalse(diamonds.equals(diamondsPrev) && rubies.equals(rubiesPrev)
                && sapphires.equals(sapphiresPrev));

        diamondsPrev = diamonds;
        rubiesPrev = rubies;
        sapphiresPrev = sapphires;
        assertFalse(dungeonWrap.search());
        dungeonWrap.movePlayer(Direction.EAST);
        loc = new Location(dungeonWrap.getPlayerLocation().getX() + 1,
                dungeonWrap.getPlayerLocation().getY());
        if (loc.equals(dungeonWrap.getEnd())) {
          dungeonWrap.shoot(1, Direction.EAST);
          dungeonWrap.shoot(1, Direction.EAST);
        }
      }
      assertTrue(dungeonWrap.search());
      assertFalse(dungeonWrap.search());

    }

  /**
   * Testing the description of the cave Wrapping.
   */
  @Test
  public void descriptionTestWrap() {
    while (dungeonWrap.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dungeonWrap.getPlayerLocation().getX() - 1,
              dungeonWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonWrap.getEnd())) {
        dungeonWrap.shoot(1, Direction.WEST);
        dungeonWrap.shoot(1, Direction.WEST);
      }
      dungeonWrap.movePlayer(Direction.WEST);
    }
    while (dungeonWrap.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dungeonWrap.getPlayerLocation().getX(),
              dungeonWrap.getPlayerLocation().getY() - 1);
      if (loc.equals(dungeonWrap.getEnd())) {
        dungeonWrap.shoot(1, Direction.NORTH);
        dungeonWrap.shoot(1, Direction.NORTH);
      }
      dungeonWrap.movePlayer(Direction.NORTH);
    }
    Description description = dungeonWrap.getPlayerDescription();
    List<String> dir = description.getCaveDirections();
    assertTrue(dir.contains("NORTH"));
    assertTrue(dir.contains("SOUTH"));
    assertTrue(dir.contains("EAST"));
    assertTrue(dir.contains("WEST"));
    List<String> tres = description.getCaveItems();
    assertFalse(tres.contains("Diamonds: 0") && tres.contains("Rubies: 0")
            && tres.contains("Sapphires: 0"));
    dungeonWrap.search();
    description = dungeonWrap.getPlayerDescription();
    tres = description.getCaveItems();
    assertTrue(tres.contains("Diamonds: 0") && tres.contains("Rubies: 0")
            && tres.contains("Sapphires: 0"));

  }

  /**
   * Testing the description of the cave Non-Wrapping.
   */
  @Test
  public void descriptionTestNoWrap() {
    while (dungeonNoWrap.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX() - 1,
              dungeonNoWrap.getPlayerLocation().getY());
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.WEST);
        dungeonNoWrap.shoot(1, Direction.WEST);
      }
      dungeonNoWrap.movePlayer(Direction.WEST);
    }
    while (dungeonNoWrap.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
              dungeonNoWrap.getPlayerLocation().getY() - 1);
      if (loc.equals(dungeonNoWrap.getEnd())) {
        dungeonNoWrap.shoot(1, Direction.NORTH);
        dungeonNoWrap.shoot(1, Direction.NORTH);
      }
      dungeonNoWrap.movePlayer(Direction.NORTH);
    }
    Description description = dungeonNoWrap.getPlayerDescription();
    List<String> dir = description.getCaveDirections();
    assertFalse(dir.contains("NORTH"));
    assertTrue(dir.contains("SOUTH"));
    assertTrue(dir.contains("EAST"));
    assertFalse(dir.contains("WEST"));
    List<String> tres = description.getCaveItems();
    assertTrue(tres.contains("Diamonds: 0") && tres.contains("Rubies: 0")
            && tres.contains("Sapphires: 0"));

    Location loc = new Location(dungeonNoWrap.getPlayerLocation().getX() + 1,
            dungeonNoWrap.getPlayerLocation().getY());
    if (loc.equals(dungeonNoWrap.getEnd())) {
      dungeonNoWrap.shoot(1, Direction.EAST);
      dungeonNoWrap.shoot(1, Direction.EAST);

    }

    dungeonNoWrap.movePlayer(Direction.EAST);

    description = dungeonNoWrap.getPlayerDescription();
    dir = description.getCaveDirections();
    assertFalse(dir.contains("NORTH"));
    assertTrue(dir.contains("SOUTH"));
    assertTrue(dir.contains("EAST"));
    assertTrue(dir.contains("WEST"));
    tres = description.getCaveItems();
    assertFalse(tres.contains("Diamonds: 0") && tres.contains("Rubies: 0")
            && tres.contains("Sapphires: 0"));

    dungeonNoWrap.search();
    description = dungeonNoWrap.getPlayerDescription();
    tres = description.getCaveItems();
    assertTrue(tres.contains("Diamonds: 0") && tres.contains("Rubies: 0")
            && tres.contains("Sapphires: 0"));

    loc = new Location(dungeonNoWrap.getPlayerLocation().getX(),
            dungeonNoWrap.getPlayerLocation().getY() + 1);
    if (loc.equals(dungeonNoWrap.getEnd())) {
      dungeonNoWrap.shoot(1, Direction.SOUTH);
      dungeonNoWrap.shoot(1, Direction.SOUTH);

    }


    dungeonNoWrap.movePlayer(Direction.SOUTH);
    description = dungeonNoWrap.getPlayerDescription();
    dir = description.getCaveDirections();
    assertTrue(dir.contains("NORTH"));
    assertTrue(dir.contains("SOUTH"));
    assertTrue(dir.contains("EAST"));
    assertTrue(dir.contains("WEST"));

  }

  /**
   * Collect Treasure and prove percentage.
   * In a non wrapping dungeon with 32 caves and full interconnectivity we have 21
   * valid caves that can hold treasure 25 % of 32 is about 8, so we expect that in
   * return.
   */
  @Test
  public void correctTreasurePercent1() {
    Dungeon dung = dungeon(6, 6, 25, false, 25, "Jack", true, 1);
    while (dung.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dung.getPlayerLocation().getX() - 1,
              dung.getPlayerLocation().getY());
      if (loc.equals(dung.getEnd())) {
        dung.shoot(1, Direction.WEST);
        dung.shoot(1, Direction.WEST);
      }
      dung.movePlayer(Direction.WEST);
    }
    while (dung.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dung.getPlayerLocation().getX(),
              dung.getPlayerLocation().getY() - 1);
      if (loc.equals(dung.getEnd())) {
        dung.shoot(1, Direction.NORTH);
        dung.shoot(1, Direction.NORTH);
      }
      dung.movePlayer(Direction.NORTH);
    }
    int count = 0;
    boolean moving_east = true;
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        Description description = dung.getPlayerDescription();
        if (!description.getCaveItems().contains("Diamonds: 0")
                || !description.getCaveItems().contains("Rubies: 0")
                || !description.getCaveItems().contains("Sapphires: 0")) {
          count++;
        }
        if (moving_east) {
          Location loc = new Location(dung.getPlayerLocation().getX() + 1,
                  dung.getPlayerLocation().getY());
          if (dung.getPlayerLocation().getX() + 1 < dung.getWidth()) {
            if (dung.getCave(loc).getMonster() != null
                    && !dung.getCave(loc).getMonster().isDead()) {
              dung.shoot(1, Direction.EAST);
              dung.shoot(1, Direction.EAST);
            }
          }

          dung.movePlayer(Direction.EAST);
        } else {
          if (dung.getPlayerLocation().getX() - 1 > 0) {
            Location loc = new Location(dung.getPlayerLocation().getX() - 1,
                    dung.getPlayerLocation().getY());
            if (dung.getCave(loc).getMonster() != null
                    && !dung.getCave(loc).getMonster().isDead()) {
              dung.shoot(1, Direction.WEST);
              dung.shoot(1, Direction.WEST);
            }
          }
          dung.movePlayer(Direction.WEST);
        }
      }
      Location loc = new Location(dung.getPlayerLocation().getX(),
              dung.getPlayerLocation().getY() + 1);
      if (dung.getPlayerLocation().getY() + 1 < dung.getHeight()) {
        if (dung.getCave(loc).getMonster() != null && !dung.getCave(loc).getMonster().isDead()) {
          dung.shoot(1, Direction.SOUTH);
          dung.shoot(1, Direction.SOUTH);
        }
      }
      dung.movePlayer(Direction.SOUTH);
      moving_east = !moving_east;
    }
    assertEquals(new Location(0, 5), dung.getPlayerLocation());
    double val = ((double) count / 32);
    assertEquals(.25, val, .0001);
    assertEquals(32 / 4, count);
  }

  /**
   * Collect Treasure and prove percentage.
   * In a non wrapping dungeon with 32 caves and full interconnectivity we have 21
   * valid caves that can hold treasure 100 % of 32 is 32, so we expect that in
   * return.
   */
  @Test
  public void correctTreasurePercent2() {
    for (int z = 0; z < 500; z++) {
      Dungeon dung = dungeon(6, 6, 25, false, 100, "Jack", true, 1);
      while (dung.getPlayerLocation().getX() != 0) {
        Location loc = new Location(dung.getPlayerLocation().getX() - 1,
                dung.getPlayerLocation().getY());
        if (loc.equals(dung.getEnd())) {
          dung.shoot(1, Direction.WEST);
          dung.shoot(1, Direction.WEST);
        }
        dung.movePlayer(Direction.WEST);
      }
      while (dung.getPlayerLocation().getY() != 0) {
        Location loc = new Location(dung.getPlayerLocation().getX(),
                dung.getPlayerLocation().getY() - 1);
        if (loc.equals(dung.getEnd())) {
          dung.shoot(1, Direction.NORTH);
          dung.shoot(1, Direction.NORTH);
        }
        dung.movePlayer(Direction.NORTH);
      }
      int count = 0;
      boolean moving_east = true;
      for (int y = 0; y < dung.getHeight(); y++) {
        for (int x = 0; x < dung.getWidth(); x++) {
          Description description = dung.getPlayerDescription();
          if (!description.getCaveItems().contains("Diamonds: 0")
                  || !description.getCaveItems().contains("Rubies: 0")
                  || !description.getCaveItems().contains("Sapphires: 0")) {
            count++;
          }

          if (moving_east) {
            if (dung.getPlayerLocation().getX() + 1 < dung.getWidth()) {
              Location loc = new Location(dung.getPlayerLocation().getX() + 1,
                      dung.getPlayerLocation().getY());
              if (dung.getCave(loc).getMonster() != null
                      && !dung.getCave(loc).getMonster().isDead()) {
                dung.shoot(1, Direction.EAST);
                dung.shoot(1, Direction.EAST);
              }
            }
            dung.movePlayer(Direction.EAST);
          } else {
            if (dung.getPlayerLocation().getX() - 1 > -1) {
              Location loc = new Location(dung.getPlayerLocation().getX() - 1,
                      dung.getPlayerLocation().getY());
              if (dung.getCave(loc).getMonster() != null
                      && !dung.getCave(loc).getMonster().isDead()) {
                dung.shoot(1, Direction.WEST);
                dung.shoot(1, Direction.WEST);
              }
            }
            dung.movePlayer(Direction.WEST);
          }
        }
        Location loc = new Location(dung.getPlayerLocation().getX(),
                dung.getPlayerLocation().getY() + 1);
        if (dung.getPlayerLocation().getY() + 1 < dung.getHeight()) {
          if (dung.getCave(loc).getMonster() != null && !dung.getCave(loc).getMonster().isDead()) {
            dung.shoot(1, Direction.SOUTH);
            dung.shoot(1, Direction.SOUTH);
          }
        }
        dung.movePlayer(Direction.SOUTH);
        moving_east = !moving_east;
      }
      assertEquals(new Location(0, 5), dung.getPlayerLocation());
      assertEquals(32, count);
    }
  }

  /**
   * Collect Treasure and prove percentage.
   * In a non wrapping dungeon with 32 caves and full interconnectivity we have 21
   * valid caves that can hold treasure 0 % of 32 is about 0, so we expect that in
   * return.
   */
  @Test
  public void correctTreasurePercent3() {
    Dungeon dung = dungeon(6, 6, 25, false, 0, "Jack", true, 1);

    while (dung.getPlayerLocation().getX() != 0) {
      Location loc = new Location(dung.getPlayerLocation().getX() - 1,
              dung.getPlayerLocation().getY());
      if (loc.equals(dung.getEnd())) {
        dung.shoot(1, Direction.WEST);
        dung.shoot(1, Direction.WEST);
      }
      dung.movePlayer(Direction.WEST);
    }
    while (dung.getPlayerLocation().getY() != 0) {
      Location loc = new Location(dung.getPlayerLocation().getX(),
              dung.getPlayerLocation().getY() - 1);
      if (loc.equals(dung.getEnd())) {
        dung.shoot(1, Direction.NORTH);
        dung.shoot(1, Direction.NORTH);
      }
      dung.movePlayer(Direction.NORTH);
    }
    int count = 0;
    boolean moving_east = true;
    for (int y = 0; y < dung.getHeight(); y++) {
      for (int x = 0; x < dung.getWidth(); x++) {
        if (dung.search()) {
          count++;
        }
        if (moving_east) {
          Location loc = new Location(dung.getPlayerLocation().getX() + 1,
                  dung.getPlayerLocation().getY());
          if (dung.getPlayerLocation().getX() + 1 < dung.getWidth()) {
            if (dung.getCave(loc).getMonster() != null
                    && !dung.getCave(loc).getMonster().isDead()) {
              dung.shoot(1, Direction.EAST);
              dung.shoot(1, Direction.EAST);
            }
          }

          dung.movePlayer(Direction.EAST);
        } else {
          if (dung.getPlayerLocation().getX() - 1 >= 0) {
            Location loc = new Location(dung.getPlayerLocation().getX() - 1,
                    dung.getPlayerLocation().getY());
            if (dung.getCave(loc).getMonster() != null
                    && !dung.getCave(loc).getMonster().isDead()) {
              dung.shoot(1, Direction.WEST);
              dung.shoot(1, Direction.WEST);
            }
          }
          dung.movePlayer(Direction.WEST);
        }
      }
      Location loc = new Location(dung.getPlayerLocation().getX(),
              dung.getPlayerLocation().getY() + 1);
      if (dung.getPlayerLocation().getY() + 1 < dung.getHeight()) {
        if (dung.getCave(loc).getMonster() != null && !dung.getCave(loc).getMonster().isDead()) {
          dung.shoot(1, Direction.SOUTH);
          dung.shoot(1, Direction.SOUTH);
        }
      }

      dung.movePlayer(Direction.SOUTH);
      moving_east = !moving_east;
    }
    assertEquals(new Location(0, 5), dung.getPlayerLocation());
    assertEquals(0, count);
  }


  /**
   * Solving the Dungeon.
   */
  @Test
  public void solvedIt() {
    Dungeon dung = dungeon(6, 6, 25, false, 25, "Jack", false, 1);
    assertEquals(new Location(0, 0), dung.getPlayerLocation());

    dung.movePlayer(Direction.SOUTH);
    assertEquals(new Location(0, 1), dung.getPlayerLocation());
    dung.movePlayer(Direction.SOUTH);
    assertEquals(new Location(0, 2), dung.getPlayerLocation());

    dung.movePlayer(Direction.SOUTH);
    assertEquals(new Location(0, 3), dung.getPlayerLocation());

    dung.movePlayer(Direction.EAST);
    assertEquals(new Location(1, 3), dung.getPlayerLocation());

    dung.movePlayer(Direction.EAST);
    assertEquals(new Location(2, 3), dung.getPlayerLocation());


    dung.shoot(1, Direction.EAST);
    dung.shoot(1, Direction.EAST);
    dung.movePlayer(Direction.EAST);
    assertEquals(new Location(3, 3), dung.getPlayerLocation());

    assertTrue(dung.hasSolved());
    assertTrue(dung.movePlayer(Direction.EAST));
    assertTrue(dung.movePlayer(Direction.SOUTH));
    assertEquals(new Location(4, 4), dung.getPlayerLocation());
    assertFalse(dung.hasSolved());
  }

  /**
   * Solving the Dungeon.
   */
  @Test
  public void solvedIt2() {
    Dungeon dung = new DungeonImpl(6, 6, 37,
            true, 25, "Jack", false, 1);
    assertEquals(new Location(0, 0), dung.getPlayerLocation());

    dung.movePlayer(Direction.WEST);
    assertEquals(new Location(5, 0), dung.getPlayerLocation());

    dung.movePlayer(Direction.NORTH);
    assertEquals(new Location(5, 5), dung.getPlayerLocation());

    dung.movePlayer(Direction.WEST);
    assertEquals(new Location(4, 5), dung.getPlayerLocation());

    dung.movePlayer(Direction.NORTH);
    assertEquals(new Location(4, 4), dung.getPlayerLocation());

    dung.movePlayer(Direction.WEST);
    assertEquals(new Location(3, 4), dung.getPlayerLocation());

    dung.shoot(1, Direction.NORTH);
    dung.shoot(1, Direction.NORTH);
    dung.movePlayer(Direction.NORTH);
    assertEquals(new Location(3, 3), dung.getPlayerLocation());
    assertTrue(dung.hasSolved());

    dung.movePlayer(Direction.NORTH);
    assertFalse(dung.hasSolved());
  }

  /**
   * testing getting the cave.
   */
  @Test
  public void getCaveTest() {
    Cave myCave = dungeonWrap.getCave(new Location(1, 3));
    assertEquals(new Location(1, 3), myCave.getLocation());

    myCave.addTreasure(CaveObject.DIAMOND);
    myCave.addTreasure(CaveObject.RUBY);
    myCave.addTreasure(CaveObject.SAPPHIRE);

    Cave check = dungeonWrap.getCave(new Location(1, 3));
    assertNotEquals(check.getItems().get(CaveObject.DIAMOND),
            myCave.getItems().get(CaveObject.DIAMOND));
    assertNotEquals(check.getItems().get(CaveObject.RUBY),
            myCave.getItems().get(CaveObject.RUBY));
    assertNotEquals(check.getItems().get(CaveObject.SAPPHIRE),
            myCave.getItems().get(CaveObject.SAPPHIRE));
  }

  /**
   * Making sure start and end are not tunnels.
   */
  @Test
  public void NotTunnelTest() {
    Dungeon dung1;
    Dungeon dung2;
    for (int x = 0; x < 800; x++) {
      dung1 = new DungeonImpl(6, 6, 10,
              true, 100, "Jack", true, 1);

      dung2 = new DungeonImpl(6, 6, 15,
              false, 100, "Jack", true, 1);

      Cave dung1Start = dung1.getCave(dung1.getStart());
      assertNotEquals(2, dung1Start.getDirections().size());
      Cave dung2Start = dung2.getCave(dung2.getStart());
      assertNotEquals(2, dung2Start.getDirections().size());
      Cave dung1End = dung1.getCave(dung1.getEnd());
      assertNotEquals(2, dung1End.getDirections().size());
      Cave dung2End = dung2.getCave(dung2.getEnd());
      assertNotEquals(2, dung2End.getDirections().size());
    }
  }

  /**
   * driver.Test that arrows are added accordingly.
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
   * driver.Test that arrows are added accordingly.
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
   * driver.Test that arrows are added accordingly.
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
   * driver.Test that arrows are added accordingly.
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
   * driver.Test monster at the end.
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
   * driver.Test monster at the end.
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
   * driver.Test monster at the end.
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
   * driver.Test to make sure a player dies correctly.
   */
  @Test
  public void dying() {
    Dungeon dung = dungeon(6, 6, 25, false, 33,
            "jack", false, 1);

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
   * Out of shot directly into a wall west.
   */
  @Test(expected = IllegalArgumentException.class)
  public void shootingIntoWall() {
    Dungeon dung = dungeon(6, 6, 25, false, 10, "jack", false, 1);
    dung.shoot(1, Direction.WEST);

  }

  /**
   * Out of shot directly into a south.
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
}







