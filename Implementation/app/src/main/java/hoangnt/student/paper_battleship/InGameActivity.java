package hoangnt.student.paper_battleship;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class InGameActivity extends TabActivity {
    private TabHost tabHost;
    private FrameLayout tabContent;
    TextView textViewTurn, textViewTimeCoundoutTurn;
    ImageView imageViewSpell1, imageViewSpell2, imageViewSpell3;
    TextView textViewTime;
    Button btnSur;
    Button btnMenu;
    Button btnYourMap;
    Button btnEnemyMap;
    ArrayList<Item> listItemUser = InfoMatch.getListItemUser();
    int isRun = 1;
    int timeCountdown = 60;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        textViewTurn = findViewById(R.id.textViewTurn);
        textViewTimeCoundoutTurn = findViewById(R.id.textViewCountdownTurn);
        imageViewSpell1 = findViewById(R.id.imgSpell1);
        imageViewSpell2 = findViewById(R.id.imgSpell2);
        imageViewSpell3 = findViewById(R.id.imgSpell3);
        btnSur = findViewById(R.id.btnSur);
        btnMenu = findViewById(R.id.btnMenu);
        textViewTime = findViewById(R.id.textViewCountdownTurn);
        btnYourMap = findViewById(R.id.btnYourMap);
        btnEnemyMap = findViewById(R.id.btnEnemyMap);

        tabContent = (FrameLayout) findViewById(android.R.id.tabcontent);

        tabHost = getTabHost();
        ArrayList<String> listEnemyData =  getIntent().getStringArrayListExtra("listEnemyShip");
        TabHost.TabSpec EnemySpec = tabHost.newTabSpec("Enemy map");
        EnemySpec.setIndicator("Enemy map");
        Intent enemyIntent = new Intent(this, PlayerMap.class);
        enemyIntent.putStringArrayListExtra("listShip", listEnemyData);
        enemyIntent.putExtra("Mode", getIntent().getStringExtra("Mode"));
        enemyIntent.putExtra("Map", "Enemy");
        EnemySpec.setContent(enemyIntent);

        ArrayList<String> listData =  getIntent().getStringArrayListExtra("listShip");
        TabHost.TabSpec yourSpec = tabHost.newTabSpec("Your map");
        yourSpec.setIndicator("Your map");
        Intent yourIntent = new Intent();
        yourIntent.setClass(this, PlayerMap.class);
        yourIntent.putStringArrayListExtra("listShip", listData);
        yourIntent.putExtra("Map", "Your");
        yourIntent.putExtra("Mode", getIntent().getStringExtra("Mode"));
        yourSpec.setContent(yourIntent);
        timeCountdownThread.run();

        if(getIntent().getStringExtra("isHost").equals("0")){
            Bluetooth.setYourTurn(false);
        }
        else{
            Bluetooth.setYourTurn(true);
        }

        tabHost.addTab(EnemySpec);
        tabHost.addTab(yourSpec);

        changeBackgroundRunable.run();
        randomItemRunable.run();

        tabHost.setCurrentTab(1);
        btnYourMap.setSelected(true);
        btnYourMap.setBackgroundColor(Color.BLUE);
        btnEnemyMap.setBackgroundResource(android.R.drawable.btn_default);

        tabHost.setCurrentTab(0);
        btnEnemyMap.setSelected(true);
        btnEnemyMap.setBackgroundColor(Color.BLUE);
        btnYourMap.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void tabHandler(View target) {
        btnEnemyMap.setSelected(false);
        btnYourMap.setSelected(false);
        if (target.getId() == R.id.btnEnemyMap) {
            tabHost.setCurrentTab(0);
            btnEnemyMap.setSelected(true);
            btnEnemyMap.setBackgroundColor(Color.BLUE);
            btnYourMap.setBackgroundResource(android.R.drawable.btn_default);
        } else if (target.getId() == R.id.btnYourMap) {
            tabHost.setCurrentTab(1);
            btnYourMap.setSelected(true);
            btnYourMap.setBackgroundColor(Color.BLUE);
            btnEnemyMap.setBackgroundResource(android.R.drawable.btn_default);
        }
    }

    private Runnable changeBackgroundRunable = new Runnable() {
        Integer[] backgroundImage = new Integer[] {R.drawable.game_bg_1, R.drawable.game_bg_2, R.drawable.game_bg_3, R.drawable.game_bg_4, R.drawable.game_bg_5, R.drawable.game_bg_4, R.drawable.game_bg_3, R.drawable.game_bg_2, R.drawable.game_bg_1};
        int index = 0;

        @Override
        public void run() {
           // Log.d("my-debug", "change background");
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                index = index < 8 ? index+1 : 0;
                chageBackground(backgroundImage[index]);
                if(Bluetooth.getYourTurn()){
                    textViewTurn.setText(R.string.your_turn);
                }
                else {
                    textViewTurn.setText(R.string.enemy_turn);
                }
                mHandler.postDelayed(this, 200);
            }
        }

        public void chageBackground(Integer id){
            tabContent.setBackgroundResource(id);
        }


    };

    private Runnable randomItemRunable = new Runnable() {
        @Override
        public void run() {
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                int turn = InfoMatch.getTurn();
                Log.d("my-debuger", "turn:" + turn);
                if(Bluetooth.getYourTurn() && !InfoMatch.getIsRandomItem() || Integer.parseInt(getIntent().getStringExtra("Mode")) != 1 && !InfoMatch.getIsRandomItem()){
                    if (InfoMatch.getTurn() % 5 == 0) {
                        Random random = new Random();
                        Integer index = random.nextInt(listItemUser.size());
                        Item skill = listItemUser.get(index);
                        imageViewSpell1.setImageResource(getResources().getIdentifier(skill.getImageName(), "drawable", getPackageName()));
                        InfoMatch.setIsRandomItem(true);
                    }
                    // Bluetooth.setDataSending("");
                }
                if (InfoMatch.isIsChangeTurn()){
                    timeCountdown = 60;
                    InfoMatch.setIsChangeTurn(false);
                }
                mHandler.postDelayed(this, 200);
            }

        }
    };

    private Runnable timeCountdownThread = new Runnable() {
        @Override
        public void run() {
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                timeCountdown = timeCountdown - 1;
                textViewTimeCoundoutTurn.setText("" + timeCountdown);
                if(timeCountdown <= 0){
                    InfoMatch.setIsTimeOut(true);
                }
                else{
                    mHandler.postDelayed(this, 1000);
                }
            }
        }
    };
}
