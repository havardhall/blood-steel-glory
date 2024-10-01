package no.uib.inf101.sample.view;

import no.uib.inf101.sample.model.map.TilePosition;

/*
* This class ended up being pretty small after some changes, 
* but GameView is already pretty large so I'll just leave it as a class
*/
public class PixelToTilePositionConverter {
  private GameView view;
  // having these variables here instead of calling upon view seemed like a good idea, 
  // but they broke the class completely
  // private double gameXStart;
  // private double gameYStart;
  
  public PixelToTilePositionConverter(GameView view){
    this.view = view;
  }
  
  /**
  * Gets a tile's position from the given coordinates
  * @param xValue is the given x value
  * @param yValue is the given y value
  * @return a TilePosition object to be used in the game's calculations
  */
  public TilePosition getTileFromPixels(double mouseX, double mouseY){
    //Removing the padding from the game box itself
    mouseX -= this.view.getGameXStart();
    mouseY -= this.view.getGameYStart();
    
    // Converts the mouse positions to Tile Positions with truncation
    // because of truncation we do not have to subtract 1 to get the right position
    int selectedCol = (int) ((mouseX / this.view.getTileSize()));
    int selectedRow = (int) ((mouseY / this.view.getTileSize()));
    
    TilePosition selectedTile = new TilePosition(selectedRow, selectedCol);
    return selectedTile;
  }
  
  /**
  * Checks if the mouse's position is within the actual game box
  * @param mouseX - the given x value
  * @param mouseY - the given y value
  * @return true if the mouse is within the game itself
  */
  public boolean isMouseWithinGameBox(double mouseX, double mouseY){
    //Removing the padding from the game box itself
    mouseX -= this.view.getGameXStart();
    mouseY -= this.view.getGameYStart();
    
    // Checks if mouse is lower than game box
    if(mouseX < 0 || mouseY < 0){
      return false;
    }
    // Checks if mouse is Larger than game box
    if((this.view.getGameWidth() < mouseX) || (this.view.getGameHeight() < mouseY)){
      return false;
    }
    return true;
  }
}
