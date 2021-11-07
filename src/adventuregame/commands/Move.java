package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;
import dungeon.enums.Direction;

/**
 *
 */
public class Move implements AdventureCommand {
  private Direction dir;

  /**
   *
   * @param dir
   */
  public Move(String dir) {
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
        throw new IllegalArgumentException("Invalid move option");
    }
  }

  @Override
  public void go(Dungeon d) {

  }
}
