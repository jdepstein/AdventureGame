package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Location is used to represent the (x,y) location within the dungeon grid. Locations are used
 * for Caves as well as players. All it requires is the x,y values for within dungeon gird.
 */
public class Location {
  private final int x;
  private final int y;

  /**
   * Makes a new Location Object with the given x and y values a location will never have negative
   * x or y for its values.
   * @param x The x value.
   * @param y The y value
   * @throws IllegalArgumentException If either x or y is passed in as a negative value.
   */
  public Location(int x, int y) throws IllegalArgumentException {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException(
              "Can't be Negative for Location");
    }
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x values for the location.
   * @return The x value
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y values for the location.
   * @return The y value
   */
  public int getY() {
    return this.y;
  }

  /**
   * Gets the Distance between two separate locations since travel diagonally is not aloud
   * Distance is calculated by adding the difference in y  + the difference in x.
   * @return the distance between the two locations
   */
  public int getDistance(Location other) {
    int difX = Math.abs(other.getX() - this.x);
    int difY = Math.abs(other.getY() - this.y);
    return difX + difY;
  }

  /**
   * Get the minimum wrapping distance between two locations given the max value
   * x can be and the max value y can be.
   * @param other The location distance that is being found
   * @param maxX The max x value
   * @param maxY The max y value
   * @return the integer representing the minimum wrapping distance
   */
  public int wrappingDistance(Location other, int maxX, int maxY) {
    int xZeroDist = this.getDistance(new Location(0, this.y)) + 1;
    int xMaxDist = this.getDistance(new Location(maxX, this.y)) + 1;
    int yZeroDist = this.getDistance(new Location(this.x, 0)) + 1;
    int yMaxDist = this.getDistance(new Location(this.x, maxY)) + 1;

    int xZeroYMaxDist = new Location(0,this.y).getDistance(new Location(0,maxY)) + 1;
    int xZeroYZeroDist = new Location(0,this.y).getDistance(new Location(0,0)) + 1;
    int xMaxYMaxDist = new Location(maxX,this.y).getDistance(new Location(maxX,maxY)) + 1;
    int xMaxYZeroDist = new Location(maxX,this.y).getDistance(new Location(maxX,0)) + 1;

    int yZeroXMaxDist = new Location(this.x, 0).getDistance(new Location(maxX,0)) + 1;
    int yZeroXZeroDist = new Location(this.x, 0).getDistance(new Location(0,0)) + 1;
    int yMaxXMaxDist = new Location(this.x, maxY).getDistance(new Location(maxX,maxY)) + 1;
    int yMaxXZeroDist = new Location(this.x, maxY).getDistance(new Location(0,maxY)) + 1;

    List<Integer> vals = new ArrayList<>();
    vals.add(xZeroDist + other.getDistance(new Location(maxX,this.y)));
    vals.add(xMaxDist + other.getDistance(new Location(0, this.y)));
    vals.add(yZeroDist + other.getDistance(new Location(this.x, maxY)));
    vals.add(yMaxDist + other.getDistance(new Location(this.x, 0)));

    vals.add(xMaxDist + xZeroYMaxDist + other.getDistance(new Location(0,0)));
    vals.add(xMaxDist +  xZeroYZeroDist + other.getDistance(new Location(0,maxY)));
    vals.add(xZeroDist + xMaxYMaxDist + other.getDistance(new Location(maxX, 0)));
    vals.add(xZeroDist + xMaxYZeroDist + other.getDistance(new Location(maxX,maxY)));

    vals.add(yMaxDist + yZeroXMaxDist + other.getDistance(new Location(0,0)));
    vals.add(yMaxDist +  yZeroXZeroDist + other.getDistance(new Location(maxX,0)));
    vals.add(yZeroDist + yMaxXMaxDist + other.getDistance(new Location(0, maxY)));
    vals.add(yZeroDist + yMaxXZeroDist + other.getDistance(new Location(maxX,maxY)));

    return Collections.min(vals);

  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  // Equals method checks to see if locations are the same x and y values
  @Override
  public boolean equals(Object other) {
    //Check and see if it is the same object
    if (this == other) {
      return true;
    }
    // Make sure it is the correct type
    if (!(other instanceof Location)) {
      return false;
    }

    //Cast as a location
    Location that = (Location) other;
    return this.x == that.getX() && this.y == that.getY();
  }

  @Override
  public String toString() {
    return String.format("(%d,%d)", this.x, this.y);
  }

}
