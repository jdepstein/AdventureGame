package adventuregame;

import dungeon.Cave;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.enums.Direction;
import view.Features;
import view.IView;


/**
 * This is a controller that implements features as well as the AdventureGame Controller.
 * The features allow us to connect the controller to the view and have the interaction between
 * the two and access to info about the keys pressed. The Controller will send the update info to
 * model that was taken in. The controller also keeps track of the last hit key.
 */
public class AdvController implements Features, AdvGameController {
  private Dungeon model;
  private IView view;
  private char lastHit;

  /**
   * The Controller only takes in the model and sets the controllers model and sets up the
   *  controllers model during construction.
   * @param model The model for the controller
   * @throws IllegalArgumentException A null value passed.
   */
  public AdvController(Dungeon model) {
    if (model == null) {
      throw new IllegalArgumentException(
              "Null passed in for the model");
    }
    this.model = model;
  }

  /**
   * Sets up the view for the controller but also sets up the connection between the view and
   * the controller by setting the features for the view with this since it's an implementation of
   * features.
   * @param v The view.
   * @throws IllegalArgumentException If the view is null.
   */
  public void setView(IView v) {
    this.view = v;
    view.setFeatures(this);
  }

  @Override
  public String move(Direction dir) {
    try {
      if (model.movePlayer(dir)) {
        return "Successfully Moved " + dir.toString();
      }
      else {
        if (model.escaped()) {
          return "Successfully Escaped A Otylph";
        }
        else {
          return "Not A Valid Move";
        }
      }
    }
    catch (IllegalStateException e) {
      return e.getMessage();
    }
  }


  @Override
  public String shoot(int x, Direction dir) {
    System.out.printf("shooting" + dir.toString() + "%d \n", x);
    boolean shot;
    try {
      shot = model.shoot(x, dir);

    } catch (IllegalArgumentException | IllegalStateException e ) {
      return e.getMessage();
    }
    if (shot) {
      return "Heard a Noise in distance";
    }
    return "Arrow shot into darkness";
  }

  @Override
  public String pickup() {
    System.out.println("Picking up");
    if (model.search()) {
      return "Found Some items in the Cave";
    }
    return "There was Nothing to Find" ;
  }

  @Override
  public char lastHit() {
    return this.lastHit;
  }

  @Override
  public void setLast(char keyCode) {
    this.lastHit = keyCode;
  }

  @Override
  public String clickMove(int x, int y) {
    Cave cave = model.getCave(model.getPlayerLocation());
    if (cave.getDirections().containsValue(new Location(x,y))) {
      String direction = "";
      for (Direction dir: cave.getDirections().keySet()) {
        if (cave.getDirections().get(dir).equals(new Location(x, y))) {
          try {
            model.movePlayer(dir);
            direction = dir.toString();
            break;
          }
          catch (IllegalStateException e) {
            return "Can't Move When your dead";
          }
        }
      }
      return "Successfully Moved " + direction;
    }
    else {
      return "Invalid Move";

    }
  }

  @Override
  public String restart() {
    this.model.reset();
    return "Dungeon Rest";
  }

  @Override
  public String createNewGame(int w, int h, boolean wrap, int i, int p, int m) {
    try {
      this.model = new DungeonImpl(w,h, i, wrap, p,
              this.model.getPlayerDescription().getName(),true, m);

    }
    catch (IllegalArgumentException e) {
      return e.getMessage();
    }
    this.view.resetModel(this.model.makeReadOnly());
    return "New Dungeon Created";
  }


  @Override
  public void playGame(Dungeon d) {
    view.makeVisible();
    view.refresh();
    while (true) {
      view.refresh();
    }
  }
}
