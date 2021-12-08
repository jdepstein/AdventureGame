package view;

import model.ReadOnlyDungeon;

/**
 * The View interface is very simple and is just for general methods of making the actual window
 * visible refreshing the window. As well as setting the features for up so the action listeners
 * work properly.
 */
public interface IView {


  /**
   * Rests the model and view to its new readOnly model that was passed in.
   * @param board the new mode we are looking at.
   */
  void resetModel(ReadOnlyDungeon board);

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();


  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();


  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  void setFeatures(Features f);



}
