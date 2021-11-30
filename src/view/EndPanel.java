package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Is the JFrame that will appear at the end of the game that ask the user if the wish to quit
 * restart from the original state or create a new game with some new values for the information
 * or the same information and just set up a completely new dungeon.
 */
public class EndPanel extends JFrame {
  private final List<JButton> endButtons;
  private final JLabel endLabel;

  /**
   * Creates an end Frame this is just a frame that has 3 buttons, and it also sets up our list of
   * buttons plus the label attached that will be used to tell if it's a win or a loss.
   */
  public EndPanel() {
    this.setLayout(new FlowLayout());
    this.setMinimumSize(new Dimension(500, 75));
    this.setResizable(false);
    this.endButtons = new ArrayList<>();
    JButton quit = new JButton();
    quit.setText("Quit");
    this.endButtons.add(quit);
    JButton restart = new JButton();
    restart.setText("Restart");
    this.endButtons.add(restart);
    JButton newGame = new JButton();
    newGame.setText("New Game");
    this.endButtons.add(newGame);

    this.endLabel = new JLabel();
    this.add(endLabel);
    this.add(quit);
    this.add(restart);
    this.add(newGame);
  }

  public List<JButton> getEndButtons() {
    return this.endButtons;
  }

  public JLabel getEndLabel() {
    return this.endLabel;
  }
}
