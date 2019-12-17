package hoangnt.student.paper_battleship;

import java.util.ArrayList;

public class InfoMatch {
    public static Ship[] listEnemyShip;

    public static boolean isRandomItem = false;

    public static boolean getIsRandomItem() {return  isRandomItem;}

    public static void  setIsRandomItem(boolean value) { isRandomItem = value;}

    public static Ship[] getListEnemyShip() {
        return listEnemyShip;
    }

    public static ArrayList<Item> listItemUser;

    public static ArrayList<Item> getListItemUser(){return listItemUser;}

    public static void setListEnemyShip(Ship[] listEnemyShip) {
        InfoMatch.listEnemyShip = listEnemyShip;
    }
}
