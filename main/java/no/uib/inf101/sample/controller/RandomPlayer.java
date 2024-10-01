package no.uib.inf101.sample.controller;

import java.util.ArrayList;
import java.util.Random;

import no.uib.inf101.sample.model.game.Move;
import no.uib.inf101.sample.model.game.Tribe;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Class that makes an AI player that makes random moves.
* Should move or heal every unit if a movement is available to them. 
*/
public class RandomPlayer extends Player{
  private ArrayList<TilePosition> possibleMoves = new ArrayList<>();
  private Random randomGenerator = new Random();
  
  public RandomPlayer(int playerID, Tribe chosenTribe) {
    super(playerID, chosenTribe);
    this.possibleMoves = createPossibleMoves();
  }
  
  /**
  * Creates the list of possible moves, of which they will be randomly selected
  * @return a list of all the possible moves a unit might take
  */
  private ArrayList<TilePosition> createPossibleMoves(){
    ArrayList<TilePosition> moveList = new ArrayList<>();
    for(int i = -1; i < 2; i++){
      for(int j = -1; j < 2; j++){
        TilePosition move = new TilePosition(i, j);
        moveList.add(move);
      }   
    }
    return moveList; 
  }
  
  /**
  * The void player automatically ends it's turn without making any real moves
  * @return a Move object that is always the same position
  */
  @Override
  public Move getMoveForUnit(Unit unit) {
    TilePosition unitPosition = unit.getUnitPosition();
    int randomInt = this.randomGenerator.nextInt(this.possibleMoves.size());
    // Gets the change in movement
    TilePosition newPositionDelta = this.possibleMoves.get(randomInt);
    int newRow = unitPosition.row() + newPositionDelta.row();
    int newCol = unitPosition.col() + newPositionDelta.col();
    
    // Creates new position
    TilePosition newPosition = new TilePosition(newRow, newCol);
    return new Move(unitPosition, newPosition);
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
