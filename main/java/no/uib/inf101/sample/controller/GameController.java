package no.uib.inf101.sample.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import no.uib.inf101.sample.model.GameModel;
import no.uib.inf101.sample.model.MainMenu;
import no.uib.inf101.sample.model.game.GameState;
import no.uib.inf101.sample.model.map.TilePosition;
import no.uib.inf101.sample.view.GameView;
import no.uib.inf101.sample.view.PixelToTilePositionConverter;

/*
* A class for controlling the game
*/
public class GameController implements KeyListener, MouseListener{
  private GameModel gm;
  private GameView view;
  private PixelToTilePositionConverter ppt;
  private MainMenu mainMenu;
  
  public GameController(GameModel gm, GameView view, MainMenu mainMenu){
    this.gm = gm;
    this.view = view;
    this.ppt = view.getPPT();
    this.mainMenu = mainMenu;
    this.view.addKeyListener(this);
    this.view.addMouseListener(this);
    this.view.setFocusable(true);
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    // KEY EVENT IN MENU
    if(this.gm.getCurrenGameState() == GameState.MAIN_MENU){
      // Up arrow is pressed
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        this.mainMenu.moveInMenu(-1);
      }
      // Down arrow is pressed
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        this.mainMenu.moveInMenu(1);
      }
      // Enter is pressed
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        System.out.println("Selected item in menu.");
        this.mainMenu.selectMenuItem();
        if(this.mainMenu.getGameState() == GameState.ACTIVE_GAME){
          this.gm.newGame();
          this.view.repaint();
          this.gm.launchGameThread();
          new Thread(new GameModel(this.gm.getGBM())).run();
          
          this.view.repaint();
        }
      }
    }
    
    // KEY EVENT IN-GAME
    if(this.gm.getCurrenGameState() == GameState.ACTIVE_GAME){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        System.out.println("Human player ended their turn!");
        this.gm.endTurn(this.gm.getCurrentPlayer());
        this.view.repaint();
      }
      if(e.getKeyCode() == KeyEvent.VK_Q){
        System.out.println("Changed production to Warrior.");
        this.gm.getGBM().changeCityProduction('W');
      }
      if(e.getKeyCode() == KeyEvent.VK_W){
        System.out.println("Changed production to Phalanx.");
        this.gm.getGBM().changeCityProduction('P');
      }
      if(e.getKeyCode() == KeyEvent.VK_E){
        System.out.println("Changed production to Elite Regiment.");
        this.gm.getGBM().changeCityProduction('E');
      }
    }
    
    // KEY EVENT GAME OVER
    if(this.gm.getCurrenGameState() == GameState.GAME_OVER){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        System.out.println("Cleared the game and went back to menu.");
        this.gm.stopThread();
        this.gm.setGameState(GameState.MAIN_MENU);
        this.mainMenu.returnToMenu();
      }
    }
    this.view.repaint();
  }
  
  @Override
  // Left click
  public void mouseReleased(MouseEvent e) {
    if(this.gm.getCurrenGameState() == GameState.ACTIVE_GAME){
      if(e.getButton() == MouseEvent.BUTTON1){
        boolean isXOutsideGame = e.getX() < this.view.getGameXStart();
        boolean isYOutsideGame = e.getY() < this.view.getGameYStart();
        if(!(isXOutsideGame || isYOutsideGame)){
          // If mouse is within the game itself
          if(this.ppt.isMouseWithinGameBox(e.getX(), e.getY())){
            TilePosition unitPosition = this.ppt.getTileFromPixels(e.getX(), e.getY());
            if (this.gm.getGBM().selectUnit(unitPosition)){
              System.out.println("Selected a unit at: " + "row: " + unitPosition.row() + "col:" + unitPosition.col());
            }   
          }
        }
      }
      // Right click
      if(e.getButton() == MouseEvent.BUTTON3){
        if(this.ppt.isMouseWithinGameBox(e.getX(), e.getY())){
          TilePosition newPosition = this.ppt.getTileFromPixels(e.getX(), e.getY());
          if (this.gm.getGBM().moveUnit(newPosition)){
            System.out.println("Moved unit to: " + "row: " + newPosition.row() + "col:" + newPosition.col());
          }
        } 
      }
      this.view.repaint();
    }
  }
  
  // Unused methods below
  @Override
  public void keyTyped(KeyEvent event) {}
  @Override
  public void keyPressed(KeyEvent event) {}
  @Override
  public void mouseClicked(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void mousePressed(MouseEvent e) {}
}
