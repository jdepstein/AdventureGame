package controller.commands;

import model.Description;
import model.Dungeon;

import java.util.Scanner;

/**
 * Has the player pick up the items within the dungeon of the current cave they are in
 * No matter if there is anything on the floor or not. This requires no arguments to
 * work since there is no movement required.
 */
public class Pickup extends controller.commands.AbstractCmd {

  /**
   * Constructs a pickup cmd with the given append and scanner.
   * @param append The appendable for the cmd
   * @param scan The scanner that the cmd will pull arguments from
   * @throws IllegalArgumentException if null is passed
   */
  public Pickup(Appendable append, Scanner scan) {
    super(append, scan);
  }

  @Override
  public boolean runCmd(Dungeon d) {
    boolean val = d.search();
    if (!val) {
      super.stringAppend("You picked up but there was nothing to pick up");
    } else {
      super.stringAppend("You found some items you now have: ");
      Description des = d.getPlayerDescription();
      for (String cur : des.getPlayerItems()) {
        if (!cur.contains(": 0")) {
          stringAppend(cur);
        }
      }
    }
    return val;
  }
}
