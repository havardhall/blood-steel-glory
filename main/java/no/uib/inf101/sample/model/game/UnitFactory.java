package no.uib.inf101.sample.model.game;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* A interface for creating new units on the board
* Each unit is assigned to a player
*/

public interface UnitFactory {
  /**
  * Spawns the newly constructed unit
  * @param ownedByPlayer the player that owns the unit
  * @param symbol the unit's type
  * @param spawnPosition where the unit should spawn (the player's city)
  * @return the unit of the proper unit type
  */
  Unit spawnUnit(Player ownedByPlayer, char symbol, TilePosition spawnPosition);
  
}
