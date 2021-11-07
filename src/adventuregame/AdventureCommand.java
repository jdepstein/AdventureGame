package adventuregame;

import dungeon.Dungeon;

/**
 *
 */
public interface AdventureCommand {

  /**
   * Starting point for the controller.
   *
   * @param d the model to use
   */
  void go(Dungeon d);
}
