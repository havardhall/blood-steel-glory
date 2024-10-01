package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class GameMapTest {
  private GameMap debugMap = GameMap.createDebugMap();
  /*
  * Checks that the default map is properly instantiated
  */
  @Test
  public void defaultMapTest(){
    GameMap defaultMap = GameMap.createDefaultMap();
    long mapSeed = defaultMap.getSeed();
    assertEquals(-405624861139231059L, mapSeed);
  }
  
  /*
  * Checks that the debug map is properly instantiated
  */
  @Test
  public void debugMapTest(){
    long mapSeed = this.debugMap.getSeed();
    assertEquals(6384110637409226728L, mapSeed);
  }
  
  /*
  * Checks that the scale is correct
  */
  @Test
  public void scaleTest(){
    assertEquals(0.8, this.debugMap.getScale());
  }
}
