package no.uib.inf101.sample.model;

import java.util.ArrayList;

import no.uib.inf101.sample.controller.HumanPlayer;
import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.controller.RandomPlayer;
import no.uib.inf101.sample.model.game.GameState;
import no.uib.inf101.sample.model.game.Move;
import no.uib.inf101.sample.model.game.PlayerList;
import no.uib.inf101.sample.model.game.Tribe;
import no.uib.inf101.sample.model.map.TilePosition;

/**
* This class handles the main game elements like turn processing,
* determining which player has the turn and what options they have
*/
public class GameModel implements Runnable{
  private GameBoardModel board;
  Thread gameThread;
  private int currentRound = 1;
  private PlayerList playerList;
  private Player currentPlayer;
  private Player humanPlayer;
  private Player victoriousPlayer;
  // The volatile identifier makes suer that a turn can be ended by endTurn() while 
  // game is in the playerTurn() loop
  private volatile boolean activeturn = false;
  private volatile boolean isRunning = true;
  private GameState gameState = GameState.MAIN_MENU;
  
  // Constructor
  public GameModel(GameBoardModel board){
    this.board = board;
    this.playerList = new PlayerList();
  }
  
  /*
  * Starts the main thread
  */
  public void launchGameThread(){
    this.gameThread = new Thread(this);
    this.gameThread.start();
  }
  
  /*
  * Runs the thread with a game loop.
  * THE CODE FOR THIS WAS PROVIDED BY MY FRIEND CHATGPT
  */
  @Override
  public void run(){
    while (isRunning) {
      // Your thread's task here
      System.out.println("Game thread is running...");
      try {
        startGame();
      } 
      finally{
        
      }
    }
    System.out.println("Game thread has stopped.");
  }
  
  
  public void stopThread(){
    this.isRunning = false;
  }
  
  
  
  /*
  *  Get the game's board
  *  Used to control the actual game
  */
  public GameBoardModel getGBM(){
    return this.board;
  }
  
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }
  
  public Player getHumanPlayer(){
    return this.humanPlayer;
  }
  
  public Player getVictoriousPlayer(){
    return this.victoriousPlayer;
  }
  
  // Gets the current round for display in view
  public int getCurrentRound(){
    return this.currentRound;
  }
  
  public GameState getCurrenGameState(){
    return this.gameState;
  }
  
  private boolean getActiveturn(){
    return this.activeturn;
  }
  
  // The game state should only be set within this class
  public void setGameState(GameState newState){
    this.gameState = newState;
  }
  
  /**
  * Checks if the current player is the human player
  * @return true if the human player has the current turn
  */
  public boolean hasHumanPlayerTurn(){
    if(this.currentPlayer.equals(humanPlayer)){
      return true;
    }
    return false;
  }
  
  /*
  * Instantiates a new game
  */
  public void newGame(){
    this.victoriousPlayer = null;
    Player player = new HumanPlayer(0, new Tribe('R'));
    Player player2 = new RandomPlayer(1, new Tribe('B'));
    this.playerList.addPlayerToList(player);
    this.playerList.addPlayerToList(player2);
    this.humanPlayer = player;
    
    //this.board.getCityBoard().newCity(new TilePosition(8, 30), player);
    this.board.getCityBoard().newCity(new TilePosition(14, 13), player);
    this.board.getCityBoard().newCity(new TilePosition(15, 9), player2);
    this.board.getUnitBoard().spawnUnit(player, 'W', new TilePosition(6, 29));
    this.board.getUnitBoard().spawnUnit(player, 'E', new TilePosition(7, 30));
    this.board.getUnitBoard().spawnUnit(player2, 'W', new TilePosition(12, 8));
    this.board.getUnitBoard().spawnUnit(player2, 'P', new TilePosition(10, 12));
  }
  
  /*
  * Starts the selected game
  */
  public void startGame(){
    this.gameState = GameState.ACTIVE_GAME;
    this.currentRound = 1;
    turnProcessing();
  }
  
  /**
  * Checks if the human player won the game
  * @param victoriousPlayer - the player that won the game
  * @return true if human player won 
  */
  public boolean didHumanPlayerWin(Player victoriousPlayer){
    if(this.humanPlayer.equals(victoriousPlayer)){
      return true;
    }
    return false;
  }
  
  /**
  * Ends the game and declares a winner
  * @param victoriousPlayer is the player that won
  */
  public void endGame(Player victoriousPlayer){
    setGameState(GameState.GAME_OVER);
    this.victoriousPlayer = victoriousPlayer;
    
    if(didHumanPlayerWin(victoriousPlayer)){
      System.out.println("Victory!");
    }
    else{
      System.out.println("Defeat.");
    }
  }
  
  /*
  * The main game loop
  */
  private void turnProcessing(){
    // Sets tha maximum amount of turns to 500, 
    // and the game should end if there is only 1 player left
    ArrayList<Player> playersInGame = this.playerList.getPlayerList();
    while((this.currentRound < 500) && !(isGameOver())){
      for(int i = 0; i < playersInGame.size(); i++){
        checkStatusOfPlayers();
        if(isGameOver()){
          break;
        }
        Player currentPlayer = playersInGame.get(i);
        playerTurn(currentPlayer);
      }
      checkStatusOfPlayers();
      if(isGameOver()){
        break;
      }
      // Once all player's turn has ended the turn should change by 1
      System.out.println("Turn: " + this.currentRound);
      this.currentRound += 1;
    }
    // End the game and declare a winner
    endGame(playersInGame.get(0));
  }
  
  /**
  * The turn for the player
  * @param player - the player to make moves
  */
  private void playerTurn(Player player){
    newRoundForPlayer(player);
    boolean isTurnActive = this.activeturn;
    
    // Move stuff
    while(isTurnActive == true){
      if(!(hasHumanPlayerTurn())){
        ArrayList<Move> unitMoves = player.getMoves();
        for(Move move : unitMoves){
          this.board.selectUnit(move.selectPosition());
          this.board.moveUnit(move.movePosition());
        }
        endTurn(player);
      }
      isTurnActive = getActiveturn();
    }
  } 
  
  /**
  * Prepares the game for the next player's round
  * @param player - the player that the round will be prepared for
  */
  private void newRoundForPlayer(Player player){
    System.out.println("It is player " + player.getPlayerID() + "'s turn!");
    // Set the turn to active
    this.activeturn = true;
    
    // Change player to this player
    this.board.setCurrentPlayer(player);
    this.currentPlayer = player;
    
    // Replenish city
    this.board.cityNewTurn();
    
    // Replenish units
    this.board.unitsNewTurn();
  }
  
  /*
  * Ends the current player's turn
  */
  public void endTurn(Player player){
    this.board.deSelectUnit();
    this.activeturn = false;
  }
  
  /*
  * Checks the status of all players to see if any of them have been defeated
  */
  public void checkStatusOfPlayers(){
    ArrayList<Player> defeatedPlayers = new ArrayList<>();
    // Finds defeated players
    for(Player player : this.playerList.getPlayerList()){
      if(isPlayerDefeated(player)){
        defeatedPlayers.add(player);
      }
    }
    // Removes the players from the game
    for(Player player : defeatedPlayers){
      defeatPlayer(player);
    }
  }
  
  /**
  * Checks if the game is complete
  * @return true if a player has won the game
  */
  public boolean isGameOver(){
    if(this.playerList.getPlayerList().size() < 2){
      return true;
    }
    return false;
  }
  
  /**
  * Checks wether a player is defeated or not
  * @param player - the player to check for defeat
  * @return true if the player have been defeated
  */
  public boolean isPlayerDefeated(Player player){
    // If the player has no cities return true
    if(player.getCityList().size() < 1){
      return true;
    }
    return false;
  }
  
  /*
  * Defeats a player and removes them from the game 
  */
  public void defeatPlayer(Player player){
    this.playerList.removePlayerFromList(player);
  }
}
