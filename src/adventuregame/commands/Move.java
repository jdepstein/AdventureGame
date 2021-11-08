package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;
import dungeon.enums.Direction;

/**
 * If a user wishes to move through the dungeon using the controller they will need to
 * call on this command. The move command will move the player north, south, east, or west
 * Note a move can be successful but not end up changing the players' location due to
 * running it a wall. If a player enters in non existent direction an illegal argument
 * error will be thrown.
 */
public class Move implements AdventureCommand {
  private Direction dir;

  /**
   * The direction of the move.
   * @param dir the moves Direction as a string
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
    System.out.println("Remember TO DO");
  }
}
