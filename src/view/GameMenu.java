package view;

import dungeon.ReadOnlyDungeon;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;


/**
 * Testing around with this for now.
 */
public class GameMenu  {
  private final JMenuBar menuBar;
  private final List<JTextField> textFields;
  private final List<JButton> buttons;
  private final List<JMenuItem> menuItems;
  private final JPopupMenu popUp;


  /**
   * Testing around with this for now.
   */
  public GameMenu(ReadOnlyDungeon model) {
    this.menuBar = new JMenuBar();
    this.menuItems = new ArrayList<>();
    this.buttons = new ArrayList<>();
    this.textFields = new ArrayList<>();

    this.createMenuItem("New Game");
    this.createMenuItem("Restart");
    this.createMenuItem("Quit");
    popUp = new JPopupMenu();
    popUp.setLayout(new GridLayout(7,2));

    this.createLine("Width", Integer.valueOf(model.getWidth()).toString());
    this.createLine("Height", Integer.valueOf(model.getHeight()).toString());
    this.createLine("Wrapping", Boolean.valueOf(model.getWrapping()).toString());
    this.createLine("Interconnectivity", Integer.valueOf(model.getInterconnectivity()).toString());
    this.createLine("Item Percent", Integer.valueOf(model.getItemPercent()).toString());
    this.createLine("Monster Count", Integer.valueOf(model.getMonsterCount()).toString());

    this.createButton("Create");
    this.createButton("Exit");
    popUp.pack();

    JMenu x = new JMenu("Menu");
    for (JMenuItem item : this.menuItems) {
      x.add(item);
    }

    menuBar.add(x);
  }

  /**
   * Gets the MenuBar of the Menu.
   * @return the Menu Bar
   */
  public JMenuBar getBar() {
    return this.menuBar;
  }

  /**
   * Gets the List of all the Text Fields.
   * @return All the Text fields in the popup
   */
  public List<JTextField> getTextFields() {
    return this.textFields;
  }

  /**
   * Gets the List of the Buttons.
   * @return All the Buttons in the popup
   */
  public List<JButton> getButtons() {
    return this.buttons;
  }

  /**
   * Gets the Popup itself.
   * @return the popup
   */
  public JPopupMenu getPopUp() {
    return this.popUp;
  }

  /**
   * Gets the Menu items.
   * @return the list of all the menu items.
   */
  public List<JMenuItem>  getMenu() {
    return this.menuItems;
  }

  private void createLine(String name, String item) {
    JLabel label = new JLabel(name);
    JTextField field = new JTextField(item,5);
    field.setName(name);
    this.popUp.add(label);
    this.popUp.add(field);
    this.textFields.add(field);
  }

  private void createMenuItem(String name) {
    JMenuItem menuItem = new JMenuItem(name);
    menuItem.setActionCommand(name);
    this.menuItems.add(menuItem);
  }

  private void createButton(String name) {
    JButton button = new JButton(name);
    button.setActionCommand(name);
    this.popUp.add(button);
    this.buttons.add(button);
  }
}
