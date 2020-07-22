package org.scrum.psd.battleship.controller;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.Arrays;
import java.util.List;

public class GameControllerTest {
    @Test
    public void testCheckHasFleetSunkTrue() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (int i = 0; i < ship.getSize(); i++) {
                ship.getPositions().add(new Position(letter, i));
            }

            counter++;
        }

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (Position pos : ship.getPositions()) {
                ship.fireHit(pos);
            }
        }

        boolean result = GameController.HasFleetSunk(ships);

        Assert.assertTrue(result);
    }
    @Test
    public void testCheckIsHitTrue() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (int i = 0; i < ship.getSize(); i++) {
                ship.getPositions().add(new Position(letter, i));
            }

            counter++;
        }

        boolean result = GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 1));

        Assert.assertTrue(result);
    }

    @Test
    public void testCheckIsHitFalse() {
        List<Ship> ships = GameController.initializeShips();
        int counter = 0;

        for (Ship ship : ships) {
            Letter letter = Letter.values()[counter];

            for (int i = 0; i < ship.getSize(); i++) {
                ship.getPositions().add(new Position(letter, i));
            }

            counter++;
        }

        boolean result = GameController.fireAndCheckIsHit(ships, new Position(Letter.H, 1));

        Assert.assertFalse(result);
    }

    @Test
    public void testCheckNoShipSunk() {
      // Arrange
      List<Ship> ships = GameController.initializeShips();
      Ship firstShip = ships.get(0);
      firstShip.addPosition("A1");
      firstShip.addPosition("A2");
      firstShip.addPosition("A3");
      firstShip.addPosition("A4");
      firstShip.addPosition("A5");

      GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 1));
      GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 2));
      GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 3));
      GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 4));

      // Act

      Assert.assertTrue(GameController.checkThatAnyShipShouldBeSunkAndSinkIt(ships, new Position(Letter.A, 4)) == null);
    }


      @Test
      public void testCheckAnyShipSunk() {
      // Arrange
        List<Ship> ships = GameController.initializeShips();
        Ship firstShip = ships.get(0);
        firstShip.addPosition("A1");
        firstShip.addPosition("A2");
        firstShip.addPosition("A3");
        firstShip.addPosition("A4");
        firstShip.addPosition("A5");

        GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 1));
        GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 2));
        GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 3));
        GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 4));
        GameController.fireAndCheckIsHit(ships, new Position(Letter.A, 5));

        // Act

        Assert.assertTrue( GameController.checkThatAnyShipShouldBeSunkAndSinkIt(ships, new Position(Letter.A, 5)) == firstShip);
      }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsHitPositstionIsNull() {
        GameController.fireAndCheckIsHit(GameController.initializeShips(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsHitShipIsNull() {
        GameController.fireAndCheckIsHit(null, new Position(Letter.H, 1));
    }

    @Test
    public void testIsShipValidFalse() {
        Ship ship = new Ship("TestShip", 3);
        boolean result = GameController.isShipValid(ship);

        Assert.assertFalse(result);
    }

    @Test
    public void testIsShipValidTrue() {
        List<Position> positions = Arrays.asList(new Position(Letter.A, 1), new Position(Letter.A, 1), new Position(Letter.A, 1));
        Ship ship = new Ship("TestShip", 3, positions);

        boolean result = GameController.isShipValid(ship);

        Assert.assertTrue(result);
    }



}
