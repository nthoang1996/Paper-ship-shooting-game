package hoangnt.student.paper_battleship;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
}
