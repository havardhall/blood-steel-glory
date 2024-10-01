package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.map.TilePosition;

/*
* Interface defining key controllable elements in the game
*/
public interface ControllableGameModel {
  /**
  * Selects a unit if the player matches the current player
  * @param tilePosition
  * @return a boolean saying if a unit was selected or not,
  * this is used for testing and sfx
  */
  boolean selectUnit(TilePosition tilePosition);
  
  /**
  * Moves a unit to a selected tile
  * @param newPosition
  * @return a boolean saying if a unit was moved or not,
  * this is used for testing and sfx
  */
  boolean moveUnit(TilePosition newPosition);
  
}
