package adventuregame.commands;

import adventuregame.AdventureCommand;
import dungeon.Dungeon;

/**
 *
 */
public class Shoot implements AdventureCommand {
  private int distance;

  /**
   *
   * @param dist
   */
  public Shoot(int dist) {
    this.distance = dist;
  }

  @Override
  public void go(Dungeon d) {

  }
}
