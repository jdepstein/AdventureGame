package model.enums;

/**
 * The treasure enum is used to represent the different kinds of treasure that are found within the
 * dungeon that a player can pick up while in different caves. There are 3 different types of
 * treasure that is found Diamonds, Rubies, and Sapphires. Then there is one general item found
 * which is the Crooked Arrow. Which can be found in both caves and tunnels and can be found with
 * or without treasure.
 */
public enum CaveObject {
  DIAMOND("treasure"),
  RUBY("treasure"),
  SAPPHIRE("treasure"),
  CROOKEDARROW("item");

  private final String type;

  CaveObject(String type) {
    this.type = type;
  }

  /**
   * Gets the type of the given object if it is treasure or just an item.
   * @return the string that describes the type.
   */
  public String getType() {
    return this.type;
  }
}
