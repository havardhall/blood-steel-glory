package no.uib.inf101.sample.model.game;

import java.util.ArrayList;

import no.uib.inf101.sample.controller.Player;

public class PlayerList {
  private ArrayList<Player> playerList = new ArrayList<>();
  
  // Empty constructor
  public PlayerList(){}
  
  // Get the player list
  public ArrayList<Player> getPlayerList(){
    return this.playerList;
  }
  
  /**
  * Adds a player to the list
  * @param player - the player to be added
  */
  public void addPlayerToList(Player player){
    this.playerList.add(player);
  }
  
  /**
  * Removes a player from the playerlist
  * @param player - the player to be removed
  */
  public void removePlayerFromList(Player player){
    this.playerList.remove(player);
  }
}
