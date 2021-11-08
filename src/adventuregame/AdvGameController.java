package adventuregame;

import dungeon.Dungeon;

/**
 *
 */
public interface AdvGameController {


  /**
   * This allows the controller to play the game with a given dungeon model.
   * @param d the dungeon model you are playing on.
   */
  void playGame(Dungeon d);
}
