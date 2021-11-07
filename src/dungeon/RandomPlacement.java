package dungeon;

import java.util.Random;

/**
 * This class random placement is used for generating the placement values for the start and finish
 * within a dungeon if you want the start and finish to always be 0,0 and 4,4 which guarantee even
 * with wrapping a distance of at least 5 then you can use the roll that sets that
 * or if you want a random values you set an xMax and a yMax that you want to roll your location
 * from.
 */
class RandomPlacement {
  private final int xMax;
  private final int yMax;

  /**
   * Builds a new randomPlacement with the given x and y max values.
   * @param xMax the Max value in the x
   * @param yMax the Max value in the y
   * @throws IllegalArgumentException if the maxes break our width and height requirements
   */
  public RandomPlacement(int xMax, int yMax) {
    this.xMax = xMax;
    this.yMax = yMax;
  }

  /**
   * Rolls a random location with the maxX and maxY note that the max values are exclusive, so it is
   *  0 - (Maxvalue - 1 ). If you want the xMax value included you must have xMax + 1 in the
   *  creation.
   * @return A location with the random generation
   */
  public Location roll() {
    Random rand = new Random();
    int x = rand.nextInt(xMax);
    int y = rand.nextInt(yMax);
    return new Location(x,y);
  }

  /**
   * Creates a location basses on if it's a starting spot or not if it is it's always at 0,0
   * if It's and end it's always at 4,4.
   * @param start the Boolean to tell if it's a start or not
   * @return the location that was generated.
   */
  public Location rollNotRandom(boolean start) {
    if (start) {
      return new Location(0,0);
    }
    return new Location(3,3);
  }

}
