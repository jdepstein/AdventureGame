package adventuregame;

import adventuregame.AdvGameController;
import dungeon.Cave;
import dungeon.Dungeon;
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
  private final  Dungeon model;
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
   * the controller by setting the features for the view with this since its an implentaion of
   * features.
   * @param v The view.
   * @throws IllegalArgumentException If the view is null.
   */
  public void setView(IView v) {
    this.view = v;
    view.setFeatures(this);
  }

  @Override
  public String moveSouth() {
    System.out.println("Moving South");
    try {
      if (model.movePlayer(Direction.SOUTH)) {
        return "Successfully Moved South";
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
  public String moveNorth() {
    System.out.println("Moving North");
    try {
      if (model.movePlayer(Direction.NORTH)) {
        return "Successfully Moved North";
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
  public String moveWest() {
    System.out.println("Moving West");
    try {
      if (model.movePlayer(Direction.WEST)) {
        return "Successfully Moved West";
      } else {
        if (model.escaped()) {
          return "Successfully Escaped A Otylph";
        } else {
          return "Not A Valid Move";
        }
      }
    }
    catch (IllegalStateException e) {
      return e.getMessage();
    }
  }

  @Override
  public String moveEast() {
    System.out.println("Moving East");
    try {
      if (model.movePlayer(Direction.EAST)) {
        return "Successfully Moved East";
      } else {
        if (model.escaped()) {
          return "Successfully Escaped A Otylph";
        } else {
          return "Not A Valid Move";
        }
      }
    } catch (IllegalStateException e) {
      return e.getMessage();
    }
  }

  @Override
  public char lastHit() {
    return this.lastHit;
  }

  @Override
  public String shootSouth(int x) {
    System.out.printf("SHOOTING SOUTH %d \n", x);
    boolean shot;
    try {
      shot = model.shoot(x, Direction.SOUTH);

    } catch (IllegalArgumentException | IllegalStateException e ) {
      return e.getMessage();
    }
    if (shot) {
      return "Heard a Noise in distance";
    }
    return "Arrow shot into darkness";
  }

  @Override
  public String shootEast(int x) {
    System.out.printf("SHOOTING East %d \n", x);
    boolean shot;
    try {
      shot = model.shoot(x, Direction.EAST);

    } catch (IllegalArgumentException | IllegalStateException e ) {
      return e.getMessage();
    }
    if (shot) {
      return "Heard a Noise in distance";
    }
    return "Arrow shot into darkness";
  }

  @Override
  public String shootWest(int x) {
    System.out.printf("SHOOTING West %d \n", x);
    boolean shot;
    try {
      shot = model.shoot(x, Direction.WEST);

    } catch (IllegalArgumentException | IllegalStateException e ) {
      return e.getMessage();
    }
    if (shot) {
      return "Heard a Noise in distance";
    }
    return "Arrow shot into darkness";
  }

  @Override
  public String shootNorth(int x) {
    System.out.printf("SHOOTING North %d \n", x);
    boolean shot;
    try {
      shot = model.shoot(x, Direction.NORTH);

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
      return "Successfully Moved" + direction;
    }
    else {
      return "Invalid Move";

    }
  }

  @Override
  public void playGame(Dungeon d) {
    view.makeVisible();
    view.refresh();
    while (!d.hasSolved() && !d.hasLost()) {
      view.refresh();
    }
    view.refresh();

  }
}
