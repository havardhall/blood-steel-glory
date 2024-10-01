package no.uib.inf101.sample.view;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.game.City;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.map.MapDimension;
import no.uib.inf101.sample.model.map.Terrain;
import no.uib.inf101.sample.model.map.Tile;

public interface ViewableGameModel {
  /**
  * Gets the dimension of the board
  * @return 
  */
  MapDimension getDimension();
  
  /**
  * Iterates through the game board's tiles
  * @return an iterable that gives the tiles with the corresponding terrain when iterated over
  */
  Iterable<Tile<Terrain>> getTilesOnMap();
  
  /**
  * Iterates through the game board's units
  * @return an iterable that gives the tiles containing units
  */
  Iterable<Tile<Unit>> getUnitsOnMap();
  
  /**
  * Iterates through the game board's cities
  * @return an iterable that gives the tiles containing cities
  */
  Iterable<Tile<City>> getCitiesOnMap();
  
  /**
  * Gets a player's city
  * @param player - the player of which the city will be returned
  * @return a city
  */
  City getPlayerCity(Player player);
}
