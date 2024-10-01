package no.uib.inf101.sample.model.map;

public interface IMapGrid<E> extends MapDimension, Iterable<Tile<E>>{
  /**
  * Sets the value of a position on the map. A subsequent call to {@link #get}
  * with an equal position as argument will return the value which was set. The
  * method will overwrite any previous value that was stored at the location.
  * 
  * @param pos the position in which to store the value
  * @param value the new value
  * @throws IndexOutOfBoundsException if the position does not exist on the map
  */
  void set(TilePosition pos, E value);
  
  /**
  * Gets the current value at the given coordinate.
  * 
  * @param pos the position to get
  * @return the value stored at the position
  * @throws IndexOutOfBoundsException if the position does not exist on the map
  */
  E get(TilePosition pos);
  
  /**
  * Reports whether the position is within bounds for the map
  * 
  * @param pos position to check
  * @return true if the coordinate is within bounds, false otherwise
  */
  boolean positionIsOnMap(TilePosition pos);
}
