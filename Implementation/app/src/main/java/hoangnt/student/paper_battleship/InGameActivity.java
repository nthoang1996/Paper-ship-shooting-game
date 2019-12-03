package hoangnt.student.paper_battleship;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.ArrayList;

public class InGameActivity extends TabActivity {
    private TabHost tabHost;
    private FrameLayout tabContent;
    TextView textViewTurn;
    ImageView imageViewSpell1, imageViewSpell2, imageViewSpell3;
    TextView textViewTime;
    Button btnSur;
    Button btnMenu;
    Button btnYourMap;
    Button btnEnemyMap;

    private Handler mHandler = new Handler();
    int isRun = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        textViewTurn = findViewById(R.id.textViewTurn);
        imageViewSpell1 = findViewById(R.id.imgSpell1);
        imageViewSpell2 = findViewById(R.id.imgSpell2);
        imageViewSpell3 = findViewById(R.id.imgSpell3);
        btnSur = findViewById(R.id.btnSur);
        btnMenu = findViewById(R.id.btnMenu);
        textViewTime = findViewById(R.id.textViewCountdownTurn);
        btnYourMap = findViewById(R.id.btnYourMap);
        btnEnemyMap = findViewById(R.id.btnEnemyMap);

        btnEnemyMap.setBackgroundColor(Color.BLUE);
        btnYourMap.setBackgroundResource(android.R.drawable.btn_default);

        tabContent = (FrameLayout) findViewById(android.R.id.tabcontent);

        tabHost = getTabHost();
        ArrayList<String> listEnemyData =  getIntent().getStringArrayListExtra("listEnemyShip");
        TabHost.TabSpec EnemySpec = tabHost.newTabSpec("Enemy map");
        EnemySpec.setIndicator("Enemy map");
        Intent enemyIntent = new Intent(this, PlayerMap.class);
        enemyIntent.putStringArrayListExtra("listShip", listEnemyData);
        enemyIntent.putExtra("Map", "Enemy");
        EnemySpec.setContent(enemyIntent);

        ArrayList<String> listData =  getIntent().getStringArrayListExtra("listShip");
        TabHost.TabSpec yourSpec = tabHost.newTabSpec("Your map");
        yourSpec.setIndicator("Your map");
        Intent yourIntent = new Intent();
        yourIntent.setClass(this, PlayerMap.class);
        yourIntent.putStringArrayListExtra("listShip", listData);
        yourIntent.putExtra("Map", "Your");
        yourSpec.setContent(yourIntent);


        tabHost.addTab(EnemySpec);
        tabHost.addTab(yourSpec);

        changeBackgroundRunable.run();

        if(getIntent().getStringExtra("isHost").equals("0")){
            Bluetooth.setYourTurn(false);
        }
        else{
            Bluetooth.setYourTurn(true);
        }
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
}
