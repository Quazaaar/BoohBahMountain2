package UI;

import java.util.HashSet;
import java.util.Scanner;

import Entities.Mercenary;
import Game.GameController;
import Map.MoveDir;



public class GameUI {
    
    private GameController gameController;
    private Scanner scanner;
    
    public GameUI(){
        gameController = new GameController();
    }

    public GameUI(int rows, int cols){
        gameController = new GameController(rows, cols);
    }

    public void start(){
        scanner = new Scanner(System.in);
        System.out.println("BooBah Dungeon Simulator");
        System.out.println("Avoid at all costs the pits and the evil BoohBah, cursed be his name.");
        printBoard();
        System.out.println();

        while (true) { 
            getInput();
            gameController.moveMercenaries();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            printBoard();
            if (gameController.getGameWon()) {
                System.out.println("You won the game!");
                break;
            }
            else if (gameController.getHasDied()) {
                System.out.println("You lost the game!");
                break;
            }
            
        }
        System.out.println("Do you want to play again? (Y/N)");
        if (scanner.next().toLowerCase().startsWith("y")) {
            gameController = new GameController();
            start();
        }
    }

    private MoveDir getInput(){
        MoveDir inp = MoveDir.NONE;
        Scanner scanner = new Scanner(System.in);

        if(gameController.isOnGoldChamber()){
            gameController.getHasGold();
            gameController.moveMercenaries();
        }else{
            while (true) { 
                System.out.println("Enter your moves with W,A,S,D");
                String scannedInput = scanner.nextLine();
                if(scannedInput.toLowerCase().startsWith("w")){
                    inp = MoveDir.UP;
                    break;
                }else if(scannedInput.toLowerCase().startsWith("a")){
                    inp = MoveDir.LEFT;
                    break;
                }else if(scannedInput.toLowerCase().startsWith("s")){
                    inp = (MoveDir.DOWN);
                    break;
                }else if(scannedInput.toLowerCase().startsWith("d")){
                    inp = (MoveDir.RIGHT);
                    break;
                }else{
                    System.out.println("Invalid input, enter again.");
                }
            }
        }
        gameController.move(inp);
        return inp;
    }
    
    public void printBoard(){
        for (int i = 0; i < gameController.getArrayChambers().length; i++) {
            printLine();
            printRow(i);
        }

        printLine();
    }

    private void printLine() {
        for (int i = 0; i < gameController.COL_QUANTITY; i++) {
            System.out.print("------");
        }
        System.out.println("-");

    }

    private void printRow(int row) {

        for(int i = 0; i < gameController.getArrayChambers()[row].length; i++){
            HashSet<Character> middleSpace = new HashSet<>();
            String centerSpace = "";

            if(gameController.getArrayChambers()[row][i].isHasVisited()){
                if(gameController.getArrayChambers()[row][i].isUserChamber()){
                    centerSpace += 'U';
                }

                /*
                try {
                    middleSpace.add(gameController.getArrayChambers()[row][i].getRIGHT().getData());  
                } catch (NullPointerException e) {
                    middleSpace.add(' ');
                }try {
                    middleSpace.add(gameController.getArrayChambers()[row][i].getLEFT().getData());  
                } catch (NullPointerException e) {
                    middleSpace.add(' ');
                }try {
                    middleSpace.add(gameController.getArrayChambers()[row][i].getUP().getData());  
                } catch (NullPointerException e) {
                    middleSpace.add(' ');
                }try {
                    middleSpace.add(gameController.getArrayChambers()[row][i].getDOWN().getData());  
                } catch (NullPointerException e) {
                    middleSpace.add(' ');
                }
                */

                //if(gameController.getArrayChambers()[row][i].getData() != 'G'){
                //   middleSpace.remove('G');
                //}else{ 
                middleSpace.add(gameController.getArrayChambers()[row][i].getData());
                //}
            }

            if(gameController.getArrayChambers()[row][i].hasMercenary()){
                for(Mercenary m :gameController.getArrayChambers()[row][i].getMercenary()){
                    switch(m.getType()){
                        case A_TEAM -> {
                            middleSpace.add('A');
                        }case LAZY -> {
                            middleSpace.add('L');
                        }case CONSCRIPT -> {
                            middleSpace.add('C');
                        }
                        default -> throw new IllegalArgumentException("Unexpected value: " + m.getType());
                    }
                }
            }

            //middleSpace.add(gameController.getArrayChambers()[row][i].getData());  
            
            for(Character c : middleSpace){
                centerSpace += c;
            }                
            middleSpace.clear();

           // centerSpace += gameController.getNearbyChambers(i);

            for(int j = centerSpace.length(); j < 4; j++){
                centerSpace += " ";
            }            
            
            System.out.printf("|%s ", centerSpace);
            centerSpace = "";

            //gameController.setCurrentChamber(gameController.getCurrentChamber().getRIGHT());
            
        }
        System.out.println("|");
    }
}
