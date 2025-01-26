package Game;
import java.util.Random;
import java.util.Scanner;

import Entities.A_Team;
import Entities.Conscript;
import Entities.Lazy;
import Entities.Mercenary;
import Map.Chamber;
import Map.MoveDir;

public class GameController{
    
    public final int ROW_QUANTITY;
    public final int COL_QUANTITY;

    private static int userPositionX;
    private static int userPositionY;
    private boolean hasGold;
    private static boolean hasDied;
    private boolean gameWon;
    MoveDir dir;

    private Chamber[][] arrayOfChambers;
    private Mercenary[] mercs = new Mercenary[Mercenary.mercPlacements.length];

    
    public GameController(int rows, int cols){
        COL_QUANTITY = cols;
        ROW_QUANTITY = rows;

        userPositionX = 0;
        userPositionY = 0;
    }
    public GameController(){
        COL_QUANTITY = 8;
        ROW_QUANTITY = 8;

        userPositionX = 0;
        userPositionY = 0;
        arrayOfChambers = createChamberRooms();
        arrayOfChambers[0][0].setUserChamber(true);
        arrayOfChambers[0][0].setHasVisited(true);

        hasGold = false;
        hasDied = false;
        gameWon = false;
        linkChambers();
        setMercenaries();
    }

    private void linkChambers(){
        for(int row = 0; row < arrayOfChambers.length; row++){
            for(int col = 0; col < arrayOfChambers[row].length; col++){
                if(row == 0 && col == 0){
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }else if(row == 0 && col == arrayOfChambers[row].length -1){
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                }else if(row == arrayOfChambers.length-1 && col == 0){
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }else if(row == arrayOfChambers.length-1 && col == arrayOfChambers[row].length -1){
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                }else if(row == 0){
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }else if(col == 0){
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }else if(row == arrayOfChambers.length-1){
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }else if(col == arrayOfChambers[row].length -1){
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                }else{
                    arrayOfChambers[row][col].setChamberDOWN(arrayOfChambers[row+1][col]);
                    arrayOfChambers[row][col].setChamberUP(arrayOfChambers[row-1][col]);
                    arrayOfChambers[row][col].setChamberLEFT(arrayOfChambers[row][col-1]);
                    arrayOfChambers[row][col].setChamberRIGHT(arrayOfChambers[row][col+1]);
                }
            }
        }
    }

    private Chamber[][] createChamberRooms(){
        Random rand = new Random();

        Chamber[][] temp = new Chamber[ROW_QUANTITY][COL_QUANTITY];

        for(int i = 0; i < temp.length; i++){
            for(int j = 0; j < temp[i].length; j++){
                temp[i][j] = new Chamber(false);
            }
        }
        
        int chamberPlacementX;
        int chamberPlacementY;
        int evilPlacementX;
        int evilPlacementY;
        
        evilPlacementX = rand.nextInt(2, COL_QUANTITY);
        evilPlacementY = rand.nextInt(2, ROW_QUANTITY);
        temp[evilPlacementX][evilPlacementY].setEvilChamber(true);
        temp[evilPlacementX][evilPlacementY].setData('S');

        //gold room
        while(true){
            int goldPlacementX = rand.nextInt(0, COL_QUANTITY);
            int goldPlacementY = rand.nextInt(0, ROW_QUANTITY);
            if(temp[goldPlacementY][goldPlacementX].getData() == ' '){
                if (!(goldPlacementX <= 1) || !(goldPlacementY <= 1)) {
                    temp[goldPlacementY][goldPlacementX].setGoldChamber(true);
                    temp[goldPlacementY][goldPlacementX].setData('G');
                    break;  
                }
            }
        }
        
        /* for(int i = 0; i < 64*0.2; i++){
            while(true){
                chamberPlacementX = rand.nextInt(0, COL_QUANTITY);
                chamberPlacementY = rand.nextInt(0, ROW_QUANTITY);
                if(temp[chamberPlacementY][chamberPlacementX].getData() == ' '){
                    if (!(chamberPlacementX <= 1) || !(chamberPlacementY <= 1)) {
                        temp[chamberPlacementX][chamberPlacementY].setDeathPit(true);
                        temp[chamberPlacementX][chamberPlacementY].setData('B');
                        break;  
                    }
                }
            }
        } */
        return temp;
    }

    public int[] getUserPosition() {
        return new int[]{userPositionX, userPositionY};
    }

    public void move(MoveDir moveDir){
        int prevPosY = userPositionY;
        int prevPosX = userPositionX;

        if(!isValidMoveDir(moveDir)){
            System.out.println("invalid move");
        }else{
            switch(moveDir){
                case UP -> {
                    userPositionY-=1;
                    arrayOfChambers[userPositionY][prevPosX].setUserChamber(true);
                    arrayOfChambers[prevPosY][prevPosX].setUserChamber(false);
                    arrayOfChambers[prevPosY][prevPosX].setHasVisited(true);
                    arrayOfChambers[userPositionY][userPositionX].setHasVisited(true);
                    dir = MoveDir.UP;
                }
                case DOWN -> {
                    userPositionY+=1;
                    arrayOfChambers[userPositionY][userPositionX].setUserChamber(true);
                    arrayOfChambers[prevPosY][userPositionX].setUserChamber(false);
                    arrayOfChambers[prevPosY][userPositionX].setHasVisited(true);
                    arrayOfChambers[userPositionY][userPositionX].setHasVisited(true);
                    dir = MoveDir.DOWN;
                }
                case RIGHT -> {
                    userPositionX += 1;
                    arrayOfChambers[userPositionY][userPositionX].setUserChamber(true);
                    arrayOfChambers[userPositionY][prevPosX].setUserChamber(false);
                    arrayOfChambers[userPositionY][prevPosX].setHasVisited(true);
                    arrayOfChambers[userPositionY][userPositionX].setHasVisited(true);
                    dir = MoveDir.RIGHT;
                }
                case LEFT -> {
                    userPositionX-=1;
                    arrayOfChambers[userPositionY][userPositionX].setUserChamber(true);
                    arrayOfChambers[userPositionY][prevPosX].setUserChamber(false);
                    arrayOfChambers[userPositionY][prevPosX].setHasVisited(true);
                    arrayOfChambers[userPositionY][userPositionX].setHasVisited(true);
                    dir = MoveDir.LEFT;
                }
                default -> {
                }
            }
        }
    }

    public boolean getHasGold(){
        return hasGold;
    }

    
    public boolean isOnGoldChamber(){
        boolean result = false;
        if(arrayOfChambers[userPositionY][userPositionX].getData() == 'G'){
            result = true;
            System.out.println("You're in the gold chamber. Enter 'P' to pick up the gold.");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().toLowerCase().startsWith("p")){
                hasGold = true;
                arrayOfChambers[userPositionY][userPositionX].setData(' ');
            }
        }
        return result;
    }

    public boolean getHasDied(){
        if(arrayOfChambers[userPositionY][userPositionX].getData() == 'B'){
            hasDied = true;
        }else if(arrayOfChambers[userPositionY][userPositionX].getData() == 'S'){
            hasDied = true;
        }else if(arrayOfChambers[userPositionY][userPositionX].hasMercenary()){
            hasDied = true;
        }
        return hasDied;
    }

    public boolean getGameWon(){
        if(userPositionX == 0 && userPositionY == 0 && hasGold == true){
            return true;
        }
        else{
            return false;
        }
    }

    public Chamber[][] getArrayChambers(){
        return arrayOfChambers;
    }

    private boolean isValidMoveDir(MoveDir moveDir) {
        if(moveDir == MoveDir.LEFT && userPositionX == 0){
            return false;
        }else if(moveDir == MoveDir.DOWN && userPositionY == arrayOfChambers[0].length -1){
            return false;
        }else if(moveDir == MoveDir.RIGHT && userPositionX == arrayOfChambers[0].length -1){
            return false;
        }else if(moveDir == MoveDir.UP && userPositionY == 0){
            return false;
        }
        return true;
    }
    private void setMercenaries(){
        Random rand = new Random();
        for(int i = 0; i < Mercenary.mercPlacements.length; i++){
            while(true){
                int mercPlacementX = rand.nextInt(0, COL_QUANTITY);
                int mercPlacementY = rand.nextInt(0, ROW_QUANTITY);
                if(arrayOfChambers[mercPlacementY][mercPlacementX].getData() == ' ' && !arrayOfChambers[mercPlacementY][mercPlacementX].hasMercenary()){
                    if (!(mercPlacementX <= 1) || !(mercPlacementY <= 1)) {
                        //arrayOfChambers[mercPlacementY][mercPlacementX].setMercenary(true);

                        Mercenary.mercPlacements[i][0] = mercPlacementY;
                        Mercenary.mercPlacements[i][1] = mercPlacementX;

                        if (i <= Mercenary.mercPlacements.length * 0.25) {
                            mercs[i] = new A_Team(mercPlacementY, mercPlacementX);
                        }else if(i > Mercenary.mercPlacements.length * 0.375 && i <= Mercenary.mercPlacements.length - Mercenary.mercPlacements.length * 0.375){
                            mercs[i] = new Lazy(mercPlacementY, mercPlacementX);   
                        }else{
                            mercs[i] = new Conscript(mercPlacementY, mercPlacementX);   
                        }
                        arrayOfChambers[mercPlacementY][mercPlacementX].addMercenary(mercs[i]);
                        //System.out.println(arrayOfChambers[mercPlacementY][mercPlacementX].getData());
                        break;  
                    }
                }
            }
        }
    }

    public void moveMercenaries(){
        for(int i = 0; i < mercs.length; i++){
            int prevX = mercs[i].getPosX();
            int prevY = mercs[i].getPosY();
            mercs[i].mercMove(arrayOfChambers);
            arrayOfChambers[prevY][prevX].removeMercenary(mercs[i]);
            //arrayOfChambers[prevY][prevX].setData(' ');
            arrayOfChambers[mercs[i].getPosY()][mercs[i].getPosX()].addMercenary(mercs[i]);
            //arrayOfChambers[mercs[i].getPosY()][mercs[i].getPosX()].setData(mercs[i].getData());
        }
    }

    public static int[] getUserPosition2(){
        return new int[]{userPositionY, userPositionX};
    }

    public static void setHasDied(boolean hasDied) {
        GameController.hasDied = hasDied;
    }
}