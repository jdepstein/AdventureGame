package model;

import model.enums.CaveObject;
import model.enums.Direction;

import java.util.Map;

/**
 * A player is the explorer of the Dungeons a player they will start somewhere in the Dungeon and
 * then travel throughout the Dungeon trying to solve it. While exploring various caves in the
 * throughout the Dungeon the player has the ability to search each cave for items. Every player
 * will know what cave they are in and the directions they can travel within that cave. Also, each
 * player will have a name and the treasure they have collected along the way. Along with treasure
 * the player may find CrookedArrows along the way to add to their quiver, and they have the ability
 * to shoot the arrows.
 */
interface Player {

  /**
   * Get the name of the player.
   * @return the players name
   */
  String getName();

  /**
   * Get the Location of the player.
   * @return the players current location as a location object.
   */
  Location getLocation();

  /**
   * Updates the players current cave they are in. In other words updating their location
   * @param cave The new cave the player is in
   * @throws IllegalArgumentException if the given Cave is null
   */
  void updateLocation(model.Cave cave) throws IllegalArgumentException;

  /**
   * Gets the directions the player can travel in the cave they are currently in and what location
   * they end up at.
   * @return A HashMap of Direction Enum and Locations
   */
  Map<Direction, Location> getMoves();


  /**
   * Have the player search for items in the cave they are in if they find any they will
   * add it to their current treasure list and return true. If they don't find any return false
   * @return The boolean value if they found treasure or not.
   */
  boolean search();

  /**
   * Has the player shoot an arrow and increment that they now carry one less arrow.
   * @throws IllegalStateException If the player does not have any arrows to shoot.
   */
  void shootArrow() throws IllegalStateException;

  /**
   * Gets the players Treasure and returns the hashMap of the Treasure and each types count.
   * @return The HashMap of each treasure type and the count of each type
   */
  Map<CaveObject, Integer> getItems();

  /**
   * Boolean to tell if the player is dead.
   * @return the boolean telling weather or not.
   */
  boolean isAlive();

  /**
   * Resets the player to their start stats.
   * @param start the location the player started;
   */
  void reset(Cave start);
}
