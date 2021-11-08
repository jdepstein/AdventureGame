package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;

/**
 * This allows the player to shoot an arrow in a given direction with the given distance
 * The player will be able to shoot the arrow it will go in the direction and the distance
 * that it was shot.
 */
public class Shoot implements AdventureCommand {
  private int distance;

  /**
   * The distance the arrow will travel.
   * @param dist teh distance of the arrow
   */
  public Shoot(int dist) {
    this.distance = dist;
  }

  @Override
  public void go(Dungeon d) {
    System.out.println("Remember TO DO");
  }
}
