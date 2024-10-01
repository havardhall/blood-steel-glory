package no.uib.inf101.sample.model.game;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.map.TilePosition;

/*
* A class for a player's city.
* Has a city's combat ability, production, food, borders,
* allegiance, infrastructure level and produced asset.
*/
public class City {
  private final TilePosition CITYPOSITION;
  private final Player player;
  private final String cityIconString = "/game_icons/city_Icons/City_";
  // 100 hp is natural, and it starts with it
  private int cityMaxHP = 100;
  private int cityHealthPoints = 100;
  // defense is the modifier for hp loss during an attack
  private int defense = 55;
  // To prevent unit stacking in city upon training of unit
  private boolean hasUnitStationed = false;
  // Pop and Inf starts at 1
  //private int population = 1;
  //private int infrastructureLevel = 0;
  //private int infrastructureCost = 30;
  private int growthPerTurn = 0;
  //private int storedGrowth = 0;
  //private int turnsUntilGrowth = 0;
  private Unit producedUnit;
  private int productionPerTurn = 8;
  private int storedProduction = 0;
  private int turnsUntilProduction;
  // Border level is used for calculating border expansion
  //private int borderlevel = 0;
  
  // Constructor 
  public City(TilePosition cityPosition, Player player){
    this.CITYPOSITION = cityPosition;
    this.player = player;
    this.cityHealthPoints = this.cityMaxHP;
    this.producedUnit = new Unit(player, 'W', cityPosition);
  }
  
  // Field variable getters below
  public TilePosition getCityPosition(){
    return this.CITYPOSITION;
  }
  public Player getPlayer(){
    return this.player;
  }
  public String getCityIconString(){
    return this.cityIconString;
  }
  public int getCityHealthPoints(){
    return this.cityHealthPoints;
  }
  public int getCityDefense(){
    return this.defense;
  }
  public boolean hasCityUnit(){
    return this.hasUnitStationed;
  }
  public int getTurnsToProduction(){
    return this.turnsUntilProduction;
  }
  public int getCurrentFood(){
    return this.growthPerTurn;
  }
  public int getCurrentProduction(){
    return this.productionPerTurn;
  }
  public int getStoredProduction(){
    return this.storedProduction;
  }
  public Unit getCurrentlyProducedItem(){
    return this.producedUnit;
  }
  
  /**
  * Sets the hasUnitStationed to true if a unit is stationed here
  * @param isUnitHere - true if a unit is stationed in city
  */
  public void setHasUnitStationed(boolean isUnitHere){
    this.hasUnitStationed = isUnitHere;
  }
  
  // Replenishes city for the new turn
  public void newTurnForCity(){
    // Heals city if not at max hp
    if(this.cityHealthPoints != this.cityMaxHP){
      healCity(); 
    }
    // Add production
    this.storedProduction += this.productionPerTurn;
    calculateTurnsUntilProduction();
    // Add food
  }
  
  /*
  * Calculates the turns left until a unit is produced
  */
  private void calculateTurnsUntilProduction(){
    // If stored production is not below 1
    if(!(this.storedProduction < 1)){
      int unitCost = this.producedUnit.getProductionCost();
      double productionFactor = unitCost / this.productionPerTurn;
      double storedProdFactor = this.storedProduction / this.productionPerTurn;
      double turnsToProduction  = Math.ceil(productionFactor - storedProdFactor);
      int turnInt = (int) (turnsToProduction);
      // Plus 1 because Match.ceil doesn't work for soem reason
      turnInt += 1;
      if(turnInt < 0){
        turnInt = 0;
      }
      this.turnsUntilProduction = turnInt;
    }
    else{
      this.turnsUntilProduction = 1;
    }
  }
  
  /**
  * Checks if a city has grown
  * @return true if city's stored growth is larger than requirement
  */
  public boolean hasCityGrown(){
    return false;
  }
  
  /**
  * Changes the queued production in the city
  * @param newProductionItem - the new item to be produced
  */
  public void changeProduction(char newProductionItem){
    // checks if the new item is the same item as the one currently being produced
    if(newProductionItem != this.producedUnit.getUnitSymbol()){
      // Halves production to prevent cheesy gameplay
      if(!(this.storedProduction < 1)){
        this.storedProduction = this.storedProduction / 2;
      }
      produceNewItem(newProductionItem);
      calculateTurnsUntilProduction();
    }
  }
  
  /*
  * Sets the city's production
  * ------ Helper method for changeProduction() ------
  */
  private void produceNewItem(char newProductionItem){
    Unit newUnit = switch(newProductionItem){
      case 'W' -> new Unit(player, 'W', CITYPOSITION);
      
      case 'P' -> new Unit(player, 'P', CITYPOSITION);
      
      case 'E' -> new Unit(player, 'E', CITYPOSITION);
      
      default -> throw new IllegalArgumentException(
      "No available unit for '" + newProductionItem + "'");
    };
    this.producedUnit = newUnit;
  }
  
  /**
  * Checks if production in a city is finished or not
  * @return true if the unit can be produced this turn
  */
  public boolean hasProductionFinished(){
    // Check if produced unit is null
    if(this.producedUnit == null){
      return false;
    }
    // Check if the stored production is less than the requirement
    int productionCost = this.producedUnit.getProductionCost();
    if(this.storedProduction < productionCost){
      return false;
    }
    return true;
  }
  
  /**
  * Adds production towards the current item being produced.
  * Should be called upon on round start
  * @return true if the unit is actually produced
  */
  public boolean produceUnit(){
    if(hasProductionFinished()){
      if(!(hasUnitStationed)){
        finishProduction();
        return true;
      }
    }
    return false;
  }
  
  /*
  * Spends the production on the new Item by subtracting the requirement
  */
  private void finishProduction(){
    this.storedProduction -= this.producedUnit.getProductionCost();
  }
  
  // Damages the city with an integer, aaah math!
  public void damageCity(int incomingDamage) {
    if (incomingDamage < 0){
      incomingDamage = 0;
    }   
    this.cityHealthPoints -= incomingDamage;
    
    if (this.cityHealthPoints < 0){
      this.cityHealthPoints = 0;
    }
    System.out.println("City takes " + incomingDamage +
    " damage and is left with " + 
    this.cityHealthPoints + "/" + this.cityMaxHP + " HP");  
  }
  
  /**
  * Check if the City is alive. 
  * A city is alive if current HP is higher than 0
  * @return true if current HP > 0, false if not
  */
  public boolean isAlive() {
    if (this.cityHealthPoints > 0){
      return true;
    }
    else{
      return false;
    }
  }
  
  // Heals the city - this is a constant
  public void healCity() {
    if(this.cityHealthPoints <= 90){
      this.cityHealthPoints += 10;
    }
    else{
      this.cityHealthPoints = this.cityMaxHP;
    }
  }
  
// I need these functions so I remember what I 
// was doing when further developing this later on

  // /**
  // * Adds a point of infrastructure to the city
  // * Also slightly increases defense
  // * Expands borders
  // */
  // public void expandInfrastructure(){
  //   this.infrastructureLevel += 1;
  //   this.borderlevel += 1;
  //   this.defense += 5;
  //   recalculateInfrastructureCost();
  // }
  
  // /**
  // * Increases cost for further improving the city's infrastructure
  // * ------ Helper method for expandInfrastructure() ------
  // * At the moment it is a 50% increase every time
  // */
  // private void recalculateInfrastructureCost(){
  //   double newCost = this.infrastructureCost * 1.5;
  //   this.infrastructureCost = (int) (newCost);
  // }

  // /**
  // * Expands the city's borders
  // */
  // public void growCity(){
  //   this.population += 1;
  //   this.storedGrowth = 0;
  //   this.borderlevel += 1;
  //   this.defense += 5;
  // }
  // public int getBorderLevel(){
  //   return this.borderlevel;
  // }
}
