package org.scrum.psd.battleship.controller;

import org.junit.Assert;
import org.junit.Test;
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

}
