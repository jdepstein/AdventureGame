package dungeon.enums;

/**
 * Caves can have smells based on how close in proximately they are to a monster the closer they
 * are or how many there are within a certain distance can effect this. There are three level none
 * light and pungent.
 */
public enum Smell {
  PUNGENT("Smell2"),
  LIGHT("Smell1"),
  NONE("Smell0");

  private final String smellLevel;

  Smell(String smellLevel) {
    this.smellLevel = smellLevel;
  }

  public String getSmellLevel() {
    return this.smellLevel;
  }
}
