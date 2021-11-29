package view;

import dungeon.ReadOnlyDungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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
public class DungeonSwingView extends JFrame implements IView, ActionListener {
  private final JLabel updates;
  private JLabel[][] labels;
  private final  List<JTextField> popUpItems;
  private final List<JButton> buttons;
  private final List<JMenuItem> menuItems;
  private final JPopupMenu popUp;
  private ReadOnlyDungeon model;
  private GamePanel dungeonPanel;
  private DescriptionPanel descriptionPanel;
  private JScrollPane scroll;
  private List<JButton> endGame;
  private boolean gameEnd;
  private JPopupMenu endPanel;


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
    this.setSize(500, 500);
    this.setMinimumSize(new Dimension(500, 500));
    this.setMinimumSize(new Dimension(500, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.updates = new JLabel();
    this.updates.setText("Nothing has been done yet");
    this.add(this.updates, BorderLayout.NORTH);

    this.setUp();
    GameMenu bar = new GameMenu(this.model);
    this.setJMenuBar(bar.getBar());
    this.popUpItems = bar.getTextFields();
    this.popUp = bar.getPopUp();
    this.menuItems = bar.getMenu();
    this.buttons = bar.getButtons();
    menuItems.get(0).addActionListener(this);
    menuItems.get(2).addActionListener(this);
    buttons.get(1).addActionListener(this);
    for (JTextField text: this.popUpItems) {
      text.addActionListener(this);
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
    this.endPanel.setVisible(false);
    this.gameEnd = false;
    this.setUp();
  }

  @Override
  public void refresh() {
    this.repaint();
    if (this.model.hasLost() || this.model.hasSolved()) {
      if (!this.gameEnd) {
        this.endPanel.setVisible(true);
        this.gameEnd = true;
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
    this.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed");
      }

      @Override
      public void keyPressed(KeyEvent e) {
        String dist = "1234567890";

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shootSouth(Integer.parseInt(Character.valueOf(f.lastHit()).toString())));
          }
          else {
            t.setText(f.moveSouth());

          }
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shootNorth(Integer.parseInt(Character.valueOf(f.lastHit()).toString())));
          }
          else {
            t.setText(f.moveNorth());
          }
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shootWest(Integer.parseInt(Character.valueOf(f.lastHit()).toString())));
          }
          else {
            t.setText(f.moveWest());
          }
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          if (dist.contains(Character.valueOf(f.lastHit()).toString())) {
            t.setText(f.shootEast(Integer.parseInt(Character.valueOf(f.lastHit()).toString())));
          }
          else {
            t.setText(f.moveEast());
          }
        }

        else if (e.getKeyCode() == KeyEvent.VK_P) {
          t.setText(f.pickup());
        }

        f.setLast(e.getKeyChar());

      }

      @Override
      public void keyReleased(KeyEvent e) {
        System.out.println("Key Released");
      }
    });
    JLabel[][] caves = this.labels;
    MouseListener m = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        for (int y = 0; y < caves.length; y++) {
          for (int x = 0; x < caves[0].length  ; x++) {
            if (e.getComponent().getName().equals(String.format("(%d, %d)", x, y))) {
              t.setText(f.clickMove(x,y));
              y = caves.length;
              break;
            }
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Enter");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        System.out.println("Mouse Exit");
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
        this.endPanel.setVisible(false);
        this.gameEnd = false;
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

        } catch (IllegalArgumentException ex) {
          t.setText(ex.getMessage());
        }
        this.popUp.setVisible(false);
      }
    };
    this.endGame.get(1).addActionListener(action);
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
    }  catch (NumberFormatException e) {
      if (counter == 2) {
        this.updates.setText(String.format(
                "Type mismatch passed for %s expected Boolean but got %s",
                this.popUpItems.get(counter).getName(), this.popUpItems.get(counter).getText()));
      }
      else {
        this.updates.setText(String.format(
                "Type mismatch passed for %s expected int value but got %s",
                this.popUpItems.get(counter).getName(), this.popUpItems.get(counter).getText()));
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("New Game")) {
      this.popUp.setVisible(true);
    }
    if (e.getActionCommand().equals("Quit")) {
      System.exit(0);
    }

    if (e.getActionCommand().equals("Exit")) {
      popUp.setVisible(false);
    }
  }

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
    return imageMap;
  }

  private void setUp() {
    this.dungeonPanel = new GamePanel(this.model, this.generateImageMap());
    this.descriptionPanel = new DescriptionPanel(this.model, this.generateImageMap());
    this.labels = dungeonPanel.getLabels();

    this.add(this.descriptionPanel, BorderLayout.SOUTH);

    this.scroll = new JScrollPane();
    this.scroll.setViewportView(this.dungeonPanel);
    this.scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(this.scroll,BorderLayout.CENTER);

    this.endGame = new ArrayList<>();
    JButton quit = new JButton();
    quit.setText("Quit");
    quit.addActionListener(this);
    this.endGame.add(quit);
    JButton restart = new JButton();
    restart.setText("Restart");
    this.endGame.add(restart);
    JButton newGame = new JButton();
    newGame.setText("New Game");
    newGame.addActionListener(this);
    this.gameEnd = false;
    this.endPanel = new JPopupMenu();
    this.endPanel.setLayout(new FlowLayout());
    this.endPanel.add(quit);
    this.endPanel.add(restart);
    this.endPanel.add(newGame);
    this.endPanel.setLocation(300,300);
  }


}

