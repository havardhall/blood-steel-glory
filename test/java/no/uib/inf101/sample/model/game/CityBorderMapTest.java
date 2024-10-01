package no.uib.inf101.sample.model.game;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.Map;
// import java.util.Set;
// import java.util.HashSet;

// import org.junit.jupiter.api.Test;

// import no.uib.inf101.sample.model.map.TilePosition;

// SCRAPPED DUE TO TIME CONSTRAINTS
// STILL IN THE FILES BECAUSE I WILL USE IT LATER ON

// public class CityBorderMapTest {
//     /*
//      * Checks that cities are placed on the map
//      */
//     @Test
//     public void cityBorderMapConstructorTest(){
//         // Set up test scenario
//         CityBoard cb = CityBoard.newCityBoard(8, 8);
//         CityBorderMap cbm = CityBorderMap.newCityBorderMap(cb);
//         TilePosition t1 = new TilePosition(1, 1);
//         Player p1 = new Player(0, new Tribe('R'));

//         // Check that the tile is empty on the map
//         assertEquals(null, cbm.get(t1));

//         // Check that a city is placed on the cityBorderMap
//         City c1 = cb.newCity(t1, p1);
//         cbm.updateCityBoard(cb.getCityBoard());
//         assertEquals('R', cbm.get(t1));
//     }

//     /*
//      * Check that a city's borders expand correctly
//      */
//     @Test
//     public void expandCityBordersTest(){
//         // Set up test scenario
//         CityBoard cb = CityBoard.newCityBoard(8, 8);
//         CityBorderMap cbm = CityBorderMap.newCityBorderMap(cb);
//         Player p1 = new Player(0, new Tribe('R'));
//         TilePosition t1 = new TilePosition(4, 4);
//         TilePosition t2 = new TilePosition(5, 4);
        
//         City c1 = cb.newCity(t1, p1);
//         cbm.updateCityBoard(cb.getCityBoard());

//         // Check that the tile is empty on the map
//         assertEquals(null, cbm.get(t2));

//         // Check that borders expands correctly
//         c1.growCity();
//         cbm.expandCityBorders(c1);
//         assertEquals('R', cbm.get(t2));

//         // Checks that the amount of tiles are correct for the first three expansions
//         Map<Character, Set<TilePosition>> map = cbm.getCityBorderMap();
//         Set<TilePosition> cityTiles = map.get('R');
//         assertEquals(9, cityTiles.size());
//         c1.growCity();
//         cbm.expandCityBorders(c1);
//         assertEquals(25, cityTiles.size());
//         c1.growCity();
//         cbm.expandCityBorders(c1);
//         assertEquals(36, cityTiles.size());
//         c1.growCity();
//         cbm.expandCityBorders(c1);
//         assertEquals(49, cityTiles.size());

//         // Check that borders stay within the game's bounds


//         // Check that border expansion overwrites another player's borders


//         // Check that border can not expand into the enemy city itself


//     }

//     /*
//      * Check that a city's borders can be removed 
//      */
//     @Test
//     public void removeCityBordersTest(){
//         // Set up test scenario
//         CityBoard cb = CityBoard.newCityBoard(8, 8);
//         CityBorderMap cbm = CityBorderMap.newCityBorderMap(cb);
//         Player p1 = new Player(0, new Tribe('R'));
//         TilePosition t1 = new TilePosition(4, 4);
//         City c1 = cb.newCity(t1, p1);
//         cbm.updateCityBoard(cb.getCityBoard());

        
//         // Check that city has borders
//         c1.growCity();
//         cbm.expandCityBorders(c1);
//         assertEquals('R', cbm.get(t1));
//         Map<Character, Set<TilePosition>> map = cbm.getCityBorderMap();
//         Set<TilePosition> cityTiles = map.get('R');
//         assertEquals(9, cityTiles.size());

//         // Check that the city's borders are removed 
//         cbm.removeCityBorders(c1);
//         assertEquals(0, cityTiles.size());

//         // Check that the other city's borders are kept 

//     }



// }
