package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class InGameActivity extends TabActivity {
    private ViewPager viewPager;
    private TabHost tabHost;
    private FrameLayout tabContent;
    TextView textViewTurn;
    ImageView imageViewSpell1, imageViewSpell2, imageViewSpell3;
    TextView textViewTime;
    Button btnSur;
    Button btnMenu;
    Button btnYourMap;
    Button btnEnemyMap;
    Button artistButton, songButton, videosButton;

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

        tabContent = (FrameLayout) findViewById(android.R.id.tabcontent);

        tabHost = getTabHost();
        TabHost.TabSpec EnemySpec = tabHost.newTabSpec("Enemy map");
        EnemySpec.setIndicator("Enemy map");
        Intent EnemyIntent = new Intent(this, PlayerMap.class);
        EnemySpec.setContent(EnemyIntent);

        ArrayList<String> listData =  getIntent().getStringArrayListExtra("listShip");
        TabHost.TabSpec yourSpec = tabHost.newTabSpec("Your map");
        yourSpec.setIndicator("Your map");
        Intent yourIntent = new Intent();
        yourIntent.setClass(this, PlayerMap.class);
        yourIntent.putStringArrayListExtra("listShip", listData);
        yourSpec.setContent(yourIntent);


        tabHost.addTab(EnemySpec);
        tabHost.addTab(yourSpec);

        changeBackgroundRunable.run();
    }

    public void tabHandler(View target) {
        btnEnemyMap.setSelected(false);
        btnYourMap.setSelected(false);
        if (target.getId() == R.id.btnEnemyMap) {
            tabHost.setCurrentTab(0);
            btnEnemyMap.setSelected(true);
        } else if (target.getId() == R.id.btnYourMap) {
            tabHost.setCurrentTab(1);
            btnYourMap.setSelected(true);
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
                mHandler.postDelayed(this, 200);
            }
        }

        public void chageBackground(Integer id){
            tabContent.setBackgroundResource(id);
        }

    };
}
