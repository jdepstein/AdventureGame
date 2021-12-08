package view;

import model.ReadOnlyDungeon;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Description Panel is a JPanel Extension that will show the info of the cave and the players
 * current state on the board. This Panel is used to display that info about the Caves current
 * contents as well as its Smell the look of the current cave as well. This also will Show the
 * players current contents they are carrying.
 */
class DescriptionPanel extends JPanel {
  List<List<JLabel>> labels;
  private final ReadOnlyDungeon model;
  private final Map<String, String> imageMap;

  /**
   * The Description Panel Requires a Map of the possible image path that can be used for the item
   * images that will be displayed. Then also the readOnly version of the model, so we can grab info
   * from the model thought the panel for updating when repainting.
   * @param model The readOnly version of our dungeon
   * @param imageMap The Map of all the image paths for loading
   * @throws IllegalArgumentException Null was passed for the model or imageMap
   */
  DescriptionPanel(ReadOnlyDungeon model, Map<String, String> imageMap) {
    if (model == null || imageMap == null) {
      throw new IllegalArgumentException("Null passed in for values");
    }

    this.imageMap = imageMap;
    this.model = model;
    this.setLayout(new GridLayout(1,3));
    this.labels = new ArrayList<>();
    labels.add(new ArrayList<>());
    labels.add(new ArrayList<>());
    labels.add(new ArrayList<>());
    JPanel caveItems = new JPanel(new GridLayout(5, 1));
    JPanel playerInfo = new JPanel(new GridLayout(5, 1));
    JPanel caveInfo = new JPanel(new GridLayout(4, 1));

    JLabel jLabel = new JLabel();
    jLabel.setText("Player Items");
    labels.get(0).add(jLabel);
    labels.get(0).add(new JLabel());
    labels.get(0).add(new JLabel());
    labels.get(0).add(new JLabel());
    labels.get(0).add(new JLabel());

    jLabel = new JLabel();
    jLabel.setText("Cave Items");
    labels.get(1).add(jLabel);
    labels.get(1).add(new JLabel());
    labels.get(1).add(new JLabel());
    labels.get(1).add(new JLabel());
    labels.get(1).add(new JLabel());

    jLabel = new JLabel();
    jLabel.setText("Cave Information");
    labels.get(2).add(jLabel);
    labels.get(2).add(new JLabel());
    labels.get(2).add(new JLabel());
    labels.get(2).add(new JLabel());

    for (JLabel j: labels.get(0)) {
      playerInfo.add(j);
    }
    for (JLabel j: labels.get(1)) {
      caveItems.add(j);
    }
    for (JLabel j: labels.get(2)) {
      caveInfo.add(j);
    }

    this.add(playerInfo);
    this.add(caveItems);
    this.add(caveInfo);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    addLabel("ruby", true, this.labels.get(0).get(1));
    addLabel("diamond", true, this.labels.get(0).get(2));
    addLabel("sapphire", true, this.labels.get(0).get(3));
    addLabel("arrow", true, this.labels.get(0).get(4));

    addLabel("ruby", false,this.labels.get(1).get(1));
    addLabel("diamond", false,this.labels.get(1).get(2));
    addLabel("sapphire", false,this.labels.get(1).get(3));
    addLabel("arrow", false,this.labels.get(1).get(4));

    try {
      BufferedImage img = ImageIO.read(new File(imageMap.get(model.getSmell())));
      ImageIcon imageIcon = new ImageIcon(img);
      labels.get(2).get(1).setText("Smell");

      labels.get(2).get(1).setIcon(imageIcon);
      img = ImageIO.read(new File(imageMap.get(model.getConnections(
              model.getPlayerLoc().getX(),
              model.getPlayerLoc().getY()))));
      imageIcon = new ImageIcon(img);
      labels.get(2).get(2).setText("Current Cave");
      labels.get(2).get(2).setIcon(imageIcon);

      if (this.model.hasMonster()) {
        img = ImageIO.read(new File(imageMap.get("Monster")));
        imageIcon = new ImageIcon(img);
        labels.get(2).get(3).setIcon(imageIcon);
        if (this.model.hasLivingMonster()) {
          labels.get(2).get(3).setText("Alive");
        }
        else {
          labels.get(2).get(3).setText("Dead");
        }
      }
      else {
        img = ImageIO.read(new File(imageMap.get("black")));
        imageIcon = new ImageIcon(img);
        labels.get(2).get(3).setText("No Monster");
        labels.get(2).get(3).setIcon(imageIcon);
      }
    } catch (IOException e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
    }

  }


  private void addLabel(String lookup, boolean player, JLabel jLabel) {
    Map<String, Integer> play = new HashMap<>();
    play.put("ruby", model.playerRuby());
    play.put("diamond", model.playerDiamond());
    play.put("sapphire", model.playerSapphire());
    play.put("arrow", model.playerArrow());
    Map<String, Integer> cave = new HashMap<>();
    cave.put("ruby", model.caveRuby());
    cave.put("diamond", model.caveDiamond());
    cave.put("sapphire", model.caveSapphire());
    cave.put("arrow", model.caveArrow());

    try {
      BufferedImage img = ImageIO.read(new File(imageMap.get(lookup)));
      ImageIcon imageIcon = new ImageIcon(img);
      if (player) {
        jLabel.setText(play.get(lookup).toString());
      }
      else {
        jLabel.setText(cave.get(lookup).toString());
      }
      jLabel.setIcon(imageIcon);


    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}

