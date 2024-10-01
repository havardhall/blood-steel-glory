package no.uib.inf101.sample.model;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameSize {
  private Dimension gameDimension;
  
  // Constructor for the game's dimension
  // Used for calculating position of the mouse among other things
  public GameSize(){
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension gameDimension = tk.getScreenSize();
    this.gameDimension = gameDimension;
  }
  
  // Returns the game's dimension
  public Dimension getGameDimension(){
    return this.gameDimension;
  }
}