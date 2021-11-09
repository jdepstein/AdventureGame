package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;
import dungeon.enums.Direction;

/**
 * If a user wishes to move through the dungeon using the controller they will need to
 * call on this command. The move command will move the player north, south, east, or west
 * Note a move can be successful but not end up changing the players' location due to
 * running it a wall. If a player enters in non-existent direction an illegal argument
 * error will be thrown.
 */
public class Move implements AdventureCommand {
  private final Direction dir;

  /**
   * The direction of the move.
   * @param dir the moves Direction as a string
   * @throws  IllegalArgumentException If you pass a value that isn't N,S,E or W.
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
  public boolean runCmd(Dungeon d) {
    if (!d.movePlayer(dir)) {
      throw new IllegalArgumentException(
              "Move given provides no progression");
    }
    return d.escaped();
  }
}


