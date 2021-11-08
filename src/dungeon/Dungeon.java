package dungeon;

import dungeon.enums.Direction;

/**
 * A Dungeon is a collection of interconnecting caves filled with treasure. Also, a player to
 * explore the Dungeon. Each Dungeon will be made a grid of Caves and making sure that there is
 * connectivity between all the caves. There will be a random starting point and ending point that
 * will be at least 5 moves between caves apart. The player will start at the starting point and
 * move throughout the Dungeon collecting treasure and hoping to find the end.
 */
public interface Dungeon {

  /**
   * Gets width of the Dungeon.
   * @return The integer that represents  width of the dungeon
   */
  int getWidth();

  /**
   * Gets Height of the Dungeon.
   * @return The integer that represents Height of the dungeon
   */
  int getHeight();

  /**
   * Gets the current Location of the player within the Dungeon.
   * @return The players location
   */
  Location getPlayerLocation();

  /**
   * Gets the playerDescription from the player.
   * @return the PlayerDescription object of the player.
   */
  Description getPlayerDescription();

  /**
   * Gets the interconnectivity values of the given dungeon.
   * @return The interconnectivity of the dungeon.
   */
  int getConnectivity();

  /**
   * Checks to see if the players' location matches the end location.
   * @return a boolean weather or not the player has made it to the end.
   */
  boolean hasSolved();

  /**
   * Moves the player in the given direction if the player does not have an option to move in that
   * direction it will return false to let them know they are still in the same location.
   * @param direction The direction that is being traveled in.
   * @return The boolean telling if it was a successful move or not
   */
  boolean movePlayer(Direction direction);

  /**
   * Player searches for treasure at their current location returns true if they found some.
   * @return Boolean if they found treasure or not
   */
  boolean search();

  /**
   * Gets the starting spot in the dungeon.
   * @return the dungeons starting location
   */
  Location getStart();

  /**
   * Gets the ending spot in the dungeon.
   * @return the dungeons ending location
   */
  Location getEnd();


  /**
   * Gets a cave at the given location.
   * @param loc the location you are looking at
   * @return the cave that you wanted
   */
  Cave getCave(Location loc);

  /**
   * Shoots an arrow a distance of x in the given direction and returns a boolean weather or not it
   * hit a monster.
   * @param x the distance the arrow is traveling
   * @param dir the direction of the arrow
   * @return the boolean telling weather or not it was successful.
   * @throws IllegalArgumentException if the player tries to shoot an arrow into a direction that
   *                          they can't in their initial cave.
   */
  boolean shoot(int x, Direction dir) throws IllegalArgumentException;


  /**
   * Tells the user if the player is dead or not.
   * @return the boolean value telling if the player is dead.
   */
  boolean hasLost();
}
