package dungeon;

/**
 * Otyugh is an implementation of monster. They are creatures that are found within the dungeon and
 * are always found within the end cave of the dungeon. They will never appear in a start cave or a
 * tunnel. They require to be shot twice to die and when inured players can escape from them.
 * They will eat anything but their favorite is flesh so if a player enters a cave with a fully
 * healthy one they are killed instantly. Otyugh are also very smelly creatures and depending on
 * how close a player is to them, they can be able to tell by the smell of the cave.
 */
public class Otyugh implements Monster {
  private int health;

  /**
   * Creates an Otyugh with a health of 2.
   */
  public Otyugh() {
    this.health = 2;
  }

  @Override
  public boolean isShot() {
    return health <= 1;
  }

  @Override
  public boolean isDead() {
    return health == 0;
  }

  @Override
  public void shot() {
    if (isDead()) {
      throw new IllegalStateException(
              "Can't shoot a dead Otyugh");
    }
    this.health -= 1;
  }
}
