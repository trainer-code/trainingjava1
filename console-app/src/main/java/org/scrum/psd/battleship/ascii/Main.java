package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final String playerWinnerMessage="You are the winner!";
    static final String playerLoserMessage="You lose!";

    static final Ansi.BColor playerLoserBackground = Ansi.BColor.RED;
    static final Ansi.BColor playerWinnerBackground = Ansi.BColor.GREEN;


    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;



    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).build();
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.clear();

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        boolean isEnemyFleetSunk=false;
        boolean isMyFleetSunk=false;
        HashSet<Position> alreadyShot= new HashSet<>();


        Set<Position> availablePositions = new HashSet();

        for(Letter l : Letter.values()){
            for(int p = 1; p<=8; p++){
                availablePositions.add(new Position(l,p));
            }
        }

        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            console.println("-------------------------- Next Turn -----------------------------------------------");
            console.setBackgroundColor(Ansi.BColor.BLACK);
            console.setForegroundColor(Ansi.FColor.GREEN);
            console.println("Player, it's your turn");
            console.println("Available moves:");
            for(Position p: availablePositions){
                console.print(p.getColumn().toString()+p.getRow()+" ");
            }
            console.println("");
            Position position = null;
            do {
                console.println("Enter coordinates for your shot :");
                position = parsePosition(scanner.next());
            }while(!availablePositions.contains(position));
            availablePositions.remove(position);
            console.clear();
            boolean isHit = GameController.fireAndCheckIsHit(enemyFleet, position);
            if (isHit) {
                beep();

                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");
            }

            if(isHit){
                console.setBackgroundColor(Ansi.BColor.YELLOW);
                console.setForegroundColor(Ansi.FColor.BLACK);
                console.println("Yeah ! Nice hit !");
                console.clear();
            }
            else{
                console.setBackgroundColor(Ansi.BColor.BLUE);
                console.setForegroundColor(Ansi.FColor.WHITE);
                console.println("Miss");
                console.clear();
            }
            isEnemyFleetSunk= GameController.HasFleetSunk(enemyFleet);

            if(isEnemyFleetSunk){
                printFinalMessage(playerWinnerBackground,playerWinnerMessage);
            }else{
                position = getRandomPosition(alreadyShot);
                isHit = GameController.fireAndCheckIsHit(myFleet, position);
                console.setBackgroundColor(Ansi.BColor.CYAN);
                console.setForegroundColor(Ansi.FColor.BLACK);
                console.println("");
                console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "miss"));
                console.clear();
                if (isHit) {
                    //beep();

                    console.println("                \\         .  ./");
                    console.println("              \\      .:\" \";'.:..\" \"   /");
                    console.println("                  (M^^.^~~:.'\" \").");
                    console.println("            -   (/  .    . . \\ \\)  -");
                    console.println("               ((| :. ~ ^  :. .|))");
                    console.println("            -   (\\- |  \\ /  |  /)  -");
                    console.println("                 -\\  \\     /  /-");
                    console.println("                   \\  \\   /  /");

                }
                isMyFleetSunk= GameController.HasFleetSunk(myFleet);
                if(isMyFleetSunk){
                    printFinalMessage(playerLoserBackground, playerLoserMessage);
                }
            }
        } while (!isMyFleetSunk && !isEnemyFleetSunk);
    }

    private static void printFinalMessage(final Ansi.BColor backgroundColor, final String message) {
        console.setBackgroundColor(backgroundColor);
        console.setForegroundColor(Ansi.FColor.BLACK);
        console.println(message);
        console.clear();
    }

    private static void beep() {
        console.println("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition(HashSet<Position> alreadyShot) {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        int prevLength= alreadyShot.size();
        Position position=null;
        while(prevLength==alreadyShot.size() || alreadyShot.size()==64){
            Letter letter = Letter.values()[random.nextInt(lines)];
            int number = random.nextInt(rows);
            position = new Position(letter, number);
            alreadyShot.add(position);
        }

        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        console.setForegroundColor(Ansi.FColor.GREEN);
        console.setBackgroundColor(Ansi.BColor.BLACK);
        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            int i=0;
            while(i<ship.getSize()){
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));
                String positionInput = scanner.next();
                if(ship.addPosition(positionInput)){
                    i++;
                }
                else{
                    console.println("Incorrect position");
                }

            }
        }
        console.clear();
    }

    private static boolean doesPositionExist(Position newPosition, List<Position> allPositions) {
      for (Position position : allPositions) {
        if (position.equals(newPosition) ) {
          return true;
        }
      }

      return false;
    }

    public static int getletterIndex(final Letter letter) {
      String letters = "ABCDEFGH";
      return letters.indexOf(letter.toString());
    }

    public static boolean doesPositionNeighbour(Position newPosition, List<Position> shipPositions) {
      for (Position position : shipPositions) {
        if (position.getRow() + 1  == newPosition.getRow() || position.getRow() - 1  == newPosition.getRow()) {
          return true;
        }

        if (getletterIndex(position.getColumn()) + 1  == getletterIndex(newPosition.getColumn()) || getletterIndex(position.getColumn()) - 1  == getletterIndex(newPosition.getColumn()) ) {
          return true;
        }
      }

      return false;
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();
        Position position;
        List<Position> allPositions = new ArrayList<>();
        for (Ship ship : enemyFleet) {
          for (int i = 1; i <= ship.getSize(); i++) {
            do {
              position = getRandomPosition();
            } while (doesPositionExist(position, allPositions)); // || !doesPositionNeighbour(position, ship.getPositions()));

            ship.getPositions().add(position);
          }
        }
    }
}
