package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class PlayerMap extends AppCompatActivity {
    GridView myGridView;
    AdapterGridViewMap adapter;
    private Handler mHandler = new Handler();
    int isRun = 1;
    int[] statusMap;
    int[] selectedMap;
    Ship [] listShip;
    ArrayList<String> listPosition = new ArrayList<String>();
    AnimationDrawable shootAnimation;

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

        for(int i = 0; i<50 ; i++){
            listPosition.add("" + i);
        }

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
            InfoMatch.setListEnemyShip(listShip);
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
                    InfoMatch.setIsRandomItem(false);
                    InfoMatch.setIsChangeTurn(true);
                    InfoMatch.setTurn(InfoMatch.getTurn() + 1);
                    String command = ""+ position;
                    if(Integer.parseInt(getIntent().getStringExtra("Mode")) == 1){
                        Bluetooth.getBluetoothConnection().write(command.getBytes(Charset.defaultCharset()));
                    }
                    else {

                    }
                    View childView=  (View)parent.getChildAt(position);
                    ImageView img = childView.findViewById(R.id.cell_grid);
                    img.setBackgroundResource(R.drawable.shooted);
                    shootAnimation = (AnimationDrawable) img.getDrawable();
                    if(statusMap[position] >=1 && statusMap[position] <=10 || statusMap[position] >= 21 && statusMap[position] <=30){
                        statusMap[position] += 50;
                        img.setBackgroundResource(R.drawable.x);
                    }
                    else {
                        statusMap[position] += 20;
                        img.setBackgroundResource(R.drawable.shooted);
                    }
                    selectedMap[position] = 1;
                    isShootToShip(finalListShip, position, 0);
                    checkAnyShipDestroyed(finalListShip);

                    if(checkFinish(listShip, 1) == 1){
                        Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                        intent.putExtra("context", "1");
                        intent.putExtra("exp", "" + caculatorExp(listShip));
                        startActivity(intent);
                    }
                }
            }
        });
        listenerRunable.run();
//        listenerTimeoutRunable.run();
    }

    public void isShootToShip(Ship[] listShip, int position, int idMap){
        for(int i =0 ; i< listShip.length; i++) {
            for (int j = 0; j < listShip[i].getPosition().size(); j++) {
                if(position == listShip[i].getPosition().get(j)){
                    listShip[i].getStatus().set(j, false);
                }
                if(idMap == 0){
                    InfoMatch.setListEnemyShip(listShip);
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
                if(listShip[i].getPosition().get(j) >= 0 ){
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
                if(listShip[i].getPosition().get(j) >= 0 ){
                    if(!listShip[i].getStatus().get(j)){
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
                    Bluetooth.setYourTurn(true);
                    InfoMatch.setIsChangeTurn(true);
                    if(getIntent().getStringExtra("Map").equals("Your")){
                        if(statusMap[Integer.parseInt(Bluetooth.getDataSending())] >=1 && statusMap[Integer.parseInt(Bluetooth.getDataSending())] <=10 || statusMap[Integer.parseInt(Bluetooth.getDataSending())] >= 21 && statusMap[Integer.parseInt(Bluetooth.getDataSending())] <=30){
                            statusMap[Integer.parseInt(Bluetooth.getDataSending())] += 61;
                        }
                        else {
                            statusMap[Integer.parseInt(Bluetooth.getDataSending())] += 20;
                        }
                        isShootToShip(listShip, Integer.parseInt(Bluetooth.getDataSending()), 1);
                        checkAnyShipDestroyed(listShip);
                        adapter.notifyDataSetChanged();
                        if(checkFinish(listShip, 2) == 2){
                            Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                            intent.putExtra("context", "2");
                            intent.putExtra("exp", "" + caculatorExp(InfoMatch.getListEnemyShip()));
                            startActivity(intent);
                        }
                        Bluetooth.setDataSending("");
                    }
                }
                else if(!Bluetooth.getYourTurn()){
                    Log.d("my-debuger", "Enter thread");
                    if(Integer.parseInt(getIntent().getStringExtra("Mode")) != 1){
                        Bluetooth.setYourTurn(true);
                        if(getIntent().getStringExtra("Map").equals("Your")){
                            int position = findNextShootPosition(listPosition, statusMap);
                            if(statusMap[position] >=1 && statusMap[position] <=10 || statusMap[position] >= 21 && statusMap[position] <=30){
                                statusMap[position] += 61;
                            }
                            else {
                                statusMap[position] += 20;
                            }
                            isShootToShip(listShip, position, 1);
                            checkAnyShipDestroyed(listShip);
                            adapter.notifyDataSetChanged();
                            listPosition.remove("" + position);
                            if(checkFinish(listShip, 2) == 2){
                                Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                                intent.putExtra("context", "2");
                                intent.putExtra("exp", "" + caculatorExp(InfoMatch.getListEnemyShip()));
                                startActivity(intent);
                            }
                            Log.d("my-debuger", "sum");
                        }
                    }
                }
                mHandler.postDelayed(this, 200);
            }
        }
    };

    public int findNextShootPosition(ArrayList<String> listPosition, int[] statusMap){
        for(int i = 0; i < statusMap.length; i++){
            if(statusMap[i] > 62 && statusMap[i] <= 71 || statusMap[i] > 82 && statusMap[i] <=91) {
                if(i-5 >=0){
                    if (statusMap[i - 5] > 1 && statusMap[i - 5] <= 10 || statusMap[i - 5] > 21 && statusMap[i - 5] <= 30) {
                        int position = i-5;
                        while (position >= 0) {
                            if (statusMap[position ] > 1 && statusMap[position] <= 10 || statusMap[position] > 21 && statusMap[position] <= 30) {
                                return position ;
                            }
                            if (statusMap[position] == 0) {
                                break;
                            }
                            position = position - 5;
                        }
                    }
                }
                if(i+5<50){
                    if (statusMap[i + 5] > 1 && statusMap[i + 5] <= 10 || statusMap[i + 5] > 21 && statusMap[i + 5] <= 30) {
                        int position = i + 5;
                        while (position < 50) {
                            if (statusMap[position] > 1 && statusMap[position] <= 10 || statusMap[position] > 21 && statusMap[position] <= 30) {
                                return position ;
                            }
                            if (statusMap[position] == 0) {
                                break;
                            }
                            position = position + 5;
                        }
                    }
                }
                if (statusMap[i - 1] > 1 && statusMap[i - 1] <= 10 || statusMap[i - 1] > 21 && statusMap[i - 1] <= 30) {
                    int position = i - 1;
                    while (position % 5 != 4) {
                        if (statusMap[position ] > 1 && statusMap[position ] <= 10 || statusMap[position] > 21 && statusMap[position ] <= 30) {
                            return position ;
                        }
                        if (statusMap[position ] == 0) {
                            break;
                        }
                        position = position - 1;
                    }
                }
                if (statusMap[i + 1] > 1 && statusMap[i + 1] <= 10 || statusMap[i + 1] > 21 && statusMap[i + 1] <= 30) {
                    int position = i + 1;
                    while (position % 5  != 0) {
                        if (statusMap[position] > 1 && statusMap[position] <= 10 || statusMap[position] > 21 && statusMap[position] <= 30) {
                            return position;
                        }
                        if (statusMap[position] == 0) {
                            break;
                        }
                        position = position + 1;
                    }
                }
            }
        }
        Random rand = new Random();
        return Integer.parseInt(listPosition.get(rand.nextInt(listPosition.size()))) ;
    }

    public int findNextShootPosition(int[] statusMap){
        for(int i =0; i<statusMap.length; i++){
            if(statusMap[i]!= 20 && (statusMap[i] < 50 || statusMap[i] > 59) && (statusMap[i] < 70 || statusMap[i] > 79) && (statusMap[i] <101 || statusMap[i] >110) && (statusMap[i] < 121 || statusMap[i] > 130)){
                return i;
            }
        }
        return -1;
    }

    private Runnable listenerTimeoutRunable = new Runnable() {
        @Override
        public void run() {
            if(isRun == 0){
                mHandler.removeCallbacks(this);
            }
            else {
                if(InfoMatch.isIsTimeOut()){
                    InfoMatch.setIsTimeOut(false);
                    Bluetooth.setYourTurn(false);
                    InfoMatch.setIsRandomItem(false);
                    InfoMatch.setIsChangeTurn(true);
                    InfoMatch.setTurn(InfoMatch.getTurn() + 1);
                    int position = findNextShootPosition(statusMap);
                    String command = ""+position;
                    Bluetooth.getBluetoothConnection().write(command.getBytes(Charset.defaultCharset()));
                    selectedMap[position] = 1;
                    isShootToShip(listShip, position, 0);
                    checkAnyShipDestroyed(listShip);

                    if(checkFinish(listShip, 1) == 1){
                        Intent intent = new Intent(PlayerMap.this, ResultActivity.class);
                        intent.putExtra("context", "1");
                        intent.putExtra("exp", "" + caculatorExp(listShip));
                        startActivity(intent);
                    }
                }
                mHandler.postDelayed(this, 1000);
            }
        }
    };
}
