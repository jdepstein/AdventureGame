package model;

import model.enums.Direction;

import java.util.List;


/**
 * A Dungeon is a collection of interconnecting caves filled with treasure. Also, a player to
 * explore the Dungeon. Each Dungeon will be made a grid of Caves and making sure that there is
 * connectivity between all the caves. There will be a random starting point and ending point that
 * will be at least 5 moves between caves apart. The player will start at the starting point and
 * move throughout the Dungeon collecting treasure and hoping to find the end. However, the player
 * must be careful in case they run into a monster along the way. To combat these players will also
 * carry a bow with arrows they find scattered across the dungeon. Luckily all players start with
 * 3 arrows. Players will be able to shoot a certain distance and direction in the dungeon
 * hoping to injure a monster. If the player does make their way to the end they do need to be
 * careful because there is always a monster at the end. If a player enters a cave with an
 * uninjured monster they immediately die. Even if it has been injured players only have a 50%
 * chance of survival.
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
   * Gets the interconnectivity value of the dungeon.
   * @return the interconnectivity
   */
  int getInterconnectivity();

  /**
   * Gets the integer value 0-100 of the item percentage.
   * @return the item percentage
   */
  int getItemPercent();

  /**
   * Gets the number of Monster within the dungeon at the start of the game.
   * @return the number of monster at the start of the game.
   */
  int getMonsterCount();

  /**
   * Gets the boolean weather or not the dungeon is wrapping.
   * @return the boolean if the dungeon is wrapping
   */
  boolean getWrapping();


  /**
   * Gets the current Location of the player within the Dungeon.
   * @return The players location
   */
  model.Location getPlayerLocation();

  /**
   * Gets the playerDescription from the player.
   * @return the PlayerDescription object of the player.
   */
  Description getPlayerDescription();


  /**
   * Makes a readOnly Version of the current Dungeon Model.
   * @return The Readonly version of the dungeon model
   */
  ReadOnlyDungeon makeReadOnly();


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
   * @throws IllegalStateException If the player is dead they cannon move
   */
  boolean movePlayer(model.enums.Direction direction) throws IllegalStateException;

  /**
   * Tells us weather or not the player had to escape a monster on their previous move.
   * @return the boolean to tell us if they escaped a monster.
   */
  boolean escaped();

  /**
   * Player searches for treasure at their current location returns true if they found some.
   * @return Boolean if they found treasure or not
   * @throws IllegalStateException If the player is dead they cannon search
   */
  boolean search() throws IllegalStateException;

  /**
   * Gets the starting spot in the dungeon.
   * @return the dungeons starting location
   */
  model.Location getStart();

  /**
   * Gets the ending spot in the dungeon.
   * @return the dungeons ending location
   */
  model.Location getEnd();


  /**
   * Gets a cave at the given location.
   * @param loc the location you are looking at
   * @return the cave that you wanted
   */
  Cave getCave(model.Location loc);

  /**
   * Shoots an arrow a distance of x in the given direction and returns a boolean weather or not it
   * hit a monster. The distance must be exact if a monster is a distance of 3, and you enter 4
   * that will not hit. Also arrow travels freely a tunnel and does not count as one of the
   * lengths of distance. However, if the tunnel changes the direction of the arrow that will be
   * the new direction the arrow will continue traveling. If an arrow enters a cave from the East
   * and there is no West exit the arrow stops there unless it's a tunnel.
   * @param x the distance the arrow is traveling
   * @param dir the direction of the arrow
   * @return the boolean telling weather or not it was successful.
   * @throws IllegalArgumentException if the player tries to shoot an arrow into a direction that
   *                          they can't in their initial cave only.
   */
  boolean shoot(int x, Direction dir) throws IllegalArgumentException;


  /**
   * Tells the user if the player is dead or not.
   * @return the boolean value telling if the player is dead.
   */
  boolean hasLost();


  /**
   * Gets the visited location already in the dungeon.
   * @return The List of Visited Location in the dungeon.
   */
  List<Location> getVisits();


  /**
   * Resets the dungeon to its starting state and resets all the caves and monsters to their
   * original state.
   */
  void reset();
}
