package no.uib.inf101.sample.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sample.controller.Player;
import no.uib.inf101.sample.model.GameModel;
import no.uib.inf101.sample.model.GameSize;
import no.uib.inf101.sample.model.MainMenu;
import no.uib.inf101.sample.model.game.City;
import no.uib.inf101.sample.model.game.GameState;
import no.uib.inf101.sample.model.game.Unit;
import no.uib.inf101.sample.model.map.Terrain;
import no.uib.inf101.sample.model.map.Tile;
import no.uib.inf101.sample.scrapped_classes_i_will_use_later.PixelToHeaderItem;
import no.uib.inf101.sample.view.utility.Inf101Graphics;

/*
* I was advised to not break up the view class into several other classes, as
* it would introduce unnecessary complexity. Well, it ended up being quite large :)
*/
public class GameView extends JPanel {
  private GameModel model;
  private ViewableGameModel viewableModel;
  private GameSize gameSize;
  private MainMenu menu;
  private GameColorTheme theme;
  private GameState gameState = GameState.MAIN_MENU;
  private int menuSize;
  // Header variables
  private double HEADER_HEIGHT = 48;
  //Game box variables
  private double gameBoxHeight;
  private double gameBoxWidth;
  private double gameBoxXStart;
  private double gameBoxYStart;
  private double gameXStart;
  private double gameYStart;
  private double gameXWidth;
  private double gameYHeight;
  // Footer box variables
  private double footerXStart;
  private double footerYStart;
  private double footerWidth;
  private double footerHeight;
  private final double FOOTER_PADDING = 8;
  // Each tile in the game must be 32 pixels big
  private final double TILE_SIZE = 32;
  private final double X_OUTER_MARGIN = 0;
  private final double Y_OUTER_MARGIN = 0;
  private PixelToTilePositionConverter ppt;
  private PixelToHeaderItem phi;
  private ArrayList<BufferedImage> terrainTextureImageList = new ArrayList<>();
  private ArrayList<BufferedImage> headerIconList = new ArrayList<>();
  
  // Constructor
  public GameView(ViewableGameModel boardModel, GameModel model, GameSize gs, GameColorTheme theme, MainMenu mainMenu) {
    this.model = model;
    this.viewableModel = boardModel;
    this.theme = theme;
    this.gameSize = gs;
    this.menu = mainMenu;
    this.menuSize = this.menu.getMenuItems().size();
    this.setFocusable(true);
    this.setPreferredSize(this.gameSize.getGameDimension());
    setGameDimensions();
    instantiateTerrainTextures();
    instantiateUIIcons();
    this.ppt = new PixelToTilePositionConverter(this);
    this.phi = new PixelToHeaderItem(this);
  }
  
  /*
  * Instantiates the textures and adds them to a list of strings - resource URLs
  * ------ INDEX MUST CORRESPOND TO TERRAIN ID ------
  */
  private void instantiateTerrainTextures(){
    ArrayList<String> terrainTextureStringList = new ArrayList<>();
    terrainTextureStringList.add("/textures/missing_texture.png");
    terrainTextureStringList.add("/textures/sea.png");
    terrainTextureStringList.add("/textures/forest.png");
    terrainTextureStringList.add("/textures/grasslands.png");
    terrainTextureStringList.add("/textures/plains.png");
    terrainTextureStringList.add("/textures/hills.png");
    terrainTextureStringList.add("/textures/mountains.png");
    this.terrainTextureImageList = preloadTextures(terrainTextureStringList);
  }
  
  /*
  * Instantiates the icons and adds them to a list of strings - resource URLs
  * ------ INDEX MUST CORRESPOND TO THE CORRECT ICON ------
  */
  private void instantiateUIIcons(){
    ArrayList<String> UIIconStringList = new ArrayList<>();
    UIIconStringList.add("/game_icons/UI_icons/Food.png");
    UIIconStringList.add("/game_icons/UI_icons/Production.png");
    UIIconStringList.add("/game_icons/build_icons/Warrior_Build_Icon.png");
    UIIconStringList.add("/game_icons/build_icons/Elite_Regiment_Build_Icon.png");
    UIIconStringList.add("/game_icons/build_icons/Phalanx_Build_Icon.png");
    //UIIconStringList.add("/game_icons/build_icons/Infrastructure_Build_Icon.png");
    this.headerIconList = preloadTextures(UIIconStringList);
  }
  
  /*
  * Pre-Loads the textures for better performance
  */
  private ArrayList<BufferedImage> preloadTextures(ArrayList<String> stringList){
    ArrayList<BufferedImage> textureList = new ArrayList<>();
    for(String textureString: stringList){
      BufferedImage texture = Inf101Graphics.loadImageFromResources(textureString);
      textureList.add(texture);
    }
    return textureList;
  }
  
  /*
  * Sets the game's dimensions to be used in calculations
  * Reduces redundancy with these calculations
  */
  public void setGameDimensions(){
    this.gameBoxWidth = (this.getWidth() - (2 * X_OUTER_MARGIN));
    this.gameBoxHeight = (TILE_SIZE * this.model.getGBM().getDimension().rows());
    this.gameBoxXStart = X_OUTER_MARGIN;
    this.gameBoxYStart = Y_OUTER_MARGIN + this.HEADER_HEIGHT;
    
    double tileSizeSum = this.TILE_SIZE * this.viewableModel.getDimension().cols();
    this.gameXStart = ((this.gameBoxWidth - (tileSizeSum)) / 2) + X_OUTER_MARGIN;
    this.gameYStart = this.gameBoxYStart;
    this.gameXWidth = tileSizeSum;
    this.gameYHeight = (TILE_SIZE * this.model.getGBM().getDimension().rows());
    
  }
  
  /**
  * The paintComponent method is called by the Java Swing framework every time
  * either the window opens or resizes, or we call .repaint() on this object. 
  * Note: NEVER call paintComponent directly yourself
  */
  @Override
  public void paintComponent(Graphics g) {
    setGameDimensions();
    this.gameState = this.model.getCurrenGameState();
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g2);
    drawHeader(g2);
  }
  
  /*
  * Draws the header at the top of the game
  */
  public void drawHeader(Graphics2D g2){
    Rectangle2D header = new Rectangle2D.Double(X_OUTER_MARGIN, Y_OUTER_MARGIN, this.gameBoxWidth, this.HEADER_HEIGHT);
    g2.setColor(theme.getPrimaryColor());
    g2.fill(header);
    g2.setColor(theme.getFontColor());
    g2.setFont(theme.getMenuFont());
    Inf101Graphics.drawCenteredString(g2, "", header);
    
    // Draw the main title if in main menu
    if(this.gameState == GameState.MAIN_MENU){
      g2.setColor(theme.getFontColor());
      g2.setFont(theme.getMenuFont());
      Inf101Graphics.drawCenteredString(g2, "Blood, Steel, Glory", header);
      
    }
    // Draw the UI for the game if the game is active
    if(this.gameState == GameState.ACTIVE_GAME){
      drawUIHeader(g2, header);
    }
    drawGameBox(g2);
  }
  
  /*
  * Draws the Header with game information
  */
  public void drawUIHeader(Graphics2D g2, Rectangle2D header){
    // For player-spcific information
    Player humanPlayer = this.model.getHumanPlayer();
    City playerCity = this.viewableModel.getPlayerCity(humanPlayer);
    
    // Draw current turn
    int currentTurn = this.model.getCurrentRound();
    String turnString = ("Turn: " + currentTurn);
    double turnBlockWidth = 100;
    double turnBlockXStart = X_OUTER_MARGIN + 4;
    Rectangle2D turnBlock = new Rectangle2D.Double(turnBlockXStart, Y_OUTER_MARGIN, turnBlockWidth, this.HEADER_HEIGHT);
    
    g2.setColor(this.theme.getFontColor());
    g2.setFont(this.theme.getUIFont());
    Inf101Graphics.drawCenteredString(g2, turnString, turnBlock);
    
    // Draw food and production
    
    // Draw food icon
    double SPACING = 32;
    double foodIconStart = (X_OUTER_MARGIN + turnBlockWidth) + SPACING;
    double foodIconWidth = 32;
    Rectangle2D foodIconR = new Rectangle2D.Double(foodIconStart, Y_OUTER_MARGIN, foodIconWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getVoidColor());
    g2.fill(foodIconR);
    BufferedImage foodIcon = this.headerIconList.get(0);
    Inf101Graphics.drawCenteredImage(g2, foodIcon, foodIconR.getCenterX(), foodIconR.getCenterY(), 0.5);
    
    // Draw food Amount
    double iconSpacing = 8;
    double foodStringStart = (foodIconStart + foodIconWidth) + iconSpacing;
    double foodStringWidth = 10;
    int currentFood = playerCity.getCurrentFood();
    String foodString = String.valueOf(currentFood);
    Rectangle2D foodBlock = new Rectangle2D.Double(foodStringStart, Y_OUTER_MARGIN, foodStringWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getFontColor());
    g2.setFont(this.theme.getUIFont());
    Inf101Graphics.drawCenteredString(g2, foodString, foodBlock);
    
    
    // Draw Production icon
    double prodIconStart = (foodStringStart + foodStringWidth) + (SPACING / 2);
    double prodIconWidth = 32;
    Rectangle2D prodIconR = new Rectangle2D.Double(prodIconStart, Y_OUTER_MARGIN, prodIconWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getVoidColor());
    g2.fill(prodIconR);
    BufferedImage prodIcon = this.headerIconList.get(1);
    Inf101Graphics.drawCenteredImage(g2, prodIcon, prodIconR.getCenterX(), prodIconR.getCenterY(), 0.5);
    
    // Draw Production Amount
    double prodStringStart = (prodIconStart + prodIconWidth) + iconSpacing;
    double prodStringWidth = 10;
    int currentProd = playerCity.getCurrentProduction();
    String prodString = String.valueOf(currentProd);
    Rectangle2D prodBlock = new Rectangle2D.Double(prodStringStart, Y_OUTER_MARGIN, prodStringWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getFontColor());
    g2.setFont(this.theme.getUIFont());
    Inf101Graphics.drawCenteredString(g2, prodString, prodBlock);
    
    // Draw Unit Production Progress BG
    double productionWidth = 400;
    double prodBarXStart = ((this.gameBoxWidth - productionWidth) / 2) + X_OUTER_MARGIN;
    Rectangle2D prodBarBG = new Rectangle2D.Double(prodBarXStart, this.Y_OUTER_MARGIN, productionWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getBlackColor());
    g2.fill(prodBarBG);
    
    // Draw Unit Production Progress FG
    double prodCost = playerCity.getCurrentlyProducedItem().getProductionCost();
    double maxProduction = playerCity.getStoredProduction();
    //double productionTurns = playerCity.getCurrentProduction();
    double fullProdFactor = prodCost / 400;
    double progressFactor = maxProduction / 400;
    double prodBarProgress =  (progressFactor / fullProdFactor) * 400;
    // Stops bar from exceeding the bounds
    if (productionWidth < prodBarProgress){
      prodBarProgress = productionWidth;
    }
    Rectangle2D prodBarFG = new Rectangle2D.Double(prodBarXStart, this.Y_OUTER_MARGIN, prodBarProgress, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getProductionColor());
    g2.fill(prodBarFG);
    
    // Draw Unit Production Progress String
    String producedUnit = playerCity.getCurrentlyProducedItem().getUnitName();
    String productionString = producedUnit + ": " + String.valueOf(playerCity.getTurnsToProduction());
    Rectangle2D prodStringBar = new Rectangle2D.Double(prodBarXStart, this.Y_OUTER_MARGIN, productionWidth, this.HEADER_HEIGHT);
    g2.setColor(this.theme.getFontColor());
    g2.setFont(this.theme.getUIFont());
    Inf101Graphics.drawCenteredString(g2, productionString, prodStringBar);
    
    // Draw end turn box
    double endTurnBlockWidth = 300;
    double endTurnBlockStartX = (this.gameBoxWidth - endTurnBlockWidth) + X_OUTER_MARGIN;
    Rectangle2D endTurnBlock = new Rectangle2D.Double(endTurnBlockStartX, Y_OUTER_MARGIN, endTurnBlockWidth, this.HEADER_HEIGHT);
    
    String endTurnString;
    if(this.model.hasHumanPlayerTurn()){
      endTurnString = "End Turn";
    }
    else{
      endTurnString = "AI's turn...";
    }
    g2.setColor(this.theme.getFontColor());
    g2.setFont(this.theme.getUIFont());
    Inf101Graphics.drawCenteredString(g2, endTurnString, endTurnBlock);
    
    // Draw outline for button
    g2.setColor(theme.getTertiaryColor());
    Rectangle2D headerOutline = new Rectangle2D.Double(endTurnBlockStartX, Y_OUTER_MARGIN, endTurnBlockWidth, this.HEADER_HEIGHT);
    g2.draw(headerOutline);
  }
  
  /*
  * Draws the game Box between the header and footer
  */
  public void drawGameBox(Graphics2D g2){
    Rectangle2D backGround = new Rectangle2D.Double(this.gameBoxXStart, this.gameBoxYStart, this.gameBoxWidth, this.gameBoxHeight);
    g2.setColor(this.theme.getSecondaryColor());
    g2.fill(backGround);
    
    drawFooter(g2);
  }
  
  public void drawFooter(Graphics2D g2){
    // Draws the footer box
    double footerXStart = X_OUTER_MARGIN;
    double footerYStart = this.gameYStart + this.gameBoxHeight;
    double footerWidth = this.getWidth() - (2 * X_OUTER_MARGIN);
    double footerHeight = (this.getHeight() - footerYStart) - (2 * Y_OUTER_MARGIN);
    this.footerXStart = footerXStart;
    this.footerYStart = footerYStart;
    this.footerWidth = footerWidth;
    this.footerHeight = footerHeight;
    
    Rectangle2D footer = new Rectangle2D.Double(footerXStart, footerYStart, footerWidth, footerHeight);
    g2.setColor(this.theme.getPrimaryColor());
    g2.fill(footer);
    
    // Draw the UI for the game if the game is active
    if(this.gameState == GameState.ACTIVE_GAME){
      drawUIFooter(g2, footer);
    }
    // Draw the game itself
    drawGame(g2);
    drawUIOutline(g2);
  }
  
  /*
  * Draws the UI footer
  */
  public void drawUIFooter(Graphics2D g2, Rectangle2D footer){
    // Draws the footer box
    double footerXStart = X_OUTER_MARGIN;
    double footerYStart = this.gameYStart + this.gameBoxHeight;
    double footerWidth = this.getWidth() - (2 * X_OUTER_MARGIN);
    double footerHeight = (this.getHeight() - footerYStart) - (2 * Y_OUTER_MARGIN);
    this.footerXStart = footerXStart;
    this.footerYStart = footerYStart;
    this.footerWidth = footerWidth;
    this.footerHeight = footerHeight;
    
    Rectangle2D backGround = new Rectangle2D.Double(footerXStart, footerYStart, footerWidth, footerHeight);
    g2.setColor(this.theme.getPrimaryColor());
    g2.fill(backGround);
    
    // Draw unit information
    double FOOTER_PADDING = this.FOOTER_PADDING;
    double unitIconXStart = footerXStart + FOOTER_PADDING;
    double unitIconYStart = footerYStart + FOOTER_PADDING;
    double unitIconWidth = 150;
    double unitIconHeight = footerHeight - (2 * FOOTER_PADDING);
    Rectangle2D unitIconBox = new Rectangle2D.Double(unitIconXStart, unitIconYStart, unitIconWidth, unitIconHeight);
    
    if(this.model.getGBM().getCurrentlySelectedUnit() != null){
      // Draw selected unit icon with color
      Unit currentlySelectedUnit = this.model.getGBM().getCurrentlySelectedUnit();
      Player unitOwner = currentlySelectedUnit.getPlayer();
      if(unitOwner.equals(this.model.getHumanPlayer())){
        g2.setColor(this.theme.getVoidColor());
        g2.fill(unitIconBox);
        String unitIconString = currentlySelectedUnit.getUnitIcon();
        String playerColorString = unitOwner.getTribe().getTribeColorString();
        String icon = (unitIconString + playerColorString + ".png");
        BufferedImage unitIcon = Inf101Graphics.loadImageFromResources(icon);
        Inf101Graphics.drawCenteredImage(g2, unitIcon, unitIconBox.getCenterX(), unitIconBox.getCenterY(), 1);
        
        // Draw Unit name
        double unitTextHeight = this.theme.getUIFont().getSize();
        int unitTextXStart = (int) (unitIconXStart + unitIconWidth + FOOTER_PADDING);
        int unitNameYStart = (int) (unitIconYStart + 3*FOOTER_PADDING);
        String unitName = currentlySelectedUnit.getUnitName();
        g2.setColor(this.theme.getFontColor());
        g2.setFont(this.theme.getUIFont());
        g2.drawString(unitName, unitTextXStart, unitNameYStart);
        
        // Draw unit strength
        int unitStrengthYStart = (int) ((unitNameYStart + unitTextHeight) + FOOTER_PADDING);
        String unitStrength = currentlySelectedUnit.strengthToString();
        g2.setColor(this.theme.getFontColor());
        g2.setFont(this.theme.getUIFont());
        g2.drawString(unitStrength, unitTextXStart, unitStrengthYStart);
        
        // Draw unit HP
        int unitHPYStart = (int) ((unitStrengthYStart + unitTextHeight) + FOOTER_PADDING);
        String unitHP = currentlySelectedUnit.hpToString();
        g2.setColor(this.theme.getFontColor());
        g2.setFont(this.theme.getUIFont());
        g2.drawString(unitHP, unitTextXStart, unitHPYStart);
        
        // Draw Movement
        int unitMovementYStart = (int) ((unitHPYStart + unitTextHeight) + FOOTER_PADDING);
        String unitMovement = currentlySelectedUnit.movementToString();
        g2.setColor(this.theme.getFontColor());
        g2.setFont(this.theme.getUIFont());
        g2.drawString(unitMovement, unitTextXStart, unitMovementYStart);
      }
    }
  }
  
  /*
  *Draws the game board 
  */
  private void drawGame(Graphics2D g2){
    Rectangle2D backGround = new Rectangle2D.Double(this.gameXStart, this.gameYStart, this.gameXWidth, this.gameYHeight);
    g2.setColor(this.theme.getSecondaryColor());
    g2.fill(backGround);
    
    TilePositionToPixelConverter tpPixel = new TilePositionToPixelConverter(backGround, this.TILE_SIZE);
    drawTiles(g2, this.viewableModel.getTilesOnMap(), tpPixel);
  }
  
  /*
  * Draws the tiles in the game board
  */
  private void drawTiles(Graphics2D g2, Iterable<Tile<Terrain>> boardTiles, TilePositionToPixelConverter tpPixel){
    for(Tile<Terrain> tile: boardTiles){
      Rectangle2D drawTile = tpPixel.getBoundsForTile(tile.pos());
      g2.setColor(this.theme.getBlackColor());
      g2.fill(drawTile);
      int terrainTextureID = tile.value().getTextureID();
      BufferedImage terrainTextureImg = this.terrainTextureImageList.get(terrainTextureID);
      Inf101Graphics.drawCenteredImage(g2, terrainTextureImg, drawTile.getCenterX(), drawTile.getCenterY(), 2);
    }
    
    // Checks the game state
    if(this.gameState == GameState.MAIN_MENU){
      drawMainMenu(g2);
    }
    if(this.gameState == GameState.ACTIVE_GAME){
      drawCities(g2, this.viewableModel.getCitiesOnMap(), tpPixel);
    }
    if(this.gameState == GameState.GAME_OVER){
      drawGameOver(g2);
    }
  }
  
  /*
  * Draws Main menu
  */
  private void drawMainMenu(Graphics2D g2){
    double menuWidth = this.getWidth() - (2 * X_OUTER_MARGIN);
    double menuHeight = this.getHeight() - (2 * X_OUTER_MARGIN);
    drawMenuSelection(g2, menuWidth, menuHeight);
  }
  
  // Draws the selections in the menu
  private void drawMenuSelection(Graphics2D g2, double width, double height){
    double menuStartX = width / 3;
    double menuStartY = height / 3;
    double menuWidth = width / 3;
    double menuHeight = height / 6;
    Rectangle2D menuBlock = new Rectangle2D.Double(menuStartX, menuStartY, menuWidth, menuHeight);
    g2.setColor(this.theme.getPrimaryColor());
    g2.fill(menuBlock);
    
    // Draw selection
    int menuIndex = this.menu.getMenuIndex();
    MenuItemToPixelConverter itemPixel = new MenuItemToPixelConverter(menuBlock, 0, this.menuSize, menuIndex);
    
    for(int i = 0; i < this.menuSize; i++){
      Rectangle2D itemRectangle = itemPixel.getBoundsForItem(i);
      if(i == this.menu.getMenuIndex()){
        g2.setColor(this.theme.getSecondaryColor());
      }
      else{
        g2.setColor(this.theme.getPrimaryColor());
      }
      g2.fill(itemRectangle);
      g2.setFont(this.theme.getMenuFont());
      g2.setColor(this.theme.getFontColor());
      Inf101Graphics.drawCenteredString(g2, this.menu.getMenuItems().get(i) , itemRectangle);
    }
  }
  
  /*
  * Draws the greyed out game over screen
  */
  public void drawGameOver(Graphics2D g2){
    // Draw the box itself
    double gameOverXStart = this.gameXStart;
    double gameOverYStart = this.gameYStart;
    double gameOverXWidth = this.gameXWidth;
    double gameOverYHeight = this.gameYHeight;
    Rectangle2D gameOverBox = new Rectangle2D.Double(gameOverXStart, gameOverYStart, gameOverXWidth, gameOverYHeight);
    g2.setColor(this.theme.getMorallyGreyColor());
    g2.fill(gameOverBox);
    
    String gameOverTitle;
    String gameOverSubtext;
    String gameOverReturn = "Press 'Enter' to return to menu.";
    
    // Checks if human won
    if(this.model.didHumanPlayerWin(this.model.getVictoriousPlayer())){
      gameOverTitle= "Victory!";
      gameOverSubtext = "Long will your triumph be remembered.";
    }
    else{
      gameOverTitle = "Defeat.";
      gameOverSubtext= "The future of your people is uncertain...";
    }
    g2.setFont(this.theme.getMenuFont());
    g2.setColor(this.theme.getFontColor());
    Inf101Graphics.drawCenteredString(g2, gameOverTitle, gameOverBox);
    
    double screenTextPadding = 3*this.FOOTER_PADDING;
    double gameOverSubtextYStart = gameOverYStart + screenTextPadding;
    double gameOverSubtextYHeight = gameOverYHeight + screenTextPadding;
    Rectangle2D gameOverSubtextBox = new Rectangle2D.Double(gameOverXStart, gameOverSubtextYStart, gameOverXWidth, gameOverSubtextYHeight);
    g2.setFont(this.theme.getUIFont());
    g2.setColor(this.theme.getFontColor());
    Inf101Graphics.drawCenteredString(g2, gameOverSubtext, gameOverSubtextBox);
    
    double gameOverReturnYStart = gameOverSubtextYStart + screenTextPadding;
    double gameOverReturnYHeight = gameOverSubtextYHeight + screenTextPadding;
    Rectangle2D gameOverReturnBox = new Rectangle2D.Double(gameOverXStart, gameOverReturnYStart, gameOverXWidth, gameOverReturnYHeight);
    g2.setFont(this.theme.getUIFont());
    g2.setColor(this.theme.getFontColor());
    Inf101Graphics.drawCenteredString(g2, gameOverReturn, gameOverReturnBox);
  }
  
  /**
  * Draws the cities in the game
  */
  private void drawCities(Graphics2D g2, Iterable<Tile<City>> cityBoard, TilePositionToPixelConverter tpPixel){
    for(Tile<City> selectedCity: cityBoard){
      City city = selectedCity.value();
      if(!(city == null)){
        String tribeColorString = city.getPlayer().getTribe().getTribeColorString();
        Rectangle2D drawCity = tpPixel.getBoundsForTile(selectedCity.pos());
        g2.setColor(this.theme.getVoidColor());
        g2.fill(drawCity);
        
        String cityIconString = city.getCityIconString();
        String icon = (cityIconString + tribeColorString + ".png");
        BufferedImage cityIcon = Inf101Graphics.loadImageFromResources(icon);
        Inf101Graphics.drawCenteredImage(g2, cityIcon, drawCity.getCenterX(), drawCity.getCenterY(), 0.3);
        
        int cityHP = city.getCityHealthPoints();
        if(cityHP < 100){
          // Start healthbar 4 px down for good looks
          double hbMargin = 4;
          double healthX = drawCity.getBounds().getX();
          double healthY = drawCity.getBounds().getY() + hbMargin;
          double hbWidth = 2;
          double hbHeight = drawCity.getHeight() - (2 * hbMargin);
          Rectangle2D healthBarBG = new Rectangle2D.Double(healthX, healthY, hbWidth, hbHeight);
          g2.setColor(this.theme.getHealthBarColor());
          g2.fill(healthBarBG);
          Color hpColor = this.theme.getVoidColor();
          
          if (70 <= cityHP){
            hpColor = this.theme.getHighHealthColor();
          }
          else if(cityHP < 70){
            hpColor = this.theme.getMidHealthColor();
          }
          else if(cityHP < 20){
            hpColor = this.theme.getLowHealthColor();
          }
          double maxHP = 100.0;
          double cityHPFactor = cityHP / maxHP;
          double healthHeight = hbHeight * cityHPFactor;
          double healthBarY = healthY + (hbHeight - healthHeight);
          Rectangle2D healthBarFG = new Rectangle2D.Double(healthX, healthBarY, hbWidth, healthHeight);
          g2.setColor(hpColor);
          g2.fill(healthBarFG);
        }
      }
    }
    drawUnits(g2, this.viewableModel.getUnitsOnMap(), tpPixel);
  }
  /*
  * Draws the units 
  */
  private void drawUnits(Graphics2D g2, Iterable<Tile<Unit>> unitBoard, TilePositionToPixelConverter tpPixel){
    for(Tile<Unit> unit: unitBoard){
      if(unit.value() != null){
        Rectangle2D drawUnit = tpPixel.getBoundsForTile(unit.pos());
        g2.setColor(this.theme.getVoidColor());
        g2.fill(drawUnit);
        String unitIconString = unit.value().getUnitIcon();
        String playerColorString = unit.value().getPlayer().getTribe().getTribeColorString();
        String icon = (unitIconString + playerColorString + ".png");
        BufferedImage unitIcon = Inf101Graphics.loadImageFromResources(icon);
        Inf101Graphics.drawCenteredImage(g2, unitIcon, drawUnit.getCenterX(), drawUnit.getCenterY(), 0.3);
        
        int unitHP = unit.value().getHealthPoints();
        if(unitHP < 100){
          // Start healthbar 4 px down for good looks
          double hbMargin = 4;
          double healthX = drawUnit.getBounds().getX();
          double healthY = drawUnit.getBounds().getY() + hbMargin;
          double hbWidth = 2;
          double hbHeight = drawUnit.getHeight() - (2 * hbMargin);
          Rectangle2D healthBarBG = new Rectangle2D.Double(healthX, healthY, hbWidth, hbHeight);
          g2.setColor(this.theme.getHealthBarColor());
          g2.fill(healthBarBG);
          Color hpColor = this.theme.getVoidColor();
          
          if (70 <= unitHP){
            hpColor = this.theme.getHighHealthColor();
          }
          else if(unitHP < 70){
            hpColor = this.theme.getMidHealthColor();
          }
          else if(unitHP < 20){
            hpColor = this.theme.getLowHealthColor();
          }
          double maxHP = 100.0;
          double unitHPFactor = unitHP / maxHP;
          double healthHeight = hbHeight * unitHPFactor;
          double healthBarY = healthY + (hbHeight - healthHeight);
          Rectangle2D healthBarFG = new Rectangle2D.Double(healthX, healthBarY, hbWidth, healthHeight);
          g2.setColor(hpColor);
          g2.fill(healthBarFG);
        }
      }
    }
  }
  
  /*
  * Draws the UI Outline for a cleaner look
  */
  private void drawUIOutline(Graphics2D g2){
    // Header Outline
    Rectangle2D headerOutline = new Rectangle2D.Double(X_OUTER_MARGIN, Y_OUTER_MARGIN, this.gameBoxWidth, this.HEADER_HEIGHT);
    g2.setColor(theme.getTertiaryColor());
    g2.draw(headerOutline);
    
    // footer Outline
    Rectangle2D footerOutline = new Rectangle2D.Double(this.footerXStart, this.footerYStart, this.footerWidth, this.footerHeight);
    g2.setColor(theme.getTertiaryColor());
    g2.draw(footerOutline);
  }
  
  //Field variable getters below
  // X and Y Margin should be the same anyway for now
  // I will fix this when further developing this on my own later
  public double getMargin(){
    return this.X_OUTER_MARGIN;
  }
  public double getTileSize(){
    return this.TILE_SIZE;
  }
  public double getHeaderHeight(){
    return this.HEADER_HEIGHT;
  }
  public double getGameHeight(){
    return this.gameBoxHeight;
  }
  public double getGameWidth(){
    return this.gameXWidth;
  }
  public double getGameXStart(){
    return this.gameXStart;
  }
  public double getGameYStart(){
    return this.gameYStart;
  }
  public PixelToTilePositionConverter getPPT(){
    return this.ppt;
  }
  public PixelToHeaderItem getPHI(){
    return this.phi;
  }
  
  // /*
  // * Draws the city's borders
  // * SCRAPPED DUE TO TIME CONSTRAINTS, BUT IT WORKS
  // * 
  // */
  // private void drawCityBorders(Graphics2D g2, City city, TilePositionToPixelConverter tpPixel){
  //   Iterable<TilePosition> cityBorders = city.getCityBorders();
  //   Player player = city.getPlayer();
  //   for(TilePosition tile : cityBorders){
  //     if(!(tile.equals(city.getCityPosition()))){
  //       Rectangle2D drawBorderTile = tpPixel.getBoundsForTile(tile);
  //       Color redTribe = new Color(0xff3131);
  //       g2.setColor(redTribe);
  //       g2.fill(drawBorderTile);
  //     }
  //   }
  // }
}
