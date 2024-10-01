package no.uib.inf101.sample.model.map;

/**
* A class for storing a tile's position and terrain.
* @param tp is the tile's position on the game board
* @param value is the tile's value, will be the terrain or unit of the tile
*/
public record Tile<E> (TilePosition pos, E value){} 