package no.uib.inf101.sample;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import no.uib.inf101.sample.controller.GameController;
import no.uib.inf101.sample.model.GameBoardModel;
import no.uib.inf101.sample.model.GameModel;
import no.uib.inf101.sample.model.GameSize;
import no.uib.inf101.sample.model.MainMenu;
import no.uib.inf101.sample.model.game.CityBoard;
import no.uib.inf101.sample.model.game.GameMap;
import no.uib.inf101.sample.model.game.UnitBoard;
import no.uib.inf101.sample.view.GameColorTheme;
import no.uib.inf101.sample.view.GameView;

public class Main {
  public static final String WINDOW_TITLE = "Blood, Steel, Glory";
  
  public static void main(String[] args) {
    GameColorTheme theme = new GameColorTheme();
    GameMap map = GameMap.createDefaultMap();
    UnitBoard unitBoard = UnitBoard.newUnitBoard(map.getRows(), map.getCols());
    CityBoard cityBoard = CityBoard.newCityBoard(map.getRows(), map.getCols());
    MainMenu mainMenu = new MainMenu();
    GameBoardModel boardModel = new GameBoardModel(map, unitBoard, cityBoard);
    GameModel model = new GameModel(boardModel);
    GameSize gamesize = new GameSize();
    
    GameView view = new GameView(boardModel, model, gamesize, theme, mainMenu);
    new GameController(model, view, mainMenu);
    
    // The JFrame is the "root" application window.
    // We here set some properties of the main window, 
    // and tell it to display our tetrisView
    JFrame frame = new JFrame(WINDOW_TITLE);
    ImageIcon gameIcon = new ImageIcon(Main.class.getResource("/game_icons/Phalanx_Game_Icon.png"));
    frame.setIconImage(gameIcon.getImage());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Here we set which component to view in our window
    frame.setContentPane(view);
    
    // Call these methods to actually display the window
    frame.pack();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
