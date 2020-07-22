package org.scrum.psd.battleship.controller.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ship {
    private boolean isPlaced;
    private String name;
    private int size;
    private List<Position> positions;
    private Color color;
    private Set<Position> hitPositions;
    boolean hasSunk  = false;


    public Ship() {
        this.positions = new ArrayList<>();
    }

    public Ship(String name, int size) {
        this();
        this.hitPositions = new HashSet();
        this.name = name;
        this.size = size;
    }

    public boolean checkThatShipShouldBeSunkAndSinkIt() {
      if (!hasSunk && hitPositions.size() == size) {
        hasSunk = true;
        return true;
      }

      return false;
    }

     public boolean checkIsSunk(){
    return hasSunk;
  }

    public void setIsSunk(final boolean value){
    hasSunk = value;
  }

    public Ship(String name, int size, List<Position> positions) {
        this(name, size);

        this.positions = positions;
    }

    public Ship(String name, int size, Color color) {
        this(name, size);

        this.color = color;
    }

    public boolean addPosition(String input) {
        if (positions == null) {
            positions = new ArrayList<>();
        }

        String letter=input.toUpperCase().substring(0, 1);
        int number = Integer.parseInt(input.substring(1));
        Position position=checkPosition(letter,number);
        if(position!=null){
            positions.add(position);
            return true;
        }
        return false;
    }

    public Position checkPosition(String letter, int number ){
        int ci=(int)letter.charAt(0);
        if(ci<65 || ci>72 || number<1 || number>8)
            return null;
        Letter letterEnum = Letter.valueOf(letter);

        return new Position(letterEnum,number);
    }

    // TODO: property change listener implementation

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean fireHit(Position shot){
        int prevLength=hitPositions.size();
        hitPositions.add(shot);
        return prevLength!=hitPositions.size();
    }
    public boolean isSunk(){
        return hitPositions.size()==positions.size();
    }
}
