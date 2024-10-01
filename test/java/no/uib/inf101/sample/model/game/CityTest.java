package no.uib.inf101.sample.model.game;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.map.TilePosition;

public class CityTest {
  
  /*
  * Checks that a city can change it's occupation value
  * if a unit enters the city
  */
  @Test
  public void setHasUnitStationedTest(){
    City C1 = new City(new TilePosition(4,4),null);
    
    // Check that the default value is false
    assertFalse(C1.hasCityUnit());
    
    // Check that it can be changed to true
    C1.setHasUnitStationed(true);
    assertTrue(C1.hasCityUnit());
    
    // Check that it can be changed back again
    C1.setHasUnitStationed(false);
    assertFalse(C1.hasCityUnit());
  }
  
  /*
  * Checks that a city can change it's current production
  */
  @Test
  public void changeProductionTest(){
    City C1 = new City(new TilePosition(4,4),null);
    
    // Check that production has correct start value
    assertEquals('W', C1.getCurrentlyProducedItem().getUnitSymbol());
    
    // Check that production is changed to new value
    C1.changeProduction('P');
    assertEquals('P', C1.getCurrentlyProducedItem().getUnitSymbol());
    C1.changeProduction('E');
    assertEquals('E', C1.getCurrentlyProducedItem().getUnitSymbol());
  }
  
  /*
  * Checks that a city can determine wether an item is 
  * finsihed with production correctly.
  * Also tests produceUnit() as they both have the same test environment.
  */
  @Test 
  public void hasProductionFinishedTest(){
    City C1 = new City(new TilePosition(4,4),null);
    
    // Check that a production is not finished if stored production 
    // is less than requirement, and that a unit is not produced
    int storedProduction = C1.getStoredProduction();
    int prodRequired = C1.getCurrentlyProducedItem().getProductionCost();
    assertTrue(storedProduction < prodRequired);
    assertFalse(C1.hasProductionFinished());
    assertFalse(C1.produceUnit());
    
    // Add production towards item
    // Producing a warrior takes 4 turns
    for(int i = 0; i < 3; i++){
      C1.newTurnForCity();
      int storedProduction2 = C1.getStoredProduction();
      assertTrue(storedProduction2 < prodRequired);
      assertFalse(C1.hasProductionFinished());
      assertFalse(C1.produceUnit());
    }
    // Chekc that production is finished if stored production is larger than requirement
    C1.newTurnForCity();
    int storedProduction3 = C1.getStoredProduction();
    System.out.println(storedProduction3);
    System.out.println(prodRequired);
    assertFalse(storedProduction3 <= prodRequired);
    assertTrue(C1.hasProductionFinished());
    assertTrue(C1.produceUnit());
  }
  
  /*
  * Checks that a city can be harmed in a way that adheres to the geneva conventions
  */
  @Test
  public void damageCityTest(){
    City C1 = new City(new TilePosition(4,4),null);
    C1.damageCity(20);
    assertEquals(80, C1.getCityHealthPoints());
    C1.damageCity(500000);
    assertEquals(0, C1.getCityHealthPoints());
  }
  
  /*
  * Checks that you can heal a city
  */
  @Test
  public void healCityTest(){
    City C1 = new City(new TilePosition(4,4),null);
    // Checks regular healing
    C1.damageCity(20);
    C1.healCity();
    assertEquals(90, C1.getCityHealthPoints());
    
    // Checks healing to full health
    C1.healCity();
    assertEquals(100, C1.getCityHealthPoints());
    C1.damageCity(5);
    C1.healCity();
    assertEquals(100, C1.getCityHealthPoints());
  }
  
  /*
  * Checks if a city is alive
  */
  @Test
  public void isAliveTest(){
    City C1 = new City(new TilePosition(4,4),null);
    // Check that it lives upon creation
    assertTrue(C1.isAlive());
    // Check that it lives even though it got tickled
    C1.damageCity(40);
    assertTrue(C1.isAlive());
    // Check that a unit is killed if hp <= 0
    C1.damageCity(60);
    assertFalse(C1.isAlive());
  }
}
