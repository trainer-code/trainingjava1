package org.scrum.psd.battleship.ascii;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.List;

public class MainTest {

    @Test
    public void testParsePosition() {
        Position actual = Main.parsePosition("A1");
        Position expected = new Position(Letter.A, 1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkLetterIndex() {
      int result = Main.getLetterIndex(Letter.B);

      Assert.assertEquals(1, result);
    }

  @Test
  public void testEnemyFleetInitialisation() {
      // Arrange/act
     Main.InitializeEnemyFleetRandomly();

     final List<Ship> enemyFleet = Main.getEnemyFleet();

     // Aircraft Carrier
     Assert.assertEquals(5, enemyFleet.get(0).getSize());
     Assert.assertEquals(5, enemyFleet.get(0).getPositions().size());

     // Battleship
     Assert.assertEquals(4, enemyFleet.get(1).getSize());
     Assert.assertEquals(4, enemyFleet.get(1).getPositions().size());


     // Submarine
    Assert.assertEquals(3, enemyFleet.get(2).getSize());
    Assert.assertEquals(3, enemyFleet.get(2).getPositions().size());

    // Destroyer
    Assert.assertEquals(3, enemyFleet.get(3).getSize());
    Assert.assertEquals(3, enemyFleet.get(3).getPositions().size());

    // Patrol Boat
    Assert.assertEquals(2, enemyFleet.get(4).getSize());
    Assert.assertEquals(2, enemyFleet.get(4).getPositions().size());
  }

  @Test
    public void testWinAscii(){
        Main.consoleSetUp();
        Main.printWinArt();
  }
  @Test
    public void testGameOverArt(){
        Main.consoleSetUp();
        Main.printGameOverArt();
  }
}
