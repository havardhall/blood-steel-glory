package no.uib.inf101.sample.controller;

import java.util.ArrayList;

import no.uib.inf101.sample.model.game.Move;
import no.uib.inf101.sample.model.game.Tribe;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.map.TilePosition;

public class VoidPlayer extends Player {
  // Constructor
  public VoidPlayer(int playerID, Tribe chosenTribe) {
    super(playerID, chosenTribe); 
  }
  
  /**
  * The void player automatically ends it's turn without making any real moves
  * @param unit - the unit to get a move for
  * @return a Move object that is always the same position
  */
  @Override
  public Move getMoveForUnit(Unit unit) {
    TilePosition unitPosition = unit.getUnitPosition();
    // new TilePosition(5, 5)
    // new TilePosition(unitPosition.row(), unitPosition.col())
    return new Move(unitPosition, new TilePosition(unitPosition.row(), unitPosition.col()));
  }
  
  /**
  * Return a W every turn
  */
  @Override
  public char getProductionChoice() {
    return 'W';
  }
  
  /**
  * Gets a move for every unit the player has
  * for a void player it is the unit's own position
  */
  @Override
  public ArrayList<Move> getMoves() {
    ArrayList<Move> unitmoveList = new ArrayList<>();
    for (int i = 0; i < this.getUnitList().size(); i++){
      Unit unit = this.getUnitList().get(i);
      Move newMove = getMoveForUnit(unit);
      unitmoveList.add(newMove);
    }
    return unitmoveList;
  }
}
