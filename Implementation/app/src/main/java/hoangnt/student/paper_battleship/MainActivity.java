package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LinearLayout rootLayout;
    private Handler mHandler = new Handler();
    SQLiteDatabase db;
    EditText editTextInputName;
    TextView textViewAlertMessage;
    Button btnDone;
    Setting setting;
    Button btnCreateGame;
    Button btnQuit;
    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initDatabase();
        setting = new Helper(getApplication()).getSetting();
        setLocale(setting.getLanguage());
        setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.root_layout);
        btnCreateGame = findViewById(R.id.btnHost);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinGameActivity.class);
                startActivity(intent);
            }
        });

        btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HostGameActivity.class);
                startActivity(intent);
            }
        });

        changeBackgroundRunable.run();
        if(setting.getUserName().equals("")){
            showDialog(rootLayout);
        }

        Button btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        btnQuit = findViewById(R.id.btnExit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showDialog(View v){
        final Dialog dialogInputName;
        dialogInputName = new Dialog(this);
        dialogInputName.setCanceledOnTouchOutside(false);
        dialogInputName.setContentView(R.layout.dialog_input_name);
        editTextInputName = dialogInputName.findViewById(R.id.editTextInputName);
        textViewAlertMessage = dialogInputName.findViewById(R.id.textViewAlertMessage);
        btnDone = dialogInputName.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextInputName.getText().toString();
                if (userName.length() > 0 && userName.length() < 21){
                    File storagePath = getApplication().getFilesDir();
                    String myDbPath = storagePath + "/" + "myGameDatabase";
                    try {
                        db = SQLiteDatabase.openDatabase(myDbPath, null,
                                SQLiteDatabase.CREATE_IF_NECESSARY);
                        db.beginTransaction();
                        db.execSQL("UPDATE tblSetting "
                            + "SET user_name = '" + userName
                            + "' WHERE user_name = '';");
                        db.setTransactionSuccessful(); //commit your changes
                        // here you do something with your database ...
                    } catch (SQLiteException e) {
                        Log.d("Db-Ex", e.getMessage());
                    }finally {
                        db.endTransaction();
                        db.close();
                    }
                    dialogInputName.dismiss();
                }
                else {
                    Integer id = getResources().getIdentifier("alert_name","string", getPackageName());
                    textViewAlertMessage.setText(getResources().getString(id));
                }
            }
        });
        dialogInputName.show();



    }

    public void initDatabase(){
        // path to internal memory file system (data/data/cis470.matos.databases)
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "myGameDatabase";

        try {
            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);
            db.beginTransaction();
            db.execSQL("create table tblHistory ("
                    + " ID integer PRIMARY KEY autoincrement, "
                    + " enemy text, "
                    + " time DATETIME,"
                    + " result interger,"
                    + " received_exp int);");
            db.execSQL("create table tblLever ("
                    + " level integer PRIMARY KEY autoincrement, "
                    + " unlock interger, "
                    + " destination_exp interger,"
                    + " current_exp interger,"
                    + " spell_name text,"
                    + " type interger,"
                    + " img_name text);");
            db.execSQL("create table tblSetting ("
                    + " language text,"
                    + " user_name text);");
            db.execSQL("insert into tblSetting("
                    + "language, user_name)"
                    + "values('vi', '');"
            );

            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(1, 1, 100, 0,'Unlock first spell slot', 1, 'skill_lvl1');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(2, 1, 150,0,'Mist 1 cell', 0, 'skill_lvl2');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(3, 1, 210,0,'Double shot', 0, 'skill_lvl3');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(4, 1, 280,0,'Set boom 1 cell', 0, 'skill_lvl4');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(5, 1, 360,0,'Freeze 1 turn', 0, 'skill_lvl5');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(6, 1, 450,0,'Shield level 1', 0, 'skill_lvl6');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(7, 1, 550,330,'Unlock lava background', 2, 'skill_lvl7');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(8, 0,660,0,'Unlock skin spaceship', 3, 'skill_lvl8');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(9, 0, 780,0,'Radar level 1', 0, 'skill_lvl9');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(10, 0, 910,0,'Unlock second spell slot', 1, 'skill_lvl10');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(11, 0, 1050,0,'Teleport', 0, 'skill_lvl11');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(12, 0, 1200,0,'Shield level 2', 0, 'skill_lvl12');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(13, 0, 1360,0,'Radar level 2', 0, 'skill_lvl13');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(14, 0, 1530,0,'Use plane battleship', 0, 'skill_lvl14');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(15, 0, 1710,0,'Fix the ship', 0, 'skill_lvl15');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(16, 0, 1900,0,'Unlimited strike', 0, 'skill_lvl16');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(17, 0, 2100,0,'Suicide', 0, 'skill_lvl17');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(18, 0, 2310,0,'Unlock background thriller sea', 2, 'skill_lvl18');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(19, 0, 2530,0,'Spell steal', 0, 'skill_lvl19');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(20, 0, 2760,0,'Unlock third spell slot', 1, 'skill_lv20');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(21, 0, 3000,0,'Unlock skin monster', 4, 'skill_lvl21');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(22, 0, 3250,0,'Double fun', 0, 'skill_lvl22');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(23, 0, 3510,0,'Unstoppable strike', 0, 'skill_lvl23');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(24, 0, 3780,0,'Let’s exchange', 0, 'skill_lvl24');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(25, 0, 4060,0,'5 column strike', 0, 'skill_lvl25');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(26, 0, 4350,0,'Respawn', 0, 'skill_lvl26');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(27, 0, 4650,0,'Random-spell', 0, 'skill_lvl27');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(28, 0, 4960,0,'Unlock space background', 2, 'skill_lvl28');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(29, 0, 5280,0,'1 row strike', 0, 'skill_lvl29');"
            );
            db.execSQL("insert into tblLever("
                    + "level, unlock, destination_exp, current_exp, spell_name, type, img_name)"
                    + "values(30, 0, 5610,0,'Unlock add 1 more battleship', 0, 'skill_lvl30');"
            );

            db.execSQL("insert into tblHistory("
                    + "ID, enemy, time, result, received_exp)"
                    + "values(1, 'Zannaghazi', '2014-10-07 15:45:57.00',0,'100');"
            );

            db.execSQL("insert into tblHistory("
                    + "ID, enemy, time, result, received_exp)"
                    + "values(2, 'HieuVT', '2019-12-05 12:30:01.00',1,'50');"
            );

            db.execSQL("insert into tblHistory("
                    + "ID, enemy, time, result, received_exp)"
                    + "values(3, 'Sad rain', '2018-05-31 10:06:30.00',1,'20');"
            );

            db.execSQL("insert into tblHistory("
                    + "ID, enemy, time, result, received_exp)"
                    + "values(4, 'Vo Thanh Hieu', '2018-06-01 00:05:33.00',0,'27');"
            );

            db.execSQL("insert into tblHistory("
                    + "ID, enemy, time, result, received_exp)"
                    + "values(5, 'Nguyen Thai Hoang', '2018-08-17 06:55:15.00',1,'78');"
            );


            db.setTransactionSuccessful(); //commit your changes

            // here you do something with your database ...
        } catch (SQLiteException e) {
            Log.d("Db-Ex", e.getMessage());
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private Runnable changeBackgroundRunable = new Runnable() {
        Integer[] backgroundImage = new Integer[] {R.drawable.main_bg1, R.drawable.main_bg2, R.drawable.main_bg3};
        int index = 0;

        @Override
        public void run() {
            index = index < 2 ? index+1 : 0;
            chageBackground(backgroundImage[index]);
            mHandler.postDelayed(this, 200);
        }

        public void chageBackground(Integer id){
            rootLayout.setBackgroundResource(id);
        }
    };
}
