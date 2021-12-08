import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import adventuregame.AdvController;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.ReadOnlyDungeon;
import dungeon.enums.Direction;
import org.junit.Before;
import org.junit.Test;
import view.Features;
import view.IView;


/**
 * Test for the features Controller.
 */
public class FeaturesTest {
  private Features controller;
  private Dungeon model;
  private IView view;

  /**
   * Set up the features controller and set all the fields.
   */
  @Before
  public void setUp() {
    model = new DungeonImpl(6,6,25,false, 100,
            "Jack",false, 1);
    controller = new AdvController(model);
    view = new MockView();
    controller.setView(view);
  }

  /**
   * Testing the move works as intended with a regular direction.
   */
  @Test
  public void moveTest() {
    assertEquals(new Location(0, 0), model.getPlayerLocation());
    assertEquals("Successfully Moved SOUTH", controller.move(Direction.SOUTH));
    assertEquals(new Location(0, 1), model.getPlayerLocation());
    assertEquals("Successfully Moved EAST", controller.move(Direction.EAST));
    assertEquals(new Location(1, 1), model.getPlayerLocation());
    assertEquals("Successfully Moved NORTH", controller.move(Direction.NORTH));
    assertEquals(new Location(1, 0), model.getPlayerLocation());
    assertEquals("Successfully Moved WEST", controller.move(Direction.WEST));
    assertEquals(new Location(0, 0), model.getPlayerLocation());

    assertEquals("Not A Valid Move", controller.move(Direction.WEST));
    assertEquals("Not A Valid Move", controller.move(Direction.NORTH));
    assertEquals(new Location(0, 0), model.getPlayerLocation());
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    assertEquals(new Location(5, 0), model.getPlayerLocation());
    assertEquals("Not A Valid Move", controller.move(Direction.EAST));
    assertEquals(new Location(5, 0), model.getPlayerLocation());

    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    assertEquals(new Location(5, 5), model.getPlayerLocation());
    assertEquals("Not A Valid Move", controller.move(Direction.SOUTH));
    assertEquals(new Location(5, 5), model.getPlayerLocation());
  }

  /**
   * Testing the move works as intended with a coordinate entered as the move spot.
   */
  @Test
  public void clickMoveTest() {
    assertEquals("Successfully Moved EAST",controller.clickMove(1,0));
    assertEquals(new Location(1,0), model.getPlayerLocation());
    assertEquals("Successfully Moved SOUTH", controller.clickMove(1,1));
    assertEquals(new Location(1,1), model.getPlayerLocation());
    assertEquals("Successfully Moved WEST", controller.clickMove(0,1));
    assertEquals(new Location(0,1), model.getPlayerLocation());
    assertEquals("Successfully Moved NORTH", controller.clickMove(0,0));
    assertEquals(new Location(0,0), model.getPlayerLocation());
    assertEquals("Not A Valid Move", controller.clickMove(1,1));
    assertEquals("Not A Valid Move", controller.clickMove(5,0));
    assertEquals("Not A Valid Move", controller.clickMove(2,3));
    assertEquals("Not A Valid Move", controller.clickMove(4,2));
    assertEquals("Not A Valid Move", controller.clickMove(1,2));
    assertEquals("Not A Valid Move", controller.clickMove(2,1));
    assertEquals("Not A Valid Move", controller.clickMove(0,5));
  }

  /**
   * Testing the pickup Works as it should.
   */
  @Test
  public void pickUpTest() {
    //Make sure we are in a full cave not a tunnel
    assertEquals("Successfully Moved SOUTH", controller.move(Direction.SOUTH));
    ReadOnlyDungeon info = model.makeReadOnly();
    int pDiamonds = info.playerDiamond();
    int pSapphire = info.playerSapphire();
    int pRuby = info.playerRuby();
    int pArrow = info.playerArrow();

    int cDiamonds = info.caveDiamond();
    int cSapphire = info.caveSapphire();
    int cRuby = info.caveRuby();
    int cArrow = info.caveArrow();

    assertEquals("Found Some items in the Cave", controller.pickup());
    assertEquals(info.playerDiamond(), pDiamonds + cDiamonds);
    assertEquals(info.playerSapphire(), pSapphire + cSapphire);
    assertEquals(info.playerRuby(), pRuby + cRuby);
    assertEquals(info.playerArrow(), pArrow + cArrow);

    assertEquals(0, info.caveDiamond());
    assertEquals(0, info.caveSapphire());
    assertEquals(0, info.caveRuby());
    assertEquals(0, info.caveArrow());
    assertEquals("There was Nothing to Find", controller.pickup());
  }

  /**
   * Testing shooting works as it should in each direction.
   */
  @Test
  public void shootTest() {
    assertEquals("Tried to shoot an arrow directly into a wall",
            controller.shoot(1, Direction.NORTH));
    assertEquals("Tried to shoot an arrow directly into a wall",
            controller.shoot(1, Direction.WEST));
    assertEquals("Invalid distance to shoot 6",
            controller.shoot(6, Direction.EAST));
    assertEquals("Invalid distance to shoot 0",
            controller.shoot(0, Direction.SOUTH));
    assertEquals("Invalid distance to shoot -2",
            controller.shoot(-2, Direction.EAST));

    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    assertEquals("Heard a Noise in distance",
            controller.shoot(1, Direction.EAST));
    assertEquals("Arrow shot into darkness",
            controller.shoot(1, Direction.NORTH));
    assertEquals("Heard a Noise in distance",
            controller.shoot(1, Direction.EAST));
    assertEquals("You have no arrows to shoot",
            controller.shoot(1, Direction.EAST));
  }

  /**
   * Testing getting killed.
   */
  @Test
  public void endGameDead() {
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    assertTrue(model.hasLost());
    assertEquals("Can't shoot when you're dead",
            controller.shoot(1, Direction.EAST));
    assertEquals("Can't move when you're dead",
            controller.move(Direction.EAST));
    assertEquals("Can't search when you're dead",
            controller.pickup());
    assertEquals("Can't move when you're dead",
            controller.clickMove(3,2));
  }

  /**
   * Testing winning.
   */
  @Test
  public void endGameWon() {
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.SOUTH);
    controller.move(Direction.EAST);
    controller.move(Direction.EAST);
    controller.shoot(1, Direction.EAST);
    controller.shoot(1, Direction.EAST);
    controller.move(Direction.EAST);
    assertTrue(model.hasSolved());
    assertEquals("Game is over no shooting to be done",
            controller.shoot(1, Direction.EAST));
    assertEquals("Game is over no more moves to be made",
            controller.move(Direction.EAST));
    assertEquals("Game is over no picking up",
            controller.pickup());
  }

  /**
   * Move testing through a wrap Directionally and coordinate.
   */
  @Test
  public void moveWrap() {
    model = new DungeonImpl(6,6,37,true, 100,
            "Jack",false, 1);
    controller = new AdvController(model);
    view = new MockView();
    controller.setView(view);
    assertEquals(new Location(0, 0), model.getPlayerLocation());
    assertEquals("Successfully Moved NORTH", controller.move(Direction.NORTH));
    assertEquals(new Location(0, 5), model.getPlayerLocation());
    assertEquals("Successfully Moved WEST", controller.move(Direction.WEST));
    assertEquals(new Location(5, 5), model.getPlayerLocation());
    assertEquals("Successfully Moved SOUTH", controller.move(Direction.SOUTH));
    assertEquals(new Location(5, 0), model.getPlayerLocation());
    assertEquals("Successfully Moved EAST", controller.move(Direction.EAST));
    assertEquals(new Location(0, 0), model.getPlayerLocation());

    assertEquals("Successfully Moved WEST",controller.clickMove(5,0));
    assertEquals(new Location(5,0), model.getPlayerLocation());
    assertEquals("Successfully Moved NORTH", controller.clickMove(5,5));
    assertEquals(new Location(5,5), model.getPlayerLocation());
    assertEquals("Successfully Moved EAST", controller.clickMove(0,5));
    assertEquals(new Location(0,5), model.getPlayerLocation());
    assertEquals("Successfully Moved SOUTH", controller.clickMove(0,0));
    assertEquals(new Location(0,0), model.getPlayerLocation());
  }

  /**
   * Testing the reset works as it should.
   */
  @Test
  public void reset() {
    for (int x = 0; x < 500; x++) {
      model = new DungeonImpl(6, 6, 37, true, 100,
            "Jack", true, 1);
      controller = new AdvController(model);
      view = new MockView();
      controller.setView(view);
      ReadOnlyDungeon info = model.makeReadOnly();
      Location initialLocation = info.getPlayerLoc();
      int cDiamonds = info.caveDiamond();
      int cSapphire = info.caveSapphire();
      int cRuby = info.caveRuby();
      int cArrow = info.caveArrow();
      controller.pickup();
      model.movePlayer(Direction.SOUTH);
      controller.pickup();
      model.movePlayer(Direction.EAST);
      controller.pickup();
      model.movePlayer(Direction.EAST);
      assertNotEquals(model.getPlayerLocation(), initialLocation);
      assertFalse(info.playerDiamond() == 0 && info.playerRuby() == 0
              && info.playerSapphire() == 0);
      assertNotEquals(3, info.playerArrow());
      assertEquals(4, info.getVisits().size());
      controller.restart();
      assertEquals(info.getPlayerLoc(), initialLocation);
      assertEquals(0, info.playerDiamond());
      assertEquals(0, info.playerSapphire());
      assertEquals(0, info.playerRuby());
      assertEquals(3, info.playerArrow());

      assertEquals(cDiamonds, info.caveDiamond());
      assertEquals(cSapphire, info.caveSapphire());
      assertEquals(cRuby, info.caveRuby());
      assertEquals(cArrow, info.caveArrow());
      assertEquals(1, info.getVisits().size());
    }
  }

  /**
   * Testing the escaping mechanism.
   */
  @Test
  public void escape() {
    int counter = 0;
    for (int x = 0; x < 500; x++) {
      controller.restart();
      controller.move(Direction.SOUTH);
      controller.move(Direction.SOUTH);
      controller.move(Direction.SOUTH);
      controller.move(Direction.EAST);
      controller.move(Direction.EAST);
      controller.shoot(1, Direction.EAST);
      String t = controller.move(Direction.EAST);
      if (t.equals("Successfully Escaped A Otylph")) {
        counter++;
      }
    }
    assertEquals(.5, (double) counter / 500, .1);
  }

  /**
   * Setting char test.
   */
  @Test
  public void setCharTest() {
    assertEquals(0, controller.lastHit());
    controller.setLast('c');
    assertEquals('c', controller.lastHit());
  }

  /**
   * Setting char test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate() {
    new AdvController(null);
  }

  /**
   * Setting char test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badSet() {
    this.controller.setView(null);
  }

}

