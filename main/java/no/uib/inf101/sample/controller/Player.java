package no.uib.inf101.sample.controller;

import java.util.ArrayList;
import java.util.Objects;

import no.uib.inf101.sample.model.game.City;
import no.uib.inf101.sample.model.game.Move;
import no.uib.inf101.sample.model.game.Tribe;
import no.uib.inf101.sample.model.game.Unit;

/*
* Player class is which player the cities, units, etc belongs to
* The player is the one who can control those, and only those that belong to it
*/
public class Player implements IPlayer {
  private int PLAYERID;
  private Tribe tribe;
  private ArrayList<City> cityList = new ArrayList<>();
  private ArrayList<Unit> unitList = new ArrayList<>();
  
  public Player(int playerID, Tribe chosenTribe){
    this.PLAYERID = playerID;
    this.tribe = chosenTribe;
  }
  
  // Field Variable Getters Below
  public int getPlayerID(){
    return this.PLAYERID;
  }
  public Tribe getTribe(){
    return this.tribe;
  }
  public ArrayList<City> getCityList(){
    return this.cityList;
  }
  public ArrayList<Unit> getUnitList(){
    return this.unitList;
  }
  
  /*
  * Adds a unit to the uit list
  */
  public void addToCityList(City city){
    this.cityList.add(city);
  } 
  
  /*
  * Removes a city from the city list
  */
  public void removeFromCityList(City city){
    this.cityList.remove(city);
  }
  
  /*
  * Adds a unit to the uit list
  */
  public void addToUnitList(Unit unit){
    this.unitList.add(unit);
  } 
  
  /*
  * Removes a unit from the unit list
  */
  public void removeFromUnitList(Unit unit){
    this.unitList.remove(unit);
  }
  
  /**
  * Gets the hashcode of the player
  * @return an integer representing the hashcode
  */
  @Override
  public int hashCode(){
    return Objects.hash(this.PLAYERID);
  }
  
  /**
  * Compares a player with another to see if they are the same player
  * @param player is the player to be compared to
  * @return a boolean determining if they are the same player
  * Inspirert av kursnotat 4.1
  */
  @Override
  public boolean equals(Object player){
    // Some important checks first
    if(player == this){
      return true;
    }      
    if(player == null){
      return false;
    }
    if(!(player instanceof Player)){
      return false;
    }
    // Comparing the two players
    Player other = (Player) player;
    boolean casePlayerID = (this.PLAYERID == other.PLAYERID);
    boolean casePlayerTribe = (this.tribe.equals(other.getTribe()));
    
    // Check that player ID's are different
    if(!(casePlayerID)){
      return false;
    }
    // Check if player's are different
    if(!(casePlayerTribe)){
      return false;
    }
    return true;
  }
  
  /*
  * Should be defined in the subclasses
  */
  @Override
  public Move getMoveForUnit(Unit unit) {
    throw new UnsupportedOperationException("Unimplemented method 'getMoveForUnit'");
  }
  
  @Override
  public char getProductionChoice() {
    throw new UnsupportedOperationException("Unimplemented method 'getProductionChoice'");
  }
  
  @Override
  public ArrayList<Move> getMoves() {
    throw new UnsupportedOperationException("Unimplemented method 'getMoves'");
  }
}
