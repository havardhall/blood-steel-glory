package no.uib.inf101.sample.model;

/*
* Determines the functions of a unit 
*/
public interface MovableUnit {
  /**
  * Moves a unit on the map
  * @params are the change in row and column
  * @return a boolean determining that the actually actually moved
  */
  boolean moveUnit(int deltaRow, int deltaCol);
  
}
