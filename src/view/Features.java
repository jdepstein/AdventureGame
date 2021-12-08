package view;

import model.enums.Direction;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed.
 */
public interface Features {

  /**
   * Sets up the view for the controller but also sets up the connection between the view and
   * the controller by setting the features for the view with this since it's an implementation of
   * features.
   * @param v The view.
   * @throws IllegalArgumentException If the view is null.
   */
  void setView(IView v);

  /**
   * Move in the given direction.
   * @param dir the direction moving
   * @return The string message tied to moving
   */
  String move(Direction dir);

  /**
   * Shoot an arrow in the given direction and distance.
   * @param dist the distance being shot
   * @param dir the direction being shot
   * @return The string message tied to shooting
   */
  String shoot(int dist, Direction dir);

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

  /**
   * Restarts the game from its dungeon state.
   */
  void restart();

  /**
   * Sets up the game with a new dungeon with the given values for the requirements of the dungeon.
   * @param w the Width
   * @param h  the height
   * @param wrap wrapping or not
   * @param i the interconnectivity
   * @param p the item percent
   * @param m the number of monsters
   * @return the string telling us if it was built correctly or not.
   */
  String createNewGame(int w, int h, boolean wrap, int i, int p, int m);

}
