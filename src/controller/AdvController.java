package controller;

import model.Cave;
import model.Dungeon;
import model.DungeonImpl;
import model.Location;
import model.enums.Direction;
import view.Features;
import view.IView;


/**
 * This is a controller that implements features as well as the AdventureGame Controller.
 * The features allow us to connect the controller to the view and have the interaction between
 * the two and access to info about the keys pressed. The Controller will send the update info to
 * model that was taken in. The controller also keeps track of the last hit key.
 */
public class AdvController implements Features {
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

  @Override
  public void setView(IView v) {
    if (v == null) {
      throw new IllegalArgumentException(
              "view passed is null");
    }
    this.view = v;
    this.view.setFeatures(this);
    this.view.makeVisible();
  }

  @Override
  public String move(Direction dir) {
    if (dir == null) {
      throw new IllegalArgumentException(
              "Null passed for direction");
    }
    if (!model.hasSolved()) {
      try {
        if (model.movePlayer(dir)) {
          this.view.refresh();
          return "Successfully Moved " + dir;
        } else {
          if (model.escaped()) {
            this.view.refresh();
            return "Successfully Escaped A Otylph";
          } else {
            this.view.refresh();
            return "Not A Valid Move";
          }
        }
      } catch (IllegalStateException e) {
        this.view.refresh();
        return e.getMessage();
      }
    }
    this.view.refresh();
    return "Game is over no more moves to be made";
  }


  @Override
  public String shoot(int x, Direction dir) {
    boolean shot;
    if (dir == null) {
      throw new IllegalArgumentException(
              "Null passed for direction");
    }

    if (!model.hasSolved()) {
      try {
        shot = model.shoot(x, dir);

      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.refresh();
        return e.getMessage();
      }
      if (shot) {
        this.view.refresh();
        return "Heard a Noise in distance";
      }
      this.view.refresh();
      return "Arrow shot into darkness";
    }
    this.view.refresh();
    return "Game is over no shooting to be done";
  }

  @Override
  public String pickup() {
    if (!model.hasSolved()) {
      try {
        if (model.search()) {
          this.view.refresh();
          return "Found Some items in the Cave";
        }
        this.view.refresh();
        return "There was Nothing to Find";
      }
      catch (IllegalStateException e) {
        return e.getMessage();
      }
    }
    this.view.refresh();
    return "Game is over no picking up";
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
    if (!model.hasSolved()) {
      Cave cave = model.getCave(model.getPlayerLocation());
      if (cave.getDirections().containsValue(new Location(x, y))) {
        String direction = "";
        for (Direction dir : cave.getDirections().keySet()) {
          if (cave.getDirections().get(dir).equals(new Location(x, y))) {
            try {
              model.movePlayer(dir);
              direction = dir.toString();
              break;
            } catch (IllegalStateException e) {
              this.view.refresh();
              return "Can't move when you're dead";
            }
          }
        }
        this.view.refresh();
        return "Successfully Moved " + direction;
      } else {
        this.view.refresh();
        return "Not A Valid Move";

      }
    }
    this.view.refresh();
    return "Game is over no more moves to be made";
  }

  @Override
  public void restart() {
    this.model.reset();
    this.view.refresh();
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
    this.view.refresh();
    return "New Dungeon Created";
  }
}

