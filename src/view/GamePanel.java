package view;

import dungeon.Location;
import dungeon.ReadOnlyDungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * The Panel that holds the dungeon map it will hold a readonly version of the dungeon and update
 * the map as new locations are visited in the dungeon. When the Map is Built you can only
 * Caves and their connections but not the contents of the cave. If the Cave is not visited it will
 * appear as a black square on the map.
 */
class GamePanel extends JPanel {
  private final ReadOnlyDungeon board;
  private final Map<String, String> imageMap;
  private final JLabel[][] caves;

  /**
   * This take in our model that we will be pulling the info from as well as the image
   * map that will give us the correct cave image path for the type of connections we are looking
   * for.
   * @param model The readonly version of the mode.
   * @param imageMap The map of image paths for the caves.
   * @throws IllegalArgumentException Null was passed for the model or imageMap
   */
  GamePanel(ReadOnlyDungeon model, Map<String, String> imageMap) {
    if (model == null || imageMap == null) {
      throw new IllegalArgumentException("Null passed in for values");
    }
    this.board = model;
    this.imageMap = imageMap;
    this.setLayout(new GridLayout(model.getHeight(), model.getWidth(), 0,0));
    this.caves = new JLabel[model.getHeight()][model.getWidth()];
    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        this.caves[y][x] = new JLabel();
        this.caves[y][x].setName(String.format("(%d, %d)", x,y));
        this.add(this.caves[y][x]);

      }
    }
  }

  JLabel[][] getLabels() {
    return this.caves;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    try {
      BufferedImage img;
      ImageIcon imageIcon;
      for (int y = 0; y < board.getHeight(); y++) {
        for (int x = 0; x < board.getWidth(); x++) {
          if (board.hasVisited(x, y)) {
            img = ImageIO.read(new File(imageMap.get(board.getConnections(x, y))));
          } else {
            img = ImageIO.read(new File(imageMap.get("black")));
          }
          imageIcon = new ImageIcon(img);
          this.caves[y][x].setIcon(imageIcon);
          this.caves[y][x].setIconTextGap(0);
          this.caves[y][x].setVerticalAlignment(1);
          this.caves[y][x].setHorizontalTextPosition(JLabel.CENTER);
          this.caves[y][x].setVerticalTextPosition(JLabel.CENTER);

          if (new Location(x,y).equals(board.getPlayerLoc())) {
            this.caves[y][x].setForeground(Color.RED);
            this.caves[y][x].setText("X");
          }
          else {
            this.caves[y][x].setText("");
          }

        }
      }
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

}

