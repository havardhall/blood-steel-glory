package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.controller.Player;

public class PlayerTest {
  /*
  * Checks that equals can differentiate between players
  */
  @Test
  public void playerEqualsTest(){
    // Set up test scenario
    Tribe t1 = new Tribe('O');
    Tribe t2 = new Tribe('G');
    Player p1 = new Player(0, t1);
    Player p2 = new Player(1, t2);
    
    // check that a player is different from another player
    assertFalse(p1.equals(p2));
    
    // Check that a player is equal to itself
    assertTrue(p1.equals(p1));
  }
}
