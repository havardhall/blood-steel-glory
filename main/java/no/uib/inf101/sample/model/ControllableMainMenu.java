package no.uib.inf101.sample.model;

public interface ControllableMainMenu {
  /**
  * Selects the selected menu item
  * @return a boolean if the item is actually selected
  */
  void selectMenuItem();
  
  /**
  * Moves in main menu
  * @param delta is change in menu item, should be +-1
  */
  void moveInMenu(int delta);
}