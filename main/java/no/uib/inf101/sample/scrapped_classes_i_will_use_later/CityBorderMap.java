package no.uib.inf101.sample.scrapped_classes_i_will_use_later;
// package no.uib.inf101.sample.model.game;

// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;

// import no.uib.inf101.sample.model.map.MapGrid;
// import no.uib.inf101.sample.model.map.Tile;
// import no.uib.inf101.sample.model.map.TilePosition;

/*
 * A class for calculating and containing all the citie's borders
 * SCRAPPED DUE TO TIME CONSTRAINTS
 */
// public class CityBorderMap extends MapGrid<Character>{
//     private CityBoard cityBoard;
//     private Map<Character, Set<TilePosition>> cityBorderList = new HashMap<>();
    
//     // Constructor
//     private CityBorderMap(CityBoard cityBoard){
//         super(cityBoard.rows(), cityBoard.cols(), '-');
//         this.cityBoard = cityBoard;

//         updateCityBoard(cityBoard);
//     }

//     // Actual Constructor
//     public static CityBorderMap newCityBorderMap(CityBoard cityBoard){
//         return new CityBorderMap(cityBoard);
//     }

//     // Field variable getters below
//     public Map<Character, Set<TilePosition>> getCityBorderMap(){
//         return this.cityBorderList;
//     }
    

//     /**
//      * Updates the city board so the calss knows where the cities are
//      * @param cityBoard - the updated city board
//      */
//     public void updateCityBoard(CityBoard cityBoard){
//         for(Tile<City> cityBoardTile : cityBoard){
//             City city = cityBoardTile.value();
//             if(!(city == null)){
//                 char cityChar = city.getPlayer().getTribe().getTribeCharacter();
//                 TilePosition cityPosition = city.getCityPosition();
//                 this.set(cityPosition, cityChar);
//                 // Add the city to the cityBorder map
//                 if(!(this.cityBorderList.containsKey(cityChar))){
//                     Set<TilePosition> cityTiles = new HashSet<>();
//                     cityTiles.add(cityPosition);
//                     this.cityBorderList.put(cityChar, cityTiles);
//                 }
//             }
//         }
//         this.cityBoard = cityBoard;
//     }

//     /*
//      * Updates the city border map to fit with the new borders
//      * ------ Helper method ------
//      */
//     private void updateCityBorderMap(TilePosition position, char cityChar){
//         Set<TilePosition> cityTiles = new HashSet<>();

//         // Add the tile to the cityTiles map
//         cityTiles = this.cityBorderList.get(cityChar);
//         cityTiles.add(position);
//         this.cityBorderList.put(cityChar, cityTiles);

//         // Remove tile from other player

//     }

//     /**
//      * Expands a given city's borders
//      * Two new rows and columns are always created upon expansion, 
//      * and these are in both directions.
//      * If the borders expand into another player's city it should overwrite
//      * the tiles to the current city. Except for the city tile itself.
//      * @param city - the city to expand borders 
//      */
//     public void expandCityBorders(City city){
//         int cityRow = city.getCityPosition().row();
//         int cityCol = city.getCityPosition().col();
        
//         // Expand the border level by 1 so the city expands borders by 1 row and column
//         int borderLevel = city.getBorderLevel();

//         // Creates the border tiles
//         for(int i = -borderLevel; i < (borderLevel + 1); i++){
//             for(int j = -borderLevel; j < (borderLevel + 1); j++){
//                 TilePosition newTile = new TilePosition((cityRow + i), (cityCol + j));
//                 // Check that new tile is valid
//                 if(checkNewBorderTile(newTile, city)){
//                     char cityChar = city.getPlayer().getTribe().getTribeCharacter();
//                     this.set(newTile, cityChar);
//                     updateCityBorderMap(newTile, cityChar);
//                 }
//             }
//         }
//     }

//     /**
//      * Checks that new border positions are inside the game's bounds, 
//      * and that they can not be the actual position of another player's city 
//      * ------ Helper method for expandCityBorders ------
//      * @param position the position to be expanded into
//      * @param city the city that is expanding it's borders
//      * @return - true if the new tile is valid
//      */
//     private boolean checkNewBorderTile(TilePosition position, City city){
//         // Checks if the tile's position is null
//         if(position == null){
//             return false;
//         }
//         // Is position within bounds
//         if(!(this.positionIsOnMap(position))){
//             return false;
//         }
//         // If position's city is another player's city - return false
//         if(!(this.cityBoard.get(position) == null)){
//             Player thisPlayer = city.getPlayer();
//             Player otherPlayer = this.cityBoard.get(position).getPlayer();
//             // Checks if players are the same or not
//             if(!(otherPlayer.equals(thisPlayer))){
//                 return false;
//             }
//         }
//         return true;
//     }

//     /**
//      * Removes a city's borders upon player defeat
//      * The hashmap removal doenst work but we have no time to fix it
//      * @param city - the city to be defeated
//      */
//     public void removeCityBorders(City city){
//         char defeatedPlayerChar = city.getPlayer().getTribe().getTribeCharacter();
//         for(Tile<Character> tile: this){
//             TilePosition tilePosition = tile.pos();
//             if(this.get(tilePosition) == defeatedPlayerChar){
//                 this.set(tilePosition, null);
//                // this.cityBorderList.remove(defeatedPlayerChar);
//             }
//         }
//     }
// }