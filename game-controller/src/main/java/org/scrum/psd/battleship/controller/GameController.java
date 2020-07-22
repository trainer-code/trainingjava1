package org.scrum.psd.battleship.controller;

import org.scrum.psd.battleship.controller.dto.Color;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;

public class GameController {
    public static boolean fireAndCheckIsHit(Collection<Ship> ships, Position shot) {
        if (ships == null) {
            throw new IllegalArgumentException("ships is null");
        }

        if (shot == null) {
            throw new IllegalArgumentException("shot is null");
        }

        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.equals(shot) ) {
                    return ship.fireHit(position);
                }
            }
        }

        return false;
    }

    public static Ship checkThatAnyShipShouldBeSunkAndSinkIt(Collection<Ship> ships, Position shot) {
      if (ships == null) {
        throw new IllegalArgumentException("ships is null");
      }

      if (shot == null) {
        throw new IllegalArgumentException("shot is null");
      }

      for (Ship ship : ships) {
        if (ship.checkThatShipShouldBeSunkAndSinkIt()) {
          return ship;
        }
      }

      return null;
    }

  public static List<String> getSunkShipNames(Collection<Ship> ships) {
    if (ships == null) {
      throw new IllegalArgumentException("ships is null");
    }

    ArrayList<String> sunkShips = new ArrayList<>();

    for (Ship ship : ships) {
      if (ship.isSunk()) {
        sunkShips.add(ship.getName());
      }
    }

    return sunkShips;
  }

    public static List<String> getUnSunkShipNames(Collection<Ship> ships) {
      if (ships == null) {
        throw new IllegalArgumentException("ships is null");
      }

      ArrayList<String> sunkShips = new ArrayList<>();

      for (Ship ship : ships) {
        if (!ship.isSunk()) {
          sunkShips.add(ship.getName());
        }
      }

      return sunkShips;
    }

    public static boolean HasFleetSunk(Collection<Ship> ships){

        for(Ship ship: ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public static List<Ship> initializeShips() {
        return Arrays.asList(
               new Ship("Aircraft Carrier", 5, Color.CADET_BLUE),
               new Ship("Battleship", 4, Color.RED),
                new Ship("Submarine", 3, Color.CHARTREUSE),
                new Ship("Destroyer", 3, Color.YELLOW),
                new Ship("Patrol Boat", 2, Color.ORANGE));
    }

    public static boolean isShipValid(Ship ship) {
        return ship.getPositions().size() == ship.getSize();
    }

    public static Position getRandomPosition(int size) {
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(size)];
        int number = random.nextInt(size);
        Position position = new Position(letter, number);
        return position;
    }
}
