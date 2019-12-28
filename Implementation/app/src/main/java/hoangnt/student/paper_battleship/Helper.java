package hoangnt.student.paper_battleship;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

public class Helper {
    SQLiteDatabase db;
    File storagePath;
    String myDbPath;
    public Helper(Application app){
        storagePath = app.getFilesDir();
        myDbPath = storagePath + "/" + "myGameDatabase";
    }
    public Setting getSetting(){
        Cursor c1;
        Setting setting = new Setting("vi", "");
        try{
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            c1 = db.rawQuery("select * from tblSetting", null);
            c1.moveToPosition(-1);
            while ( c1.moveToNext() ){
                setting.setLanguage(c1.getString(0));
                setting.setUserName(c1.getString(1));
            }
        }catch (Exception ex){
            Log.d("Db-Ex", ex.getMessage());
        }finally {
            db.close();
        }
        return setting;
    }

    public Item getItemPerLevel(int level){
        Cursor c1;
        Item myItem = null;
        try{
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            c1 = db.rawQuery("select * from tblLever where level = " + level, null);
            c1.moveToPosition(-1);
            while ( c1.moveToNext() ){
                myItem = new Item(c1.getString(6), c1.getInt(5), c1.getInt(0), c1.getInt(1)==1, c1.getInt(2), c1.getInt(3), c1.getString(4));
            }
        }catch (Exception ex){
            Log.d("Db-Ex", ex.getMessage());
        }finally {
            db.close();
        }
        return  myItem;
    }

    public ArrayList<Item> getListItem(){
        Cursor c1;
        ArrayList<Item> listItem = new ArrayList<Item>();
        try{
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            c1 = db.rawQuery("select * from tblLever where unlock = 1", null);
            c1.moveToPosition(-1);
            while ( c1.moveToNext() ){
                listItem.add(new Item(c1.getString(6), c1.getInt(5), c1.getInt(0), c1.getInt(1)==1, c1.getInt(2), c1.getInt(3), c1.getString(4)));
            }
        }catch (Exception ex){
            Log.d("Db-Ex", ex.getMessage());
        }finally {
            db.close();
        }
        return listItem;

    }

    public ArrayList<Item> getAllItem(){
        Cursor c1;
        ArrayList<Item> listItem = new ArrayList<Item>();
        try{
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            c1 = db.rawQuery("select * from tblLever", null);
            c1.moveToPosition(-1);
            while ( c1.moveToNext() ){
                listItem.add(new Item(c1.getString(6), c1.getInt(5), c1.getInt(0), c1.getInt(1)==1, c1.getInt(2), c1.getInt(3), c1.getString(4)));
            }
        }catch (Exception ex){
            Log.d("Db-Ex", ex.getMessage());
        }finally {
            db.close();
        }
        return listItem;

    }

    public ArrayList<BattleHistory> getListBattleHistory(){
        Cursor c1;
        ArrayList<BattleHistory> listItem = new ArrayList<BattleHistory>();
        try{
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            c1 = db.rawQuery("select * from tblHistory", null);
            c1.moveToPosition(-1);
            while ( c1.moveToNext() ){
                listItem.add(new BattleHistory(c1.getInt(0), c1.getString(1), c1.getString(c1.getColumnIndexOrThrow("time")), c1.getInt(3), c1.getInt(4)));
            }
        }catch (Exception ex){
            Log.d("Db-Ex", ex.getMessage());
        }finally {
            db.close();
        }
        return listItem;
    }

    public Ship[] parseListStringToListShipObject(ArrayList<String> listStr){
        Ship[] listShip = new Ship[listStr.size()] ;
        for (int i=0; i< listStr.size(); i++){
            String strShip = listStr.get(i);
            Log.d("My-debuger", strShip);
            String idPartStr = strShip.substring(strShip.indexOf('[')+1, strShip.indexOf(']'));
            ArrayList<Integer> idPartInt = new ArrayList<Integer>();
            idPartInt = parseStringToArrayInterger(idPartStr);
            strShip = strShip.substring(strShip.indexOf(']')+2);
            String origentationStr = strShip.substring(strShip.indexOf('=')+ 1, strShip.indexOf('=') + 2);
            strShip = strShip.substring(strShip.indexOf(',')+1);
            String positionStr = strShip.substring(strShip.indexOf('[')+1, strShip.indexOf(']'));
            strShip = strShip.substring(strShip.indexOf(']')+2);
            ArrayList<Integer> positionInt = new ArrayList<Integer>();
            positionInt = parseStringToArrayInterger(positionStr);
            String statusStr = strShip.substring(strShip.indexOf('[')+1, strShip.indexOf(']'));
            strShip = strShip.substring(strShip.indexOf(']')+2);
            ArrayList<Boolean> statusBoolean = new ArrayList<Boolean>();
            statusBoolean = parseStringToArrayBoolean(statusStr);
            String typeStr = strShip.substring(strShip.indexOf('=')+1, strShip.indexOf('=') + 2);
            listShip[i] = new Ship(idPartInt, Integer.parseInt(origentationStr), positionInt, statusBoolean, Integer.parseInt(typeStr));
        }
        return listShip;
    }

    public ArrayList<Integer> parseStringToArrayInterger(String str){
        ArrayList<Integer> arrInt = new ArrayList<Integer>();
        String[] arrStr = str.split(",");
        for (int i =0 ; i<arrStr.length; i++){
            arrInt.add(Integer.parseInt(arrStr[i].trim()));
        }
        return arrInt;
    }

    public ArrayList<Boolean> parseStringToArrayBoolean(String str){
        ArrayList<Boolean> arrBoolean = new ArrayList<Boolean>();
        String[] arrStr = str.split(",");
        for (int i =0 ; i<arrStr.length; i++){
            arrBoolean.add(arrStr[i].trim().equals("true") ? true :false);
        }
        return arrBoolean;
    }

    public void drawShip(ImageView img, int value){
        switch (value){
            case 1: img.setBackgroundResource(R.drawable.ship_1);
                break;
            case 2: img.setBackgroundResource(R.drawable.ship_2_1);
                break;
            case 3: img.setBackgroundResource(R.drawable.ship_2_2);
                break;
            case 4: img.setBackgroundResource(R.drawable.ship_3_1);
                break;
            case 5: img.setBackgroundResource(R.drawable.ship_3_2);
                break;
            case 6: img.setBackgroundResource(R.drawable.ship_3_3);
                break;
            case 7: img.setBackgroundResource(R.drawable.ship_4_1);
                break;
            case 8: img.setBackgroundResource(R.drawable.ship_4_2);
                break;
            case 9: img.setBackgroundResource(R.drawable.ship_4_3);
                break;
            case 10: img.setBackgroundResource(R.drawable.ship_4_4);
                break;
            case 21: img.setBackgroundResource(R.drawable.n_ship_1);
                break;
            case 22: img.setBackgroundResource(R.drawable.n_ship_2_2);
                break;
            case 23: img.setBackgroundResource(R.drawable.n_ship_2_1);
                break;
            case 24: img.setBackgroundResource(R.drawable.n_ship_3_3);
                break;
            case 25: img.setBackgroundResource(R.drawable.n_ship_3_2);
                break;
            case 26: img.setBackgroundResource(R.drawable.n_ship_3_1);
                break;
            case 27: img.setBackgroundResource(R.drawable.n_ship_4_4);
                break;
            case 28: img.setBackgroundResource(R.drawable.n_ship_4_3);
                break;
            case 29: img.setBackgroundResource(R.drawable.n_ship_4_2);
                break;
            case 30: img.setBackgroundResource(R.drawable.n_ship_4_1);
                break;
        }
    }
}
