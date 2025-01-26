package Entities;
import java.util.Random;

import Map.Chamber;
import Map.MoveDir;

public class Lazy extends Mercenary{

    public Lazy(int x, int y){
        super(x, y);
        super.setType(GuardType.LAZY);

    }

    @Override
    public MoveDir mercMove(Chamber[][] chambers){
        Random rand = new Random();
        MoveDir placements = MoveDir.UP;
        if(rand.nextDouble() > 0.35){
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
        return placements;
    }
}
