package view;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed.
 */
public interface Features {

  /**
   * Moves the Player South.
   * @return The String message tied to the Move.
   */
  String moveSouth();

  /**
   * Moves the Player North.
   * @return The String message tied to the Move.
   */
  String moveNorth();

  /**
   * Moves the Player West.
   * @return The String message tied to the Move.
   */
  String moveWest();

  /**
   * Moves the Player East.
   * @return The String message tied to the Move.
   */
  String moveEast();

  /**
   * Shoots an Arrow South a distance of x.
   * @param x the distance traveled.
   * @return The String message tied to the Shot.
   */
  String shootSouth(int x);

  /**
   * Shoots an Arrow East a distance of x.
   * @param x the distance traveled.
   * @return The String message tied to the Shot.
   */
  String shootEast(int x);

  /**
   * Shoots an Arrow West a distance of x.
   * @param x the distance traveled.
   * @return The String message tied to the Shot.
   */
  String shootWest(int x);

  /**
   * Shoots an Arrow North a distance of x.
   * @param x the distance traveled.
   * @return The String message tied to the Shot.
   */
  String shootNorth(int x);

  /**
   * Picking up the items in the current Cave.
   * @return The String message tied to the pickup.
   */
  String pickup();

  /**
   * Get the Last it Key value.
   * @return The char tied to the last hit value.
   */
  char lastHit();

  /**
   * Set the last to a new Key value.
   * @param key The Key the last hit is being set to.
   */
  void setLast(char key);


  /**
   * Takes care of a click and gives the location x and y on where that click happened.
   * @param x the X related to the click
   * @param y the Y related to the Click
   * @return The String message tied to the action carried out
   */
  String clickMove(int x, int y);
}
