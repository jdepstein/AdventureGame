import dungeon.ReadOnlyDungeon;
import view.Features;
import view.IView;

/**
 * Mock of the view.
 */
public class MockView implements IView {

  @Override
  public void resetModel(ReadOnlyDungeon board) {
    assert true;
  }

  @Override
  public void makeVisible() {
    assert true;
  }

  @Override
  public void refresh() {
    assert true;
  }

  @Override
  public void setFeatures(Features f) {
    assert true;
  }
}
