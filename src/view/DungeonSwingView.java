package view;

import model.ReadOnlyDungeon;
import model.enums.Direction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 * This is the main view of the Dungeon Game. It will display a Map of the dungeon and its visited
 * Caves as well as the stats about the player what they cary as well as info about the current
 * cave the player is in at the moment. The player then will also have the ability to scroll
 * through the map if it is too large for the window and a menu to view info about the dungeon
 * itself. As well as the ability to restart the game.
 */
public class DungeonSwingView extends JFrame implements IView {
  private ReadOnlyDungeon model;
  private boolean isOver;

  private final JLabel updates;
  private final JFrame newGamePopUP;
  private JScrollPane scroll;

  private final EndPanel endGamePopUp;
  private DescriptionPanel descriptionPanel;
  private GamePanel dungeonPanel;
  private final GameMenu gameMenu;



  /**
   * Builds the Dungeon view with a Readonly model of the dungeon. It sets the scrollbar and the
   * initial state of the map and adding it to the JFrame.
   * @param board The Readonly Version of the Dungeon Model.
   * @throws IllegalArgumentException Null was passed for the model.
   */
  public DungeonSwingView(ReadOnlyDungeon board) {
    super("Dungeon Game");
    if (board == null) {
      throw new IllegalArgumentException("Null Passed");
    }
    this.model = board;
    this.setMinimumSize(new Dimension(6 * 64, 6 * 64 + 300));
    this.setMaximumSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64 + 300));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.updates = new JLabel();
    this.updates.setText("Nothing has been done yet");
    this.add(this.updates, BorderLayout.NORTH);

    this.setUp();
    this.gameMenu = new GameMenu(this.model);
    this.setJMenuBar(gameMenu.getBar());

    this.newGamePopUP = gameMenu.getPopUp();
    this.newGamePopUP.setLocation(this.model.getWidth() / 2, this.model.getHeight() / 2);

    this.endGamePopUp = new EndPanel();
    this.endGamePopUp.setLocation(this.model.getWidth() / 2, this.model.getHeight() / 2);

    ActionListener action = e -> {
      if (e.getActionCommand().equals("New Game")) {
        this.newGamePopUP.setVisible(true);
      }
      if (e.getActionCommand().equals("Quit")) {
        System.exit(0);
      }

      if (e.getActionCommand().equals("Exit")) {
        newGamePopUP.setVisible(false);
      }
    };
    this.endGamePopUp.getEndButtons().get(0).addActionListener(action);
    this.endGamePopUp.getEndButtons().get(2).addActionListener(action);
    this.gameMenu.getMenu().get(0).addActionListener(action);
    this.gameMenu.getMenu().get(2).addActionListener(action);
    gameMenu.getButtons().get(1).addActionListener(action);
    for (JTextField text: this.gameMenu.getTextFields()) {
      text.addActionListener(action);
    }
    this.pack();
  }

  @Override
  public void resetModel(ReadOnlyDungeon board) {
    if (board == null) {
      throw new IllegalArgumentException("Null Passed");
    }
    this.model = board;
    this.setMaximumSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64 + 300));
    this.setSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64 + 300));

    this.remove(scroll);
    this.remove(descriptionPanel);
    this.endGamePopUp.setVisible(false);
    this.newGamePopUP.setVisible(false);
    this.isOver = false;
    this.setUp();
    this.pack();
  }

  @Override
  public void refresh() {
    this.repaint();
    if (this.model.hasLost() || this.model.hasSolved()) {
      if (!this.isOver) {
        if (this.model.hasLost()) {
          this.endGamePopUp.getEndLabel().setText("You Have Lost the Game");
        }
        if (this.model.hasSolved()) {
          this.endGamePopUp.getEndLabel().setText("You Have Won the Game");
        }
        this.endGamePopUp.setVisible(true);
        this.isOver = true;
      }
    }
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }


  @Override
  public void setFeatures(Features f) {
    if (f == null ) {
      throw new IllegalArgumentException(
              "Null passed for f");

    }
    JLabel t = this.updates;
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        String dist = "1234567890";

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shoot(Integer.parseInt(Character.valueOf(f.lastHit()).toString()),
                    Direction.SOUTH));
          } else {
            t.setText(f.move(Direction.SOUTH));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shoot(Integer.parseInt(Character.valueOf(f.lastHit()).toString()),
                    Direction.NORTH));
          } else {
            t.setText(f.move(Direction.NORTH));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shoot(Integer.parseInt(Character.valueOf(f.lastHit()).toString()),
                    Direction.WEST));
          } else {
            t.setText(f.move(Direction.WEST));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shoot(Integer.parseInt(Character.valueOf(f.lastHit()).toString()),
                    Direction.EAST));
          } else {
            t.setText(f.move(Direction.EAST));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          t.setText(f.pickup());
        } else if (dist.contains(Character.valueOf(e.getKeyChar()).toString())) {
          t.setText("Recorded the distance of " + Character.valueOf(e.getKeyChar()).toString());
        }  else {
          t.setText("The Key Entered did not do anything");
        }
        f.setLast(e.getKeyChar());
      }
    });

    JLabel[][] caves = this.dungeonPanel.getLabels();
    MouseAdapter m = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        for (int y = 0; y < caves.length; y++) {
          for (int x = 0; x < caves[0].length; x++) {
            if (e.getComponent().getName().equals(String.format("(%d, %d)", x, y))) {
              t.setText(f.clickMove(x, y));
              y = caves.length;
              break;
            }
          }
        }
      }
    };
    for (JLabel[] row: this.dungeonPanel.getLabels()) {
      for (JLabel cur: row) {
        cur.addMouseListener(m);
      }
    }
    ActionListener action = e -> {
      if (e.getActionCommand().equals("Restart")) {
        f.restart();
        t.setText("Dungeon Rest");
        dungeonPanel.resetPanel();
        this.endGamePopUp.setVisible(false);
        this.newGamePopUP.setVisible(false);
        this.isOver = false;
      }
      if (e.getActionCommand().equals("Create")) {
        try {
          this.checkValidGameValues();
          t.setText(f.createNewGame(Integer.parseInt(
                  this.gameMenu.getTextFields().get(0).getText()),
                  Integer.parseInt(this.gameMenu.getTextFields().get(1).getText()),
                  Boolean.parseBoolean(this.gameMenu.getTextFields().get(2).getText()),
                  Integer.parseInt(this.gameMenu.getTextFields().get(3).getText()),
                  Integer.parseInt(this.gameMenu.getTextFields().get(4).getText()),
                  Integer.parseInt(this.gameMenu.getTextFields().get(5).getText())));

        } catch (IllegalArgumentException | IllegalStateException ex) {
          t.setText(ex.getMessage());
        }
        this.repaint();
        this.newGamePopUP.setVisible(false);
      }
    };
    this.endGamePopUp.getEndButtons().get(1).addActionListener(action);
    this.gameMenu.getButtons().get(0).addActionListener(action);
    this.gameMenu.getMenu().get(1).addActionListener(action);

  }

  private void checkValidGameValues() {
    int counter = 0;
    try {
      for (JTextField text: this.gameMenu.getTextFields()) {
        if (counter == 2) {
          if (!text.getText().equals("false") && !text.getText().equals("true")) {
            throw new NumberFormatException();
          }
        }
        else {
          Integer.valueOf(text.getText());
        }
        counter++;
      }
    }
    catch (NumberFormatException e) {
      if (counter == 2) {
        throw new IllegalArgumentException(
                String.format("Type mismatch passed for %s expected Boolean but got %s",
                        this.gameMenu.getTextFields().get(counter).getName(),
                        this.gameMenu.getTextFields().get(counter).getText()));
      }
      else {
        throw new IllegalArgumentException(String.format(
                "Type mismatch passed for %s expected int value but got %s",
                this.gameMenu.getTextFields().get(counter).getName(),
                this.gameMenu.getTextFields().get(counter).getText()));
      }
    }
  }


  /**
   * Helper method that just creates a map for all the images that I will be loading.
   * @return The map of all the images being loaded
   */
  private Map<String, String> generateImageMap() {
    Map<String, String> imageMap = new HashMap<>();
    imageMap.put("S", "res/dungeonImages/S.png");
    imageMap.put("E", "res/dungeonImages/E.png");
    imageMap.put("W", "res/dungeonImages/W.png");
    imageMap.put("N", "res/dungeonImages/N.png");
    imageMap.put("SE", "res/dungeonImages/ES.png");
    imageMap.put("EW", "res/dungeonImages/EW.png");
    imageMap.put("NE", "res/dungeonImages/NE.png");
    imageMap.put("NS", "res/dungeonImages/NS.png");
    imageMap.put("SW", "res/dungeonImages/SW.png");
    imageMap.put("NW", "res/dungeonImages/WN.png");
    imageMap.put("SEW", "res/dungeonImages/ESW.png");
    imageMap.put("NEW", "res/dungeonImages/NEW.png");
    imageMap.put("NSE", "res/dungeonImages/NES.png");
    imageMap.put("NSW", "res/dungeonImages/SWN.png");
    imageMap.put("NSEW", "res/dungeonImages/NESW.png");
    imageMap.put("black", "res/dungeonImages/black.png");

    imageMap.put("Smell0","res/dungeonImages/black.png");
    imageMap.put("Smell1","res/dungeonImages/stench01.png");
    imageMap.put("Smell2","res/dungeonImages/stench02.png");

    imageMap.put("diamond","res/dungeonImages/diamond.png");
    imageMap.put("ruby","res/dungeonImages/ruby.png");
    imageMap.put("sapphire","res/dungeonImages/emerald.png");
    imageMap.put("arrow","res/dungeonImages/arrow-black.png");
    imageMap.put("Monster", "res/dungeonImages/otyugh.png");
    return imageMap;
  }

  /**
   * Helper method for creating the panels and the fact that some will be rewritten when the game
   * is set to a new game.
   */
  private void setUp() {
    this.descriptionPanel = new DescriptionPanel(this.model, this.generateImageMap());
    this.add(this.descriptionPanel, BorderLayout.SOUTH);


    this.dungeonPanel = new GamePanel(this.model, this.generateImageMap());
    this.dungeonPanel.setMaximumSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64));


    this.scroll = new JScrollPane();
    this.scroll.setViewportView(this.dungeonPanel);
    this.scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(this.scroll,BorderLayout.CENTER);

  }


}

