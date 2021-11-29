package dungeon;

import dungeon.enums.CaveObject;
import dungeon.enums.Direction;
import dungeon.enums.Smell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dungeon Impl is an implementation of Dungeon. In this case a dungeon will be initialized with
 * a given width and height, a level of interconnectivity of the caves, and weather or not the
 * dungeon wraps. Following that a dungeon meeting those requirements will be generated.
 * As a note width and height must be greater than 5 and interconnectivity is 0 or greater 0 being
 * 1 path between every cave. Our starting point and end point will be decided randomly by choosing
 * two cave they will have to be 5 moves apart. Once a valid start is dedicated a player is placed
 * at the starting point and the dungeon exploration can begin. However, the player
 * must be careful in case they run into an Otyugh along the way. To combat these players will also
 * carry a bow with arrows they find scattered across the dungeon. Luckily all players start with
 * 3 arrows. Players will be able to shoot a certain distance and direction in the dungeon
 * hoping to injure Otyugh. If the player does make their way to the end they do need to be
 * careful because there is always a monster at the end. If a player enters a cave with an
 * uninjured Otyugh they immediately die. Even if it has been injured players only have a 50%.
 * The good news is even if the player is blind in a sense to knowing exact locations of the
 * Otyugh. They are very smelly creatures and cause the caves near them to have a stench that can
 * warn a player one is nearby.
 */
public class DungeonImpl implements Dungeon {
  private Cave[][] caves;
  private final int xDim;
  private final int yDim;
  private final Player player;
  private Location end;
  private Location start;
  private final int interConnectivity;
  private boolean escaped;
  private List<Location> visits;

  private static final int MIN_WH = 6;

  /**
   * Creates a new Dungeon with the given width, Height and interConnectivity and a boolean to see
   * if the Dungeon will wrap it will create a player with a given name and place them at the
   * Dungeons starting point. You can define how much treasure you would like in the dungeon, and
   * you can set if you want a random start and finish.
   * player with the given name.
   *
   * @param width The width of the dungeon.
   * @param height  The Height of the dungeon.
   * @param interConnectivity The interconnectivity of teh dungeons cave system 0 if there is 1 path
   *                          to every cave from any given cave
   * @param wrapping If the Dungeon wraps or not
   * @param itemPercent The percent of caves that have treasure & percent of caves that
   *                    carry arrows
   * @param playerName The name of the player
   * @param random If you want a random start and finish placement
   * @param monsterCount The count of Monster in the dungeon.
   * @throws IllegalArgumentException Width and Height did not fall within the requirements
   * @throws IllegalArgumentException The interconnectivity was a negative value or too large
   * @throws IllegalArgumentException The players name was null.
   * @throws IllegalArgumentException Invalid item Percentages .
   * @throws IllegalArgumentException Invalid Monster counts.
   */
  public DungeonImpl(int width, int height, int interConnectivity, boolean wrapping,
                     int itemPercent, String playerName, boolean random, int monsterCount)
          throws IllegalArgumentException {

    if (width < MIN_WH || height < MIN_WH) {
      throw new IllegalArgumentException(
              " Width and Height both must be greater than 5");
    }

    if (playerName == null) {
      throw new IllegalArgumentException(
              " Player name was null");
    }

    if (itemPercent < 0) {
      throw new IllegalArgumentException(
              "0% of valid caves must carry treasure");
    }

    if (itemPercent > 100) {
      throw new IllegalArgumentException(
              "Over 100% is not possible");
    }


    if (interConnectivity > (width - 1) * (height - 1) && !wrapping) {
      throw new IllegalArgumentException(
              "Your Interconnectivity was too large");
    }

    if (interConnectivity > (width * height) + 1 && wrapping) {
      throw new IllegalArgumentException(
              "Your Interconnectivity was too large");
    }

    if (interConnectivity < 0) {
      throw new IllegalArgumentException(
              "Your Interconnectivity was negative");
    }

    if (monsterCount <= 0) {
      throw new IllegalArgumentException(
              "You must have at least one monster in the dungeon");

    }
    if (monsterCount >= .2 * (width * height)) {
      throw new IllegalArgumentException(
              "You have too many monsters requested");
    }


    this.caves = new Cave[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.caves[y][x] = new CaveImpl(new Location(x, y));
      }
    }

    this.escaped = false;
    this.xDim = width;
    this.yDim = height;
    this.interConnectivity = interConnectivity;
    this.visits = new ArrayList<>();

    List<Edge> kruskalStart = this.getUniqueEdges(wrapping);
    this.kruskal(kruskalStart);

    List<Cave> notTunnels = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (this.caves[y][x].getDirections().size() != 2) {
          notTunnels.add(this.caves[y][x]);
        }
      }
    }

    while (!this.setStartEnd(random,wrapping, notTunnels)) {
      this.caves = new Cave[height][width];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          this.caves[y][x] = new CaveImpl(new Location(x, y));
        }
      }

      kruskalStart = this.getUniqueEdges(wrapping);
      this.kruskal(kruskalStart);

      notTunnels = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          if (this.caves[y][x].getDirections().size() != 2) {
            notTunnels.add(this.caves[y][x]);
          }
        }
      }
    }

    this.player = new PlayerImpl(playerName, this.caves[this.start.getY()][this.start.getX()]);
    this.addTreasure(notTunnels.size(), itemPercent);
    this.addArrows(itemPercent);
    this.addMonster(notTunnels, monsterCount, random);
    this.visits.add(this.start);
  }

  @Override
  public int getWidth() {
    return this.xDim;
  }

  @Override
  public int getHeight() {
    return this.yDim;
  }

  @Override
  public Location getPlayerLocation() {
    return this.player.getLocation();
  }

  @Override
  public Description getPlayerDescription() {
    return new Description(this.player,
            caves[this.player.getLocation().getY()][this.player.getLocation().getX()]);
  }

  @Override
  public ReadOnlyModel makeReadOnly() {
    return new ReadOnlyModel(this, this.player);
  }

  @Override
  public int getConnectivity() {
    return this.interConnectivity;
  }

  @Override
  public boolean hasSolved() {
    if (this.hasLost()) {
      return false;
    }
    return this.player.getLocation().equals(this.end);
  }

  @Override
  public boolean movePlayer(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException(
              "Direction was given as null");
    }

    Location move = this.player.getMoves().get(direction);
    if (move == null) {
      return false;
    }

    this.escaped = false;
    Cave cave = this.caves[move.getY()][move.getX()];
    Location previous = player.getLocation();
    player.updateLocation(cave);

    if (previous.equals(player.getLocation())) {
      this.escaped = true;
      return false;
    }

    if (!this.visits.contains(this.getPlayerLocation())) {
      this.visits.add(getPlayerLocation());
    }
    return true;
  }

  @Override
  public boolean escaped() {
    return this.escaped;
  }

  @Override
  public boolean search() {

    return player.search();
  }

  @Override
  public Location getStart() {
    return this.start;
  }

  @Override
  public Location getEnd() {
    return this.end;
  }


  @Override
  public Cave getCave(Location loc) {
    Cave cur = this.caves[loc.getY()][loc.getX()];
    Cave cave = new CaveImpl(cur.getLocation());

    for (Direction dir : cur.getDirections().keySet()) {
      cave.addConnection(dir, cur.getDirections().get(dir));
    }

    for (CaveObject tres : cur.getItems().keySet()) {
      for (int t = 0; t < cur.getItems().get(tres); t++) {
        if (tres == CaveObject.CROOKEDARROW) {
          cave.addArrow();
        }
        else {
          cave.addTreasure(tres);
        }
      }
    }
    if (cur.getMonster() != null) {
      Monster m = new Otyugh();
      if (cur.getMonster().isShot()) {
        m.shot();
      }
      if (cur.getMonster().isDead()) {
        m.shot();
      }
      cave.addMonster(m);
    }
    cave.updateSmell(cur.getSmell());

    return cave;
  }

  @Override
  public boolean shoot(int x, Direction dir) {
    if (x > 5 || x < 1) {
      throw new IllegalArgumentException(
              "Invalid distance to shoot " + x );
    }

    if (dir == null) {
      throw new IllegalArgumentException(
              "Null passed for direction");
    }

    Cave cur = this.caves[this.player.getLocation().getY()][this.player.getLocation().getX()];
    if (cur.getDirections().get(dir) == null) {
      throw new IllegalArgumentException(
              "Tried to shoot an arrow directly into a wall");
    }
    boolean ranOut = false;
    this.player.shootArrow();
    int origin = x;
    while (x > 0) {
      if (cur.getDirections().size() == 2 && x != origin) {
        for (Direction hold: cur.getDirections().keySet()) {
          if (hold != dir.getInverse()) {
            dir = hold;
            cur = this.caves[cur.getDirections().get(dir).getY()]
                    [cur.getDirections().get(dir).getX()];
            break;
          }
        }

      } else {

        if (cur.getDirections().get(dir) == null) {
          ranOut = true;
          x = 0;

        }
        else {
          x--;
          cur  = this.caves[cur.getDirections().get(dir).getY()]
                  [cur.getDirections().get(dir).getX()];
        }
      }
    }
    while (cur.getDirections().size() == 2) {
      for (Direction hold : cur.getDirections().keySet()) {
        if (hold != dir.getInverse()) {
          dir = hold;
          cur = this.caves[cur.getDirections().get(dir).getY()]
                  [cur.getDirections().get(dir).getX()];
          break;
        }
      }
    }

    if (cur.getMonster() != null && !ranOut) {
      if (!cur.getMonster().isDead()) {
        cur.getMonster().shot();
        if (cur.getMonster().isDead()) {
          this.setSmell();
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean hasLost() {
    return !this.player.isAlive();
  }

  @Override
  public List<Location> getVisits() {
    return this.visits;
  }


  @Override
  public String toString() {
    StringBuilder dungeon = new StringBuilder();
    for (int y = 0; y < this.getHeight(); y++) {
      if (y != 0) {
        dungeon.append("\n");
      }
      for (int x = 0; x < this.getWidth(); x++) {
        if (this.start.equals(new Location(x, y)) && this.start.equals(player.getLocation())) {
          String[] split = this.caves[y][x].toString().split("  ");
          dungeon.append(String.format("%sSP%s", split[0], split[1]));
        }
        else if (this.start.equals(new Location(x, y))) {
          String[] split = this.caves[y][x].toString().split("  ");
          dungeon.append(String.format("%s S%s", split[0], split[1]));
        }
        else if (this.end.equals(new Location(x, y)) && this.end.equals(player.getLocation())) {
          String[] split = this.caves[y][x].toString().split("  ");
          dungeon.append(String.format("%sEP%s", split[0], split[1]));
        }
        else if (this.player.getLocation().equals(new Location(x, y))) {
          String[] split = this.caves[y][x].toString().split("  ");
          dungeon.append(String.format("%s P%s", split[0], split[1]));
        }
        else if (this.end.equals(new Location(x, y))) {
          if (this.getCave(this.end).getMonster() != null) {
            if (!this.getCave(this.end).getMonster().isDead()) {
              String[] split = this.caves[y][x].toString().split("  ");
              dungeon.append(String.format("%sOE%s", split[0], split[1]));
            }
            else {
              String[] split = this.caves[y][x].toString().split("  ");
              dungeon.append(String.format("%s E%s", split[0], split[1]));
            }
          }
          else {
            String[] split = this.caves[y][x].toString().split("  ");
            dungeon.append(String.format("%s E%s", split[0], split[1]));
          }
        }

        else if (this.getCave(new Location(x, y)).getMonster() != null) {
          if (!this.caves[y][x].getMonster().isDead()) {
            String[] split = this.caves[y][x].toString().split("  ");
            dungeon.append(String.format("%s O%s", split[0], split[1]));
          }
          else {
            dungeon.append(this.caves[y][x].toString());
          }
        }

        else {
          dungeon.append(this.caves[y][x].toString());
        }
        dungeon.append("  ");
      }
    }
    return dungeon.toString();
  }


  /**
   * Gets a list of all the possible unique edges in the dungeon.
   * @param wrapping if the dungeon wraps or not
   * @return the list of unique edges in the dungeon
   */
  private List<Edge> getUniqueEdges(boolean wrapping) {
    List<Edge> allEdges = new ArrayList<>();
    // Make a list of all the edges even repeats for right now
    for (int y = 0; y < this.yDim; y++) {
      for (int x = 0; x < this.xDim; x++) {
        if (x > 0 && y > 0 && y < yDim - 1 && x < xDim - 1) {
          allEdges.add(new Edge(this.caves[y][x], this.caves[y][x - 1],
                  Direction.WEST, Direction.EAST));

          allEdges.add(new Edge(this.caves[y][x], this.caves[y][x + 1],
                  Direction.EAST, Direction.WEST));

          allEdges.add(new Edge(this.caves[y][x], this.caves[y + 1][x],
                  Direction.SOUTH, Direction.NORTH));

          allEdges.add(new Edge(this.caves[y][x], this.caves[y - 1][x],
                  Direction.NORTH, Direction.SOUTH));
        }
      }
    }

    for (int y = 0; y < yDim - 1; y++) {
      allEdges.add(new Edge(this.caves[y][0], this.caves[y + 1][0],
              Direction.SOUTH,Direction.NORTH));
      allEdges.add(new Edge(this.caves[y][xDim - 1], this.caves[y + 1][xDim - 1],
              Direction.SOUTH,Direction.NORTH));
    }

    for (int x = 0; x < xDim - 1; x++) {
      allEdges.add(new Edge(this.caves[yDim - 1][x], this.caves[yDim - 1][x + 1],
              Direction.EAST, Direction.WEST));
      allEdges.add(new Edge(this.caves[0][x], this.caves[0][x + 1],
              Direction.EAST, Direction.WEST));
    }

    if (wrapping) {
      for (int x = 0; x < xDim; x++) {
        allEdges.add(new Edge(this.caves[0][x], this.caves[yDim - 1][x],
                Direction.NORTH, Direction.SOUTH));
      }
      for (int y = 0; y < yDim; y++) {
        allEdges.add(new Edge(this.caves[y][0],
                this.caves[y][xDim - 1],Direction.WEST,Direction.EAST));
      }
    }

    List<Edge> kruskalStart = new ArrayList<>();
    for (Edge e : allEdges) {
      if (!kruskalStart.contains(e)) {
        kruskalStart.add(e);
      }
    }
    return kruskalStart;
  }

  /**
   * Does Kruskal algorithm takes a list of edges its pulling from.
   * @param kruskalStart The edges that are available
   */
  private void kruskal(List<Edge> kruskalStart) {
    List<Edge> kruskalLeftOver = new ArrayList<>();
    List<List<Edge>> connect = new ArrayList<>();
    while (kruskalStart.size() > 0) {
      Collections.shuffle(kruskalStart);
      Edge e = kruskalStart.remove(0);
      List<Edge> loc1 = new ArrayList<>();
      List<Edge> loc2 = new ArrayList<>();
      boolean doNotAdd = false;
      for (List<Edge> edges : connect) {
        if (e.hasBoth(edges)) {
          doNotAdd = true;
          break;
        }

        if (e.hasA(edges) && loc1.size() == 0) {
          loc1 = edges;
        } else if (e.hasA(edges) && loc1.size() != 0) {
          loc2 = edges;
          break;
        }
      }

      if (doNotAdd) {
        kruskalLeftOver.add(e);
      } else {
        if (loc1.size() == 0) {
          List<Edge> temp = new ArrayList<>();
          temp.add(e);
          connect.add(temp);
        } else if (loc2.size() == 0) {
          connect.remove(loc1);
          loc1.add(e);
          connect.add(loc1);
        } else {
          connect.remove(loc1);
          connect.remove(loc2);
          loc1.addAll(loc2);
          loc1.add(e);
          connect.add(loc1);
        }
      }
    }
    for (int x = 0; x < this.interConnectivity; x++) {
      Collections.shuffle(kruskalLeftOver);
      connect.get(0).add(kruskalLeftOver.remove(0));
    }

    for (Edge e: connect.get(0)) {
      e.getCave1().addConnection(e.getDir1(), e.getCave2().getLocation());
      e.getCave2().addConnection(e.getDir2(), e.getCave1().getLocation());
    }
  }


  /**
   * Adds treasure to the dungeon at a certain percentage.
   * @param caveCount the count of all the caves that are not tunnels
   * @param treasurePercent the percent of treasure we want filled
   */
  private void  addTreasure(int caveCount, int treasurePercent) {
    RandomPlacement rand = new RandomPlacement(this.xDim, this.yDim);
    List<CaveObject> caveObjects = new ArrayList<>();
    caveObjects.add(CaveObject.DIAMOND);
    caveObjects.add(CaveObject.RUBY);
    caveObjects.add(CaveObject.SAPPHIRE);
    Location loc;
    int addTo = (int) Math.ceil(caveCount * ((double) treasurePercent / 100));
    while (addTo != 0) {
      loc = rand.roll();
      Cave cave = this.caves[loc.getY()][loc.getX()];
      Collections.shuffle(caveObjects);
      if (cave.getItems().get(CaveObject.DIAMOND) == 0
              && cave.getItems().get(CaveObject.RUBY) == 0
              && cave.getItems().get(CaveObject.SAPPHIRE) == 0) {

        if (cave.addTreasure(caveObjects.get(0))) {
          addTo--;
        }
      }

      else {
        cave.addTreasure(caveObjects.get(0));
      }
    }
  }

  /**
   * Adds arrows to the caves at the given percent.
   * @param percent the percent of caves we add arrows to
   */
  private void addArrows(int percent) {
    RandomPlacement rand = new RandomPlacement(this.xDim, this.yDim);
    int addTo = (int) Math.ceil(this.xDim * this.yDim * ((double) percent / 100));
    Location loc;
    while (addTo != 0) {
      loc = rand.roll();
      Cave cave = this.caves[loc.getY()][loc.getX()];
      if (cave.getItems().get(CaveObject.CROOKEDARROW) == 0) {
        addTo--;
      }
      cave.addArrow();
    }
  }

  /**
   * Adds the Otyugh into the dungeon making sure there is one at the end and none at the start.
   * @param nonTunnel the list of non tunnels in the dungeon
   * @param monsterCount the number of monsters wanting to be added
   */
  private void addMonster(List<Cave> nonTunnel, int monsterCount, boolean random) {
    nonTunnel.removeIf(c -> c.getLocation().equals(this.start));
    nonTunnel.removeIf(c -> c.getLocation().equals(this.end));
    this.caves[this.end.getY()][this.end.getX()].addMonster(new Otyugh());
    monsterCount--;

    if (random) {
      while (monsterCount > 0) {
        Collections.shuffle(nonTunnel);
        if (nonTunnel.size() == 0 ) {
          throw new IllegalArgumentException("No More caves open");
        }

        Cave addingTo = nonTunnel.remove(0);
        addingTo.addMonster(new Otyugh());
        monsterCount--;
      }
    }
    else {
      while (monsterCount > 0) {
        for (Cave[] myCave : this.caves) {
          if (monsterCount == 0) {
            break;
          }
          for (int x = 0; x < this.caves[0].length; x++) {
            if (!myCave[x].getLocation().equals(new Location(0,0))) {
              if (myCave[x].addMonster(new Otyugh())) {
                monsterCount--;
                this.caves[0][0].addArrow();
                this.caves[0][0].addArrow();
              }
              if (monsterCount == 0) {
                break;
              }
            }
          }
        }
      }
    }

    this.setSmell();
  }


  private void setSmell() {
    for (Cave[] myCave : this.caves) {
      for (int x = 0; x < this.caves[0].length; x++) {
        myCave[x].updateSmell(Smell.NONE);
      }
    }

    for (Cave[] myCave : this.caves) {
      for (int x = 0; x < this.caves[0].length; x++) {
        if (myCave[x].getMonster() != null && !myCave[x].getMonster().isDead()) {

          List<Cave> oneAway = new ArrayList<>();
          for (Location loc: myCave[x].getDirections().values()) {
            Cave hold = this.caves[loc.getY()][loc.getX()];
            if (loc != myCave[x].getLocation()) {
              oneAway.add(hold);
              hold.updateSmell(Smell.PUNGENT);
            }
          }

          List<Location> second = new ArrayList<>();
          for (Cave cur: oneAway) {
            for (Location loc: cur.getDirections().values()) {
              boolean prev = false;
              for (Cave cave: oneAway) {
                if (loc.equals(cave.getLocation())) {
                  prev = true;
                }
              }
              if (!loc.equals(myCave[x].getLocation()) && !prev) {
                second.add(loc);
              }
            }
          }
          second = second.stream().distinct().collect(Collectors.toList());
          for (Location cur: second) {
            Cave hold = this.caves[cur.getY()][cur.getX()];
            if (hold.getSmell() == Smell.LIGHT) {
              hold.updateSmell(Smell.PUNGENT);
            }
            else if (hold.getSmell() == Smell.NONE) {
              hold.updateSmell(Smell.LIGHT);
            }
          }
        }
      }
    }
  }

  /**
   * Sets the start and end of the dungeon.
   * @param random if it is going to be a random start and end
   * @param wrapping if the dungeon wraps
   * @param notTunnels a list of caves that are not tunnels.
   */
  private boolean setStartEnd(boolean random, boolean wrapping ,List<Cave> notTunnels) {
    RandomPlacement rand =  new RandomPlacement(this.xDim, this.yDim);
    Location start;
    Location end;
    if (random) {
      Collections.shuffle(notTunnels);
      start = notTunnels.get(0).getLocation();
      end = notTunnels.get(1).getLocation();
      int count = 0;
      if (wrapping) {
        while (start.getDistance(end) <= 5 || start.wrappingDistance(end,
                this.xDim - 1, this.yDim - 1) <= 5 && count < 500) {
          Collections.shuffle(notTunnels);
          start = notTunnels.get(0).getLocation();
          end = notTunnels.get(1).getLocation();
          count++;
        }
      }
      else {
        while (start.getDistance(end) <= 5 && count < 500) {
          Collections.shuffle(notTunnels);
          start = notTunnels.get(0).getLocation();
          end = notTunnels.get(1).getLocation();
          count++;
        }
      }
      if (count == 500) {
        return false;
      }
    }

    else {
      start = rand.rollNotRandom(true);
      end = rand.rollNotRandom(false);
    }
    this.start = start;
    this.end = end;
    return true;
  }

}

