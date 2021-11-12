package adventuregame.commands;

import dungeon.Dungeon;
import dungeon.enums.Direction;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This allows the player to shoot an arrow in a given direction with the given distance
 * The player will be able to shoot the arrow it will go in the direction and the distance
 * that it was shot.
 */
public class Shoot extends AbstractCmd {
  private final int distance;
  private final Direction dir;

  /**
   * Constructs a shoot cmd with the given append and scanner. Set ths distance and direction
   * @param append The appendable for the cmd
   * @param scan The scanner that the cmd will pull arguments from
   * @throws IllegalArgumentException if null is passed
   */
  public Shoot(Appendable append, Scanner scan) {
    super(append, scan);
    stringAppend("Enter a Distance 1-5: ");
    try {
      this.distance = scan.nextInt();
    } catch (InputMismatchException e) {
      throw new IllegalStateException(
              "Expected an Integer but got " + scan.next());
    }
    this.dir = super.selectDirection();
  }

  @Override
  public boolean runCmd(Dungeon d) {
    if (d.shoot(distance, dir)) {
      super.stringAppend("You shoot and arrow and hear a loud roar in the distance");
    } else {
      super.stringAppend("You Shoot an arrow into darkness and hear nothing");
    }
    return true;
  }
}
