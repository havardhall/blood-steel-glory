package no.uib.inf101.sample.model.game;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* Combat system is heavily inspired by lab 2
*/
public class Unit {
  private Player ownedByPlayer;
  private String unitIcon;
  private char unitSymbol;
  private String unitName;
  private TilePosition unitPosition;
  private int productionCost;
  private int movement = 1;
  private int maxHealthPoints = 100;
  private int healthPoints = 100;
  private int strength;
  
  // Constructor
  public Unit(Player ownedByPlayer, char symbol, TilePosition tilePosition){
    this.ownedByPlayer = ownedByPlayer;
    this.unitSymbol = symbol;
    this.unitPosition = tilePosition;
    if(symbol == 'W'){
      this.unitName = "Warrior";
      this.strength = 15;
      this.productionCost = 30;
      this.unitIcon = "/game_icons/unit_icons/Warrior_";
    }
    else if(symbol == 'P'){
      this.unitName = "Phalanx";
      this.strength = 33;
      this.productionCost = 55;
      this.unitIcon = "/game_icons/unit_icons/Phalanx_";
    }
    else if(symbol == 'E'){
      this.unitName = "Elite Regiment";
      this.strength = 52;
      this.productionCost = 85;
      this.unitIcon = "/game_icons/unit_icons/Elite_Regiment_";
    }
    else{
      throw new IllegalArgumentException(
      "No available unit for '" + symbol + "'");
    }
  }
  
  // Getters for field variables - self-explanatory
  public Player getPlayer(){
    return this.ownedByPlayer;
  }
  public String getUnitIcon(){
    return this.unitIcon;
  }
  public char getUnitSymbol(){
    return this.unitSymbol;
  }
  public String getUnitName(){
    return this.unitName;
  }
  public TilePosition getUnitPosition(){
    return this.unitPosition;
  }
  public int getProductionCost(){
    return this.productionCost;
  }
  public int getUnitMovement(){
    return this.movement;
  }
  public int getHealthPoints(){
    return this.healthPoints;
  }
  public int getStrength(){
    return this.strength;
  }
  
  // Updates the unit's position upon movement
  public void moveUnitToPosition(TilePosition newPosition) {
    this.unitPosition = newPosition;
    this.movement -=1;
  }
  
  // Damages the unit with an integer arrr
  public void damageUnit(int incomingDamage) {
    if (incomingDamage < 0){
      incomingDamage = 0;
    }   
    healthPoints -= incomingDamage;
    
    if (healthPoints < 0){
      healthPoints = 0;
    }
    System.out.println(this.unitName + " takes " + incomingDamage +
    " damage and is left with " + 
    this.healthPoints + "/" + this.maxHealthPoints + " HP");  
  }
  
  /**
  * Check if the Unit is alive. 
  * A unit is alive if current HP is higher than 0
  * @return true if current HP > 0, false if not
  */
  public boolean isAlive() {
    if (this.healthPoints > 0){
      return true;
    }
    else{
      return false;
    }
  }
  
  // Heals the unit - this is a constant
  public void healUnit() {
    if(this.healthPoints <= 90){
      this.healthPoints += 10;
    }
    else{
      this.healthPoints = this.maxHealthPoints;
    }
  }
  
  // Replenishes unit for the new turn
  public void newTurnReplenish(){
    // Heals unit if movement points are available
    if(this.movement == 1){
      if(this.healthPoints != this.maxHealthPoints){
        healUnit();
      }
    }
    this.movement = 1;
  }
  
  /**
  * Gets the hp of the unit and represents it with a string
  * ------ Helper method for GameView ------ 
  * @return a string representing the unit's hp
  */
  public String hpToString(){
    String hpString = this.healthPoints + "/" + "100 " + "HP";
    return hpString;
  }
  
  /**
  * Gets the strength of the unit and represents it with a string
  * ------ Helper method for GameView ------ 
  * @return a string representing the unit's strength
  */
  public String strengthToString(){
    String strengthString = "Strength: " + this.strength;
    return strengthString;
  }
  
  /**
  * Gets the movement of the unit and represents it with a string
  * ------ Helper method for GameView ------ 
  * @return a string representing the unit's movement
  */
  public String movementToString(){
    String movementString = "Movement: " + this.movement;
    return movementString;
  }
}