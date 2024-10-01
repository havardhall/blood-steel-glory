package no.uib.inf101.sample.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
* Testing the class TilePosition
*/
public class TilePositionTest {
  
  @Test
  void sanityTest() {
    TilePosition cp = new TilePosition(4, 3);
    assertEquals(4, cp.row());
    assertEquals(3, cp.col());
  }
  
  @Test
  void coordinateEqualityTest() {
    TilePosition a = new TilePosition(2, 3);
    TilePosition b = new TilePosition(2, 3);
    
    assertFalse(a == b);
    assertTrue(a.equals(b));
    assertTrue(b.equals(a));
    assertTrue(Objects.equals(a, b));
  }
  
  @Test
  void coordinateInequalityTest() {
    TilePosition a = new TilePosition(2, 3);
    TilePosition b = new TilePosition(3, 2);
    
    assertFalse(a == b);
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
    assertFalse(Objects.equals(a, b));
  }
  
  @Test
  void coordinateHashcodeTest() {
    TilePosition a = new TilePosition(2, 3);
    TilePosition b = new TilePosition(2, 3);
    assertTrue(a.hashCode() == b.hashCode());
    
    TilePosition c = new TilePosition(100, 100);
    TilePosition d = new TilePosition(100, 100);
    assertTrue(c.hashCode() == d.hashCode());
  }
}
