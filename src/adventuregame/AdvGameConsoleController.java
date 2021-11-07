package adventuregame;

import dungeon.Dungeon;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 */
public class AdvGameConsoleController implements AdvGameController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public AdvGameConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(Dungeon d) {

  }

  /**
   *
   * @param str
   */
  private void stringAppend(String str) {
    try {
      out.append(str).append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
