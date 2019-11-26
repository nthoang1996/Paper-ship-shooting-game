package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerMap extends AppCompatActivity {
    GridView myGridView;
    AdapterGridViewMap adapter;

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

            }
        });
//        for (int i =0 ; i<listShip.size(); i++){
//            listShip.get(i).setPositionShip(myGridView, getApplication(), statusMap);
//        }

    }
}
