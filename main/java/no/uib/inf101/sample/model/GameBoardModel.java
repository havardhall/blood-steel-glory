package no.uib.inf101.sample.model;

import no.uib.inf101.sample.controller.ControllableGameModel;
import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.game.City;
import no.uib.inf101.sample.model.game.CityBoard;
import no.uib.inf101.sample.model.game.GameMap;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.game.UnitBoard;
import no.uib.inf101.sample.model.map.MapDimension;
import no.uib.inf101.sample.model.map.Terrain;
import no.uib.inf101.sample.model.map.Tile;
import no.uib.inf101.sample.model.map.TilePosition;
import no.uib.inf101.sample.view.ViewableGameModel;

/*
* Some of the methods are helper methods and should be private,
* however they are so vital that they must be public in order to be tested properly
*/
public class GameBoardModel implements ViewableGameModel, ControllableGameModel {
  private GameMap map;
  private CityBoard cityBoard;
  private UnitBoard unitBoard;
  private Player currentPlayer;
  private Unit currentlySelectedUnit;
  
  public GameBoardModel(GameMap map, UnitBoard unitBoard, CityBoard cityBoard){
    this.map = map;
    this.unitBoard = unitBoard;
    this.cityBoard = cityBoard;
  }
  
  // Getters for field variables
  public UnitBoard getUnitBoard(){
    return this.unitBoard;
  }
  public CityBoard getCityBoard(){
    return this.cityBoard;
  }
  public Player getCurrentPlayer(){
    return this.currentPlayer;
  } 
  public Unit getCurrentlySelectedUnit(){
    return this.currentlySelectedUnit;
  }
  @Override
  public MapDimension getDimension() {
    return this.map;
  }
  @Override
  public Iterable<Tile<Terrain>> getTilesOnMap(){
    return this.map;
  }
  @Override
  public Iterable<Tile<Unit>> getUnitsOnMap(){
    return this.unitBoard;
  }
  @Override
  public Iterable<Tile<City>> getCitiesOnMap(){
    return this.cityBoard;
  }
  
  /**
  * Gets the city of a player
  * @param player - the player of which the city will be returned
  * @return a city
  */
  @Override
  public City getPlayerCity(Player player){
    char playerChar = player.getTribe().getTribeCharacter();
    TilePosition cityPosition = this.cityBoard.getCityPosition(playerChar);
    City playerCity = this.cityBoard.get(cityPosition);
    return playerCity;
  }
  
  /**
  * Select a unit on the board
  * @param tilePosition is the position of the unit
  * @return true if it is the player's unit, false if not. Also false if no unit at position
  */
  public boolean selectUnit(TilePosition tilePosition){
    // If position is not valid it should not be selectable
    if(!(isNewPositionValid(tilePosition))){
      return false;
    }
    Unit selectedUnit = this.unitBoard.get(tilePosition);
    if(!(selectedUnit == null)){
      if(selectedUnit.getPlayer().equals(this.currentPlayer)){
        this.currentlySelectedUnit = selectedUnit;
        System.out.println("Selected a unit at: " + "row: " + tilePosition.row() + "col:" + tilePosition.col());
        return true;
      }
      return false;
    }
    else{
      return false;
    }
  }
  
  /**
  * Deselects the currently selected unit 
  * Should be used when the current player ends their turn
  */
  public void deSelectUnit(){
    this.currentlySelectedUnit = null;
  }
  
  /**
  * Moves a unit on the board. This is a large method, but
  * there are so many possible cases during movement that it is simpler this way.
  * Also used for attacking units and cities.
  * @param newPosition is the position to be moved to
  * @return true if unit moved or attacked. False if invalid movement
  */
  public boolean moveUnit(TilePosition newPosition){
    // Checks if position is feasible
    if(!(isNewPositionValid(newPosition))){
      return false;
    }
    //Checks that a unit is selected
    if(this.currentlySelectedUnit == null){
      return false;
    }
    // Check if position is the same
    if(this.currentlySelectedUnit.getUnitPosition().equals(newPosition)){
      return false;
    }
    // Check if cost of movement is valid
    if(!(checkMovementCost(newPosition))){
      return false;
    }
    // Check if position contains enemy city
    if(!(checkForFriendlyCity(newPosition))){
      return attackCity(newPosition);
    }
    // Check if tile contains a unit
    Unit otherUnit = this.unitBoard.get(newPosition);
    if(this.unitBoard.get(newPosition) != null){
      Player otherPlayer = otherUnit.getPlayer();
      // Attacks a player
      if(!(otherPlayer.equals(this.currentPlayer))){
        return attackUnit(newPosition);
      }
      // Attempts a swap
      if(otherPlayer.equals(this.currentPlayer)){
        return swapUnits(newPosition);
      }
    }
    this.unitBoard.moveUnitOnBoard(newPosition, currentlySelectedUnit);
    System.out.println("Moved unit to: " + "row: " + newPosition.row() + "col:" + newPosition.col());
    return true;
  }
  
  /**
  * Checks that a unit is not trying to move into mountains or out of bounds
  * ------ Helper Method for moveUnit() and selectUnit()------
  * @param position
  * @return boolean determining if the selected tilePosition is a valid option
  */
  public boolean isNewPositionValid(TilePosition position){
    int row = position.row();
    int col = position.col();
    // Is row or col out of bounds
    if((row < 0) || ((this.getDimension().rows() - 1) < row)){
      return false;
    }
    if((col < 0) || ((this.getDimension().cols() - 1) < col)){
      return false;
    }
    // Is position mountains
    if(this.map.get(position).getPassable() == false){
      return false;
    }
    return true;
  }
  
  /**
  * Method for controlling movement cost
  * ------ Helper method for moveUnit() ------ 
  * @param newPosition is the new position to be checked
  * @return true if the movement is valid, false if invalid
  * 
  */
  public boolean checkMovementCost(TilePosition newPosition){
    Unit unit = this.currentlySelectedUnit;
    int newRow = newPosition.row();
    int newCol = newPosition.col();
    int oldRow = unit.getUnitPosition().row();
    int oldCol = unit.getUnitPosition().col();
    // Absolute value to account for all directions
    int deltaRow = Math.abs(oldRow - newRow);
    int deltaCol = Math.abs(oldCol - newCol);
    
    // If change in row and col = 1 or 0: the movement cost is valid
    if((deltaRow <= unit.getUnitMovement()) && (deltaCol <= unit.getUnitMovement())){
      return true;
    }
    else{
      return false;
    }
  }
  
  /**
  * Validates a new position to check for possible movement
  * @return true if the new position contains no enemy cities
  */
  public boolean checkForFriendlyCity(TilePosition newPosition){
    City possibleCity = this.cityBoard.get(newPosition);
    // If there is no city - return true
    if(possibleCity == null){
      return true;
    }
    // Return true if friendly city
    if(possibleCity.getPlayer().equals(this.currentPlayer)){
      return true;
    }
    // Return false if anyone else's
    else{
      return false;
    }
  }
  
  /*
  * Chekcs if a city is being occupied by a unit, and changes it's value accordingly
  * Should be called on start of a player's turn
  */
  public void cityUnitOccupationController(TilePosition cityPosition){
    // Updates city to account for unit being stationed there
    if(checkForFriendlyCity(cityPosition) && (this.cityBoard.get(cityPosition) != null)){
      if(this.unitBoard.get(cityPosition) != null){
        this.cityBoard.get(cityPosition).setHasUnitStationed(true);
      }
    }
    // Updates city unit stationed if there is no unit stationed in it
    if(checkForFriendlyCity(cityPosition) && (this.cityBoard.get(cityPosition) != null)){
      if(this.unitBoard.get(cityPosition) == null){
        this.cityBoard.get(cityPosition).setHasUnitStationed(false);
      }
    }
  }
  
  /**
  * Attack a unit. Simple as that
  * This is a large method, but there are many scenarios 
  * that needs to be taken into consideration
  * ------ Helper method for moveUnit() ------
  * @param newPosition 
  * @return true if the enemy unit was defeated, false if not
  */
  public boolean attackUnit(TilePosition defenderPosition){
    //Sets up necessary combat variables
    Unit attacker = this.getCurrentlySelectedUnit();
    Unit defender = this.unitBoard.get(defenderPosition);
    double defenderCombatModifier = this.map.get(defenderPosition).getCombatModifier();
    double defenderStrengthMod = defender.getStrength() * defenderCombatModifier;
    double attackerStrength = attacker.getStrength();
    double deltaStrength = Math.abs(defender.getStrength() - attackerStrength);
    System.out.println(attacker.getUnitName() + " attacks " + defender.getUnitName());
    
    // Calculate damage
    double damageFactor = (0.3 + (2 * deltaStrength / 100)); 
    int defenderDamageTaken = (int) ((attackerStrength) + (attackerStrength * damageFactor));
    int attackerDamageTaken = (int) ((defenderStrengthMod) + ((defenderStrengthMod * damageFactor) / 1.4));
    
    //int defenderDamageTaken = (int) ((2 * attackerStrength) + deltaStrength - defenderStrengthMod);
    //int attackerDamageTaken = (int) ((2 * defenderStrengthMod) + deltaStrength - attackerStrength);
    
    // Damages and aftermath of combat
    defender.damageUnit(defenderDamageTaken);
    attacker.damageUnit(attackerDamageTaken);
    
    // If the defender dies the attacker should live - If a unit gains health from the whole combat we can say it's a morale boost ;)
    if(!(defender.isAlive())){
      if(!(attacker.isAlive())){
        attacker.healUnit();
      }
    }
    // If attacker dies it should be removed from the game
    if(!(attacker.isAlive())){
      defeatUnit(attacker);
      return false;
    }
    // Defender is alive - attacking unit should stay in place, and lose a movement point
    if(defender.isAlive()){
      attacker.moveUnitToPosition(this.currentlySelectedUnit.getUnitPosition());
      return false;
    }
    // VICTORY!!! Move into the now open position
    else{
      System.out.println("Enemy " + defender.getUnitName() +
      " is defeated by " + this.currentlySelectedUnit.getUnitName());
      defeatUnit(defender);
      this.unitBoard.moveUnitOnBoard(defenderPosition, attacker);
      return true;
    }
  }
  
  /**
  * This is the combat method for attacking cities
  * TODO: This is very similiar to attackUnit(), see if they can be combined
  * ------ Helper method for moveUnit ------
  * @param position
  * @return true if captured a city, false if not
  */
  public boolean attackCity(TilePosition cityPosition){
    // Set up necessary variables
    Unit attacker = this.getCurrentlySelectedUnit();
    City city = this.cityBoard.get(cityPosition);
    double cityCombatModifier = this.map.get(cityPosition).getCombatModifier();
    double defenderStrengthMod = city.getCityDefense() * cityCombatModifier;
    double attackerStrength = attacker.getStrength();
    double deltaStrength = Math.abs(city.getCityDefense() - attackerStrength);
    System.out.println(attacker.getUnitName() + " attacks a city.");
    
    // Calculate damage
    double damageFactor = (0.3 + (2 * deltaStrength / 100)); 
    int cityDamageTaken = (int) ((attackerStrength / 2) + (deltaStrength/2));
    int attackerDamageTaken = (int) ((defenderStrengthMod) + ((defenderStrengthMod * damageFactor) / 1.4));
    
    // Damages and aftermath of combat
    city.damageCity(cityDamageTaken);
    attacker.damageUnit(attackerDamageTaken);
    
    // If the defender dies the attacker should live - If a unit gains health from the whole combat we can say it's a morale boost ;)
    if(!(city.isAlive())){
      if(!(attacker.isAlive())){
        attacker.healUnit();
      }
    }
    // If attacker dies it should be removed from the game
    if(!(attacker.isAlive())){
      defeatUnit(attacker);
      return false;
    }
    // Defender is alive - attacking unit should stay in place, and lose a movement point
    if(city.isAlive()){
      attacker.moveUnitToPosition(this.currentlySelectedUnit.getUnitPosition());
      return false;
    }
    // VICTORY!!! Move into the now open position
    else{
      System.out.println("Enemy Capital" +
      " is defeated by " + this.currentlySelectedUnit.getUnitName());
      defeatCity(city);
      this.unitBoard.moveUnitOnBoard(cityPosition, attacker);
      return true;
    }
  }
  
  /**
  * Swaps two friendly units units 
  * ------ Helper method for moveUnit() ------
  * @param newPosition is the new position of the unit moving in to
  * swap with the unit stationed at that tile
  * @return a boolean determining if the swap took place or not
  */
  public boolean swapUnits(TilePosition newPosition){
    TilePosition oldPosition = this.currentlySelectedUnit.getUnitPosition();
    Unit thisUnit = this.currentlySelectedUnit;
    Unit otherUnit = this.unitBoard.get(newPosition);
    
    // If they both have movement points the swap may take place
    if((otherUnit.getUnitMovement() == 1) && thisUnit.getUnitMovement() == 1){
      this.unitBoard.moveUnitOnBoard(oldPosition, otherUnit);
      this.unitBoard.moveUnitOnBoard(newPosition, thisUnit);
      return true;
    }
    return false;  
  }
  
  /**
  * On a units defeat this method should start
  * Removes the unit from the game by setting the object 
  * to null in the UnitBoard class
  * @param defeatedUnit the unit to be defeated
  */
  public void defeatUnit(Unit defeatedUnit){
    this.currentPlayer.removeFromUnitList(defeatedUnit);
    this.unitBoard.removeUnit(defeatedUnit.getUnitPosition());
  }
  
  /**
  * On a city's defeat this method should start
  * Removes the city from the game by setting the object 
  * to null in the cityBoard class
  * @param defeatedCity - the city to be defeated
  */
  public void defeatCity(City defeatedCity){
    this.cityBoard.removeCity(defeatedCity.getCityPosition(), defeatedCity);
  }
  
  /*
  * Makes city produce units and heal. If a unit is finished it spawns.
  */
  public void cityNewTurn(){
    // Process new turn for city
    char playerChar = this.currentPlayer.getTribe().getTribeCharacter();
    TilePosition cityPosition = this.cityBoard.getCityPosition(playerChar);
    City city = this.cityBoard.get(cityPosition);
    cityUnitOccupationController(cityPosition);
    city.newTurnForCity();
    
    // If production has finished
    if(city.hasProductionFinished()){
      // Checks if the city's tile is available
      if(!(city.hasCityUnit())){
        char unitChar = city.getCurrentlyProducedItem().getUnitSymbol();
        city.produceUnit();
        this.unitBoard.spawnUnit(this.currentPlayer, unitChar, cityPosition);
        this.cityBoard.get(cityPosition).setHasUnitStationed(true);
      }
    }
  }
  
  /**
  * Makes the units heal if they have movement left
  */
  public void unitsNewTurn(){
    for(Unit unit : this.currentPlayer.getUnitList()){
      if(!(unit.equals(null))){
        unit.newTurnReplenish();
      }
    }
  }
  
  /**
  * Changes the city's production
  * @param newItem - the new Item to be made
  * @return true if production has changed
  */
  public void changeCityProduction(char newItem){
    char playerChar = this.currentPlayer.getTribe().getTribeCharacter();
    TilePosition cityPosition = this.cityBoard.getCityPosition(playerChar);
    this.cityBoard.get(cityPosition).changeProduction(newItem);
  }
  
  /*
  * Sets the current player
  * Updated on round end for each player
  */
  public void setCurrentPlayer(Player nextPlayer){
    this.currentPlayer = nextPlayer;
  }
}
