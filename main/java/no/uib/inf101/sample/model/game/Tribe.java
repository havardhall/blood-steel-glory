package no.uib.inf101.sample.model.game;

import java.awt.Color;

/*
* Class for defining the different tribes a player can control
*/
public class Tribe {
  private char tribeChar;
  private Color tribeColor;
  private String tribeColorString;
  
  public Tribe(char tribe){
    this.tribeChar = tribe;
    this.tribeColor = setTribeColor(tribe);
    this.tribeColorString = setTribeColorString(tribe);
  }
  
  /**
  * Sets the color of the tribe
  * @param tribe - the character representing a tribe
  * @return - the color of the tribe 
  */
  private Color setTribeColor(char tribe){
    Color tribeColor = switch(tribe){
      case 'R' -> new Color(0xff313120);
      
      case 'B' -> new Color(0x0cc0df20);
      
      case 'G' -> new Color(0x00bf6320);
      
      case 'O' -> new Color(0xff914d20);
      
      default -> throw new IllegalArgumentException(
      "No available color for '" + tribe + "'");
    };
    return tribeColor;
  }
  
  /**
  * Sets the color string of the tribe
  * @param tribe - the character representing a tribe
  * @return - a string with the color of the tribe 
  */
  private String setTribeColorString(char tribe){
    String tribeColorString = switch(tribe){
      case 'R' -> "Red";
      
      case 'B' -> "Blue";
      
      case 'G' -> "Green";
      
      case 'O' -> "Orange";
      
      default -> throw new IllegalArgumentException(
      "No available color string for '" + tribe + "'");
    };
    return tribeColorString;
  }
  
  /*
  * Gets the character of the tribe. 
  * Primarily used to calculate borders
  */
  public char getTribeCharacter(){
    return this.tribeChar;
  }
  /*
  * Gets the color of the tribe to be used in view
  */
  public Color getTribeColor(){
    return this.tribeColor;
  }
  /*
  * Gets the trib's color as a string
  * Used to find the correct icons in view
  */
  public String getTribeColorString(){
    return this.tribeColorString;
  }
}
