package no.uib.inf101.sample.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TerrainTest {
  /*
  * Checks that all terrain types are properly instantiated
  */
  @Test
  public void checkSea(){
    Terrain t1 = Terrain.newTerrain('S');
    assertEquals("Sea", t1.getTerrainName());
    int food = t1.getYield().getFood();
    int production = t1.getYield().getProd();
    assertEquals(2, food);
    assertEquals(0, production);
    assertEquals(0.5, t1.getCombatModifier());
    assertEquals(true, t1.getPassable());
  }
  @Test
  public void checkGrasslands(){
    Terrain t2 = Terrain.newTerrain('G');
    assertEquals("Grasslands", t2.getTerrainName());
    int food = t2.getYield().getFood();
    int production = t2.getYield().getProd();
    assertEquals(2, food);
    assertEquals(0, production);
    assertEquals(1, t2.getCombatModifier());
    assertEquals(true, t2.getPassable());
  }
  @Test
  public void checkPlains(){
    Terrain t3 = Terrain.newTerrain('P');
    assertEquals("Plains", t3.getTerrainName());
    int food = t3.getYield().getFood();
    int production = t3.getYield().getProd();
    assertEquals(1, food);
    assertEquals(1, production);
    assertEquals(1, t3.getCombatModifier());
    assertEquals(true, t3.getPassable());
  }
  @Test
  public void checkForest(){
    Terrain t4 = Terrain.newTerrain('F');
    assertEquals("Forest", t4.getTerrainName());
    int food = t4.getYield().getFood();
    int production = t4.getYield().getProd();
    assertEquals(1, food);
    assertEquals(1, production);
    assertEquals(1.25, t4.getCombatModifier());
    assertEquals(true, t4.getPassable());
  }
  @Test
  public void checkHills(){
    Terrain t5 = Terrain.newTerrain('H');
    assertEquals("Hills", t5.getTerrainName());
    int food = t5.getYield().getFood();
    int production = t5.getYield().getProd();
    assertEquals(0, food);
    assertEquals(2, production);
    assertEquals(1.25, t5.getCombatModifier());
    assertEquals(true, t5.getPassable());
  }
  @Test
  public void checkMountains(){
    Terrain t6 = Terrain.newTerrain('M');
    assertEquals("Mountains", t6.getTerrainName());
    int food = t6.getYield().getFood();
    int production = t6.getYield().getProd();
    assertEquals(0, food);
    assertEquals(0, production);
    assertEquals(1.5, t6.getCombatModifier());
    assertEquals(false, t6.getPassable());
  }
  @Test
  public void checkMissingTerrain(){
    Terrain t7 = Terrain.newTerrain('?');
    assertEquals("Missing Terrain", t7.getTerrainName());
    int food = t7.getYield().getFood();
    int production = t7.getYield().getProd();
    assertEquals(0, food);
    assertEquals(0, production);
    assertEquals(1, t7.getCombatModifier());
    assertEquals(true, t7.getPassable());
  }
}