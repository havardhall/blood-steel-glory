package no.uib.inf101.sample.model.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* Class is a continuation of the Grid class in sem1 - Tetris 
* Unknown Author
* Hentet: 17.04.2024
*/
public class MapGrid<E> implements IMapGrid<E>{
  private int rows;
  private int cols;
  private List<List<E>> map = new ArrayList<>();
  
  // Constructor
  public MapGrid(int rows, int cols, E defaultValue){
    this.rows = rows;
    this.cols = cols;
    
    for (int i = 0; i < this.rows; i++){
      List<E> newRow = new ArrayList<>();
      for(int j = 0; j < this.cols; j++){
        newRow.add(defaultValue);
      }
      this.map.add(newRow);
    }
  }
  
  public MapGrid(int rows, int cols){
    this(rows, cols, null);
  }
  
  /*
  * Getter for amount of grid rows
  */
  @Override
  public int rows() {
    return this.rows;
  }
  
  /*
  * Getter for number of grid columns
  */
  @Override
  public int cols() {
    return this.cols;
  }
  
  /*
  * Gets element from a tile given a tile's position
  */
  @Override
  public E get(TilePosition pos) {
    int row = pos.row();
    int col = pos.col();
    
    E element = this.map.get(row).get(col);
    return element;
  }
  
  /*
  * Checks if a given position is on the map
  */
  @Override
  public boolean positionIsOnMap(TilePosition pos) {
    int row = pos.row();
    int col = pos.col();
    
    if((row < 0) || ((this.rows() - 1) < row)){
      return false;
    }
    if((col < 0) || ((this.cols() - 1) < col)){
      return false;
    }
    return true;
  }
  
  /**
  * Creates an object representing the map tiles, 
  * and can be iterated over
  * @return an Iterable representing the map's tiles
  */
  @Override
  public Iterator<Tile<E>> iterator() {
    List<Tile<E>> iterMap = new ArrayList<>();
    for(int i = 0; i < rows; i++){
      for (int j = 0; j < cols; j++){
        TilePosition pos = new TilePosition(i, j);
        E value = get(pos);
        Tile<E> tile = new Tile<E>(pos, value);
        iterMap.add(tile);
      }
    }
    return iterMap.iterator();
  }
  
  /*
  * Sets a tile's position and value
  */
  @Override
  public void set(TilePosition pos, E value) {
    int row = pos.row();
    int col = pos.col();
    this.map.get(row).set(col, value);
  }    
}
