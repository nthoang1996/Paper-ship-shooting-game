package hoangnt.student.paper_battleship;

import java.util.ArrayList;

public class InfoMatch {
    public static Ship[] listEnemyShip;

    public static boolean isRandomItem = false;

    public static int turn = 1;

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

    public static boolean isIsRandomItem() {
        return isRandomItem;
    }

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
