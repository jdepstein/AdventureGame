package view;

import dungeon.ReadOnlyDungeon;
import dungeon.enums.Direction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
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
  private final JLabel updates;
  private JLabel[][] labels;
  private final  List<JTextField> popUpItems;
  private final List<JButton> buttons;
  private final List<JMenuItem> menuItems;
  private final JFrame newGamePopUP;
  private ReadOnlyDungeon model;

  private GamePanel dungeonPanel;
  private DescriptionPanel descriptionPanel;
  private JScrollPane scroll;

  private List<JButton> endButtons;
  private boolean isOver;
  private EndPanel endGamePopUp;
  private JLabel endLabel;


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
    if (this.model.getWidth() * 64 < 1000) {
      this.setMinimumSize(new Dimension(this.model.getWidth() * 64, 700));
    }
    else {
      this.setMinimumSize(new Dimension(this.model.getWidth() * 64 / 2, 700));
    }
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.updates = new JLabel();
    this.updates.setText("Nothing has been done yet");
    this.add(this.updates, BorderLayout.NORTH);

    this.setUp();
    GameMenu bar = new GameMenu(this.model);
    this.setJMenuBar(bar.getBar());
    this.popUpItems = bar.getTextFields();
    this.newGamePopUP = bar.getPopUp();
    this.newGamePopUP.setLocation(this.getWidth() / 2, this.getHeight() / 2);
    this.menuItems = bar.getMenu();
    this.buttons = bar.getButtons();

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
    this.endButtons.get(0).addActionListener(action);
    this.endButtons.get(2).addActionListener(action);
    menuItems.get(0).addActionListener(action);
    menuItems.get(2).addActionListener(action);
    buttons.get(1).addActionListener(action);
    for (JTextField text: this.popUpItems) {
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
    this.remove(scroll);
    this.remove(dungeonPanel);
    this.remove(descriptionPanel);
    this.endGamePopUp.setVisible(false);
    this.isOver = false;
    this.setUp();
  }

  @Override
  public void refresh() {
    this.repaint();
    if (this.model.hasLost() || this.model.hasSolved()) {
      if (!this.isOver) {
        if (this.model.hasLost()) {
          this.endLabel.setText("You Have Lost the Game");
        }
        if (this.model.hasSolved()) {
          this.endLabel.setText("You Have Won the Game");
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
        } else {
          t.setText("The Key Entered did not do anything");
        }
        f.setLast(e.getKeyChar());
      }
    });

    JLabel[][] caves = this.labels;
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
    for (JLabel[] row: this.labels) {
      for (JLabel cur: row) {
        cur.addMouseListener(m);
      }
    }
    ActionListener action = e -> {
      if (e.getActionCommand().equals("Restart")) {
        t.setText(f.restart());
        this.endGamePopUp.setVisible(false);
        this.isOver = false;
      }
      if (e.getActionCommand().equals("Create")) {
        try {
          this.checkValidGameValues();
          t.setText(f.createNewGame(Integer.parseInt(this.popUpItems.get(0).getText()),
                  Integer.parseInt(this.popUpItems.get(1).getText()),
                  Boolean.parseBoolean(this.popUpItems.get(2).getText()),
                  Integer.parseInt(this.popUpItems.get(3).getText()),
                  Integer.parseInt(this.popUpItems.get(4).getText()),
                  Integer.parseInt(this.popUpItems.get(5).getText())));

        } catch (IllegalArgumentException | IllegalStateException ex) {
          t.setText(ex.getMessage());
        }
        this.newGamePopUP.setVisible(false);
      }
    };
    this.endButtons.get(1).addActionListener(action);
    this.buttons.get(0).addActionListener(action);
    this.menuItems.get(1).addActionListener(action);

  }

  private void checkValidGameValues() {
    int counter = 0;
    try {
      for (JTextField text: this.popUpItems) {
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
                this.popUpItems.get(counter).getName(), this.popUpItems.get(counter).getText()));
      }
      else {
        throw new IllegalArgumentException(String.format(
                "Type mismatch passed for %s expected int value but got %s",
                this.popUpItems.get(counter).getName(), this.popUpItems.get(counter).getText()));
      }
    }
  }


  /**
   * Helper method that just creates a map for all the images that I will be loading.
   * @return The map of all the images being loaded
   */
  private Map<String, String> generateImageMap() {
    Map<String, String> imageMap = new HashMap<>();
    imageMap.put("S", "res/dungeon-images-bw/S.png");
    imageMap.put("E", "res/dungeon-images-bw/E.png");
    imageMap.put("W", "res/dungeon-images-bw/W.png");
    imageMap.put("N", "res/dungeon-images-bw/N.png");
    imageMap.put("SE", "res/dungeon-images-bw/ES.png");
    imageMap.put("EW", "res/dungeon-images-bw/EW.png");
    imageMap.put("NE", "res/dungeon-images-bw/NE.png");
    imageMap.put("NS", "res/dungeon-images-bw/NS.png");
    imageMap.put("SW", "res/dungeon-images-bw/SW.png");
    imageMap.put("NW", "res/dungeon-images-bw/WN.png");
    imageMap.put("SEW", "res/dungeon-images-bw/ESW.png");
    imageMap.put("NEW", "res/dungeon-images-bw/NEW.png");
    imageMap.put("NSE", "res/dungeon-images-bw/NES.png");
    imageMap.put("NSW", "res/dungeon-images-bw/SWN.png");
    imageMap.put("NSEW", "res/dungeon-images-bw/NESW.png");
    imageMap.put("black", "res/dungeon-images-bw/black.png");

    imageMap.put("Smell0","res/dungeon-images-bw/blank.png");
    imageMap.put("Smell1","res/dungeon-images-bw/stench01.png");
    imageMap.put("Smell2","res/dungeon-images-bw/stench02.png");

    imageMap.put("diamond","res/dungeon-images-bw/diamond.png");
    imageMap.put("ruby","res/dungeon-images-bw/ruby.png");
    imageMap.put("sapphire","res/dungeon-images-bw/emerald.png");
    imageMap.put("arrow","res/dungeon-images-bw/arrow-black.png");
    imageMap.put("Monster", "res/dungeon-images-bw/otyugh.png");
    return imageMap;
  }

  /**
   * Helper method for creating the panels and the fact that some will be rewritten when the game
   * is set to a new game.
   */
  private void setUp() {
    this.dungeonPanel = new GamePanel(this.model, this.generateImageMap());
    this.descriptionPanel = new DescriptionPanel(this.model, this.generateImageMap());
    this.labels = dungeonPanel.getLabels();
    this.dungeonPanel.setMaximumSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64));

    this.add(this.descriptionPanel, BorderLayout.SOUTH);

    this.scroll = new JScrollPane();
    this.scroll.setMaximumSize(new Dimension(this.model.getWidth() * 64,
            this.model.getHeight() * 64));
    this.scroll.setViewportView(this.dungeonPanel);
    this.setResizable(false);

    this.scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(this.scroll,BorderLayout.CENTER);

    this.endGamePopUp = new EndPanel();
    this.endButtons = this.endGamePopUp.getEndButtons();
    this.endLabel = this.endGamePopUp.getEndLabel();
    this.endGamePopUp.setLocation(this.getWidth() / 2, this.getHeight() / 2);
  }


}

