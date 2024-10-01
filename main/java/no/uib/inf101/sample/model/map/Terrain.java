package no.uib.inf101.sample.model.map;

/*
* The terrain of a tile.
* Contains the name, a given symbol, 
* it's yield, passability and combat modifier.
* The default terrain before selection is missing terrain - ?.
*/
public class Terrain {
  private String terrainName = "Missing Terrain";
  private char terrainSymbol = '?';
  private Yield yield;
  private double combatModifier;
  private boolean passable = false;
  private int terrainTextureID;
  
  // Constructor
  private Terrain(String terrainName, char terrainSymbol, 
  Yield yield, double combatModifier, boolean passable, int textureID){
    
    this.terrainName = terrainName;
    this.terrainSymbol = terrainSymbol;
    this.yield = yield;
    this.combatModifier = combatModifier;
    this.passable = passable;   
    this.terrainTextureID = textureID;
  }
  
  /**
  * Method to create new terrain. Calls upon the constructor
  * to generate terrain of a certain type
  * @param terrainSymbol a cher representing the terrain to be made
  * @return a Terrain object of the terrain that was put into the method
  */
  public static Terrain newTerrain(char terrainSymbol){
    Terrain terrain = switch(terrainSymbol){
      // Sea
      case 'S' -> {
        String terrainName = "Sea";
        Yield terrainYield = new Yield(2, 0);
        double combatModifier = 0.5;
        boolean passable = true;
        int textureID = 1;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      // Forest
      case 'F' -> {
        String terrainName = "Forest";
        Yield terrainYield = new Yield(1, 1);
        double combatModifier = 1.25;
        boolean passable = true;
        int textureID = 2;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield,
        combatModifier, passable, textureID);
        yield t;
      }
      // Grasslands
      case 'G' -> {
        String terrainName = "Grasslands";
        Yield terrainYield = new Yield(2, 0);
        double combatModifier = 1;
        boolean passable = true;
        int textureID = 3;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      // Plains
      case 'P' -> {
        String terrainName = "Plains";
        Yield terrainYield = new Yield(1, 1);
        double combatModifier = 1;
        boolean passable = true;
        int textureID = 4;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      // Hills
      case 'H' -> {
        String terrainName = "Hills";
        Yield terrainYield = new Yield(0, 2);
        double combatModifier = 1.25;
        boolean passable = true;
        int textureID = 5;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      // Mountains
      case 'M' -> {
        String terrainName = "Mountains";
        Yield terrainYield = new Yield(0, 0);
        double combatModifier = 1.5;
        boolean passable = false;
        int textureID = 6;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      // Debug Terrain
      case '?' -> {
        String terrainName = "Missing Terrain";
        Yield terrainYield = new Yield(0, 0);
        double combatModifier = 1;
        boolean passable = true;
        int textureID = 0;
        Terrain t = new Terrain(terrainName, terrainSymbol, terrainYield, 
        combatModifier, passable, textureID);
        yield t;
      }
      default -> throw new IllegalArgumentException(
      "No available terrain for '" + terrainSymbol + "'");
    };
    return terrain;
  }
  
  public String getTerrainName(){
    return this.terrainName;
  }
  public char getTerrain(){
    return this.terrainSymbol;
  }
  public Yield getYield(){
    return this.yield;
  }
  public double getCombatModifier(){
    return this.combatModifier;
  }
  public boolean getPassable(){
    return this.passable;
  }
  // Gets the ID for the texture of the terrain to be used in view
  public int getTextureID(){
    return this.terrainTextureID;
  }
}
