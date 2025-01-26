package Entities;
import java.util.Random;

import Game.GameController;
import Map.Chamber;
import Map.MoveDir;

public class A_Team extends Mercenary{
    public A_Team(int x, int y){
        super(x, y);
        super.setType(GuardType.A_TEAM);
    }

    @Override
    public MoveDir mercMove(Chamber[][] chambers){
        Random rand = new Random();
        MoveDir placements = MoveDir.UP;
        
        int userY = GameController.getUserPosition2()[0];
        int userX = GameController.getUserPosition2()[1];

        if (userY == posY && userX == posX) {
            GameController.setHasDied(true);
        }
        else if(userNearby(chambers) != null) {
            switch (userNearby(chambers)) {
                case DOWN -> this.posY += 1;
                case UP -> this.posY -= 1;
                case LEFT -> this.posX -= 1;
                default -> this.posX += 1;
            }
        }
        else{
            if(rand.nextDouble() > 0.1){
                int fourCords = rand.nextInt(4);
                placements = switch (fourCords) {
                    case 0 -> MoveDir.UP;
                    case 1 -> MoveDir.DOWN;
                    case 2 -> MoveDir.LEFT;
                    default -> MoveDir.RIGHT;
                };

                while(true){
                    if(!super.isValidMoveDir(placements, posX, posY, chambers)){
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
        }
        return placements;
    }

    private MoveDir userNearby(Chamber[][] chambers){
        int userY = GameController.getUserPosition2()[0];
        int userX = GameController.getUserPosition2()[1];
        try{
            if ((posY + 1 == userY || posY + 2 == userY) && posX == userX) {
                return MoveDir.DOWN;
            }else if ((posY - 1 == userY || posY - 2 == userY) && posX == userX) {
                return MoveDir.UP;
            }else if (posY == userY && (posX + 1 == userX || posX + 2 == userX)) {
                return MoveDir.RIGHT;
            }else if (posY == userY && (posX - 1 == userX || posX - 2 == userX)) {
                return MoveDir.LEFT;
            }else if (posY + 1 == userY && posX - 1 == userX) {
                return MoveDir.LEFT;
            }else if (posY - 1 == userY && posX - 1 == userX) {
                return MoveDir.UP;
            }else if (posY + 1 == userY && posX + 1 == userX) {
                return MoveDir.RIGHT;
            }else if (posY - 1 == userY && posX - 1 == userX) {
                return MoveDir.DOWN;
            }
        }catch(RuntimeException e){}
        return null;
    }
}
