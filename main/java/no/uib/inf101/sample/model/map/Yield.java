package no.uib.inf101.sample.model.map;

/*
* Class for getting and setting
* a tile's yield in food and production
*/
public class Yield {
  private int food; 
  private int production;
  
  // Constructor
  public Yield(int food, int production){
    this.food = food;
    this.production = production;
  }
  
  // Getters
  public int getFood(){
    return this.food;
  }
  public int getProd(){
    return this.production;
  }
  
  /*
  * Methods to increase food and production for a tile
  */
  public void increaseFood(int deltaFood){
    this.food += deltaFood;
  }
  public void increaseProduction(int deltaProd){
    this.production += deltaProd;
  }
}