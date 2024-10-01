package no.uib.inf101.sample.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
* Testing the class MapGrid
*/
public class GridTest {
  
  @Test
  void gridTestGetRowsAndCols() {
    IMapGrid<Integer> map = new MapGrid<>(3, 2);
    assertEquals(3, map.rows());
    assertEquals(2, map.cols());
  }
  
  @Test
  void gridSanityTest() {
    String defaultValue = "x";
    IMapGrid<String> map = new MapGrid<>(3, 2, defaultValue);
    
    assertEquals(3, map.rows());
    assertEquals(2, map.cols());
    
    assertEquals("x", map.get(new TilePosition(0, 0)));
    assertEquals("x", map.get(new TilePosition(2, 1)));
    
    map.set(new TilePosition(1, 1), "y");
    
    assertEquals("y", map.get(new TilePosition(1, 1)));
    assertEquals("x", map.get(new TilePosition(0, 1)));
    assertEquals("x", map.get(new TilePosition(1, 0)));
    assertEquals("x", map.get(new TilePosition(2, 1)));
  }
  
  @Test
  void gridCanHoldNull() {
    String defaultValue = "x";
    IMapGrid<String> map = new MapGrid<>(3, 2, defaultValue);
    
    assertEquals("x", map.get(new TilePosition(0, 0)));
    assertEquals("x", map.get(new TilePosition(2, 1)));
    
    map.set(new TilePosition(1, 1), null);
    
    assertEquals(null, map.get(new TilePosition(1, 1)));
    assertEquals("x", map.get(new TilePosition(0, 1)));
    assertEquals("x", map.get(new TilePosition(1, 0)));
    assertEquals("x", map.get(new TilePosition(2, 1)));
  }
  
  @Test
  void gridNullsInDefaultConstructor() {
    IMapGrid<String> map = new MapGrid<>(3, 2);
    
    assertEquals(null, map.get(new TilePosition(0, 0)));
    assertEquals(null, map.get(new TilePosition(2, 1)));
    
    map.set(new TilePosition(1, 1), "y");
    
    assertEquals("y", map.get(new TilePosition(1, 1)));
    assertEquals(null, map.get(new TilePosition(0, 1)));
    assertEquals(null, map.get(new TilePosition(1, 0)));
    assertEquals(null, map.get(new TilePosition(2, 1)));
  }
  
  @Test
  void coordinateIsOnGridTest() {
    IMapGrid<Double> map = new MapGrid<>(3, 2, 0.9);
    
    assertTrue(map.positionIsOnMap(new TilePosition(2, 1)));
    assertFalse(map.positionIsOnMap(new TilePosition(3, 1)));
    assertFalse(map.positionIsOnMap(new TilePosition(2, 2)));
    
    assertTrue(map.positionIsOnMap(new TilePosition(0, 0)));
    assertFalse(map.positionIsOnMap(new TilePosition(-1, 0)));
    assertFalse(map.positionIsOnMap(new TilePosition(0, -1)));
  }
  
  @Test
  void throwsExceptionWhenCoordinateOffGrid() {
    IMapGrid<String> map = new MapGrid<>(3, 2, "x");
    
    try {
      @SuppressWarnings("unused")
      String x = map.get(new TilePosition(3, 1));
      fail();
    } catch (IndexOutOfBoundsException e) {
      // Test passed
    }
  }
  
  @Test
  void testIterator() {
    IMapGrid<String> map = new MapGrid<>(3, 2, "x");
    map.set(new TilePosition(0, 0), "a");
    map.set(new TilePosition(1, 1), "b");
    map.set(new TilePosition(2, 1), "c");
    
    List<Tile<String>> items = new ArrayList<>();
    for (Tile<String> coordinateItem : map) {
      items.add(coordinateItem);
    }
    
    assertEquals(3 * 2, items.size());
    assertTrue(items.contains(new Tile<String>(new TilePosition(0, 0), "a")));
    assertTrue(items.contains(new Tile<String>(new TilePosition(1, 1), "b")));
    assertTrue(items.contains(new Tile<String>(new TilePosition(2, 1), "c")));
    assertTrue(items.contains(new Tile<String>(new TilePosition(0, 1), "x")));
  }
}
