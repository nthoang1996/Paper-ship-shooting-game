package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class PlayerMap extends AppCompatActivity {
    GridView myGridView;
    AdapterGridViewMap adapter;
    private Handler mHandler = new Handler();
    int isRun = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_map);

        final int[] statusMap = new int[50];
        for(int i =0 ; i< 50; i++){
            statusMap[i] = 0;
        }
        final int init=1;

        ArrayList<String> listData =  getIntent().getStringArrayListExtra("listShip");
        Ship [] listShip = new Ship[7];
        if(listData != null){
            listShip = new Helper(getApplication()).parseListStringToListShipObject(listData);
        }

        myGridView = findViewById(R.id.gridview_BoardGame);
        adapter = new AdapterGridViewMap(this, R.layout.map_cell, listShip);
        myGridView.setAdapter(adapter);

        final Ship[] finalListShip = listShip;
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getIntent().getStringExtra("Map").equals("Enemy") && Bluetooth.getYourTurn()){
                    Log.d("my-debuger", "" + position);
                }
            }
        });

    }

//    private Runnable changeBackgroundRunable = new Runnable() {
//        @Override
//        public void run() {
//            if(isRun == 0){
//                mHandler.removeCallbacks(this);
//            }
//            else {
//                if(Bluetooth.getYourTurn()){
//                    myGridView.setEnabled(true);
//                }
//                else{
//                    myGridView.setEnabled(false);
//                }
//                mHandler.postDelayed(this, 200);
//            }
//        }
//    };
}
