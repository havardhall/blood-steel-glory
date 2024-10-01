package no.uib.inf101.sample.view;

import java.awt.geom.Rectangle2D;

/*
* Class a carbon copy of my own class in sem1.
* Used to convert a list of menu items to rectangle2D objects
*/
public class MenuItemToPixelConverter {
  private Rectangle2D box = new Rectangle2D.Double(0,0,0,0);
  private double margin;
  private int menuSize;
  private int menuIndex;
  private static final int AMOUNT_OF_MENU_COLS = 1;
  
  // Constructor
  public MenuItemToPixelConverter(Rectangle2D rect, double db, int menuSize, int menuIndex){
    this.box = rect;
    this.margin = db;
    this.menuSize = menuSize;
    this.menuIndex = menuIndex;
  }
  
  /**
  * Calculates the bounds for the menu item in question
  * @param menuItem - the menu item to be selected
  * @return a Rectangle2D object with the correct parameters 
  */
  public Rectangle2D getBoundsForItem(int menuItem) {
    // Calculate menu item width and menu item heigth 
    double height = this.box.getHeight();
    double width = this.box.getWidth();
    double startX = this.box.getX();
    double startY = this.box.getY();
    double margin = this.margin;
    
    double heightSpace = (height - (margin * (this.menuSize + 1)));
    double widthSpace = (width - (margin * (AMOUNT_OF_MENU_COLS + 1)));
    double itemWidth = widthSpace / AMOUNT_OF_MENU_COLS;
    double itemHeight = heightSpace / this.menuSize;
    
    // Calculate XY coordinate of cell
    double cellX = startX;
    double cellY = startY + ((menuIndex + 1) * margin) + (menuItem * itemHeight);
    
    Rectangle2D r2Bounds = new Rectangle2D.Double(cellX, cellY, itemWidth, itemHeight);
    return r2Bounds;
  }
}