package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class TribeTest {
  
  /*
  * Check that the tribe's color is correctly instantiated
  */
  @Test
  public void tribeColorTest(){
    // Check that red tribe is correctly made
    Tribe red = new Tribe('R');
    assertEquals('R', red.getTribeCharacter());
    assertEquals(new Color(0xff313120), red.getTribeColor());
    assertEquals("Red", red.getTribeColorString());
    
    // Check that blue tribe is correctly made
    Tribe blue= new Tribe('B');
    assertEquals('B', blue.getTribeCharacter());
    assertEquals(new Color(0x0cc0df20), blue.getTribeColor());
    assertEquals("Blue", blue.getTribeColorString());
    
    // Check that green tribe is correctly made
    Tribe green= new Tribe('G');
    assertEquals('G', green.getTribeCharacter());
    assertEquals(new Color(0x00bf6320), green.getTribeColor());
    assertEquals("Green", green.getTribeColorString());
    
    // Check that orange tribe is correctly made
    Tribe orange = new Tribe('O');
    assertEquals('O', orange.getTribeCharacter());
    assertEquals(new Color(0xff914d20), orange.getTribeColor());
    assertEquals("Orange", orange.getTribeColorString());
  }
}
