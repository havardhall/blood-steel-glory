package no.uib.inf101.sample.model.game;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.MapGrid;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Class to keep track of all the units on the board.
* This is to make sure that combat and movement goes smoothly
*/
public class UnitBoard extends MapGrid<Unit> implements UnitFactory{
  private UnitBoard(int rows, int cols) {
    super(rows, cols);
  }
  
  // Constructor
  public static UnitBoard newUnitBoard(int rows, int cols){
    return new UnitBoard(rows, cols);
  }
  
  /**
  * Spawns a new unit at the current Player's city
  * @param player the player that the unit belongs to
  * @param unit a char representing the type of unit to be spawned
  * @param unitPosition a TilePosition object saying where to spawn it
  * @return the unit that spawned
  */
  public Unit spawnUnit(Player player, char unit, TilePosition unitPosition){
    Unit newUnit = new Unit(player, unit, unitPosition);
    player.addToUnitList(newUnit);
    this.set(unitPosition, newUnit);
    return newUnit;
  }
  
  /* 
  * Sets a position on the board
  */
  public void moveUnitOnBoard(TilePosition unitPosition, Unit unit){
    TilePosition oldPosition = unit.getUnitPosition();
    unit.moveUnitToPosition(unitPosition);
    this.set(unitPosition, unit);
    if(this.get(oldPosition).equals(unit)){
      this.set(oldPosition, null);
    }
  }
  
  /**
  * Removes a unit from the board by setting it to null
  * @param unitPosition the position of the unit to be removed
  */
  public void removeUnit(TilePosition unitPosition){
    this.set(unitPosition, null);
  }
}
