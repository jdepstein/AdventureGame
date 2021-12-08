package controller.commands;

import model.Dungeon;
import model.enums.Direction;

import java.util.Scanner;

/**
 * If a user wishes to move through the dungeon using the controller they will need to
 * call on this command. The move command will move the player north, south, east, or west
 * Note a move can be successful but not end up changing the players' location due to
 * running it a wall. If a player enters in non-existent direction an illegal argument
 * error will be thrown.
 */
public class Move extends controller.commands.AbstractCmd {
  private final Direction dir;

  /**
   * Constructs a move cmd with the given append and scanner. Set the direction.
   * @param append The appendable for the cmd
   * @param scan The scanner that the cmd will pull arguments from
   * @throws IllegalArgumentException if null is passed
   */
  public Move(Appendable append, Scanner scan) {
    super(append, scan);
    dir = super.selectDirection();
  }

  @Override
  public boolean runCmd(Dungeon d) {
    boolean val = d.movePlayer(dir);
    boolean execute = false;
    if (d.escaped() && !val) {
      stringAppend("You Narrowly escaped a Otyugh and "
              + "returned to your previous location");
    } else if (!val) {
      stringAppend("You tried to move into a wall "
              + "you are still at your previous location");
    } else {
      execute = true;
    }
    return execute;
  }
}


