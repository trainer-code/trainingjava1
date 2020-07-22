package org.scrum.psd.battleship.controller;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Color;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

public class ShipTest {
    @Test
    public void TestWrongShipPosition(){
        Ship ship= new Ship();
        boolean isValid=ship.addPosition("P1");
        Assert.assertFalse(isValid);
        isValid=ship.addPosition("A9");
        Assert.assertFalse(isValid);
    }
    @Test
    public void TestCorrectShipPosition(){
        Ship ship= new Ship();
        boolean isValid=ship.addPosition("A1");
        Assert.assertTrue(isValid);
    }


    @Test
    public void TestSetShipSunkCondition(){
      // Arrange
      Ship ship = new Ship("My Ship", 2, Color.CADET_BLUE);
      ship.addPosition("A1");
      ship.addPosition("A2");

      // Act
      ship.fireHit(new Position(Letter.A, 1));
      ship.fireHit(new Position(Letter.A, 2));

      // Assert
      Assert.assertTrue(ship.isSunk());
    }

    @Test
    public void TestShipNotSunkCondition(){
      // Arrange
      Ship ship = new Ship("My Ship", 2, Color.CADET_BLUE);
      ship.addPosition("A1");
      ship.addPosition("A2");

      // Act
      ship.fireHit(new Position(Letter.A, 1));

      // Assert
      Assert.assertTrue(!ship.isSunk());
    }

}
