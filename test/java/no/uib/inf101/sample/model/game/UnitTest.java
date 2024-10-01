package no.uib.inf101.sample.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import no.uib.inf101.sample.model.map.TilePosition;

public class UnitTest {
  /*
  * Checks that the constructor instantiates 
  * instances correctly
  */
  @Test
  public void unitSanityTest(){
    // Test for Warrior
    Unit W = new Unit(null, 'W', new TilePosition(1,1));
    assertEquals("Warrior", W.getUnitName());
    assertEquals(15, W.getStrength());
    assertEquals(30, W.getProductionCost());
    assertEquals("/game_icons/unit_icons/Warrior_", W.getUnitIcon());
    
    // Test for Phalanx
    Unit P = new Unit(null, 'P', new TilePosition(1,1));
    assertEquals("Phalanx", P.getUnitName());
    assertEquals(33, P.getStrength());
    assertEquals(55, P.getProductionCost());
    assertEquals("/game_icons/unit_icons/Phalanx_", P.getUnitIcon());
    
    // Test for Elite Regiment
    Unit E = new Unit(null, 'E', new TilePosition(1,1));
    assertEquals("Elite Regiment", E.getUnitName());
    assertEquals(52, E.getStrength());
    assertEquals(85, E.getProductionCost());
    assertEquals("/game_icons/unit_icons/Elite_Regiment_", E.getUnitIcon());
  }    
  
  /*
  * Checks that a unit moves correctly
  */
  @Test
  public void moveUnitTest(){
    Unit U1 = new Unit(null, 'W', new TilePosition(1,1));
    U1.moveUnitToPosition(new TilePosition(0, 0));
    assertEquals(0, U1.getUnitPosition().row());
    assertEquals(0, U1.getUnitPosition().col());
    assertEquals(0, U1.getUnitMovement());
  }
  
  /*
  * Checks that damaging units works correctly
  */
  @Test
  public void damageUnitTest(){
    Unit U2 = new Unit(null, 'W', new TilePosition(1,1));
    U2.damageUnit(20);
    assertEquals(80, U2.getHealthPoints());
    U2.damageUnit(9001);
    assertEquals(0, U2.getHealthPoints());
  }
  
  /*
  * Checks that a unit is good at dying
  */
  @Test
  public void isAliveTest(){
    Unit U2 = new Unit(null, 'W', new TilePosition(1,1));
    // Check that it lives upon creation
    assertTrue(U2.isAlive());
    // Check that it lives even though it got tickled
    U2.damageUnit(40);
    assertTrue(U2.isAlive());
    // Check that a unit is killed if hp <= 0
    U2.damageUnit(60);
    assertFalse(U2.isAlive());
  }
  
  /*
  * Checks that healing units works correctly
  */
  @Test
  public void healUnitTest(){
    Unit U3 = new Unit(null, 'W', new TilePosition(1,1));
    // Checks regular healing
    U3.damageUnit(20);
    U3.healUnit();
    assertEquals(90, U3.getHealthPoints());
    
    // Checks healing to full health
    U3.healUnit();
    assertEquals(100, U3.getHealthPoints());
    U3.damageUnit(5);
    U3.healUnit();
    assertEquals(100, U3.getHealthPoints());
  }
  
  /*
  * Checks that a new turn updates unit properly
  */
  @Test
  public void newTurnReplenishTest(){
    Unit U3 = new Unit(null, 'W', new TilePosition(1,1));
    U3.moveUnitToPosition(new TilePosition(1, 1));
    // Checks that movement is expended
    assertEquals(0, U3.getUnitMovement());
    U3.damageUnit(20);
    
    //Checks that no movement corresponds to no healing, and that movement is replenished
    U3.newTurnReplenish();
    assertEquals(80, U3.getHealthPoints());
    assertEquals(1, U3.getUnitMovement());
    
    // Checks that excess movement causes healing
    U3.newTurnReplenish();
    assertEquals(90, U3.getHealthPoints());
  }
  
  /*
  * Checks that hpToString units works correctly
  */
  @Test
  public void hpToStringTest(){
    Unit U4 = new Unit(null, 'W', new TilePosition(1,1));
    String s1 = U4.hpToString();
    assertEquals("100/100 HP", s1);
    U4.damageUnit(20);
    String s2 = U4.hpToString();
    assertEquals("80/100 HP", s2);
  }
  
  /*
  * Checks that strengthToString works correctly
  */
  @Test
  public void strengthToStringTest(){
    Unit U5 = new Unit(null, 'W', new TilePosition(1,1));
    String s1 = U5.strengthToString();
    assertEquals("Strength: 15", s1);
  }
  
  /*
  * Checks that movementToString works correctly
  */
  @Test
  public void movementToStringTest(){
    Unit U5 = new Unit(null, 'W', new TilePosition(1,1));
    String s1 = U5.movementToString();
    assertEquals("Movement: 1", s1);
    
    U5.moveUnitToPosition(new TilePosition(0, 0));
    String s2 = U5.movementToString();
    assertEquals("Movement: 0", s2);
  }
}