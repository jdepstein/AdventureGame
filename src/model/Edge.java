package model;

import model.enums.Direction;

import java.util.List;
import java.util.Objects;

/**
 * An  Edge is a possible connection between two caves within a dungeon and the direction that
 * you would have to travel from each cave to reach the other.
 */
public class Edge {
  model.Cave cave1;
  model.Cave cave2;
  model.enums.Direction dir1;
  model.enums.Direction dir2;

  /**
   * This builds an edge with the two given caves and the two given directions.
   * @param one the first Cave in the edge
   * @param two the second Cave in the edge
   * @param dir1 the direction from cave 1 to two
   * @param dir2 the direction from cave to tone
   * @throws IllegalArgumentException if null is passed for any value
   * @throws IllegalArgumentException if you pass North for one but South not for the other
   * @throws IllegalArgumentException fi you pass East for one but not East for the other
   */
  public Edge(model.Cave one, model.Cave two, model.enums.Direction dir1 , model.enums.Direction dir2) {
    if (one == null || two == null || dir1 == null || dir2 == null) {
      throw new IllegalArgumentException(
              "Null value passed");
    }

    if (dir1 == model.enums.Direction.NORTH && dir2 != model.enums.Direction.SOUTH) {
      throw new IllegalArgumentException(
              "Passed North for one but didn't pass South as the other");
    }

    if (dir1 == model.enums.Direction.EAST && dir2 != model.enums.Direction.WEST) {
      throw new IllegalArgumentException(
              "Passed East for one but didn't pass West as the other");
    }

    if (dir1 == model.enums.Direction.SOUTH && dir2 != model.enums.Direction.NORTH) {
      throw new IllegalArgumentException(
              "Passed North for one but didn't pass South as the other");
    }

    if (dir1 == model.enums.Direction.WEST && dir2 != model.enums.Direction.EAST) {
      throw new IllegalArgumentException(
              "Passed East for one but didn't pass West as the other");
    }

    if (one.sameLocation(two)) {
      throw new IllegalArgumentException(
              "Passed Caves with the same location");
    }

    cave1 = one;
    cave2 = two;
    this.dir1 = dir1;
    this.dir2 = dir2;
  }

  /**
   * Checks to see if one of the caves of this is within the list edges.
   * @param edges the list of edges being checked
   * @return boolean if we find one of the caves
   */
  public boolean hasA(List<Edge> edges) {
    for (Edge e : edges) {
      if (e.cave1.sameLocation(this.cave1) || e.cave2.sameLocation(this.cave1)) {
        return true;
      }
      if (e.cave1.sameLocation(this.cave2) || e.cave2.sameLocation(this.cave2)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks to see if both of the caves of this is within the list edges.
   * @param edges the list of edges being checked
   * @return boolean if we find both of the caves
   */
  public boolean hasBoth(List<Edge> edges) {
    boolean one = false;
    boolean two = false;
    for (Edge e : edges) {
      if (e.cave1.sameLocation(this.cave1) || e.cave2.sameLocation(this.cave1)) {
        one = true;
      }

      if (e.cave1.sameLocation(this.cave2) || e.cave2.sameLocation(this.cave2)) {
        two = true;
      }
    }
    return one && two;
  }

  /**
   * Gets the first cave in the Edge.
   * @return the cave1
   */
  public model.Cave getCave1() {
    return cave1;
  }

  /**
   * Gets the second cave in the Edge.
   * @return the cave2
   */
  public Cave getCave2() {
    return cave2;
  }

  /**
   * Gets the first caves direction to cave 2.
   * @return the dir 1
   */
  public model.enums.Direction getDir1() {
    return dir1;
  }

  /**
   * Gets the second caves' direction to cave one.
   * @return the dir 2
   */
  public Direction getDir2() {
    return dir2;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.cave1.getLocation(), this.cave2.getLocation());
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Edge) {
      Edge e = (Edge) other;
      if (this.cave1.sameLocation(e.cave1) && this.cave2.sameLocation(e.cave2)) {
        return true;
      }
      return this.cave1.sameLocation(e.cave2) && this.cave2.sameLocation(e.cave1);
    }
    return false;
  }
}

