package dungeon;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;

import java.util.HashMap;

/**
 * A Cave is what makes up a Dungeon. Each cave can have 1-4 connections with the options of going
 * North, South, East or West into other caves. There is a special type of cave called a tunnel and
 * is a cave that only has 2 connections. Another feature is a tunnel unlike all other caves cannot
 * hold treasure. A cave has the ability to have treasure added and removed as long as they are not
 * a tunnel. Each cave will also have its own unique location within the Dungeon. A cave will also
 * be able to have connections added to it in certain directions. Cave along with treasure can hold
 * arrows although arrows can be found inside caves and tunnels. Along with them lurking in the
 * dark can be monsters within the caves. Monsters like treasure cannot be found in tunnels
 */
public interface Cave {

  /**
   * Adds a connections in the given direction for the cave .
   * @param dir the direction that this connection is going
   * @param loc the location this connection, connects the cave to
   * @throws IllegalArgumentException you cannot override a caves' direction that is already set
   * @throws IllegalArgumentException if you pass in null for cave or direction
   * @throws IllegalArgumentException if the given location is the same as the caves location
   */
  void addConnection(Direction dir, Location loc) throws IllegalArgumentException;

  /**
   * Gets a HashMap of the directions and the location they connect to.
   * @return The HashMap of Direction,Location of the caves connections and where they go to.
   */
  HashMap<Direction, Location> getDirections();

  /**
   * Adds treasure to the given cave as long as it is a not a tunnel if it is it won't do anything
   * there is no error thrown though since we return a boolean if successful.
   * @param caveObject The treasure being added to the cave
   * @return The boolean value letting us know if it was successfully added
   * @throws IllegalArgumentException if you pass null for the treasure
   */
  boolean addTreasure(CaveObject caveObject) throws IllegalArgumentException;

  /**
   * Removes the treasure from the cave and returns the treasure that was in the cave as a
   * HasMap of each type and its count.
   * @return the HashMap of Treasure that was in the cave and their counts
   */
  HashMap<CaveObject, Integer> removeItems();

  /**
   * Gets the Location of the cave.
   * @return The location object of the cave.
   */
  Location getLocation();

  /**
   * Gets the HashMap of treasure that is within the cave.
   * @return The Caves Treasure HashMap
   */
  HashMap<CaveObject, Integer> getItems();

  /**
   * Gets a String representation of all the connections of the Cave.
   * @return The string representation of the Caves Connections.
   */
  String getCon();


  /**
   * Checks to see if two caves share a location.
   * @param other the other cave we are comparing locations with
   * @return boolean if they are the same location or not
   */
  boolean sameLocation(Cave other);

  /**
   * Gives us the monster in the cave.
   * @return Monster values if there is a monster or if not returns null.
   */
  Monster getMonster();

  /**
   * Adds a monster to the cave.
   * @param m the monster that is being added
   * @return if it was successfully added
   * @throws IllegalArgumentException If the monster passed is null.
   */
  boolean addMonster(Monster m) throws IllegalArgumentException;

  /**
   * Adds a crooked arrow into the cave.
   */
  void addArrow();

  /**
   * Updates the smell of the cave.
   * @param smell the new smell of the cave
   */
  void updateSmell(Smell smell);

  /**
   * ets the smell of the cave.
   * @return the smell of the cave.
   */
  Smell getSmell();
}
