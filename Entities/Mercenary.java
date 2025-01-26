package Entities;
//A_TEAM will copy player movement if seen 1 block away
//picking up GOLD Will take 1 movement tick...

import Map.Chamber;
import Map.MoveDir;

public abstract class Mercenary{
    char data;
    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    int posX;
    int posY;
    private GuardType type;

    public GuardType getType() {
        return type;
    }

    public void setType(GuardType type) {
        this.type = type;
    }

    public static int[][] mercPlacements = new int[8][2];


    public Mercenary(int pY, int pX){
        posY = pY;
        posX = pX;
    }

    public abstract MoveDir mercMove(Chamber[][] chambers);
     /* {
       Random rand = new Random();
        MoveDir placements = MoveDir.UP;

        if(rand.nextDouble() > 0.5){
            int fourCords = rand.nextInt(4);
            placements = switch (fourCords) {
                case 0 -> MoveDir.UP;
                case 1 -> MoveDir.DOWN;
                case 2 -> MoveDir.LEFT;
                default -> MoveDir.RIGHT;
            };

            while(true){
                if(!isValidMoveDir(placements, posX, posY, chambers)){
                    fourCords = rand.nextInt(4);
                    placements = switch (fourCords) {
                        case 0 -> MoveDir.UP;
                        case 1 -> MoveDir.DOWN;
                        case 2 -> MoveDir.LEFT;
                        default -> MoveDir.RIGHT;
                    };
                }
                else{
                    switch (placements) {
                        case DOWN -> this.posY += 1;
                        case UP -> this.posY -= 1;
                        case LEFT -> this.posX -= 1;
                        default -> this.posX += 1;
                    }
                    break;
                }
                
            }
        }
        return placements; 
    }*/

    public boolean isValidMoveDir(MoveDir moveDir, int posX, int posY, Chamber[][] chambers) {
        if(moveDir == MoveDir.LEFT && posX == 0){
            return false;
        }else if(moveDir == MoveDir.DOWN && posY == chambers[0].length -1){
            return false;
        }else if(moveDir == MoveDir.RIGHT && posX == chambers[0].length -1){
            return false;
        }else if(moveDir == MoveDir.UP && posY == 0){
            return false;
        }
        return validMercMoveChamber(moveDir, chambers);
    }

    private boolean validMercMoveChamber(MoveDir moveDir, Chamber[][] chambers){
        if (moveDir == MoveDir.LEFT && chambers[posY][posX-1].getData() != 'S') {
            return true;
        }else if (moveDir == MoveDir.RIGHT && chambers[posY][posX+1].getData() != 'S') {
            return true;
        }else if (moveDir == MoveDir.UP && chambers[posY-1][posX].getData() != 'S') {
            return true;
        }else if (moveDir == MoveDir.DOWN && chambers[posY+1][posX].getData() != 'S') {
            return true;
        }
        return false;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public static int[][] getMercPlacements() {
        return mercPlacements;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public static void setMercPlacements(int[][] mercPlacements) {
        Mercenary.mercPlacements = mercPlacements;
    }

    
}
