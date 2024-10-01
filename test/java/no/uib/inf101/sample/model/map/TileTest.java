package no.uib.inf101.sample.model.map;

import org.junit.jupiter.api.Test;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
* Testing the class Tile
*/
public class TileTest {
  
  @Test
  void sanityTest() {
    String item = "Test";
    TilePosition pos = new TilePosition(4, 2);
    Tile<String> Tile = new Tile<>(pos, item);
    
    assertEquals(pos, Tile.pos());
    assertEquals(item, Tile.value());
  }
  
  @Test
  void TileEqualityAndHashCodeTest() {
    String item = "Test";
    TilePosition pos = new TilePosition(4, 2);
    Tile<String> Tile = new Tile<>(pos, item);
    
    String item2 = "Test";
    TilePosition pos2 = new TilePosition(4, 2);
    Tile<String> Tile2 = new Tile<>(pos2, item2);
    
    assertTrue(Tile2.equals(Tile));
    assertTrue(Tile.equals(Tile2));
    assertTrue(Objects.equals(Tile, Tile2));
    assertTrue(Tile.hashCode() == Tile2.hashCode());
  }
  
  @Test
  void TileInequalityTest() {
    String item = "Test";
    TilePosition pos = new TilePosition(4, 2);
    Tile<String> Tile = new Tile<>(pos, item);
    
    String item2 = "Test2";
    TilePosition pos2 = new TilePosition(2, 4);
    
    Tile<String> Tile2 = new Tile<>(pos2, item);
    Tile<String> Tile3 = new Tile<>(pos, item2);
    
    assertFalse(Tile2.equals(Tile));
    assertFalse(Tile.equals(Tile2));
    assertFalse(Tile.equals(Tile3));
    assertFalse(Tile2.equals(Tile3));
    assertFalse(Objects.equals(Tile, Tile2));
    assertFalse(Objects.equals(Tile, Tile3));
    assertFalse(Objects.equals(Tile2, Tile3));
  }
}
