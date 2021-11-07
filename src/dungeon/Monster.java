package dungeon;


/**
 * Throughout the dungeon lurking in the dark you may come across monsters. They are extremely
 * dangerous to the player and will attack when a player enters the same cave as them. The only
 * way to damage a monster is by shooting them with arrows. If they get hit enough they will die.
 */
public interface Monster {

  /**
   * returns weather or not a monster has been shot at-least once.
   * @return the boolean telling us if the monster has been shot or not
   */
  boolean isShot();

  /**
   * returns a boolean weather or not the monster is dead.
   * @return the boolean telling us the monster is dead or not
   */
  boolean isDead();

  /**
   * Updates a monsters' health to note that they have been shot  once.
   * @throws  IllegalStateException If the monster is dead you cannot shoot it.
   */
  void shot() throws IllegalStateException;

}
