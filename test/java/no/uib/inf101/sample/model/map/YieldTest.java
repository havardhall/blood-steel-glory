package no.uib.inf101.sample.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class YieldTest {
  
  @Test
  public void testYield(){
    // Check that the yield values are instantiated correctly
    Yield yield = new Yield(1, 1);
    assertEquals( 1, yield.getFood());
    assertEquals( 1, yield.getProd());
    
    // Check that setting the yield's values works properly
    yield.increaseFood(4);
    yield.increaseProduction(3);
    assertEquals( 5, yield.getFood());
    assertEquals( 4, yield.getProd());
  }
}
