package adventuregame;

import dungeon.Dungeon;

/**
 * In the adventure game there are many cmds the user can do to move and interact with
 * the dungeon they are in. This is a way to have the user call on those cmds and have
 * the dungeon execute them. An adventure cmd will update the state of the dungeon when executed.
 */
public interface AdventureCommand {

  /**
   * Starting point for the controller.
   *
   * @param d the model to use
   */
  boolean runCmd(Dungeon d);
}
