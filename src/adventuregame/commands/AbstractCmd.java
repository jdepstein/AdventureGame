package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.enums.Direction;

import java.io.IOException;
import java.util.Scanner;


abstract class AbstractCmd implements AdventureCommand {
  private final Appendable append;
  private final Scanner scan;

  /**
   * Creates a cmd argument with th given appendable value and scanner.
   * @param append The appendable for the cmd
   * @param scan The scanner that the cmd will pull arguments from
   * @throws IllegalArgumentException Null passed in.
   */
  AbstractCmd(Appendable append, Scanner scan) {
    if (append == null || scan == null) {
      throw new IllegalArgumentException(
              "Null value passed");
    }
    this.append = append;
    this.scan = scan;
  }

  /**
   * Selecting a direction from the Scanner.
   * @return the direction that was got
   * @throws IllegalArgumentException If an invalid value was passed into the scanner.
   */
  Direction selectDirection() {
    stringAppend("Enter a Direction:");
    String direction = scan.next();
    switch (direction) {
      case "N":
        return Direction.NORTH;
      case "S":
        return Direction.SOUTH;
      case "E":
        return Direction.EAST;
      case "W":
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("Invalid Direction " + direction );
    }
  }

  /**
   * Appends a string and checks for the IO error.
   *
   * @param str the string being appended
   */
  void stringAppend(String str) {
    try {
      append.append(str).append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed \n");
    }
  }
}


