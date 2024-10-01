package no.uib.inf101.sample.model;

import java.util.ArrayList;

import no.uib.inf101.sample.model.game.GameState;

/*
* A class containing the main menu
*/
public class MainMenu implements ControllableMainMenu{
  private volatile GameState gameState = GameState.MAIN_MENU;
  private final ArrayList<String> menu = new ArrayList<>();
  private int menuIndex = 0; 
  private String currentSelection = "Start Game";
  
  public MainMenu(){
    if (this.gameState == GameState.MAIN_MENU){
      menu.add("Start Game");
    }
  }
  
  /*
  * Makes the menu reappear
  */
  public void returnToMenu(){
    this.gameState = GameState.MAIN_MENU;
  }
  
  /*
  * Moves between the different menu items
  */
  @Override
  public void moveInMenu(int delta) {
    int newIndex = this.menuIndex;
    newIndex += delta;
    if(newIndex > menu.size()-1){
      newIndex = 0;
    }
    if (newIndex < 0){
      newIndex = menu.size()-1;
    }
    this.menuIndex = newIndex;
    this.currentSelection = menu.get(this.menuIndex);
    System.out.println(this.currentSelection);
  }
  
  /*
  * Selects an item from the menu
  */
  @Override
  public void selectMenuItem(){
    switch(this.currentSelection){
      case "Start Game" ->{
        startGame();
      }
      default -> throw new IllegalArgumentException(
      "No available menu item for '" + this.currentSelection + "'");
    };
  }
  
  /*
  * Starts the game
  */
  public void startGame(){
    this.gameState = GameState.ACTIVE_GAME;
  }
  
  // Gets the menu items to be drawn in view
  public ArrayList<String> getMenuItems(){
    return this.menu;
  }
  
  // Gets the game state
  public GameState getGameState(){
    return this.gameState;
  }
  
  // Helper method for TetrisController
  public String getSelection(){
    return this.currentSelection;
  }
  
  // Gets the menu index for marking selected Menu Item
  public int getMenuIndex(){
    return menuIndex;
  }
}



