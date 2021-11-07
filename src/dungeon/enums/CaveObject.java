package dungeon.enums;

/**
 * The treasure enum is used to represent the different kinds of treasure that are found within the
 * dungeon that a player can pick up while in different caves. There are 3 different types of
 * treasure that is found Diamonds, Rubies, and Sapphires.
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

  public String getType() {
    return this.type;
  }
}
