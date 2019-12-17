package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView textViewResult;
    TextView textViewLevelResult;
    TextView textViewExpResult;
    ProgressBar progressBarLevelResult;
    Button btnMainMenu;
    LinearLayout unlockSpellLinear;
    ImageView imgSpell;
    TextView textViewSpellName;
    private Handler mHandler = new Handler();
    int isRun = 1;
    int curExp;
    int limitExp;
    int receiveExp;
    int userLevel;
    boolean isLevelUp = false;
    Item myLvlupItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewResult = findViewById(R.id.textViewResult);
        textViewLevelResult = findViewById(R.id.textViewLevelResult);
        textViewExpResult = findViewById(R.id.textViewExpResult);
        progressBarLevelResult = findViewById(R.id.progressBarLevelResult);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        unlockSpellLinear = findViewById(R.id.unlock_spell_linear);
        unlockSpellLinear.setVisibility(View.INVISIBLE);
        imgSpell = findViewById(R.id.imgSpellLevelUp);
        textViewSpellName = findViewById(R.id.textViewSpellNameLevelUp);
        if(getIntent().getStringExtra("context").equals("1")){
            textViewResult.setText(R.string.you_win_text);
        }else {
            textViewResult.setText(R.string.you_loose_text);
        }

        ArrayList<Item> listItem = new ArrayList<Item>();
        listItem = new Helper(getApplication()).getListItem();
        String textLevel = getResources().getString(R.string.lv_text) + listItem.size();
        userLevel = listItem.size();
        textViewLevelResult.setText(textLevel);
        textViewExpResult.setText("" + listItem.get(listItem.size()-1).getCurExp() + "/" + listItem.get(listItem.size()-1).getDesExp());
        progressBarLevelResult.setMax(listItem.get(listItem.size()-1).getDesExp());
        progressBarLevelResult.setProgress(listItem.get(listItem.size()-1).getCurExp());
        curExp = listItem.get(listItem.size()-1).getCurExp();
        limitExp = listItem.get(listItem.size()-1).getDesExp();
        receiveExp = Integer.parseInt(getIntent().getStringExtra("exp"));
        Log.d("Result", "" + receiveExp);

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnMainMenu.setEnabled(false);

        aminationProgressBar.run();
    }

    private  Runnable aminationProgressBar = new Runnable() {
        int count = 0;
        SQLiteDatabase db;
        @Override
        public void run() {
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                curExp += 1;
                count +=1;
                if(count < receiveExp){
                    if(curExp < limitExp){
                        progressBarLevelResult.setMax(limitExp);
                        progressBarLevelResult.setProgress(curExp);
                        textViewExpResult.setText("" + curExp + "/" + limitExp);
                    }
                    else {
                        userLevel += 1;
                        myLvlupItem = new Helper(getApplication()).getItemPerLevel(userLevel);
                        curExp = 0;
                        limitExp = myLvlupItem.getDesExp();
                        Integer resource = getResources().getIdentifier(myLvlupItem.getImageName(),"drawable", getPackageName());
                        imgSpell.setImageResource(resource);
                        textViewSpellName.setText(myLvlupItem.getSpellName());
                        unlockSpellLinear.setVisibility(View.VISIBLE);
                        String textLevel = getResources().getString(R.string.lv_text) + userLevel;
                        textViewLevelResult.setText(textLevel);
                        progressBarLevelResult.setMax(limitExp);
                        progressBarLevelResult.setProgress(curExp);
                        textViewExpResult.setText("" + curExp + "/" + limitExp);
                    }
                    mHandler.postDelayed(this,100);
                }
                else {
                    mHandler.removeCallbacks(this);
//                    File storagePath = getApplication().getFilesDir();
//                    String myDbPath = storagePath + "/" + "myGameDatabase";
//                    try{
//                        db = SQLiteDatabase.openDatabase(myDbPath, null,
//                                SQLiteDatabase.CREATE_IF_NECESSARY);
//                        db.beginTransaction();
//                        db.execSQL("update tblLever set current_exp = " + curExp + ", unlock = 1 where level =" + userLevel);
//
//                        db.setTransactionSuccessful(); //commit your changes
//                    } catch (SQLiteException e) {
//                        Log.d("Db-Ex", e.getMessage());
//                    }finally {
//                        db.endTransaction();
//                        db.close();
//                    }
                    btnMainMenu.setEnabled(true);
                }
            }
        }
    };
}
