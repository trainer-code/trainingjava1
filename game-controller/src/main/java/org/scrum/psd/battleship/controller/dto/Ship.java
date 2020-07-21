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


    public Ship() {
        this.positions = new ArrayList<>();
    }

    public Ship(String name, int size) {
        this();
        this.hitPositions = new HashSet();
        this.name = name;
        this.size = size;
    }

    public Ship(String name, int size, List<Position> positions) {
        this(name, size);

        this.positions = positions;
    }

    public Ship(String name, int size, Color color) {
        this(name, size);

        this.color = color;
    }

    public void addPosition(String input) {
        if (positions == null) {
            positions = new ArrayList<>();
        }

        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));

        positions.add(new Position(letter, number));
    }

    // TODO: property change listener implementieren

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
