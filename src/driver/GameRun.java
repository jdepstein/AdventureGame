package driver;

import adventuregame.AdvController;
import adventuregame.AdvGameConsoleController;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import view.DungeonSwingView;
import view.IView;

import java.io.InputStreamReader;

/**
 * Test for the controller.
 */
public class GameRun {

  /**
   *The controller run.
   */
  public static void main(String[] args) {
    if (args.length != 0) {
      if (args.length < 6) {
        throw new IllegalArgumentException(
                String.format("Expected 6 arguments but got %d", args.length));
      }
      if (!args[3].equals("true") && !args[3].equals("false")) {
        throw new IllegalArgumentException(
                String.format("Expected True or False for arg3 and got %s", args[3]));
      }
      Dungeon dung = null;
      try {
        dung = new DungeonImpl(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]), Integer.parseInt(args[4]),
                "Jack", true, Integer.parseInt(args[5]));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      if (dung != null) {
        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;
        new AdvGameConsoleController(input, output, false).playGame(dung);
      }
    } else {
      Dungeon dung = new DungeonImpl(15,20, 25 , true,60,
           "Fred", true, 8);
      AdvController control = new AdvController(dung);
      IView view = new DungeonSwingView(dung.makeReadOnly());
      control.setView(view);
    }
  }
}
