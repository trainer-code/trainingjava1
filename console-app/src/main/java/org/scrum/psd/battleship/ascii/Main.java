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


    private static int MAX_ROW = 8;
    private static int MAX_COLUMN = 8;
    public static  String LETTERS = "ABCDEFGHIJKLMN";

    private static int testingMode = 0;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("testing1")) {
          testingMode = 1;
        } else  if (args.length > 0 && args[0].equals("testing2")) {
            testingMode = 2;
        } else  if (args.length > 0 && args[0].equals("testing3")) {
          testingMode = 3;
        }
        consoleSetUp();
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

    public static void consoleSetUp() {
        console = new ColoredPrinter.Builder(1, false).build();
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

              Ship justSunkShip = GameController.checkThatAnyShipShouldBeSunkAndSinkIt(enemyFleet, position);
              if (justSunkShip != null) {
                printSunkStatus(enemyFleet, "Enemy fleet after hit", justSunkShip);
              }
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

                    Ship justSunkShip = GameController.checkThatAnyShipShouldBeSunkAndSinkIt(myFleet, position);
                    if (justSunkShip != null) {
                      printSunkStatus(myFleet, "My fleet after hit", justSunkShip);
                    }
                }

                isMyFleetSunk= GameController.HasFleetSunk(myFleet);
                if(isMyFleetSunk){
                    printFinalMessage(playerLoserBackground, playerLoserMessage);
                }
            }
        } while (!isMyFleetSunk && !isEnemyFleetSunk);

        if(isEnemyFleetSunk){
            printWinArt();
        }
        else{
            printGameOverArt();
        }
    }

    public static void printGameOverArt() {
        /*console.println(" ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ███▀▀▀██┼███▀▀▀███┼███▀█▄█▀███┼██▀▀▀");
        console.println(" ██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼█┼┼┼██┼██┼┼┼");
        console.println(" ██┼┼┼▄▄▄┼██▄▄▄▄▄██┼██┼┼┼▀┼┼┼██┼██▀▀▀");
        console.println(" ██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██┼┼┼");
        console.println(" ███▄▄▄██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██▄▄▄");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ███▀▀▀███┼▀███┼┼██▀┼██▀▀▀┼██▀▀▀▀██▄┼");
        console.println(" ██┼┼┼┼┼██┼┼┼██┼┼██┼┼██┼┼┼┼██┼┼┼┼┼██┼");
        console.println(" ██┼┼┼┼┼██┼┼┼██┼┼██┼┼██▀▀▀┼██▄▄▄▄▄▀▀┼");
        console.println(" ██┼┼┼┼┼██┼┼┼██┼┼█▀┼┼██┼┼┼┼██┼┼┼┼┼██┼");
        console.println(" ███▄▄▄███┼┼┼─▀█▀┼┼─┼██▄▄▄┼██┼┼┼┼┼██▄");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼████▄┼┼┼▄▄▄▄▄▄▄┼┼┼▄████┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼▀▀█▄█████████▄█▀▀┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼█████████████┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼██▀▀▀███▀▀▀██┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼██┼┼┼███┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼█████▀▄▀█████┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼┼███████████┼┼┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼▄▄▄██┼┼█▀█▀█┼┼██▄▄▄┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼▀▀██┼┼┼┼┼┼┼┼┼┼┼██▀▀┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼");
        console.println(" ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼");

        */
        console.println("        GGGGGGGGGGGGG");
                console.println("     GGG::::::::::::G");
                        console.println("   GG:::::::::::::::G");
                                console.println("  G:::::GGGGGGGG::::G");
                                        console.println(" G:::::G       GGGGGG  aaaaaaaaaaaaa      mmmmmmm    mmmmmmm       eeeeeeeeeeee            ooooooooooo vvvvvvv           vvvvvvv eeeeeeeeeeee    rrrrr   rrrrrrrrr");
        console.println("G:::::G                a::::::::::::a   mm:::::::m  m:::::::mm   ee::::::::::::ee        oo:::::::::::oov:::::v         v:::::vee::::::::::::ee  r::::rrr:::::::::r");
        console.println("G:::::G                aaaaaaaaa:::::a m::::::::::mm::::::::::m e::::::eeeee:::::ee     o:::::::::::::::ov:::::v       v:::::ve::::::eeeee:::::eer:::::::::::::::::r");
        console.println("G:::::G    GGGGGGGGGG           a::::a m::::::::::::::::::::::me::::::e     e:::::e     o:::::ooooo:::::o v:::::v     v:::::ve::::::e     e:::::err::::::rrrrr::::::r");
        console.println("G:::::G    G::::::::G    aaaaaaa:::::a m:::::mmm::::::mmm:::::me:::::::eeeee::::::e     o::::o     o::::o  v:::::v   v:::::v e:::::::eeeee::::::e r:::::r     r:::::r");
        console.println("G:::::G    GGGGG::::G  aa::::::::::::a m::::m   m::::m   m::::me:::::::::::::::::e      o::::o     o::::o   v:::::v v:::::v  e:::::::::::::::::e  r:::::r     rrrrrrr");
        console.println("G:::::G        G::::G a::::aaaa::::::a m::::m   m::::m   m::::me::::::eeeeeeeeeee       o::::o     o::::o    v:::::v:::::v   e::::::eeeeeeeeeee   r:::::r               ");
        console.println(" G:::::G       G::::Ga::::a    a:::::a m::::m   m::::m   m::::me:::::::e                o::::o     o::::o     v:::::::::v    e:::::::e            r:::::r                 ");
        console.println("  G:::::GGGGGGGG::::Ga::::a    a:::::a m::::m   m::::m   m::::me::::::::e               o:::::ooooo:::::o      v:::::::v     e::::::::e           r:::::r                 ");
        console.println("   GG:::::::::::::::Ga:::::aaaa::::::a m::::m   m::::m   m::::m e::::::::eeeeeeee       o:::::::::::::::o       v:::::v       e::::::::eeeeeeee   r:::::r                 ");
        console.println("     GGG::::::GGG:::G a::::::::::aa:::am::::m   m::::m   m::::m  ee:::::::::::::e        oo:::::::::::oo         v:::v         ee:::::::::::::e   r:::::r                 ");
        console.println("        GGGGGG   GGGG  aaaaaaaaaa  aaaammmmmm   mmmmmm   mmmmmm    eeeeeeeeeeeeee          ooooooooooo            vvv            eeeeeeeeeeeeee   rrrrrrr    ");



    }

    public static void printWinArt() {
        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.setBackgroundColor(Ansi.BColor.BLACK);
        console.println("                                   .''.       ");
        console.println("       .''.      .        *''*    :_\\/_:     . ");
        console.println("      :_\\/_:   _\\(/_  .:.*_\\/_*   : /\\ :  .'.:.'.");
        console.println("  .''.: /\\ :   ./)\\   ':'* /\\ * :  '..'.  -=:o:=-");
        console.println(" :_\\/_:'.:::.    ' *''*    * '.\\'/.' _\\(/_'.':'.'");
        console.println(" : /\\ : :::::     *_\\/_*     -= o =-  /)\\    '  *");
        console.println("  '..'  ':::'     * /\\ *     .'/.\\'.   '");
        console.println("      *            *..*         :");
        console.println("       *");
        console.println("        *");
        console.println("");
        console.println(" .----------------.  .----------------.  .----------------.   .----------------.  .----------------.  .-----------------.  .----------------. ");
        console.println("| .--------------. || .--------------. || .--------------. | | .--------------. || .--------------. || .--------------. | | .--------------. |");
        console.println("| |  ____  ____  | || |     ____     | || | _____  _____ | | | | _____  _____ | || |     _____    | || | ____  _____  | | | |              | |");
        console.println("| |_  _||_  _| | || |   .'    `.   | || ||_   _||_   _|| | | ||_   _||_   _|| || |    |_   _|   | || ||_   \\|_   _| | | | |      _       | |");
        console.println("| |   \\ \\  / /   | || |  /  .--.  \\  | || |  | |    | |  | | | |  | | /\\ | |  | || |      | |     | || |  |   \\ | |   | | | |     | |      | |");
        console.println("| |    \\ \\/ /    | || |  | |    | |  | || |  | '    ' |  | | | |  | |/  \\| |  | || |      | |     | || |  | |\\ \\| |   | | | |     | |      | |");
        console.println("| |    _|  |_    | || |  \\  `--'  /  | || |   \\ `--' /   | | | |  |   /\\   |  | || |     _| |_    | || | _| |_\\   |_  | | | |     | |      | |");
        console.println("| |   |______|   | || |   `.____.'   | || |    `.__.'    | | | |  |__/  \\__|  | || |    |_____|   | || ||_____|\\____| | | | |     |_|      | |");
        console.println("| |              | || |              | || |              | | | |              | || |              | || |              | | | |     (_)      | |");
        console.println("| '--------------' || '--------------' || '--------------' | | '--------------' || '--------------' || '--------------' | | '--------------' |");
        console.println("'----------------'  '----------------'  '----------------'   '----------------'  '----------------'  '----------------'   '----------------' ");
        console.clear();
    }

  private static void printSunkStatus(final List<Ship> fleet, final String title, final Ship justSunkShip) {
    console.println("You sink enemy ship: " + justSunkShip.getName());

    console.println("░░░░░░░█████████████");
    console.println("░░░░░░░░░███░███░░░░░░██");
    console.println("███░░░░░██░░░░██░██████████");
    console.println("████████░░░░░░████░░░░░░░██");
    console.println("████░░░░░░░░░░██░░██████████");
    console.println("████░░░░░░░░░░░███░░░░░░░░░██");
    console.println("████░░░░░░░░░░░██░░██████████");
    console.println("████░░░░░░░░░░░░████░░░░░░░░█");
    console.println("████░░░░░░░░░░░░░███░░████░░█");
    console.println("█████████░░░░░░░░░░████░░░░░█");
    console.println("███░░░░░██░░░░░░░░░░░░░█████");
    console.println("░░░░░░░░░███░░░░░░░██████");
    console.println("░░░░░░░░░░░██░░░░░░██");
    console.println("░░░░░░░░░░░░███░░░░░██");
    console.println("░░░░░░░░░░░░░░██░░░░██");
    console.println("░░░░░░░░░░░░░░░███░░░██");
    console.println("░░░░░░░░░░░░░░░░░██░░░█");
    console.println("░░░░░░░░░░░░░░░░░░█░░░█");
    console.println("░░░░░░░░░░░░░░░░░░██░██");
    console.println("░░░░░░░░░░░░░░░░░░░███");


    console.println("---------- " + title + " ---------");
    for (Ship ship : fleet) {
      if (ship.checkIsSunk()) {
        console.println(ship.getName() + " SUNK");
      } else {
        console.println(ship.getName() + " NOT SUNK");
      }
    }
    console.println("-------------------------");
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

      if (testingMode != 0) {
        InitializeMyFleetRandomly();
      } else {
        InitializeMyFleetManually();
      }

      if (testingMode == 3) {
        initialiseAndShotAllShipsInEnemyFleet();
      } else {
        InitializeEnemyFleetRandomly();
      }
    }

    private static void InitializeMyFleetManually() {
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

    private static boolean doesPositionExist(Position newPosition, List<Position> occupiedPositions) {
      for (Position position : occupiedPositions) {
        if (position.equals(newPosition) ) {
          return true;
        }
      }

      return false;
    }

    public static int getLetterIndex(final Letter letter) {
      return LETTERS.indexOf(letter.toString());
    }

  public static Letter getLetter(final int letterIndex) {
    return Letter.values()[letterIndex];
  }

  private static boolean isInsideBoard(final int row, final int column) {
    if (row < MAX_ROW && row >= 0 && column < MAX_COLUMN && column >= 0) {
      return true;
    }

    return false;
  }

    private static Position getHorizontalNeighbour(final Position position, final int direction, final List<Position> occupiedPositions) {
      int letterIndex = getLetterIndex(position.getColumn());
      int neighbourColumn = letterIndex + direction;
      int row = position.getRow();
      if (isInsideBoard(row, neighbourColumn)) {
        final Letter neighbourLetter= getLetter(neighbourColumn);
        final Position possibleNeighbourPosition = new Position(neighbourLetter, row);
        if (!doesPositionExist(possibleNeighbourPosition, occupiedPositions)) {
          return possibleNeighbourPosition;
        }
      }
      return null;
    }

    private static Position getVerticalNeighbour(final Position position, final int direction, final List<Position> occupiedPositions) {
      Letter letter = position.getColumn();
      int letterIndex = getLetterIndex(letter);
      int neighbourRow = position.getRow() + direction;
      if (isInsideBoard(neighbourRow, letterIndex)) {
        final Position possibleNeighbourPosition = new Position(letter, neighbourRow);
        if (!doesPositionExist(possibleNeighbourPosition, occupiedPositions)) {
          return possibleNeighbourPosition;
        }
      }
      return null;
    }

    public static Position getRandomAdjacentPosition(final Position tailPosition, final List<Position> occupiedPositions) {
      final ArrayList<Position> availableNeighbour = new ArrayList<>();
      final Position possibleLeftPosition = getHorizontalNeighbour( tailPosition, -1, occupiedPositions);
      if (possibleLeftPosition !=null ) {
        availableNeighbour.add(possibleLeftPosition);
      }

      final Position possibleRightPosition = getHorizontalNeighbour( tailPosition, 1, occupiedPositions);
      if (possibleRightPosition !=null ) {
        availableNeighbour.add(possibleRightPosition);
      }

      final Position possibleUpPosition = getVerticalNeighbour( tailPosition, -1, occupiedPositions);
      if (possibleUpPosition !=null ) {
        availableNeighbour.add(possibleUpPosition);
      }

      final Position possibleDownPosition = getVerticalNeighbour( tailPosition, 1, occupiedPositions);
      if (possibleDownPosition !=null ) {
        availableNeighbour.add(possibleDownPosition);
      }

      if (availableNeighbour.size() > 0) {
        Random random = new Random();
        int randomNeighbourIndex = random.nextInt(availableNeighbour.size());
        return availableNeighbour.get(randomNeighbourIndex);
      }

      return null;
    }

  private static Position getRandomPositionWithoutRestriction() {
    int rows = 8;
    int lines = 8;
    Random random = new Random();
    Letter letter = Letter.values()[random.nextInt(lines)];
    int number = random.nextInt(rows);
    Position position = new Position(letter, number);
    return position;
  }

    public static void InitializeShipRandomly(final Ship ship, List<Position> occupiedPositions) {
      Position position;
      List<Position> possibleShipPositions = new ArrayList<>();
      boolean startAgain;
      do {
        startAgain = false;
        possibleShipPositions.clear();
        // we might waste possibleShipPositions, so remove them from occupiedPositions if startAgain;
        possibleShipPositions.forEach((Position pos) -> {
          occupiedPositions.remove(pos);
        });

        do {
          position = getRandomPositionWithoutRestriction();
        } while (doesPositionExist(position, occupiedPositions));

        Position tailPosition = position;
        possibleShipPositions.add(tailPosition);
        occupiedPositions.add(tailPosition);

        for (int i = 1; i < ship.getSize(); i++) {
          tailPosition = getRandomAdjacentPosition(position, occupiedPositions);
          if (tailPosition == null) { // if there is no tail position available we need to start from a different position again
            startAgain = true;
          } else {
              possibleShipPositions.add(tailPosition);
              occupiedPositions.add(tailPosition);
          }
        }

      } while (startAgain);

      possibleShipPositions.forEach((Position pos) -> {
        ship.getPositions().add(pos);
      });
    }

    private static void printFleet(List<Ship> fleet, final String title) {
      console.println("---------- " + title + " ---------");
      for (Ship ship : fleet) {
        console.println(ship.getName());
        ship.getPositions().forEach((Position pos) -> {
          int letterIndex = getLetterIndex(pos.getColumn());
          final Letter letter = getLetter(letterIndex);
          int row = pos.getRow();

          console.println("   " + letter + row);
        });
      }
      console.println("-------------------------");
    }

    public static void InitializeEnemyFleetRandomly() {
        enemyFleet = GameController.initializeShips();
        List<Position> occupiedPositions = new ArrayList<>();
        for (Ship ship : enemyFleet) {
          InitializeShipRandomly(ship, occupiedPositions);
        }

        if (testingMode == 2) {
          printFleet(enemyFleet, "Enemy Fleet");
        }
    }

  public static void initialiseAndShotAllShipsInEnemyFleet() {
    enemyFleet = GameController.initializeShips();

    // Aircraft Carrier
    Ship ship1 = enemyFleet.get(0);
    ship1.addPosition("G3");
    ship1.addPosition("G2");
    ship1.addPosition("G4");
    ship1.addPosition("F3");
    ship1.addPosition("H3");

    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.G, 3));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.G, 2));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.G, 4));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.F, 3));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.H, 3));
    ship1.setIsSunk(true);

    //  Battleship
    Ship ship2 = enemyFleet.get(1);
    ship2.addPosition("D6");
    ship2.addPosition("D7");
    ship2.addPosition("C6");
    ship2.addPosition("E6");

    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.D, 6));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.D, 7));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.C, 6));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.E, 6));
    ship2.setIsSunk(true);


    // Submarine
    Ship ship3 = enemyFleet.get(2);
    ship3.addPosition("C5");
    ship3.addPosition("B5");
    ship3.addPosition("C4");

    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.C, 5));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.B, 5));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.C, 4));
    ship3.setIsSunk(true);

    // Destroyer
    Ship ship4 = enemyFleet.get(3);
    ship4.addPosition("A0");
    ship4.addPosition("A1");
    ship4.addPosition("B0");

    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.A, 0));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.A, 1));
    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.B, 0));
    ship4.setIsSunk(true);

    // Patrol Boat
    Ship ship5 = enemyFleet.get(4);
    ship5.addPosition("A6");
    ship5.addPosition("A5");

    GameController.fireAndCheckIsHit(enemyFleet, new Position(Letter.A, 6));
  //  ship5.fireHit(new Position(Letter.A, 5));

    printFleet(enemyFleet, "Enemy Fleet");

  }

  public static void InitializeMyFleetRandomly() {
    myFleet = GameController.initializeShips();
    List<Position> occupiedPositions = new ArrayList<>();
    for (Ship ship : myFleet) {
      InitializeShipRandomly(ship, occupiedPositions);
    }

    printFleet(myFleet, "My Fleet");
  }

    public static List<Ship> getEnemyFleet() {
      return enemyFleet;
    }

    public static  List<Ship> getMyFleet() {
      return myFleet;
    }
}
