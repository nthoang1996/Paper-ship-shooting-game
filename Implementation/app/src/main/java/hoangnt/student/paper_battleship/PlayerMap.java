package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class PlayerMap extends AppCompatActivity {
    GridView myGridView;
    AdapterGridViewMap adapter;
    private Handler mHandler = new Handler();
    int isRun = 1;
    int[] statusMap;
    int[] selectedMap;
    Ship [] listShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_map);

        statusMap = new int[50];
        selectedMap = new int[50];
        for(int i =0 ; i< 50; i++){
            statusMap[i] = 0;
            selectedMap[i] = 0;
        }
        final int init=1;

        ArrayList<String> listData =  getIntent().getStringArrayListExtra("listShip");
        listShip = new Ship[7];
        if(listData != null){
            listShip = new Helper(getApplication()).parseListStringToListShipObject(listData);
        }

        for(int i =0 ; i< listShip.length; i++){
            for(int j=0; j< listShip[i].getPosition().size(); j++){
                if(listShip[i].getPosition().get(j) >= 0){
                    statusMap[listShip[i].getPosition().get(j)] = listShip[i].getId_part().get(j);
                }
            }
        }

        myGridView = findViewById(R.id.gridview_BoardGame);
        int idMap;
        if(getIntent().getStringExtra("Map").equals("Enemy")){
            idMap = 0;
        }
        else {
            idMap = 1;
        }
        adapter = new AdapterGridViewMap(this, R.layout.map_cell, listShip, statusMap, idMap);
        myGridView.setAdapter(adapter);

        final Ship[] finalListShip = listShip;
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getIntent().getStringExtra("Map").equals("Enemy") && Bluetooth.getYourTurn() && selectedMap[position] != 1){
                    Bluetooth.setYourTurn(false);
                    Log.d("my-debuger", String.valueOf(position));
                    String command = ""+ position;
                    Bluetooth.getBluetoothConnection().write(command.getBytes(Charset.defaultCharset()));
                    View childView=  (View)parent.getChildAt(position);
                    TextView textView = childView.findViewById(R.id.cell_grid);
                    statusMap[position] += 50;
                    textView.setBackgroundResource(R.drawable.x);
                    selectedMap[position] = 1;
                    isShootToShip(finalListShip, position);
                    checkAnyShipDestroyed(finalListShip);
                    if(checkFinish(listShip, 1) == 1){
                        Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                        intent.putExtra("context", "1");
                        intent.putExtra("exp", caculatorExp(listShip));
                        startActivity(intent);
                    }
                }
            }
        });
        listenerRunable.run();
    }

    public void isShootToShip(Ship[] listShip, int position){
        for(int i =0 ; i< listShip.length; i++) {
            for (int j = 0; j < listShip[i].getPosition().size(); j++) {
                if(position == listShip[i].getPosition().get(j)){
                    listShip[i].getStatus().set(j, false);
                }
            }
        }
    }

    public void checkAnyShipDestroyed(Ship[] listShip){
        for(int i =0 ; i< listShip.length; i++) {
            int count = 0;
            for (int j = 0; j < listShip[i].getPosition().size(); j++) {
                if(!listShip[i].getStatus().get(j)){
                    count +=1;
                }
            }
            if(count== listShip[i].getPosition().size()){
                showShip(listShip[i]);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void showShip(Ship ship){
        for (int i = 0; i<ship.getPosition().size(); i++){
            Log.d("my-debuger", "" + ship.getPosition().get(i));
            if(statusMap[ship.getPosition().get(i)]<100){
                statusMap[ship.getPosition().get(i)] += 50;
            }

        }
    }

    public int checkFinish(Ship[] listShip, int context){
        for(int i = 0; i<listShip.length; i++){
            for(int j = 0; j < listShip[i].getPosition().size(); j++){
                if(listShip[i].getPosition().get(j) > 0 ){
                    if(listShip[i].getStatus().get(j)){
                        return 0;
                    }
                }
            }
        }
        if(context == 2){
            return 2;
        }
        else {
            return 1;
        }
    }

    public int caculatorExp(Ship[] listShip){
        int exp = 0;
        for(int i =0;i <listShip.length; i++){
            for(int j = 0; j< listShip[i].getStatus().size(); j++){
                if(listShip[i].getStatus().get(j)){
                    switch (listShip[i].getType()){
                        case 1: exp += 10;
                        break;
                        case 2: exp +=15;
                        break;
                        case 3: exp += 20;
                        break;
                        case 4: exp+= 25;
                        break;
                    }
                }
            }
        }
        return exp;
    }

    private Runnable listenerRunable = new Runnable() {
        @Override
        public void run() {
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                if(!Bluetooth.getDataSending().isEmpty()){
                    Log.d("My-debuger", "This is your turn");
                    Bluetooth.setYourTurn(true);
                    if(getIntent().getStringExtra("Map").equals("Your")){
                        Log.d("my-debuger", Bluetooth.getDataSending());
                        statusMap[Integer.parseInt(Bluetooth.getDataSending())] += 11;
                        isShootToShip(listShip, Integer.parseInt(Bluetooth.getDataSending()));
                        checkAnyShipDestroyed(listShip);
                        adapter.notifyDataSetChanged();
                        if(checkFinish(listShip, 2) == 2){
                            Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                            intent.putExtra("context", "2");
                            intent.putExtra("exp", "" + caculatorExp(listShip));
                            startActivity(intent);
                        }
                        Bluetooth.setDataSending("");
                    }
                }
                mHandler.postDelayed(this, 200);
            }
        }
    };
}
