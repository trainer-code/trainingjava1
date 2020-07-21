package org.scrum.psd.battleship.ascii;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;

import java.util.ArrayList;

public class MainTest {

    @Test
    public void testParsePosition() {
        Position actual = Main.parsePosition("A1");
        Position expected = new Position(Letter.A, 1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkLetterIndex() {
      int result = Main.getletterIndex(Letter.B);

      Assert.assertEquals(1, result);
    }

  @Test
  public void checkPositionInNeighbour() {
    Position target = Main.parsePosition("A1");
    ArrayList<Position> points = new ArrayList(); ;
    points.add(Main.parsePosition("A2"));
    boolean result = Main.doesPositionNeighbour(target, points);
    Assert.assertEquals(true, result);
  }

  @Test
  public void checkPositionInNotNeighbour() {
    Position target = Main.parsePosition("A1");
    ArrayList<Position> points = new ArrayList(); ;
    points.add(Main.parsePosition("D4"));
    boolean result = Main.doesPositionNeighbour(target, points);
    Assert.assertEquals(false, result);
  }
}
