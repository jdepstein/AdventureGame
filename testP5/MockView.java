import dungeon.ReadOnlyDungeon;
import view.Features;
import view.IView;

/**
 * Mock of the view.
 */
public class MockView implements IView {
  private ReadOnlyDungeon board;

  /**
   * Sets up the Mock board.
   * @param board the board for the board.
   */
  public MockView(ReadOnlyDungeon board) {
    this.board = board;
  }

  @Override
  public void resetModel(ReadOnlyDungeon board) {
    this.board = board;
  }

  @Override
  public void makeVisible() {
    System.out.println("Make Visible");
  }

  @Override
  public void refresh() {
    System.out.println("Refresh");
  }

  @Override
  public void setFeatures(Features f) {
    System.out.println("Set Features");
  }
}
