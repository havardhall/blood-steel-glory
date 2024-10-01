package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.game.City;
import no.uib.inf101.sample.model.game.CityBoard;
import no.uib.inf101.sample.model.game.GameMap;
import no.uib.inf101.sample.model.game.Tribe;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.game.UnitBoard;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Debug map mountain coordinates:
* (0,3), (1,3), (5,4), (6,4)
*/
public class GameBoardModelTest {
  /*
  * Checks that a unit can be selected properly, 
  * and that a player can only select units they own
  */
  @Test
  public void selectUnitTest(){
    // Sets up test map
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    
    // Check that you can select your own unit 
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(0, 0);
    // Should return false if tile is empty
    assertFalse(model.selectUnit(t1));
    model.getUnitBoard().spawnUnit(p1, 'W', t1);
    // Should return true if tile is not empty
    assertTrue(model.selectUnit(t1));
    
    // Check that one can not select another player's unit
    Player p2 = new Player(1, new Tribe('R'));
    TilePosition t2 = new TilePosition(1, 1);
    model.getUnitBoard().spawnUnit(p2, 'W', t2);
    assertFalse(model.selectUnit(t2));
    
    // Should return true since the unit owner is currently selected
    model.setCurrentPlayer(p2);
    assertTrue(model.selectUnit(t2));
  }
  
  /*
  * Checks that a unit is deselected properly
  */
  @Test
  public void deSelectUnitTest(){
    // Sets up test map
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    
    // Sets the current player and unit
    Player p1 = new Player(0, new Tribe('R'));
    TilePosition t1 = new TilePosition(0, 0);
    model.setCurrentPlayer(p1);
    
    // Should be null if no unit is currently selected
    assertEquals(null, model.getCurrentlySelectedUnit());
    
    // Checks that the currently selected unit is correct
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'W', t1);
    model.selectUnit(t1);
    assertEquals(u1, model.getCurrentlySelectedUnit());
    
    // Checks that a unit is properly deselected
    model.deSelectUnit();
    assertEquals(null, model.getCurrentlySelectedUnit());
  }
  
  /*
  * Checks that units move on the map, can not move into impassable terrain,
  * and can not move out of the map
  */
  @Test 
  public void moveUnitTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    Player p1 = new Player(0, new Tribe('R'));
    TilePosition t1 = new TilePosition(2, 3);
    model.setCurrentPlayer(p1);
    model.getUnitBoard().spawnUnit(p1, 'W', t1);
    model.selectUnit(t1);
    
    // Checks that isNewPositionValid() activates
    TilePosition t2 = new TilePosition(1, 3);
    assertFalse(model.moveUnit(t2));
    
    // Checks that checkMovementCost() activates
    TilePosition t3 = new TilePosition(4, 3);
    assertFalse(model.moveUnit(t3));
    
    // Can move to a new position
    TilePosition t4 = new TilePosition(3, 4);
    assertEquals(null, model.getUnitBoard().get(t4));
    assertTrue(model.moveUnit(t4));
    assertEquals(model.getCurrentlySelectedUnit(), model.getUnitBoard().get(t4));
    
    // Checks that old position is null
    assertEquals(null, model.getUnitBoard().get(t1));
    
    // Move to the same position
    model.getCurrentlySelectedUnit().newTurnReplenish();
    assertFalse(model.moveUnit(t4));
    assertEquals(model.getCurrentlySelectedUnit(), model.getUnitBoard().get(t4));
    model.defeatUnit(model.getCurrentlySelectedUnit());
    
    // Check that attackCity() activates
    Player p2 = new Player(1, new Tribe('O'));
    TilePosition t5 = new TilePosition(4, 4);
    City c1 = new City(t5, p2);
    cityB.set(t5, c1);
    model.getUnitBoard().spawnUnit(p1, 'E', t4);
    model.selectUnit(t4);
    
    // Should return false if battle is lost, aka unable to move into tile
    assertFalse(model.moveUnit(t5));
    model.defeatUnit(model.getCurrentlySelectedUnit());
    cityB.removeCity(t5, c1);
    
    // Checks that attackUnit() activates
    model.getUnitBoard().spawnUnit(p1, 'P', t4);
    Unit u1 = model.getUnitBoard().spawnUnit(p2, 'P', t5);
    model.selectUnit(t4);
    
    assertFalse(model.moveUnit(t5));
    model.defeatUnit(u1);
    
    // Checks that swapUnits() activates
    TilePosition t6 = new TilePosition(4, 4);
    model.getUnitBoard().spawnUnit(p1, 'W', t6);
    model.getCurrentlySelectedUnit().newTurnReplenish();
    assertTrue(model.moveUnit(t6));
  }
  
  /*
  * Checks that a new position that should not be 
  * a possible movement option is classified as such
  */
  @Test 
  public void isNewPositionValidTest(){
    // Sets up test map
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    
    // A regular tile - should be valid
    TilePosition t1 = new TilePosition(0, 0);
    // Rows out of bounds
    TilePosition t2 = new TilePosition(-1, 0);
    TilePosition t3 = new TilePosition(8, 0);
    // Columns out of bounds
    TilePosition t4 = new TilePosition(0, -1);
    TilePosition t5 = new TilePosition(0, 8);
    // Mountain tile
    TilePosition t6 = new TilePosition(0, 3);
    
    assertTrue(model.isNewPositionValid(t1));
    assertFalse(model.isNewPositionValid(t2));
    assertFalse(model.isNewPositionValid(t3));
    assertFalse(model.isNewPositionValid(t4));
    assertFalse(model.isNewPositionValid(t5));
    assertFalse(model.isNewPositionValid(t6));
  }
  
  /*
  * Checks that the cost of movement is calculated properly
  */
  @Test
  public void checkMovementCostTest(){
    // Sets up test map
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    Player p1 = new Player(0, new Tribe('R'));
    // (3,2) has open tiles all around - so low risk of environment poisoning test
    TilePosition t1 = new TilePosition(3, 2);
    model.setCurrentPlayer(p1);
    model.getUnitBoard().spawnUnit(p1, 'W', t1);
    model.selectUnit(t1);
    
    // Check that all movement options are valid
    TilePosition t2 = new TilePosition(2, 2);
    TilePosition t3 = new TilePosition(2, 3);
    TilePosition t4 = new TilePosition(3, 3);
    TilePosition t5 = new TilePosition(4, 3);
    TilePosition t6 = new TilePosition(4, 2);
    TilePosition t7 = new TilePosition(4, 1);
    TilePosition t8 = new TilePosition(3, 1);
    TilePosition t9 = new TilePosition(2, 1);
    assertTrue(model.checkMovementCost(t1));
    assertTrue(model.checkMovementCost(t2));
    assertTrue(model.checkMovementCost(t3));
    assertTrue(model.checkMovementCost(t4));
    assertTrue(model.checkMovementCost(t5));
    assertTrue(model.checkMovementCost(t6));
    assertTrue(model.checkMovementCost(t7));
    assertTrue(model.checkMovementCost(t8));
    assertTrue(model.checkMovementCost(t9));
    
    // Attempt to go out of bounds on movement
    TilePosition t10 = new TilePosition(1, 2);
    TilePosition t11 = new TilePosition(5, 2);
    TilePosition t12 = new TilePosition(3, 0);
    TilePosition t13 = new TilePosition(3, 4);
    assertFalse(model.checkMovementCost(t10));
    assertFalse(model.checkMovementCost(t11));
    assertFalse(model.checkMovementCost(t12));
    assertFalse(model.checkMovementCost(t13));
  }
  
  /*
  * Checks that a city on the board is friendly or antagonistic to the current player
  */
  @Test
  public void checkForFriendlyCityTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cb = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cb);
    Player p1 = new Player(0, new Tribe('R'));
    Player p2 = new Player(1, new Tribe('B'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    TilePosition t2 = new TilePosition(1, 1);
    cb.newCity(t1, p1);
    cb.newCity(t2, p2);
    
    // Checks that a tile has no city
    // Should return true
    assertTrue(model.checkForFriendlyCity(new TilePosition(0, 0)));
    
    // Check that a tile has friendly city
    assertTrue(model.checkForFriendlyCity(t1));
    
    // Check that tile has unfriendly city
    assertFalse(model.checkForFriendlyCity(t2));
  }
  
  /*
  * Checks that units can be swapped if they are moving into
  * each other's tiles when they both have movement
  */
  @Test
  public void swapUnitsTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    Player p1 = new Player(0, new Tribe('R'));
    TilePosition t1 = new TilePosition(2, 3);
    TilePosition t2 = new TilePosition(2, 4);
    model.setCurrentPlayer(p1);
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'W', t1);
    Unit u2 = model.getUnitBoard().spawnUnit(p1, 'W', t2);
    model.selectUnit(t1);
    
    // Return true if swap happened
    assertTrue(model.swapUnits(t2));
    model.selectUnit(t2);
    // Check that swap actually took place
    assertEquals(u1, model.getCurrentlySelectedUnit());
    assertEquals(u2, model.getUnitBoard().get(t1));
    
    // Return false if required movement is missing 
    assertFalse(model.swapUnits(t1));
    model.selectUnit(t2);
    // Control that units did not swap
    assertEquals(u1, model.getCurrentlySelectedUnit());
    assertEquals(u2, model.getUnitBoard().get(t1));
    
  }
  
  /*
  * Checks that combat goes the right way
  */
  @Test
  public void attackUnitTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    Player p1 = new Player(0, new Tribe('R'));
    Player p2 = new Player(1, new Tribe('B'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    TilePosition t2 = new TilePosition(2, 4);
    TilePosition t3 = new TilePosition(3, 4);
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'W', t1);
    Unit u2 = model.getUnitBoard().spawnUnit(p2, 'W', t2);
    Unit u3 = model.getUnitBoard().spawnUnit(p1, 'E', t3);
    model.selectUnit(t1);
    
    // Check that defender and attacker is alive if indecisive battle
    assertFalse(model.attackUnit(t2));
    assertTrue(u2.isAlive());
    assertTrue(u1.isAlive());
    
    // Check that attacker and defender stays put if failed assault
    assertEquals(t1, u1.getUnitPosition());
    assertEquals(t2, u2.getUnitPosition());
    
    // Check that attacker and defender loses hp
    assertTrue(u1.getHealthPoints() < 100);
    assertTrue(u2.getHealthPoints() < 100);
    
    // Check that attacker can win, and that it occupies the defenders tile
    model.selectUnit(t3);
    assertTrue(model.attackUnit(t2));
    assertEquals(t2, u3.getUnitPosition());
    
    // Check that attacker can die in an assault 
    model.defeatUnit(u1);
    model.defeatUnit(u2);
    model.defeatUnit(u3);
    Unit u4 = model.getUnitBoard().spawnUnit(p1, 'E', t1);
    Unit u5 = model.getUnitBoard().spawnUnit(p2, 'E', t2);
    u4.damageUnit(99);
    model.selectUnit(t1);
    assertFalse(model.attackUnit(t2));
    assertEquals(null, board.get(t1));
    
    // Check that the attacker wins and is not killed, even if it also "dies"
    // Hp should be 10 since it healed up from 0
    model.defeatUnit(u4);
    model.defeatUnit(u5);
    Unit u6 = model.getUnitBoard().spawnUnit(p1, 'E', t1);
    Unit u7 = model.getUnitBoard().spawnUnit(p2, 'E', t2);
    u6.damageUnit(99);
    u7.damageUnit(99);
    model.selectUnit(t1);
    
    // Check that unit wins
    assertTrue(model.attackUnit(t2));
    
    // Check that unit moved into defenders position
    assertEquals(null, board.get(t1));
    assertEquals(u6, board.get(t2));
    
    // Check that attacker healed
    assertEquals(10, u6.getHealthPoints());
  }
  
  /*
  * Checks that a city is assaulted properly
  */
  @Test
  public void attackCityTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cb = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cb);
    Player p1 = new Player(0, new Tribe('R'));
    Player p2 = new Player(1, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    TilePosition t2 = new TilePosition(2, 2);
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'E', t1);
    City c1 = cb.newCity(t2, p2);
    model.selectUnit(t1);
    
    // Check that defending city and attacker is alive if indecisive battle
    assertFalse(model.attackCity(t2));
    assertTrue(u1.isAlive());
    assertTrue(c1.isAlive());
    
    // Check that attacker stays put if failed assault
    assertEquals(t1, u1.getUnitPosition());
    
    // Check that attacker and city loses hp
    assertTrue(u1.getHealthPoints() < 100);
    assertTrue(c1.getCityHealthPoints() < 100);
    
    // Check that attacker can win, and that it occupies the city's tile
    model.defeatUnit(u1);
    model.defeatCity(c1);
    Unit u2 = model.getUnitBoard().spawnUnit(p1, 'W', t1);
    City c2 = cb.newCity(t2, p2);
    cb.get(t2).damageCity(99);
    model.selectUnit(t1);
    assertTrue(model.attackCity(t2));
    assertEquals(t2, u2.getUnitPosition());
    
    // Check that attacker can die in an assault 
    model.defeatUnit(u2);
    model.defeatCity(c2);
    Unit u3 = model.getUnitBoard().spawnUnit(p1, 'E', t1);
    City c3 = cb.newCity(t2, p2);
    u3.damageUnit(99);
    model.selectUnit(t1);
    assertFalse(model.attackCity(t2));
    assertEquals(null, board.get(t1));
    
    // Check that the attacker wins and is not killed, even if it also "dies"
    // Hp should be 10 since it healed up from 0
    model.defeatUnit(u3);
    model.defeatCity(c3);
    Unit u4 = model.getUnitBoard().spawnUnit(p1, 'E', t1);
    City c4 = cb.newCity(t2, p2);
    u4.damageUnit(99);
    c4.damageCity(99);
    model.selectUnit(t1);
    
    // Check that unit wins
    assertTrue(model.attackCity(t2));
    
    // Check that unit moved into the city's position
    assertEquals(null, board.get(t1));
    assertEquals(u4, board.get(t2));
    
    // Check that attacker healed
    assertEquals(10, u4.getHealthPoints());
  }
  
  /*
  * Check that a unit can be defeated during combat
  */
  @Test
  public void defeatUnit(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'W', t1);
    
    // Check that unit is in place before removal
    assertEquals(u1, board.get(t1));
    
    // Check that unit is gone after removal
    model.defeatUnit(u1);
    assertEquals(null, board.get(t1));
  }
  
  /*
  * Checks that a city can be defeated 
  */
  @Test
  public void defeatCityTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cb = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cb);
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    City c1 = cb.newCity(t1, p1);
    
    // Check that city is in place before defeat
    assertEquals(c1, cb.get(t1));
    
    // Check that city is gone after defeat
    model.defeatCity(c1);
    assertEquals(null, cb.get(t1));
  }
  
  /*
  * Checks that a city's occupation value changes correctly
  */
  @Test
  public void cityUnitOccupationControllerTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cb = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cb);
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    TilePosition t2 = new TilePosition(2, 2);
    Unit u1 = model.getUnitBoard().spawnUnit(p1, 'P', t1);
    City c1 = cb.newCity(t2, p1);
    model.selectUnit(t1);
    
    // Check that an empty city is not occupied
    model.cityUnitOccupationController(t2);
    assertFalse(c1.hasCityUnit());
    
    // Check that moving into city changes it to occupied
    model.moveUnit(t2);
    model.cityUnitOccupationController(t2);
    assertTrue(c1.hasCityUnit());
    
    // Check that moving out changes it to not occupied again
    u1.newTurnReplenish();
    model.moveUnit(t1);
    model.cityUnitOccupationController(t2);
    assertFalse(c1.hasCityUnit());
  }
  
  /*
  * Checks that a city's production can be changed properly
  */
  @Test
  public void changeCityProductionTest(){
    // Sets up test scenario
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cb = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cb);
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    TilePosition t1 = new TilePosition(2, 3);
    cb.newCity(t1, p1);
    
    // Checks that current production is a Warrior
    Unit u1 = model.getCityBoard().get(t1).getCurrentlyProducedItem();
    assertEquals('W', u1.getUnitSymbol());
    
    // Check that new production is a different item
    model.changeCityProduction('E');
    Unit u2 = model.getCityBoard().get(t1).getCurrentlyProducedItem();
    assertEquals('E', u2.getUnitSymbol());
  }
  
  /*
  * Checks that a player is properly set
  */
  @Test
  public void setPlayerTest(){
    // Sets up test map
    GameMap map = GameMap.createDebugMap();
    UnitBoard board = UnitBoard.newUnitBoard(8, 8);
    CityBoard cityB = CityBoard.newCityBoard(8, 8);
    GameBoardModel model = new GameBoardModel(map, board, cityB);
    
    // Checks that no player gives null
    assertEquals(null, model.getCurrentPlayer());
    
    // Checks that a new player is set
    Player p1 = new Player(0, new Tribe('R'));
    model.setCurrentPlayer(p1);
    assertEquals(p1, model.getCurrentPlayer());
  }
}
