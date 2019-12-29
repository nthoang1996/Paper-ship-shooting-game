package hoangnt.student.paper_battleship;

import java.util.ArrayList;

public class InfoMatch {

    public static ArrayList<Item> listAllItem ;

    public static Ship[] listEnemyShip;

    public static boolean isRandomItem = false;

    public static int turn = 1;

    public static int isShooting = 0;

    public static ArrayList<Item> getListAllItem() {
        return listAllItem;
    }

    public static boolean isIsRandomItem() {
        return isRandomItem;
    }

    public static Item[] getCurrentSpells() {
        return currentSpells;
    }

    public static void setCurrentSpells(Item[] currentSpells) {
        InfoMatch.currentSpells = currentSpells;
    }

    public static boolean isIsHandleDataYourMap() {
        return isHandleDataYourMap;
    }

    public static void setIsHandleDataYourMap(boolean isHandleDataYourMap) {
        InfoMatch.isHandleDataYourMap = isHandleDataYourMap;
    }

    public static boolean isIsIsHandleDataEnemyMap() {
        return isIsHandleDataEnemyMap;
    }

    public static void setIsIsHandleDataEnemyMap(boolean isIsHandleDataEnemyMap) {
        InfoMatch.isIsHandleDataEnemyMap = isIsHandleDataEnemyMap;
    }

    public static int numberSpell=0;

    public static Item[] currentSpells;

    public static boolean isHandleDataYourMap = false;

    public static boolean isIsHandleDataEnemyMap = false;

    public static void setCurrentSpell(Item item, int index){ currentSpells[index] = item;}

    public static void setNumberSpell(int numberSpell) {
        InfoMatch.numberSpell = numberSpell;
        currentSpells = new Item[numberSpell];
    }

    public static int getNumberSpell(){return numberSpell;}

    public static  Item getCurrentSpell(int index){ return currentSpells[index];}

    public static void setIsShooting(int value){isShooting = value;}

    public static int getIsShooting(){return isShooting;}

    public static boolean getIsRandomItem() {return  isRandomItem;}

    public static void  setIsRandomItem(boolean value) { isRandomItem = value;}

    public static Ship[] getListEnemyShip() {
        return listEnemyShip;
    }

    public static ArrayList<Item> listItemUser;

    public static ArrayList<Item> getListItemUser(){return listItemUser;}

    public static boolean isChangeTurn = false;

    public static boolean isTimeOut = false;

    public static boolean isIsTimeOut() {
        return isTimeOut;
    }

    public static void setIsTimeOut(boolean isTimeOut) {
        InfoMatch.isTimeOut = isTimeOut;
    }

    public static boolean isIsChangeTurn() {
        return isChangeTurn;
    }

    public static void setIsChangeTurn(boolean isChangeTurn) {
        InfoMatch.isChangeTurn = isChangeTurn;
    }

    public static void setListAllItem(ArrayList<Item> list){listAllItem = list;}

    public static Item getItemFromAllItem(int index) {return listAllItem.get(index);}

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        InfoMatch.turn = turn;
    }



    public static void setListItemUser(ArrayList<Item> listItemUser) {
        InfoMatch.listItemUser = listItemUser;
    }

    public static void setListEnemyShip(Ship[] listEnemyShip) {
        InfoMatch.listEnemyShip = listEnemyShip;
    }
}
