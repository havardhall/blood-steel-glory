package no.uib.inf101.sample.view;

import java.awt.geom.Rectangle2D;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Whole class is a copy of my own Lab4
* Heavily adjusted for a post-tetris project
*/

// Converts a tile's position to pixels
public class TilePositionToPixelConverter {
  private Rectangle2D box = new Rectangle2D.Double(0,0,0,0);
  private double tileSize;
  
  public TilePositionToPixelConverter(Rectangle2D rect, double tileSize){
    this.box = rect;
    this.tileSize = tileSize;
  }
  
  //Calculates location of a tile, calls TileToPixelConverter.getBoundsForTile
  public Rectangle2D getBoundsForTile(TilePosition tp) {
    // Calculate tile width and tile heigth 
    int col = tp.col();
    int row = tp.row();
    double startX = this.box.getX();
    double startY = this.box.getY();
    
    // Calculate XY coordinate of the tile
    double tileX = startX + (col * this.tileSize);
    double tileY = startY + (row * this.tileSize);
    
    Rectangle2D r2Bounds = new Rectangle2D.Double(tileX, tileY, this.tileSize, this.tileSize);
    return r2Bounds;
  }
}