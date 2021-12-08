package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.enums.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for the edge class.
 */
public class EdgeTest {

  private model.Edge myEdge;

  /**
   *Sets up a EDge Object for all the tests.
   */
  @Before
  public void setUp() {
    model.Cave cave1 = new model.CaveImpl(new model.Location(0,0));
    model.Cave cave2 = new model.CaveImpl(new model.Location(1,0));
    myEdge = new model.Edge(cave1, cave2,Direction.WEST, Direction.EAST);
  }

  private model.Edge edge(model.Cave cave1, model.Cave cave2, Direction dir1, Direction dir2) {
    return new model.Edge(cave1, cave2, dir1, dir2);
  }

  /**
   * Null cave1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate1() {
    edge(null, new model.CaveImpl(new model.Location(0,2)), Direction.NORTH, Direction.SOUTH);
  }

  /**
   * Null cave2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate2() {
    edge(new model.CaveImpl(new model.Location(0,2)), null, Direction.NORTH, Direction.SOUTH);
  }

  /**
   * Null dir1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate3() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            null, Direction.SOUTH);
  }

  /**
   * Null dir2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate4() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.NORTH, null);
  }

  /**
   * Caves same location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate5() {
    edge(new model.CaveImpl(new model.Location(1,1)), new model.CaveImpl(new model.Location(1,1)),
            Direction.NORTH, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate6() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.EAST, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate7() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.EAST, Direction.NORTH);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate8() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.WEST, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate9() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.WEST, Direction.NORTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate10() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.NORTH, Direction.WEST);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate11() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.NORTH, Direction.EAST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate12() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.SOUTH, Direction.EAST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate13() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.SOUTH, Direction.WEST);
  }


  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate14() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.SOUTH, Direction.SOUTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate15() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.WEST, Direction.WEST);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate16() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.NORTH, Direction.NORTH);
  }

  /**
   * offset directions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badCreate17() {
    edge(new model.CaveImpl(new model.Location(0,2)), new model.CaveImpl(new model.Location(1,1)),
            Direction.EAST, Direction.EAST);
  }

  /**
   * Testing the getters.
   */
  @Test
  public void gettersTest() {
    assertEquals(new model.Location(0,0), myEdge.getCave1().getLocation());
    assertEquals(new model.Location(1,0), myEdge.getCave2().getLocation());
    assertEquals(Direction.WEST, myEdge.getDir1());
    assertEquals(Direction.EAST, myEdge.getDir2());

  }

  /**
   * Testing the equals and hashcode.
   */
  @Test
  public void equalsAndHash() {
    model.Cave cave1 = new model.CaveImpl(new model.Location(0,0));
    model.Cave cave2 = new model.CaveImpl(new model.Location(1,0));
    model.Edge myEdge2 = edge(cave2, cave1,Direction.EAST, Direction.WEST);
    model.Edge myEdge3 = edge(cave1, cave2,Direction.WEST, Direction.EAST);
    assertEquals(myEdge, myEdge2);
    assertEquals(myEdge2, myEdge);
    assertEquals(myEdge, myEdge3);
    assertEquals(myEdge3, myEdge);

    assertEquals(myEdge.hashCode(), myEdge3.hashCode());
    assertEquals(myEdge3.hashCode(),myEdge.hashCode());

  }

  /**
   * Test that hasA and hasBoth works correctly.
   */
  @Test
  public void hasBothAndHasA() {
    List<model.Edge> edges = new ArrayList<>();
    model.Cave cave1 = new model.CaveImpl(new model.Location(0,0));
    model.Cave cave2 = new model.CaveImpl(new model.Location(1,0));
    model.Cave cave3 = new model.CaveImpl(new model.Location(1,1));
    model.Cave cave4 = new model.CaveImpl(new model.Location(0,1));
    model.Cave cave5 = new model.CaveImpl(new model.Location(0,2));
    model.Cave cave6 = new model.CaveImpl(new model.Location(2,1));
    model.Cave cave7 = new model.CaveImpl(new model.Location(2,3));
    Cave cave8 = new CaveImpl(new Location(3,3));
    model.Edge edge1 = edge(cave1,cave3, Direction.NORTH, Direction.SOUTH);
    model.Edge edge2 = edge(cave1,cave4, Direction.EAST, Direction.WEST);
    model.Edge edge3 = edge(cave1,cave5, Direction.NORTH, Direction.SOUTH);
    model.Edge edge4 = edge(cave1,cave6, Direction.EAST, Direction.WEST);
    model.Edge edge5 = edge(cave2,cave3, Direction.NORTH, Direction.SOUTH);
    model.Edge edge6 = edge(cave2,cave5, Direction.SOUTH, Direction.NORTH);
    model.Edge edge7 = edge(cave2,cave6, Direction.EAST, Direction.WEST);
    model.Edge edge8 = edge(cave2,cave3, Direction.EAST, Direction.WEST);

    edges.add(edge1);
    edges.add(edge2);
    edges.add(edge3);
    edges.add(edge4);
    edges.add(edge5);
    edges.add(edge6);
    edges.add(edge7);
    edges.add(edge8);

    model.Edge edge9 = edge(cave6, cave7,Direction.NORTH,Direction.SOUTH);
    model.Edge edge10 = edge(cave5, cave6,Direction.NORTH,Direction.SOUTH);
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