package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;
import dungeon.enums.Direction;

/**
 * This allows the player to shoot an arrow in a given direction with the given distance
 * The player will be able to shoot the arrow it will go in the direction and the distance
 * that it was shot.
 */
public class Shoot implements AdventureCommand {
  private final int distance;
  private final Direction dir;

  /**
   * The distance the arrow will travel.
   * @param dist teh distance of the arrow
   */
  public Shoot(int dist, String dir) {
    this.distance = dist;
    switch (dir) {
      case "N":
        this.dir = Direction.NORTH;
        break;
      case "S":
        this.dir = Direction.SOUTH;
        break;
      case "E":
        this.dir = Direction.EAST;
        break;
      case "W":
        this.dir = Direction.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid shoot option");
    }
  }

  @Override
  public boolean runCmd(Dungeon d) {
    return d.shoot(distance, dir);
  }
}
