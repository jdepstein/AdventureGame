
import model.Dungeon;
import model.DungeonImpl;
import org.junit.Test;
import view.DungeonSwingView;
import view.IView;


/**
 * Tests for the view Construction arguments thrown.
 */
public class ViewConstruction {

  /**
   * Null passed in the constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badModel() {
    new DungeonSwingView(null);
  }

  /**
   * Null passed in the set Features.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badFeatures() {
    Dungeon dung = new DungeonImpl(6,6,6,false,5,
            "Jack", true, 1);
    IView view =  new DungeonSwingView(dung.makeReadOnly());
    view.setFeatures(null);
  }

}


