package view;

import dungeon.ReadOnlyModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

/**
 * This is the main view of the Dungeon Game. It will display a Map of the dungeon and its visited
 * Caves as well as the stats about the player what they cary as well as info about the current
 * cave the player is in at the moment. The player then will also have the ability to scroll
 * through the map if it is too large for the window and a menu to view info about the dungeon
 * itself. As well as the ability to restart the game.
 */
public class DungeonSwingView extends JFrame implements IView {
  private final JLabel updates;
  private JScrollPane scroll;
  private JLabel[][] labels;

  /**
   * Builds the Dungeon view with a Readonly model of the dungeon. It sets the scrollbar and the
   * initial state of the map and adding it to the JFrame.
   * @param board The Readonly Version of the Dungeon Model.
   * @throws IllegalArgumentException Null was passed for the model.
   */
  public DungeonSwingView(ReadOnlyModel board) {
    super("Dungeon Game");
    if (board == null) {
      throw new IllegalArgumentException("Null Passed");
    }
    this.setSize(500, 500);
    this.setMinimumSize(new Dimension(500, 500));
    this.setMinimumSize(new Dimension(500, 500));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    GamePanel dungeonPanel = new GamePanel(board, this.generateImageMap());
    DescriptionPanel description = new DescriptionPanel(board, this.generateImageMap());
    this.labels = dungeonPanel.getLabels();

    this.add(description, BorderLayout.SOUTH);
    updates = new JLabel();
    updates.setText("Nothing has been done yet");
    this.add(updates, BorderLayout.NORTH);

    this.scroll = new JScrollPane();
    scroll.setViewportView(dungeonPanel);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(scroll);
    JMenuBar bar = new GameMenu(board).getBar();
    this.setJMenuBar(bar);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
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
      }
    });
    JLabel[][] caves = this.labels;
    MouseListener m = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println(e.getComponent());
        for (int y = 0; y < caves.length; y++) {
          for (int x = 0; x < caves[0].length  ; x++) {
            if (e.getComponent().getName().equals(String.format("(%d, %d)", x, y))) {
              f.clickMove(x,y);
              y = caves.length;
              break;
            }
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    };

    for (JLabel[] row: this.labels) {
      for (JLabel cur: row) {
        cur.addMouseListener(m);
      }
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
}

