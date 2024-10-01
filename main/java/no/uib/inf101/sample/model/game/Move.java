package no.uib.inf101.sample.model.game;

import no.uib.inf101.sample.model.map.TilePosition;

/**
 * A record containing the moves for a unit
 * @param selectPosition is the unit's starting position
 * @param movePosition is the unit's new position
 */
public record Move (TilePosition selectPosition, TilePosition movePosition){}  
