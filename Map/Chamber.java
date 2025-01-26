package Map;
import java.util.ArrayList;

import Entities.Mercenary;

public class Chamber {
    
    Chamber UP;
    Chamber DOWN;
    Chamber RIGHT;
    Chamber LEFT;

    boolean hasVisited = false;
    boolean userChamber = false;
    boolean evilChamber = false;
    boolean goldChamber = false;
    boolean deathPit = false;
    boolean hasMercenary = false;

    ArrayList<Mercenary> mercs;

    char data;

    public Chamber(boolean userChamber){
        this.userChamber = userChamber;
        data = ' ';
        mercs = new ArrayList<>();
    }

    public void setChamberUP(Chamber c){
        UP = c;
    }
    public void setChamberDOWN(Chamber c){
        DOWN = c;
    }
    public void setChamberRIGHT(Chamber c){
        RIGHT = c;
    }
    public void setChamberLEFT(Chamber c){
        LEFT = c;
    }

    public Chamber getUP() {
        return UP;
    }

    public Chamber getDOWN() {
        return DOWN;
    }

    public Chamber getRIGHT() {
        return RIGHT;
    }

    public Chamber getLEFT() {
        return LEFT;
    }

    public boolean isHasVisited() {
        return hasVisited;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public boolean isUserChamber() {
        return userChamber;
    }

    public void setUserChamber(boolean userChamber) {
        this.userChamber = userChamber;
    }

    public boolean isEvilChamber() {
        return evilChamber;
    }

    public void setEvilChamber(boolean evilChamber) {
        this.evilChamber = evilChamber;
    }

    public boolean isGoldChamber() {
        return goldChamber;
    }

    public void setGoldChamber(boolean goldChamber) {
        this.goldChamber = goldChamber;
    }

    public boolean isDeathPit() {
        return deathPit;
    }

    public void setDeathPit(boolean deathPit) {
        this.deathPit = deathPit;
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public boolean hasMercenary(){
        return !mercs.isEmpty();
    }

    public void addMercenary(Mercenary set){
        mercs.add(set);
    }

    public ArrayList<Mercenary> getMercenary(){
        return mercs;
    }
    public void removeMercenary(Mercenary m){
        mercs.remove(m);
    }
    
}
