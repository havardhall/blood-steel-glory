package no.uib.inf101.sample.model.game;

import java.util.HashMap;
import java.util.Map;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.MapGrid;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* A board that keeps track of the cities in the game
*/
public class CityBoard extends MapGrid<City>{
  private Map<Character, TilePosition> cityPositions = new HashMap<>();
  
  // Constructor
  private CityBoard(int rows, int cols){
    super(rows, cols);
  }
  
  /**
  * Gets the city positions without iterating through everything
  */
  public TilePosition getCityPosition(char cityController){
    return this.cityPositions.get(cityController);
  }
  
  /*
  * Creates a new city board.
  * Called upon when a new game is created
  */
  public static CityBoard newCityBoard(int rows, int cols){
    return new CityBoard(rows, cols);
  }
  
  /**
  * Create a new City
  * @param cityPosition - the new city's position
  * @param player - the player the city belongs to
  */
  public City newCity(TilePosition cityPosition, Player player){
    City newCity = new City(cityPosition, player);
    player.addToCityList(newCity);
    this.cityPositions.put(player.getTribe().getTribeCharacter(), cityPosition);
    placeCity(cityPosition, newCity);
    return newCity;
  }
  
  /**
  * Place a city on the map
  * ------ Helper method for newCity() ------
  * @param position - the new city's position
  * @param city - the city to be placed
  */
  private void placeCity(TilePosition cityPosition, City city){
    this.set(cityPosition, city);
  }
  
  /**
  * Removes a city from the board
  */
  public void removeCity(TilePosition cityposition, City city){
    Player cityOwner = city.getPlayer();
    cityOwner.removeFromCityList(city);
    this.set(cityposition, null);
    
  }
  
}
