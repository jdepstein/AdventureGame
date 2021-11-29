package dungeon;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * PlayerImpl is an implementation of Player. They are the explorers of the Dungeons and will start
 * with a given name and be placed at the starting point of the dungeon. They will explore the
 * dungeon updating their location as they travel and collecting items as they move about. At any
 * given time a player will know what directions they can travel in their current cave the treasure
 * that it holds and the treasure they have on them as well. The player while they start with no
 * treasure items will be started with three crooked arrows to use and can find more.
 */
class PlayerImpl implements Player {
  private final String name;
  private final Map<CaveObject, Integer> items;
  private Cave cave;
  private boolean alive;


  /**
   * Creates a new player object with the given name and within the given cave. If either is null
   * an error is thrown. Initializes the items to 0 count for each type that is treasure and 3 for
   * the arrows.
   * @param name The name of the player
   * @param cave The cave the player is in
   * @throws IllegalArgumentException If either field is passed in as null
   */
  public PlayerImpl(String name, Cave cave) throws IllegalArgumentException {
    if (name == null || cave == null) {
      throw new IllegalArgumentException(
              "Cant have null values");
    }
    this.name = name;
    this.cave = cave;
    this.items = new HashMap<>();
    this.items.put(CaveObject.DIAMOND, 0);
    this.items.put(CaveObject.RUBY, 0);
    this.items.put(CaveObject.SAPPHIRE, 0);
    this.items.put(CaveObject.CROOKEDARROW, 3);
    this.alive = true;

  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Location getLocation() {
    return this.cave.getLocation();
  }

  @Override
  public void updateLocation(Cave cave) throws IllegalArgumentException {
    if (cave == null) {
      throw new IllegalArgumentException(
              "Cave can't be null");
    }
    if (!isAlive()) {
      throw new
              IllegalStateException("Can't move when you're dead");
    }

    boolean isLoc = false;
    for (Location loc: this.cave.getDirections().values()) {
      if (loc.equals(cave.getLocation())) {
        isLoc = true;
      }
    }

    if (this.cave.getLocation().equals(cave.getLocation())) {
      isLoc = true;
    }

    if (!isLoc) {
      throw new IllegalArgumentException(
              "The given cave is not connected to your current location");
    }

    if (cave.getMonster() != null && !cave.getMonster().isShot()) {
      this.alive = false;
      this.cave = cave;
    }

    else if (cave.getMonster() != null && cave.getMonster().isShot()
            && !cave.getMonster().isDead()) {
      Random rand = new Random();
      int x = rand.nextInt(2);
      if (x == 0) {
        this.alive = false;
        this.cave = cave;
      }
    }
    else {
      this.cave = cave;
    }
  }

  @Override
  public Map<Direction, Location> getMoves() {
    return this.cave.getDirections();
  }


  @Override
  public boolean search() {
    if (!isAlive()) {
      throw new
              IllegalStateException("Can't search when you're dead");
    }
    int count;

    Map<CaveObject, Integer> items = this.cave.removeItems();


    int diamond = items.get(CaveObject.DIAMOND);
    int ruby = items.get(CaveObject.RUBY);
    int sapphire = items.get(CaveObject.SAPPHIRE);
    int arrow = items.get(CaveObject.CROOKEDARROW);
    count = diamond + ruby + sapphire + arrow;
    this.items.put(CaveObject.DIAMOND, this.items.get(CaveObject.DIAMOND) + diamond);
    this.items.put(CaveObject.RUBY, this.items.get(CaveObject.RUBY) + ruby);
    this.items.put(CaveObject.SAPPHIRE, this.items.get(CaveObject.SAPPHIRE) + sapphire);
    this.items.put(CaveObject.CROOKEDARROW, this.items.get(CaveObject.CROOKEDARROW) + arrow);
    return count > 0;
  }

  @Override
  public void shootArrow() throws IllegalStateException {
    if (!isAlive()) {
      throw new
              IllegalStateException("Can't shoot when you're dead");
    }

    if (this.items.get(CaveObject.CROOKEDARROW) == 0) {
      throw new IllegalStateException("You have no arrows to shoot");
    }
    this.items.put(CaveObject.CROOKEDARROW, this.items.get(CaveObject.CROOKEDARROW) - 1);
  }

  @Override
  public Map<CaveObject, Integer> getItems() {
    return new HashMap<>(this.items);
  }

  @Override
  public boolean isAlive() {
    return this.alive;
  }

  @Override
  public void reset(Cave start) {
    this.alive = true;
    this.items.put(CaveObject.DIAMOND, 0);
    this.items.put(CaveObject.RUBY, 0);
    this.items.put(CaveObject.SAPPHIRE, 0);
    this.items.put(CaveObject.CROOKEDARROW, 3);
    this.cave = start;
  }

}
