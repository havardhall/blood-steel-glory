package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.controller.VoidPlayer;
import no.uib.inf101.sample.model.map.TilePosition;

public class CityBoardTest {
  /*
  * Check that a city can be placed on the city board
  */
  @Test
  public void newCityTest(){
    CityBoard board = CityBoard.newCityBoard(8, 8);
    TilePosition t1 = new TilePosition(1, 1);
    Player p1 = new VoidPlayer(0, new Tribe('R'));
    // Check that tile is empty before placing city
    assertEquals(null, board.get(t1));
    
    // Check that a city can be placed
    City c1 = board.newCity(t1, p1);
    assertEquals(c1, board.get(t1));
  }
  
  /*
  * Check that a city can be removed from the board
  */
  @Test
  public void removeCityTest(){
    CityBoard board = CityBoard.newCityBoard(8, 8);
    TilePosition t1 = new TilePosition(1, 1);
    Player p1 = new VoidPlayer(0, new Tribe('R'));
    
    // Check that a city is present at tile
    City c1 = board.newCity(t1, p1);
    assertEquals(c1, board.get(t1));
    
    // Check that a city can be removed
    board.removeCity(t1, c1);
    assertEquals(null, board.get(t1));
  }
  
}
