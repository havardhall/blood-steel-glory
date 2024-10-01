package no.uib.inf101.sample.controller;

import java.util.ArrayList;

import no.uib.inf101.sample.model.game.Move;
import no.uib.inf101.sample.model.game.Unit;

/*
* Interface that gives the appropriate methods to play the game.
* To be used by both the human and AI players.
*/
public interface IPlayer {
  /**
  * Gets the move to be made
  * @return the position of the move to be made
  */
  Move getMoveForUnit(Unit unit);
  
  /**
  * Gets the production to be set
  * @return a char representing the unit that should be produced
  */
  char getProductionChoice();
  
  /**
  * Gets the moves the player wishes to make
  * @return an arraylist containing the player's moves that turn
  */
  ArrayList<Move> getMoves();
  
  /**
  * Gets the units owned by the player
  */
  ArrayList<Unit> getUnitList();
  
  
}  
