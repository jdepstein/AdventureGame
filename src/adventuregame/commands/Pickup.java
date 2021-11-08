package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;

/**
 * Has the player pick up the items within the dungeon of the current cave they are in
 * No matter if there is anything on the floor or not. This requires no arguments to
 * work since there is no movement required.
 */
public class Pickup implements AdventureCommand {


  @Override
  public boolean runCmd(Dungeon d) {
    boolean hold = d.search();
    if (!hold) {
      throw new IllegalArgumentException(
              "You picked up but there was nothing to pick up");
    }
    return hold;
  }
}
