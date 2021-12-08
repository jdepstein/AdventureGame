package view;

import model.ReadOnlyDungeon;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * THis is the panel for the menu of the Frame this menu has the ability to create a popUp
 * menu for if the user wants to create a new Game. THe ability to exit the window overall
 * The ability to restart the game from its initial state. This class just builds up the menu
 * bar itself as well as the popUp window that can appear from the menu bar.
 */
class GameMenu  {
  private final JMenuBar menuBar;
  private final List<JTextField> textFields;
  private final List<JButton> buttons;
  private final List<JMenuItem> menuItems;
  private final JFrame popUp;

  /**
   * Builds the Game menu Bar that we need for the info and sets all the info in the popUP
   * to the initial state of the values in the model.
   * @param model The model that is being used for info being accessed.
   */
  GameMenu(ReadOnlyDungeon model) {
    this.menuBar = new JMenuBar();
    this.menuItems = new ArrayList<>();
    this.buttons = new ArrayList<>();
    this.textFields = new ArrayList<>();

    this.createMenuItem("New Game");
    this.createMenuItem("Restart");
    this.createMenuItem("Quit");
    popUp = new JFrame();
    popUp.setLayout(new GridLayout(7,2));
    popUp.setMinimumSize(new Dimension(200,200));
    popUp.setResizable(false);

    this.createLine("Width", Integer.valueOf(model.getWidth()).toString());
    this.createLine("Height", Integer.valueOf(model.getHeight()).toString());
    this.createLine("Wrapping", Boolean.valueOf(model.getWrapping()).toString());
    this.createLine("Interconnectivity", Integer.valueOf(model.getInterconnectivity()).toString());
    this.createLine("Item Percent", Integer.valueOf(model.getItemPercent()).toString());
    this.createLine("Monster Count", Integer.valueOf(model.getMonsterCount()).toString());

    this.createButton("Create");
    this.createButton("Exit");

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
  JMenuBar getBar() {
    return this.menuBar;
  }

  /**
   * Gets the List of all the Text Fields.
   * @return All the Text fields in the popup
   */
  List<JTextField> getTextFields() {
    return this.textFields;
  }

  /**
   * Gets the List of the Buttons.
   * @return All the Buttons in the popup
   */
  List<JButton> getButtons() {
    return this.buttons;
  }

  /**
   * Gets the Popup itself.
   * @return the popup
   */
  JFrame getPopUp() {
    return this.popUp;
  }

  /**
   * Gets the Menu items.
   * @return the list of all the menu items.
   */
  List<JMenuItem>  getMenu() {
    return this.menuItems;
  }

  /**
   * Creates a line in the popupMenu.
   * @param name the name of the item being added
   * @param item the initial state of the text box
   */
  private void createLine(String name, String item) {
    JLabel label = new JLabel(name);
    JTextField field = new JTextField(item,5);
    field.setName(name);
    this.popUp.add(label);
    this.popUp.add(field);
    this.textFields.add(field);
  }

  /**
   * Creates a menu item with the given name.
   * @param name the name of the menu item.
   */
  private void createMenuItem(String name) {
    JMenuItem menuItem = new JMenuItem(name);
    menuItem.setActionCommand(name);
    this.menuItems.add(menuItem);
  }

  /**
   * Creates a button with the given name.
   * @param name the name of the button.
   */
  private void createButton(String name) {
    JButton button = new JButton(name);
    button.setActionCommand(name);
    this.popUp.add(button);
    this.buttons.add(button);
  }
}
