package dungeon;

import dungeon.enums.CaveObject;

/**
 * This is a readOnly version of the dungeon model that is used to get info about the dungeon to
 * the view. The method in this will only return info about the state of the dungeon model. It
 * does not allow updates to the actual dungeon itself.
 */
public class ReadOnlyModel {
  private final Dungeon d;
  private final Player p;

  /**
   * Creates the readOnly version of the dungeon model requiring the player in the dungeon
   * and the dungeon itself.
   * @param d the Dungeon.
   * @param p the player in the dungeon.
   */
  public ReadOnlyModel(Dungeon d, Player p) {
    this.d = d;
    this.p = p;
  }

  /**
   * Gets the Location of the Player.
   * @return The Location of the player.
   */
  public Location getPlayerLoc() {
    return d.getPlayerLocation();
  }

  /**
   * Gets the width of the dungeon.
   * @return The number of caves wide the dungeon is.
   */
  public int getWidth() {
    return d.getWidth();
  }

  /**
   * Gets the height of the dungeon.
   * @return The number of caves high the dungeon is.
   */
  public int getHeight() {
    return d.getHeight();
  }

  /**
   * Get the number of diamonds the player carries.
   * @return the number of diamonds the player has.
   */
  public int playerDiamond() {
    return p.getItems().get(CaveObject.DIAMOND);
  }

  /**
   * Get the number of rubies the player carries.
   * @return the number of rubies the player has.
   */
  public int playerRuby() {
    return p.getItems().get(CaveObject.RUBY);
  }

  /**
   * Get the number of sapphires the player carries.
   * @return the number of sapphires the player has.
   */
  public int playerSapphire() {
    return p.getItems().get(CaveObject.SAPPHIRE);
  }

  /**
   * Get the number of arrows the player carries.
   * @return the number of arrows the player has.
   */
  public int playerArrow() {
    return p.getItems().get(CaveObject.CROOKEDARROW);
  }

  /**
   * Get the number of diamonds the cave carries.
   * @return the number of diamonds the cave has.
   */
  public int caveDiamond() {
    return d.getCave(d.getPlayerLocation()).getItems().get(CaveObject.DIAMOND);
  }

  /**
   * Get the number of rubies the cave carries.
   * @return the number of rubies the cave has.
   */
  public int caveRuby() {
    return d.getCave(d.getPlayerLocation()).getItems().get(CaveObject.RUBY);
  }

  /**
   * Get the number of sapphires the cave carries.
   * @return the number of sapphires the cave has.
   */
  public int caveSapphire() {
    return d.getCave(d.getPlayerLocation()).getItems().get(CaveObject.SAPPHIRE);
  }

  /**
   * Get the number of arrows the cave carries.
   * @return the number of arrows the cave has.
   */
  public int caveArrow() {
    return d.getCave(d.getPlayerLocation()).getItems().get(CaveObject.CROOKEDARROW);
  }

  /**
   * Gets the string smell of the player is cave they are in.
   * @return The String representation of the current caves smell
   */
  public String getSmell() {
    return d.getCave(this.getPlayerLoc()).getSmell().getSmellLevel();
  }

  /**
   * Checks to see if this current location x and y has been visited.
   * @param x The Locations x value.
   * @param y The Locations y value.
   * @return boolean if this cave has been visited or not.
   */
  public boolean hasVisited(int x, int y) {
    return d.getVisits().contains(new Location(x, y));
  }

  /**
   * Get the string representations of the cave with location x,y connections.
   * @param x The Locations x value.
   * @param y The Locations y value.
   * @return The string representation of the caves connections.
   */
  public String getConnections(int x, int y) {
    return d.getCave(new Location(x, y)).getCon();
  }

}
