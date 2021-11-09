package dungeon;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * At any given time while traveling throughout the Dungeon the user may want a description of the
 * player and where that player is at currently. A player will always have a name and a List of
 * treasure on them if you want to know that info this allows you to do so. Also, it knows the
 * players current cave and lets us know if there is any treasure and what it is. As well as the
 * possible routes the player can go in the current cave North,South East or West. As well as
 * weather or not this current cave is a tunnel or not. It also provides the smell of the current
 * cave as a string to describe it
 */
public class Description {
  private final Cave cave;
  private final Player player;

  /**
   * Builds a player description Object with a given cave and a player object.
   * @param player The player object that we are looking at
   * @param cave The cave object we are looking at
   * @throws IllegalArgumentException If either the player or the cave that is passed is null
   *                         an error will be thrown.
   */
  public Description(Player player, Cave cave) throws IllegalArgumentException {
    if (player == null || cave == null) {
      throw new IllegalArgumentException(
              "Can't pass null for player or Cave");
    }
    this.player = player;
    this.cave = cave;
  }

  /**
   * Gets the name of the Player.
   * @return the players name.
   */
  public String getName() {
    return player.getName();
  }

  /**
   * Gets the List of treasure the player is currently holding.
   * @return The List of our Treasure counts as formatted Strings
   */
  public List<String> getPlayerItems() {
    List<String> myItems = new ArrayList<>();
    HashMap<CaveObject,Integer> hashTreasure = this.player.getItems();
    myItems.add(String.format("Diamonds: %d", hashTreasure.get(CaveObject.DIAMOND)));
    myItems.add(String.format("Rubies: %d", hashTreasure.get(CaveObject.RUBY)));
    myItems.add(String.format("Sapphires: %d", hashTreasure.get(CaveObject.SAPPHIRE)));
    myItems.add(String.format("CrookedArrow: %d", hashTreasure.get(CaveObject.CROOKEDARROW)));

    return myItems;
  }

  /**
   * Gets the Treasure that is within the cave.
   * @return The List of the caves Treasure counts as formatted Strings
   */
  public List<String> getCaveItems() {
    List<String> items = new ArrayList<>();
    HashMap<CaveObject,Integer> hashTreasure = this.cave.getItems();
    items.add(String.format("Diamonds: %d", hashTreasure.get(CaveObject.DIAMOND)));
    items.add(String.format("Rubies: %d", hashTreasure.get(CaveObject.RUBY)));
    items.add(String.format("Sapphires: %d", hashTreasure.get(CaveObject.SAPPHIRE)));
    items.add(String.format("CrookedArrow: %d", hashTreasure.get(CaveObject.CROOKEDARROW)));
    return items;
  }

  /**
   * Gets the Directions you can travel in the cave.
   * @return The Caves current Directions.
   */
  public List<String> getCaveDirections() {
    HashMap<Direction,Location> dir = this.cave.getDirections();
    List<String> myDirections = new ArrayList<>();
    for (Direction cur: dir.keySet()) {
      myDirections.add(cur.toString());
    }
    return myDirections;
  }

  /**
   * Tells us weather or not the cave is a tunnel or regular.
   * @return the type of the cave.
   */
  public String caveType() {
    if (this.cave.getDirections().size() == 2) {
      return "Tunnel";
    }
    else {
      return "Cave";
    }
  }

  /**
   * The smell of the cave as a string.
   * @return The string smell representation.
   */
  public String getCaveSmell() {
    if (this.cave.getSmell().equals(Smell.PUNGENT)) {
      return "There's a Fowl Smell close by";
    }
    else if (this.cave.getSmell().equals(Smell.LIGHT)) {
      return "There's a Slightly foul smell in the distance";
    }
    else {
      return String.format("The %s seems normal", this.caveType());
    }
  }

}
