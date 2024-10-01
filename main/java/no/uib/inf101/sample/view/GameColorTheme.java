package no.uib.inf101.sample.view;

import java.awt.Color;
import java.awt.Font;

/*
* A class containing the colors, fonts, etc of the game
*/
public class GameColorTheme implements ColorTheme{
  private final Font UIfont = new Font("Serif", Font.BOLD, 24);
  private final Font menuFont = new Font("Serif", Font.BOLD, 40);
  private final Color fontColor = new Color(0xC0C0C0);
  private final Color backgroundColor = new Color(0x06768d);
  private final Color morallyGrey = new Color(0, 0, 0, 120);
  private final Color voidColor = new Color(0,0,0, 0);
  private final Color healthBarColor = new Color(0x231f20);
  private final Color highHealthcolor = new Color(0x1db954);
  private final Color midHealthcolor = new Color(0xffff00);
  private final Color lowHealthcolor = new Color(0xcc0000);
  private final Color productionBarColor = new Color(0xcd853f);
  private final Color selectedTileColor = new Color(230,230,230, 20);
  private final Color primaryColor = new Color(0x003747);
  private final Color secondaryColor = new Color(0x06768d);
  private final Color tertiaryColor = new Color(0xC0C0C0);
  private final Color blackColor = new Color(0x231f20);
  
  // The getters explain themselves
  @Override
  public Font getUIFont() {
    return this.UIfont;
  }
  
  @Override
  public Font getMenuFont() {
    return this.menuFont;
  }
  
  @Override
  public Color getFontColor() {
    return this.fontColor;
  }
  
  @Override
  public Color getBackgroundColor() {
    return this.backgroundColor;
  }
  
  
  public Color getMorallyGreyColor() {
    return this.morallyGrey;
  }
  
  // Void Color is transparent
  @Override
  public Color getVoidColor() {
    return this.voidColor;
  }
  
  public Color getHealthBarColor(){
    return this.healthBarColor;
  }
  
  public Color getHighHealthColor(){
    return this.highHealthcolor;
  }
  
  public Color getMidHealthColor(){
    return this.midHealthcolor;
  }
  
  public Color getLowHealthColor(){
    return this.lowHealthcolor;
  }
  
  public Color getProductionColor(){
    return this.productionBarColor;
  }
  
  @Override
  public Color getSelectedTileColor(){
    return this.selectedTileColor;
  }
  
  @Override
  public Color getPrimaryColor() {
    return this.primaryColor;
  }
  
  @Override
  public Color getSecondaryColor() {
    return this.secondaryColor;
  }
  
  @Override
  public Color getTertiaryColor() {
    return this.tertiaryColor;
  }
  
  public Color getBlackColor() {
    return this.blackColor;
  }
  
}
