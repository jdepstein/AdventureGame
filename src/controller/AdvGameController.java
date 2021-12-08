package controller;

import model.Dungeon;

/**
 * The adventure game controller allows a way for the user to interact with the Dungeon Model. Like
 * many adventure games we follow the Protagonist as the travel throughout the dungeon. At any
 * given time while traveling the player can call on the cmds of the dungeon either processing
 * further into the dungeon by moving Searching for treasure or items within the cave you are in.
 * Or picking up your bow and shooting an arrowing hoping to meet its mark. The end goal of
 * the game is to make it to the end of the dungeon before the monster eat you in the dungeon.
 * As well as collect as many valuables as possible throughout your travels. The interactions
 * are carried about by taking user inputs and appending updates along the way to the console.
 */
public interface AdvGameController {

  /**
   * This allows the controller to play the game with a given dungeon model.
   * @param d the dungeon model you are playing on.
   */
  void playGame(Dungeon d);
}
