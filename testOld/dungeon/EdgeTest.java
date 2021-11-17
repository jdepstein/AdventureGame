package dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.enums.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * driver.Test for the edge class.
 */
public class EdgeTest {

  private Edge myEdge;

  /**
   *Sets up a EDge Object for all the tests.
   */
  @Before
  public void setUp() {
    Cave cave1 = new CaveImpl(new Location(0,0));
    Cave cave2 = new CaveImpl(new Location(1,0));
    myEdge = new Edge(cave1, cave2,Direction.WEST, Direction.EAST);
  }

  private Edge edge(Cave cave1, Cave cave2,Direction dir1, Direction dir2) {
    return new Edge(cave1, cave2, dir1, dir2);
  }

  /**
   * Null cave1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate1() {
    edge(null, new CaveImpl(new Location(0,2)), Direction.NORTH, Direction.SOUTH);
  }

  /**
   * Null cave2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate2() {
    edge(new CaveImpl(new Location(0,2)), null, Direction.NORTH, Direction.SOUTH);
  }

  /**
   * Null dir1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate3() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            null, Direction.SOUTH);
  }

  /**
   * Null dir2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate4() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.NORTH, null);
  }

  /**
   * Caves same location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate5() {
    edge(new CaveImpl(new Location(1,1)), new CaveImpl(new Location(1,1)),
            Direction.NORTH, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate6() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.EAST, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate7() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.EAST, Direction.NORTH);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate8() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.WEST, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate9() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.WEST, Direction.NORTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate10() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.NORTH, Direction.WEST);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate11() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.NORTH, Direction.EAST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate12() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.SOUTH, Direction.EAST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate13() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.SOUTH, Direction.WEST);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate14() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.SOUTH, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate15() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.WEST, Direction.WEST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate16() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.NORTH, Direction.NORTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate17() {
    edge(new CaveImpl(new Location(0,2)), new CaveImpl(new Location(1,1)),
            Direction.EAST, Direction.EAST);
  }

  /**
   * Testing the getters.
   */
  @Test
  public void gettersTest() {
    assertEquals(new Location(0,0), myEdge.getCave1().getLocation());
    assertEquals(new Location(1,0), myEdge.getCave2().getLocation());
    assertEquals(Direction.WEST, myEdge.getDir1());
    assertEquals(Direction.EAST, myEdge.getDir2());

  }

  /**
   * Testing the equals and hashcode.
   */
  @Test
  public void equalsAndHash() {
    Cave cave1 = new CaveImpl(new Location(0,0));
    Cave cave2 = new CaveImpl(new Location(1,0));
    Edge myEdge2 = edge(cave2, cave1,Direction.EAST, Direction.WEST);
    Edge myEdge3 = edge(cave1, cave2,Direction.WEST, Direction.EAST);
    assertEquals(myEdge, myEdge2);
    assertEquals(myEdge2, myEdge);
    assertEquals(myEdge, myEdge3);
    assertEquals(myEdge3, myEdge);

    assertEquals(myEdge.hashCode(), myEdge3.hashCode());
    assertEquals(myEdge3.hashCode(),myEdge.hashCode());

  }

  /**
   * driver.Test that hasA and hasBoth works correctly.
   */
  @Test
  public void hasBothAndHasA() {
    List<Edge> edges = new ArrayList<>();
    Cave cave1 = new CaveImpl(new Location(0,0));
    Cave cave2 = new CaveImpl(new Location(1,0));
    Cave cave3 = new CaveImpl(new Location(1,1));
    Cave cave4 = new CaveImpl(new Location(0,1));
    Cave cave5 = new CaveImpl(new Location(0,2));
    Cave cave6 = new CaveImpl(new Location(2,1));
    Cave cave7 = new CaveImpl(new Location(2,3));
    Cave cave8 = new CaveImpl(new Location(3,3));
    Edge edge1 = edge(cave1,cave3, Direction.NORTH, Direction.SOUTH);
    Edge edge2 = edge(cave1,cave4, Direction.EAST, Direction.WEST);
    Edge edge3 = edge(cave1,cave5, Direction.NORTH, Direction.SOUTH);
    Edge edge4 = edge(cave1,cave6, Direction.EAST, Direction.WEST);
    Edge edge5 = edge(cave2,cave3, Direction.NORTH, Direction.SOUTH);
    Edge edge6 = edge(cave2,cave5, Direction.SOUTH, Direction.NORTH);
    Edge edge7 = edge(cave2,cave6, Direction.EAST, Direction.WEST);
    Edge edge8 = edge(cave2,cave3, Direction.EAST, Direction.WEST);

    edges.add(edge1);
    edges.add(edge2);
    edges.add(edge3);
    edges.add(edge4);
    edges.add(edge5);
    edges.add(edge6);
    edges.add(edge7);
    edges.add(edge8);

    Edge edge9 = edge(cave6, cave7,Direction.NORTH,Direction.SOUTH);
    Edge edge10 = edge(cave5, cave6,Direction.NORTH,Direction.SOUTH);
    Edge edge11 = edge(cave8 ,cave7,Direction.WEST,Direction.EAST);
    assertFalse(edge9.hasBoth(edges));
    assertFalse(edge11.hasBoth(edges));
    assertTrue(edge10.hasBoth(edges));
    assertTrue(myEdge.hasBoth(edges));


    assertTrue(myEdge.hasA(edges));
    assertTrue(edge10.hasA(edges));
    assertTrue(edge9.hasA(edges));
    assertFalse(edge11.hasA(edges));
  }


}