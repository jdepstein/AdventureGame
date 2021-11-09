package dungeon.enums;

/**
 * While traveling throughout the dungeon in any given cave there are only 4 possible directions
 * to travel North, South , East or West.
 */
public enum Direction {
  NORTH,
  SOUTH,
  EAST,
  WEST;

  /**
   * Gets the inverse of the direction.
   * @return the inverse direction
   */
  public Direction getInverse() {
    if (this == EAST) {
      return WEST;
    }
    else if (this == WEST) {
      return EAST;
    }
    else if (this == NORTH) {
      return SOUTH;
    }
    else {
      return NORTH;
    }
  }
}
