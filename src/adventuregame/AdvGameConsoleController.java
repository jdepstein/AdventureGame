package adventuregame;

import adventuregame.commands.Move;
import adventuregame.commands.Pickup;
import adventuregame.commands.Shoot;
import dungeon.Description;
import dungeon.Dungeon;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 */
public class AdvGameConsoleController implements AdvGameController {

  private final Appendable out;
  private final Scanner scan;
  private final Map<String, Function<Scanner, AdventureCommand>> knownCommands;

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
    this.knownCommands = new HashMap<>();
    knownCommands.put("M", s -> new Move(s.next()));
    knownCommands.put("S", s -> new Shoot(s.nextInt(), s.next()));
    knownCommands.put("P", s -> new Pickup());

    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(Dungeon d) {
    boolean quitting = false;
    while (!d.hasLost() && !quitting) {
      Description des = d.getPlayerDescription();
      stringAppend(String.format("%s is in a %s and, %s", des.getName(),
              des.caveType(), des.getCaveSmell()));
      stringAppend("The cave Holds: ");

      for (String cur : des.getCaveItems()) {
        if (!cur.contains(": 0")) {
          stringAppend(cur);
        }
      }
      stringAppend("Connections Lead: ");
      for (String dir : des.getCaveDirections()) {
        stringAppend(dir);
      }

      stringAppend(d.toString());


      boolean execute = false;
      while (!execute) {
        if (d.hasSolved()) {
          stringAppend("You Have reached the end of the Dungeon type Q or q to leave");
          stringAppend("You Can still Explore but you run the risk of dying or"
                  + " not finding your way back");
        }

        stringAppend("Move, Pickup, or Shoot (M-P-S)?");
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("Q")) {
          quitting = true;
          break;
        }
        Function<Scanner, AdventureCommand> cmd1 = this.knownCommands.getOrDefault(in, null);
        if (cmd1 == null) {
          stringAppend("Unknown command");
        } else {
          try {
            AdventureCommand c = cmd1.apply(scan);
            boolean val = c.runCmd(d);
            execute = true;

            if (in.equals("P")) {
              Description play = d.getPlayerDescription();
              stringAppend("You Now have: ");
              for (String cur : play.getPlayerItems()) {
                if (!cur.contains(": 0")) {
                  stringAppend(cur);
                }
              }
            }

            if (in.equals("M") && val) {
              stringAppend("You Narrowly escaped a Otyugh and "
                        + "returned to your previous location");
            }

            if (in.equals("S") && val) {
              stringAppend("You Hear a loud roar in the distance");
            }



          } catch (IllegalArgumentException e) {
            stringAppend(e.getMessage());
          } catch (InputMismatchException e) {
            String got = scan.next();
            stringAppend("Expected an Integer But got: " + got);
          }
        }
      }
    }

    if (quitting && !d.hasSolved()) {
      stringAppend("Quit the dungeon So no items were collected");
    }

    else if (d.hasLost()) {
      stringAppend("Eaten by a Monster Comp Comp");
    }

    else {
      stringAppend("You have made it through the dugeon through your travels you collected: ");
      Description des = d.getPlayerDescription();
      for (String cur : des.getPlayerItems()) {
        if (!cur.contains(": 0")) {
          stringAppend(cur);
        }
      }
    }
  }

  /**
   * Appends a string and checks for the IO error.
   * @param str the string being appended
   */
  private void stringAppend(String str) {
    try {
      out.append(str).append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
