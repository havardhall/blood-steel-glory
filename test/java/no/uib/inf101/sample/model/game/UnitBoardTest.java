package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* The debug map is 8 rows, 8 columns large
*/
public class UnitBoardTest{
  /*
  * Checks that units are spawned at the board properly
  */
  @Test
  public void spawnNewUnitTest(){
    UnitBoard board = UnitBoard.newUnitBoard(8,8);
    Player p1 = new Player(0, new Tribe('O'));
    // Checks that there is no unit before we spawn one
    assertEquals(null, board.get(new TilePosition(0, 0)));
    // Spawn the units and check the locations
    Unit u1 = board.spawnUnit(p1, 'W', new TilePosition(0, 0));
    Unit u2 = board.spawnUnit(p1, 'P', new TilePosition(1, 1));
    Unit u3 = board.spawnUnit(p1, 'E', new TilePosition(2, 2));
    assertEquals(u1, board.get(new TilePosition(0, 0)));
    assertEquals(u2, board.get(new TilePosition(1, 1)));
    assertEquals(u3, board.get(new TilePosition(2, 2)));
  }
  
  /*
  * Checks that the board is correctly updated on unit movement
  */
  @Test
  public void moveUnitOnBoardTest(){
    UnitBoard board = UnitBoard.newUnitBoard(8,8);
    Player p1 = new Player(0, new Tribe('O'));
    Unit u1 = board.spawnUnit(p1, 'W', new TilePosition(0, 0));
    TilePosition t1 = new TilePosition(0, 0);
    TilePosition t2 = new TilePosition(0, 1);
    
    // Check that the unit is occupying it's tile 
    assertEquals(u1, board.get(t1));
    
    // Check that next tile is empty
    assertEquals(null, board.get(t2));
    
    // Check that unit moved into the empty tile
    board.moveUnitOnBoard(t2, u1);
    assertEquals(u1, board.get(t2));
    
    // Check that the old tile is now empty
    assertEquals(null, board.get(t1));
  }
  
  /*
  * Checks that units are actually removed from the board when removed from the game
  */
  @Test
  public void removeUnitTest(){
    UnitBoard board = UnitBoard.newUnitBoard(8,8);
    Player p1 = new Player(0, new Tribe('O'));
    // Checks that there is no unit before we spawn one
    assertEquals(null, board.get(new TilePosition(0, 0)));
    // Spawn the unit and checks the location
    Unit u4 = board.spawnUnit(p1, 'W', new TilePosition(0, 0));
    assertEquals(u4, board.get(new TilePosition(0, 0)));
    // Removes the unit and checks again
    board.removeUnit(new TilePosition(0, 0));
    assertEquals(null, board.get(new TilePosition(0, 0)));
    
  }
}
