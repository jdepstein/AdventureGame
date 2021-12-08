package model;

import static org.junit.Assert.assertEquals;

import model.enums.Direction;
import org.junit.Test;

import java.util.Map;

/**
 * Test for the CaveImpl Class.
 */
public class CaveTestP5 {



  @Test
  public void connectionsStringTest() {
    Cave cave = new CaveImpl(new Location(0,0));
    cave.addConnection(Direction.NORTH, new Location(1,0));
    assertEquals("N", cave.getCon());
    cave.addConnection(Direction.EAST, new Location(1,1));
    assertEquals("NE", cave.getCon());
    cave.addConnection(Direction.SOUTH, new Location(1,2));
    assertEquals("NSE", cave.getCon());
    cave.addConnection(Direction.WEST, new Location(3,2));
    assertEquals("NSEW", cave.getCon());

    cave = new CaveImpl(new Location(0,0));
    cave.addConnection(Direction.EAST, new Location(1,1));
    assertEquals("E", cave.getCon());
    cave.addConnection(Direction.SOUTH, new Location(1,2));
    assertEquals("SE", cave.getCon());
    cave.addConnection(Direction.WEST, new Location(3,2));
    assertEquals("SEW", cave.getCon());
    cave.addConnection(Direction.NORTH, new Location(1,0));
    assertEquals("NSEW", cave.getCon());
  }

}
