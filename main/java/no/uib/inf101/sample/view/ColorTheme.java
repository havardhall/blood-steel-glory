package no.uib.inf101.sample.view;

import java.awt.Color;
import java.awt.Font;

/*
* Sets the aestethics of the game.
* There will ever only be one theme.
*/
public interface ColorTheme {
  /**
  * Gets the color of the background (the background behind the game)
  * @return the color of the background
  * --MUST NOT BE NULL--
  */
  Color getBackgroundColor();
  
  /**
  * The color displayed behind an icon on the map
  * @return the void color
  * --MUST BE TRANSPARENT--
  */
  Color getVoidColor();
  
  /**
  * The color displayed when a tile is selected
  * @return the selection color
  * --MUST BE SOMEWHAT TRANSPARENT--
  */
  Color getSelectedTileColor();
  
  /**
  * Gets the primary color
  * --MUST NOT BE TRANSPARENT--
  */
  Color getPrimaryColor();
  
  /**
  * Gets the secondary color
  * --MUST NOT BE TRANSPARENT--
  */
  Color getSecondaryColor();
  
  /**
  * Gets the tertiary color
  * --MUST NOT BE TRANSPARENT--
  */
  Color getTertiaryColor();
  
  /*
  * Gets the font for the game
  */
  Font getUIFont();
  
  /*
  * Sets the font for the menu
  */
  Font getMenuFont();
  
  /*
  * Gets the game's font color
  */
  Color getFontColor();
} 
