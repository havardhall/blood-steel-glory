package no.uib.inf101.sample.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.uib.inf101.sample.model.OpenxSimplexNoise.OpenSimplex2S;
import no.uib.inf101.sample.model.map.MapGrid;
import no.uib.inf101.sample.model.map.Terrain;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Class that creates a map for the game using noisemap.
* Contains a string representing the map's terrain in chars.
* 
* The overall class is inspired by this video:
* https://www.youtube.com/watch?v=jv6YT9pPIHw&t=314s&ab_channel=BarneyCodes
* 
* The simplex noisemap code can be found at this github link:
* https://github.com/KdotJPG/OpenSimplex2
* 
* Code for the individual noise points and the simplex mapping was
* provided by chatGPT.
*/
public class GameMap extends MapGrid<Terrain>{
  private int rows;
  private int cols;
  private long seed;
  private List<List<Double>> noiseMap;
  private List<String> mapString = new ArrayList<>();
  // SCALE is the scale of how similiar values are in the noisemap
  // A really small one makes the map look like minecraft lol
  private final Double SCALE = 0.8;
  
  // Creates the map
  private GameMap(int rows, int cols, long seed){
    super(rows, cols);
    this.seed = seed;
    this.rows = rows;
    this.cols = cols;
    // A good seed should always be known to the developer
    System.out.println("Seed: " + this.seed);
    
    // Converts noisemap to terrain
    this.noiseMap = seedToNoisemap(this.seed);
    this.mapString = noisemapToCharmap(this.noiseMap);
    charmapToTerrainmap(this.mapString);
  }
  
  /**
  * Generates a new map using noisemapping.
  * Creates a new seed and calls the constructor.
  * @param rows,cols the dimension of the map
  * @return a GameMap object - A map of the game with terrain
  */
  public static GameMap newMap(int rows, int cols){
    Random random = new Random();
    long newSeed = random.nextLong();
    return new GameMap(rows, cols, newSeed);
  }
  
  /**
  * The default map.
  * Shown on game launch and is the default choice
  * @return a GameMap object - A map of the game with terrain
  */
  public static GameMap createDefaultMap(){
    // MapDimension should be 29 rows by 59 columns for full display
    int rows = 24;
    int cols = 40;
    long defaultSeed = -405624861139231059L;
    
    // Custom seed should be 18 digits long
    //long customSeed = 0000000000000000000L;
    return new GameMap(rows, cols, defaultSeed);
  }
  
  /**
  * The debug map.
  * A small map improves testing performance drastically
  * Used for debugging and testing.
  * @return a GameMap object - A map of the game with terrain
  */
  public static GameMap createDebugMap(){
    // MapDimension should be 8 rows by 8 columns
    int rows = 8;
    int cols = 8;
    long debugSeed = 6384110637409226728L;
    
    // Custom seed should be 18 digits long
    //long customSeed = 0000000000000000000L;
    return new GameMap(rows, cols, debugSeed);
  }
  
  /**
  * Creates a noise map from a given seed
  * @param seed is a long with the seed for the map
  * @return a neste list with doubles representing noise
  */
  private List<List<Double>> seedToNoisemap(long seed){
    List<List<Double>> noiseMap = new ArrayList<>();
    for(int i = 0; i < this.rows; i++){
      List<Double> newRow = new ArrayList<>();
      for(int j = 0; j < this.cols; j++){
        Double localNoise = newNoise(i, j);
        newRow.add(localNoise);
      }
      noiseMap.add(newRow);
    }
    return noiseMap;
  }
  
  /**
  * Creates a noise for the noise mapping
  * @param tilePosition is the 
  * @return a Double reresenting the noise in a tile
  */
  private Double newNoise(int a, int b){
    // We scale the integers
    a *= this.SCALE;
    b *= this.SCALE;
    
    float simplexNoise = OpenSimplex2S.noise2_ImproveX(this.seed, a, b);
    Double localNoise = (double) simplexNoise;
    return localNoise;
  }
  
  /**
  * Converts the noisemap to a map of chars
  * @param map of doubles
  * @return a list of Strings consisting of the maps chars
  */
  private List<String> noisemapToCharmap(List<List<Double>> map){
    List<String> stringList = new ArrayList<>();
    for (int i = 0; i < this.rows; i++){
      String stringRow = "";
      for (int j = 0; j < this.cols; j++){
        Double noise = getNoise(new TilePosition(i, j));
        char noiseChar = noiseToTerrainchar(noise);
        stringRow += noiseChar;
      }
      stringList.add(stringRow);
    }
    return stringList;
  }
  
  /**
  * Converts noise at a given point to a char representing a given terrain
  * ------ Helper method for noisemapToCharmap() -------
  * @param noise is the noise at a location
  * @return a char representing a terrain
  */
  private char noiseToTerrainchar(Double noise){
    char terrainChar = '?';
    // Sea
    if(noise < -0.4){
      terrainChar = 'S';
    }
    // Grasslands
    else if(noise < 0){
      terrainChar = 'G';
    }
    // Plains
    else if(noise < 0.3){
      terrainChar = 'P';
    }
    // Forest
    else if(noise < 0.5){
      terrainChar = 'F';
    }
    // Hills
    else if(noise < 0.80){
      terrainChar = 'H';
    }
    // Mountains
    else if(noise <= 1){
      terrainChar = 'M';
    }
    return terrainChar;
  }
  
  /**
  * Fills the map with the correct terrain
  * @param mapString is the map to be filled represented by a string
  */
  private void charmapToTerrainmap(List<String> mapString){
    for (int i = 0; i < this.rows(); i++){
      String s = mapString.get(i);
      for(int j = 0; j < this.cols(); j++){
        char c = s.charAt(j);
        this.set(new TilePosition(i, j), Terrain.newTerrain(c));
      }
    }
  }
  
  /**
  * Gets the noise at a particular location on the map
  * ------ Helper method for noisemapToCharmap ------
  * @param position the position of the tile
  * @return a double with the noise at the given position
  */
  public Double getNoise(TilePosition position){
    Double locationNoise = this.noiseMap.get(position.row()).get(position.col());
    return locationNoise;
  }
  
  /*
  * Field variable Getters below
  */
  public int getRows(){
    return this.rows;
  }
  public int getCols(){
    return this.cols;
  }
  public long getSeed(){
    return this.seed;
  }
  public List<List<Double>> getNoiseMap(){
    return this.noiseMap;
  }
  public List<String> getMapString(){
    return this.mapString;
  }
  public Double getScale(){
    return this.SCALE;
  }
}
