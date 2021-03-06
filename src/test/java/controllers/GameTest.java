/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package controllers;

import org.junit.Test;

import constants.Constants;
import controller.Game;
import controller.MockPlayerVersusPlayer;
import model.Board;
import model.Cell;
import model.Player;
import model.Ship;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class GameTest {
    
    @Test 
    public void isCorrectPlayerNameTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	
    	boolean res1 = controller.isCorrectPlayerName("");
        assertFalse(res1);   
        
    	boolean res2 = controller.isCorrectPlayerName("hola");
        assertTrue(res2);   
    }
    
    @Test
    public void isCorrectShipOrientationTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	
    	boolean res1 = controller.isCorrectShipOrientation("");
    	assertFalse(res1);
    	
    	boolean res2 = controller.isCorrectShipOrientation("test");
    	assertFalse(res2);
    	
    	boolean res3 = controller.isCorrectShipOrientation("-1");
    	assertFalse(res3);
    	
    	boolean res4 = controller.isCorrectShipOrientation("2");
    	assertFalse(res4);
    	
    	boolean res5 = controller.isCorrectShipOrientation(Constants.SHIP_HORIZONTAL);
    	assertTrue(res5);
    	
    	boolean res6 = controller.isCorrectShipOrientation(Constants.SHIP_VERTICAL);
    	assertTrue(res6);
    	
    	boolean res7 = controller.isCorrectShipOrientation("H");
    	assertFalse(res7);
    	
    	boolean res8 = controller.isCorrectShipOrientation("V");
    	assertFalse(res8);
    	
    	boolean res9 = controller.isCorrectShipOrientation("test");
    	assertFalse(res9);
    }
    
    @Test
    public void isCorrectInputCoordinateTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	
    	boolean res1 = controller.isCorrectInputCoordinate("-1");
    	assertFalse(res1);
    	
    	boolean res2 = controller.isCorrectInputCoordinate("0");
    	assertTrue(res2);
    	
    	boolean res3 = controller.isCorrectInputCoordinate("1");
    	assertTrue(res3);
    	
    	boolean res4 = controller.isCorrectInputCoordinate(Integer.toString(Constants.N_BOARD_ROWS_CELLS-1));
    	assertTrue(res4);
  
    	boolean res5 = controller.isCorrectInputCoordinate(Integer.toString(Constants.N_BOARD_ROWS_CELLS));
    	assertFalse(res5);
    	
    	boolean res7 = controller.isCorrectInputCoordinate("test");
    	assertFalse(res7);
    }
    
    @Test 
    public void initializeGameTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	/** 
    	 * initializes both players (player 1 and player 2) boards in the following state ('o' is a ship):
	   	      0  1  2  3  4  5  6  7  8  9
		   0  o  o  o  -  -  -  -  -  -  -  
		   1  -  -  -  -  -  -  -  -  -  -  
		   2  -  -  -  -  -  -  -  -  -  -  
		   3  -  -  -  -  -  -  -  -  -  -  
		   4  -  -  -  -  -  -  -  -  -  -  
		   5  -  -  -  -  -  o  o  o  o  -  
		   6  -  -  -  -  -  -  -  -  -  -  
		   7  -  -  -  -  -  -  -  -  -  o  
		   8  -  -  -  -  -  -  -  -  -  o  
		   9  -  -  -  -  -  -  -  -  -  o 
    	**/
    	controller.initializeGame();
    
    	// Test 1: Checks if both players are created.
    	assertTrue(controller.players.size() == 2);
    	
    	// Test 2: Checks if players names are the expected.
    	Player player1 = controller.players.get(0);
    	assertTrue(player1.getName() == "player 1");
    	Player player2 = controller.players.get(1);
    	assertTrue(player2.getName() == "player 2");
    	
    	// Test 3: Checks if all players ships are in the expected coordinates.
    	int[] xs = {0, 0, 0, 5, 5, 5, 5, 9, 8, 7};
    	int[] ys = {0, 1, 2, 5, 6, 7, 8, 9, 9, 9};
    	for (Player player : controller.players) {
    		Board board = player.getShipsBoard();
    		for (int i = 0; i < xs.length; i++) {
        		assertTrue(board.isShipCell(xs[i], ys[i]));
        	}
    	}
    	
    	// Test 4: Checks if all players ships have the expected health.
    	int[] healths = {1, 2, 3, 4};
    	for (Player player : controller.players) {
    		Board board = player.getShipsBoard();
    		ArrayList<Ship> ships = board.getShips();
			for (int i = 0; i < healths.length; i++) {
        		assertTrue(ships.get(i).getHealth() == healths[i]);
        	}
    	}
    }
    
    @Test 
    public void makePlayTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	/** 
    	 * Initializes both players (player 1 and player 2) boards in the following state ('o' is a ship):
	   	      0  1  2  3  4  5  6  7  8  9
		   0  o  o  o  -  -  -  -  -  -  -  
		   1  -  -  -  -  -  -  -  -  -  -  
		   2  -  -  -  -  -  -  -  -  -  -  
		   3  -  -  -  -  -  -  -  -  -  -  
		   4  -  -  -  -  -  -  -  -  -  -  
		   5  -  -  -  -  -  o  o  o  o  -  
		   6  -  -  -  -  -  -  -  -  -  -  
		   7  -  -  -  -  -  -  -  -  -  o  
		   8  -  -  -  -  -  -  -  -  -  o  
		   9  -  -  -  -  -  -  -  -  -  o 
    	**/
    	controller.initializeGame();
    
    	/* Every time makePlay is called, a shoot is fired, in sequence, in the following (x, y) coordinates.
    	 * So, first call shoots at (0, 0), second call shoots at (1, 2), and then. */
    	int[] xs = {0, 1, 1, 0, 0, 9, 8, 7, 5, 5, 5, 5};
    	int[] ys = {0, 2, 2, 1, 2, 9, 9, 9, 5, 6, 7, 8};
    	int i = 0;
    	
    	final int firstPlayerId = 0;
    	final int secondPlayerId = 1;
    	
    	/* * Test 1. Checks: 
    	 * - 1. if the next player id is equal to the player id that fires.
    	 * - 2. if the one cell ship is sunk.
    	 * - 3. if there is a shoot, in shootsBoard, at coordinates where the shoot was fired.
    	 **/
    	int nextPlayerId = controller.makePlay(firstPlayerId);
    	assertTrue(nextPlayerId == firstPlayerId);
    	
    	Player secondPlayer = controller.getPlayers().get(secondPlayerId);
    	Board shipsBoard = secondPlayer.getShipsBoard(); 
    	Cell cell = shipsBoard.getCells()[xs[i]][ys[i]];
    	Ship ship = cell.getShip(); 
    	assertTrue(ship.isSunk());
    	
    	Player firstPlayer = controller.getPlayers().get(secondPlayerId);
    	String[][] shootsBoard = firstPlayer.getShootsBoard(); 
    	assertTrue(shootsBoard[xs[i]][xs[i]].equals("o"));
    	i++;
    	
    	// Test 2: Checks if the next player id is NOT equals to the player id that fires.
    	nextPlayerId = controller.makePlay(firstPlayerId);
    	assertTrue(nextPlayerId != firstPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	assertTrue(nextPlayerId == firstPlayerId);
    	i++;
    	
    	/* * Test 3. Checks: 
    	 * - 1. if the next player id is equal to the player id that fires.
    	 * - 2. if the two cells ship is sunk.
    	 * - 3. if there is a shoot, in shootsBoard, at coordinates where the shoot was fired.
    	 **/
    	nextPlayerId = controller.makePlay(firstPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	assertTrue(nextPlayerId == firstPlayerId);
    	
    	shipsBoard = secondPlayer.getShipsBoard(); 
    	cell = shipsBoard.getCells()[xs[i]][ys[i]];
    	ship = cell.getShip(); 
    	assertTrue(ship.isSunk());

    	shootsBoard = firstPlayer.getShootsBoard(); 
    	assertTrue(shootsBoard[xs[i]][ys[i]].equals("o"));
    	i++;
    	
    	/* * Test 4. Checks: 
    	 * - 1. if the next player id is equal to the player id that fires.
    	 * - 2. if the three cells ship is sunk.
    	 * - 3. if there is a shoot, in shootsBoard, at coordinates where the shoot was fired.
    	 **/
    	nextPlayerId = controller.makePlay(firstPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	assertTrue(nextPlayerId == firstPlayerId);
    	
    	shipsBoard = secondPlayer.getShipsBoard(); 
    	cell = shipsBoard.getCells()[xs[i]][ys[i]];
    	ship = cell.getShip(); 
    	assertTrue(ship.isSunk());

    	shootsBoard = firstPlayer.getShootsBoard(); 
    	assertTrue(shootsBoard[xs[i]][ys[i]].equals("o"));
    	
    	i++;
    	
    	/* * Test 5. Checks: 
    	 * - 1. if the next player id is equal to the player id that fires.
    	 * - 2. if the four cells ship is sunk.
    	 * - 3. if there is a shoot, in shootsBoard, at coordinates where the shoot was fired.
    	 **/
    	nextPlayerId = controller.makePlay(firstPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	i++;
    	
    	nextPlayerId = controller.makePlay(nextPlayerId);
    	assertTrue(nextPlayerId == firstPlayerId);
    	
    	shipsBoard = secondPlayer.getShipsBoard(); 
    	cell = shipsBoard.getCells()[xs[i]][ys[i]];
    	ship = cell.getShip(); 
    	assertTrue(ship.isSunk());

    	shootsBoard = firstPlayer.getShootsBoard(); 
    	assertTrue(shootsBoard[xs[i]][ys[i]].equals("o"));
    }
    
    @Test 
    public void endOfGameTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	/** 
    	 * Initialize players boards with given ships coordinates ('o' is a ship):
	   	      0  1  2  3  4  5  6  7  8  9
		   0  o  o  o  -  -  -  -  -  -  -  
		   1  -  -  -  -  -  -  -  -  -  -  
		   2  -  -  -  -  -  -  -  -  -  -  
		   3  -  -  -  -  -  -  -  -  -  -  
		   4  -  -  -  -  -  -  -  -  -  -  
		   5  -  -  -  -  -  o  o  o  o  -  
		   6  -  -  -  -  -  -  -  -  -  -  
		   7  -  -  -  -  -  -  -  -  -  o  
		   8  -  -  -  -  -  -  -  -  -  o  
		   9  -  -  -  -  -  -  -  -  -  o 
    	**/
    	controller.initializeGame();
    
    	// Test 1: check if is end game before shooting any ship.
    	assertFalse(controller.endOfGame());
    	
    	// Test 2: check if is end game when shooting to water.
    	controller.players.get(0).shoot(0, 3);
    	assertFalse(controller.endOfGame());
    	
    	// Test 3: check if is end game when down 1 of 4 ships.
    	controller.players.get(0).shoot(0, 0);
    	assertFalse(controller.endOfGame());
    	
    	// Test 4: check if is end game when down 2 of 4 ships.
    	controller.players.get(0).shoot(0, 1);
    	controller.players.get(0).shoot(0, 2);
    	assertFalse(controller.endOfGame());
    	
    	// Test 5: check if is end game when down 3 of 4 ships.
    	controller.players.get(0).shoot(7, 9);
    	controller.players.get(0).shoot(8, 9);
    	controller.players.get(0).shoot(9, 9);
    	assertFalse(controller.endOfGame());
    	
    	// Test 6: check if is end game when down 4 of 4 ships.
    	controller.players.get(0).shoot(5, 5);
    	controller.players.get(0).shoot(5, 6);
    	controller.players.get(0).shoot(5, 7);
    	controller.players.get(0).shoot(5, 8);
    	assertTrue(controller.endOfGame());
    }
    
    @Test 
    public void playOneGameTest() {
    	Game controller = new MockPlayerVersusPlayer();
    	
    	// Runs a hard-coded game where the player 1 is the winner. */
    	controller.playOneGame();
    	
    	/* Test 1: checks if the player 1 is the winner. */
    	Player player1 = controller.winner;
    	assertTrue(player1.getName() == "player 1");
    	
    	/* Test 2: checks if the player 2 is NOT the winner. */
    	Player player2 = controller.players.get(1);
    	assertTrue(player2.isAllShipsSunk());
    }
}
