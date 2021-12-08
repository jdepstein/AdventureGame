package model;

import model.enums.CaveObject;
import model.enums.Direction;
import model.enums.Smell;

import java.util.HashMap;
import java.util.Map;


/**
 * CaveImpl is an implementation of cave. A cave is a location within a dungeon each cave will
 * always have 1-4 connections and within the Dungeon there will be a level of interconnectivity
 * between them. A cave can only be filled with treasure once it actually has had its connections
 * set. There is a special type of cave called a tunnel which only has 2 connections and cannot hold
 * treasure. However, a player can exist in a tunnel the only difference is a tunnels' ability
 * to hold treasure. All Caves are initialized with 0 count of treasure of each type and no
 * connections at all. Connections cannot be overridden or loop back to yourself or have a location
 * count as two separate connections. Along with treasure you a may find arrows around they are
 * found in both caves and tunnels. Along with arrows you can find monsters lurking in the caves
 * but not in tunnels. Each cave can only hold one monster though. Due to these caves can gain
 * smells weather or not a monster is in proximity to the cave.
 */
public class CaveImpl implements model.Cave {
  private final Map<model.enums.Direction, Location> connections;
  private Map<model.enums.CaveObject, Integer> contents;
  private final Location loc;
  private model.enums.Smell smell;
  private Monster occupant;

  /**
   * Creates a new Cave Object at the given location it initializes all item types to 0 count
   * within the Map and only initializes the Map of connections does not add any keys.
   * @param loc The location of the given cave
   * @throws IllegalArgumentException if the given caves location is null
   */
  public CaveImpl(Location loc) throws IllegalArgumentException {
    if (loc == null) {
      throw new IllegalArgumentException(
              "Location can't be null");

    }
    this.connections = new HashMap<>();
    this.contents = new HashMap<>();
    this.contents.put(model.enums.CaveObject.DIAMOND, 0);
    this.contents.put(model.enums.CaveObject.RUBY, 0);
    this.contents.put(model.enums.CaveObject.SAPPHIRE, 0);
    this.contents.put(model.enums.CaveObject.CROOKEDARROW, 0);
    this.loc = loc;
    this.smell = model.enums.Smell.NONE;
    this.occupant = null;
  }

  @Override
  public void addConnection(model.enums.Direction dir, Location loc) throws IllegalArgumentException {
    if (this.connections.get(dir) != null) {
      throw new IllegalArgumentException(
              "This Caves Direction was already set");
    }

    if (dir == null || loc == null) {
      throw new IllegalArgumentException(
              "Can't have null values");
    }

    if (loc.equals(this.loc)) {
      throw new IllegalArgumentException(
              "Can't connect to yourself");
    }

    for (model.enums.Direction key : this.connections.keySet()) {
      if (this.connections.get(key).equals(loc)) {
        throw new IllegalArgumentException(
                "Can't Can't to the same location from two directions");
      }
    }
    this.connections.put(dir,  loc);
  }

  @Override
  public Map<model.enums.Direction, Location> getDirections() {
    return new HashMap<>(this.connections);
  }

  @Override
  public boolean addTreasure(model.enums.CaveObject caveObject) throws IllegalArgumentException {
    if (caveObject == null) {
      throw new IllegalArgumentException(
              "Treasure was null");
    }

    if (caveObject.getType().equals("item")) {
      throw new IllegalArgumentException(
              "Arrows are not treasure");
    }

    if (this.connections.size() == 0 || this.connections.size() == 2) {
      return false;
    }
    this.contents.put(caveObject, this.contents.get(caveObject) + 1);
    return true;
  }

  @Override
  public Map<model.enums.CaveObject, Integer> removeItems() {
    HashMap<model.enums.CaveObject, Integer> hold = new HashMap<>();
    hold.put(model.enums.CaveObject.DIAMOND, 0);
    hold.put(model.enums.CaveObject.RUBY, 0);
    hold.put(model.enums.CaveObject.SAPPHIRE, 0);
    hold.put(model.enums.CaveObject.CROOKEDARROW, 0);
    HashMap<model.enums.CaveObject, Integer> temp = (HashMap<model.enums.CaveObject, Integer>) this.getItems();
    this.contents = hold;
    return temp;
  }


  @Override
  public Location getLocation() {
    return this.loc;
  }

  @Override
  public Map<model.enums.CaveObject, Integer> getItems() {
    return new HashMap<>(this.contents);
  }


  @Override
  public String getCon() {
    StringBuilder b  = new StringBuilder();
    if (this.connections.get(model.enums.Direction.NORTH) != null) {
      b.append("N");
    }

    if (this.connections.get(model.enums.Direction.SOUTH) != null) {
      b.append("S");
    }

    if (this.connections.get(model.enums.Direction.EAST) != null) {
      b.append("E");
    }

    if (this.connections.get(model.enums.Direction.WEST) != null) {
      b.append("W");
    }

    return b.toString();
  }

  @Override
  public boolean sameLocation(Cave other) {
    return this.getLocation().equals(other.getLocation());
  }

  @Override
  public Monster getMonster() {
    return this.occupant;
  }

  @Override
  public boolean addMonster(Monster m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException(
              "Monster was null");
    }

    if (this.occupant != null) {
      return false;
    }

    if (this.connections.size() == 2) {
      return false;
    }

    this.occupant = m;
    return true;
  }

  @Override
  public void addArrow() {
    this.contents.put(model.enums.CaveObject.CROOKEDARROW, this.contents.get(CaveObject.CROOKEDARROW) + 1);
  }

  @Override
  public void updateSmell(model.enums.Smell smell) {
    if (smell == null) {
      throw new IllegalArgumentException(
              "null passed for smell");
    }
    this.smell = smell;
  }

  @Override
  public Smell getSmell() {
    return this.smell;
  }


  @Override
  public String toString() {
    StringBuilder caveString = new StringBuilder();
    caveString.append("(");
    if (this.connections.get(model.enums.Direction.WEST) != null) {
      caveString.append("<=|");
    } else {
      caveString.append("--|");
    }
    if (this.connections.get(model.enums.Direction.NORTH) != null) {
      caveString.append("^^");
    } else {
      caveString.append("--");
    }
    caveString.append("|  |");
    if (this.connections.get(model.enums.Direction.SOUTH) != null) {
      caveString.append("vv|");
    } else {
      caveString.append("--|");
    }
    if (this.connections.get(Direction.EAST) != null) {
      caveString.append("=>");
    } else {
      caveString.append("--");
    }
    caveString.append(")");
    return caveString.toString();
  }

}
