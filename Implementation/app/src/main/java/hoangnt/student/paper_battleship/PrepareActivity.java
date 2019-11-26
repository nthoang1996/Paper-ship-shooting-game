package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class PrepareActivity extends AppCompatActivity implements Serializable {
    GridView grv_board;
    Button btnRotation;
    Button btnShip111, btnShip112, btnShip113, btnShip121, btnShip122, btnShip131, btnShip141;
    Button btnResetMap;
    Button btnReady;
    AdapterGridViewMap itemImageAdapter;
    Ship[] listShip;
    int selectedShip = -1;
    int previousSelectedShip;
    int isPlaced = 0;
    int forceDeleteShip = 0;
    private Handler mHandler = new Handler();
    int isRun = 1;
    TextView textViewTimeCountdown;
    int timeCountdown = 60;
    LinearLayout layoutMap;
    int[] statusMap;
    BluetoothConnectionService mBluetoothConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        grv_board = (GridView) findViewById(R.id.gridview_Board);
        btnRotation = (Button)  findViewById(R.id.btn_Rotation);
        btnResetMap = (Button) findViewById(R.id.btn_ResetLocation);
        btnShip111 = (Button)  findViewById(R.id.btn_Ship1x1_1);
        btnShip112 = (Button)  findViewById(R.id.btn_Ship1x1_2);
        btnShip113 = (Button)  findViewById(R.id.btn_Ship1x1_3);
        btnShip121 = (Button)  findViewById(R.id.btn_Ship1x2_1);
        btnShip122 = (Button)  findViewById(R.id.btn_Ship1x2_2);
        btnShip131 = (Button)  findViewById(R.id.btn_Ship1x3);
        btnShip141 = (Button)  findViewById(R.id.btn_Ship1x4);
        btnReady = (Button) findViewById(R.id.btnReady);
        layoutMap = findViewById(R.id.layoutMap);
        mBluetoothConnection = (BluetoothConnectionService) getIntent().getSerializableExtra("myBT");

        textViewTimeCountdown = (TextView) findViewById(R.id.textViewCountdown);
        statusMap = new int[50];
        final int[] valueMap = new int[50];
        for(int i = 0; i< 50; i++){
            statusMap[i] = 0;
            valueMap[i] = 0;
        }
        timeCountdownThread.run();
        listShip = new Ship[7];

        itemImageAdapter = new AdapterGridViewMap(this, R.layout.map_cell, listShip);
        grv_board.setAdapter(itemImageAdapter);
        initShip();
        grv_board.setBackgroundColor(Color.TRANSPARENT);
        changeBackgroundRunable.run();

        btnShip111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip() == false){
                    selectedShip = 1;
                    disableShipPlaced();
                    resetSelection();
                    btnShip111.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip() == false){
                    selectedShip = 2;
                    disableShipPlaced();
                    resetSelection();
                    btnShip112.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip113.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip()==false){
                    selectedShip = 3;
                    disableShipPlaced();
                    resetSelection();
                    btnShip113.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip() == false){
                    selectedShip = 4;
                    disableShipPlaced();
                    resetSelection();
                    btnShip121.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip122.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip() == false){
                    selectedShip = 5;
                    disableShipPlaced();
                    resetSelection();
                    btnShip122.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip131.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip()== false){
                    selectedShip = 6;
                    disableShipPlaced();
                    resetSelection();
                    btnShip131.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip141.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnable = btnShip141.isEnabled();
                if (isDuplicatedShip() == false){
                    selectedShip = 7;
                    disableShipPlaced();
                    resetSelection();
                    btnShip141.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listData = new ArrayList<String>();
                for (int i=0; i<listShip.length; i++){
                    listData.add(listShip[i].toString());
                }
                isRun = 0;
                Intent intent = new Intent(PrepareActivity.this, InGameActivity.class);
                intent.putExtra("listShip", listData);
                startActivity(intent);
            }
        });

        grv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedShip > 0){
                    Ship shipSelected = listShip[selectedShip-1];
                    int type = shipSelected.getType();
                    int nextPost = -1;
                    int prePos = -1;
                    int semiNextPost = -1;
                    if(previousSelectedShip == selectedShip){
                        deleteOldPosition(parent, position, valueMap, selectedShip);
                    }
                    if(forceDeleteShip == 0){
                        switch (type){
                            case 1:
                                setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                break;
                            case 2:
                                if(shipSelected.getOrigentation() == 1){
                                    nextPost = position + 5;
                                    if(nextPost >= 50){
                                        nextPost = position - 5;
                                        shipSelected.setId_part(1,2);
                                        shipSelected.setId_part(0,3);
                                    }
                                    else {
                                        shipSelected.setId_part(0,2);
                                        shipSelected.setId_part(1,3);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                }
                                else {
                                    nextPost = position - 1;
                                    if(nextPost/5 != position/5 || nextPost == -1){
                                        nextPost = position+1;
                                        shipSelected.setId_part(1,2);
                                        shipSelected.setId_part(0,3);
                                    }
                                    else {
                                        shipSelected.setId_part(0,2);
                                        shipSelected.setId_part(1,3);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                }
                                break;
                            case 3:
                                if(shipSelected.getOrigentation()==1){
                                    nextPost = position + 5;
                                    prePos = position - 5;
                                    if(nextPost >= 50){
                                        nextPost = position - 10;
                                        shipSelected.setId_part(0,6);
                                        shipSelected.setId_part(1,4);
                                        shipSelected.setId_part(2,5);
                                    }
                                    else if(prePos < 0){
                                        prePos = position + 10;
                                        shipSelected.setId_part(0,4);
                                        shipSelected.setId_part(1,5);
                                        shipSelected.setId_part(2,6);
                                    }
                                    else {
                                        shipSelected.setId_part(0,5);
                                        shipSelected.setId_part(1,6);
                                        shipSelected.setId_part(2,4);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                    setShipAtPosition(parent, prePos, 2, shipSelected.getId_part().get(2), false, valueMap, selectedShip);
                                }
                                else {
                                    nextPost = position - 1;
                                    prePos = position + 1;
                                    if(nextPost/5 != position/5 || nextPost == -1){
                                        nextPost = position+2;
                                        shipSelected.setId_part(0,6);
                                        shipSelected.setId_part(1,4);
                                        shipSelected.setId_part(2,5);

                                    }
                                    else if(prePos/5 != position/5){
                                        prePos = position - 2;
                                        shipSelected.setId_part(0,4);
                                        shipSelected.setId_part(1,5);
                                        shipSelected.setId_part(2,6);
                                    }
                                    else {
                                        shipSelected.setId_part(0,5);
                                        shipSelected.setId_part(1,6);
                                        shipSelected.setId_part(2,4);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                    setShipAtPosition(parent, prePos, 2, shipSelected.getId_part().get(2), false, valueMap, selectedShip);
                                }
                                break;
                            case 4:
                                if(shipSelected.getOrigentation() == 1){
                                    nextPost = position + 10;
                                    prePos = position - 5;
                                    semiNextPost = position + 5;
                                    if(nextPost >=55){
                                        nextPost = position - 15;
                                        semiNextPost = position - 10;
                                        shipSelected.setId_part(0,10);
                                        shipSelected.setId_part(1,8);
                                        shipSelected.setId_part(2,9);
                                        shipSelected.setId_part(3,7);
                                    }
                                    else if(nextPost >= 50){
                                        nextPost = position - 10;
                                        shipSelected.setId_part(0,9);
                                        shipSelected.setId_part(1,10);
                                        shipSelected.setId_part(2,8);
                                        shipSelected.setId_part(3,7);
                                    }
                                    else if(prePos < 0){
                                        prePos = position + 15;
                                        shipSelected.setId_part(0,7);
                                        shipSelected.setId_part(1,8);
                                        shipSelected.setId_part(2,10);
                                        shipSelected.setId_part(3,9);
                                    }
                                    else {
                                        shipSelected.setId_part(0,8);
                                        shipSelected.setId_part(1,9);
                                        shipSelected.setId_part(2,7);
                                        shipSelected.setId_part(3,10);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,semiNextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,prePos,2, shipSelected.getId_part().get(2), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,3, shipSelected.getId_part().get(3), false, valueMap, selectedShip);

                                }
                                else {
                                    nextPost = position - 2;
                                    prePos = position +1;
                                    semiNextPost = position - 1;
                                    if(nextPost/5 != position/5 || nextPost < 0){
                                        if(nextPost % 5 == 3 || nextPost == -2){
                                            nextPost = position + 3;
                                            semiNextPost = position + 2;
                                            shipSelected.setId_part(0,10);
                                            shipSelected.setId_part(1,8);
                                            shipSelected.setId_part(2,9);
                                            shipSelected.setId_part(3,7);

                                        }
                                        else if(nextPost % 5 == 4 || nextPost == -1){
                                            nextPost = position + 2;
                                            shipSelected.setId_part(0,9);
                                            shipSelected.setId_part(1,10);
                                            shipSelected.setId_part(2,8);
                                            shipSelected.setId_part(3,7);
                                        }
                                    }
                                    else if(prePos/5 != position /5){
                                        prePos = position - 3;
                                        shipSelected.setId_part(0,7);
                                        shipSelected.setId_part(1,8);
                                        shipSelected.setId_part(2,10);
                                        shipSelected.setId_part(3,9);
                                    }
                                    else {
                                        shipSelected.setId_part(0,8);
                                        shipSelected.setId_part(1,9);
                                        shipSelected.setId_part(2,7);
                                        shipSelected.setId_part(3,10);
                                    }
                                    setShipAtPosition(parent,position,0, shipSelected.getId_part().get(0), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,semiNextPost,1, shipSelected.getId_part().get(1), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,prePos,2, shipSelected.getId_part().get(2), false, valueMap, selectedShip);
                                    setShipAtPosition(parent,nextPost,3, shipSelected.getId_part().get(3), false, valueMap, selectedShip);
                                }
                                break;
                        }
                        previousSelectedShip = selectedShip;
                        isPlaced = 1;
                    }
                }
            }
        });

        btnRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaced == 1){
                    rotateShip(listShip[selectedShip-1]);
                }
            }
        });

        btnResetMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMap(valueMap);
            }
        });
    }



    private void resetMap(int[] valueMap) {
        selectedShip = 0;
        previousSelectedShip = -1;
        btnShip111.setEnabled(true);
        btnShip112.setEnabled(true);
        btnShip113.setEnabled(true);
        btnShip121.setEnabled(true);
        btnShip122.setEnabled(true);
        btnShip131.setEnabled(true);
        btnShip141.setEnabled(true);
        resetSelection();
        initShip();
        itemImageAdapter = new AdapterGridViewMap(this, R.layout.map_cell, listShip);
        grv_board.setAdapter(itemImageAdapter);
        for(int i = 0; i< 50; i++){
            statusMap[i] = 0;
            valueMap[i] = 0;
        }
    }

    private Runnable timeCountdownThread = new Runnable() {
        @Override
        public void run() {
            timeCountdown = timeCountdown - 1;
            textViewTimeCountdown.setText("" + timeCountdown);
            if(timeCountdown <= 0){
                if(isDuplicatedShip()){
                    Ship shipSelected = listShip[selectedShip];
                    forceDeleteShip = 1;
                    grv_board.performItemClick(
                            grv_board.getAdapter().getView(shipSelected.getPosition().get(0), null, null),
                            shipSelected.getPosition().get(0),
                            grv_board.getAdapter().getItemId(shipSelected.getPosition().get(0)));
                }
                mHandler.removeCallbacks(this);
            }
            else {
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    private void rotateShip(Ship selectedShip) {
        if(selectedShip.getOrigentation() == 1){
            selectedShip.setOrigentation(2);
        }
        else{
            selectedShip.setOrigentation(1);
        }
        grv_board.performItemClick(
                grv_board.getAdapter().getView(selectedShip.getPosition().get(0), null, null),
                selectedShip.getPosition().get(0),
                grv_board.getAdapter().getItemId(selectedShip.getPosition().get(0)));
    }

    public void initShip(){
        listShip = new Ship[7];
        ArrayList<Integer> id_part = new ArrayList<Integer>();
        ArrayList<Integer> position = new ArrayList<Integer>();
        ArrayList<Boolean> status = new ArrayList<Boolean>();
        id_part.add(1);
        position.add(-10);
        status.add(true);
        listShip[0] = new Ship(id_part, 1, position, status,1);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(1);
        position.add(-10);
        status.add(true);
        listShip[1] = new Ship(id_part, 1, position, status,1);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(1);
        position.add(-10);
        status.add(true);
        listShip[2] = new Ship(id_part, 1, position, status,1);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(2);
        position.add(-10);
        status.add(true);
        id_part.add(3);
        position.add(-10);
        status.add(true);
        listShip[3] = new Ship(id_part,1, position, status,2);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(2);
        position.add(-10);
        status.add(true);
        id_part.add(3);
        position.add(-10);
        status.add(true);
        listShip[4] = new Ship(id_part, 1, position, status,2);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(4);
        position.add(-10);
        status.add(true);
        id_part.add(5);
        position.add(-10);
        status.add(true);
        id_part.add(6);
        position.add(-10);
        status.add(true);
        listShip[5] = new Ship(id_part, 1, position, status,3);
        id_part = new ArrayList<Integer>();
        position = new ArrayList<Integer>();
        status = new ArrayList<Boolean>();
        id_part.add(7);
        position.add(-10);
        status.add(true);
        id_part.add(8);
        position.add(-10);
        status.add(true);
        id_part.add(9);
        position.add(-10);
        status.add(true);
        id_part.add(10);
        position.add(-10);
        status.add(true);

        listShip[6] = new Ship(id_part,1, position, status,4);

    }

    public void resetSelection(){
        if(!btnShip111.isEnabled()){
            btnShip111.setBackgroundColor(Color.RED);
        }
        else {
            btnShip111.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip112.isEnabled()){
            btnShip112.setBackgroundColor(Color.RED);
        }
        else {
            btnShip112.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip113.isEnabled()){
            btnShip113.setBackgroundColor(Color.RED);
        }
        else {
            btnShip113.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip121.isEnabled()){
            btnShip121.setBackgroundColor(Color.RED);
        }
        else {
            btnShip121.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip122.isEnabled()){
            btnShip122.setBackgroundColor(Color.RED);
        }
        else {
            btnShip122.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip131.isEnabled()){
            btnShip131.setBackgroundColor(Color.RED);
        }
        else {
            btnShip131.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if(!btnShip141.isEnabled()){
            btnShip141.setBackgroundColor(Color.RED);
        }
        else {
            btnShip141.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void disableShipPlaced(){
        if(isPlaced == 1 && previousSelectedShip > 0){
            switch (previousSelectedShip){
                case 1 : btnShip111.setEnabled(false);
                    break;
                case 2 : btnShip112.setEnabled(false);
                    break;
                case 3 : btnShip113.setEnabled(false);
                    break;
                case 4 : btnShip121.setEnabled(false);
                    break;
                case 5 : btnShip122.setEnabled(false);
                    break;
                case 6 : btnShip131.setEnabled(false);
                    break;
                case 7 : btnShip141.setEnabled(false);
                    break;
            }
            isPlaced = 0;
        }
    }

    public void setShipAtPosition(AdapterView<?> parent, int position, int index, int value, boolean isRecover, int[] valueMap, int selectedShip){
        View childView=  (View)parent.getChildAt(position);
        TextView textView = childView.findViewById(R.id.cell_grid);
        new Helper(getApplication()).drawShip(textView, value);
        listShip[selectedShip-1].getPosition().set(index, position);
        listShip[selectedShip-1].getStatus().set(index, true);
        statusMap[position] +=1;
        if(isRecover == false){
            valueMap[position] += listShip[selectedShip-1].getId_part().get(index);
        }
    }

    public void deleteShipAtOldPosition(AdapterView<?> parent, int position, int index, int[] valueMap, int selectedShip){
        View childView=  (View)parent.getChildAt(position);
        TextView textView = childView.findViewById(R.id.cell_grid);
        textView.setBackgroundColor(Color.TRANSPARENT);
        listShip[selectedShip-1].getPosition().set(index, position);
        statusMap[position] = 0;
        valueMap[position] -= listShip[selectedShip-1].getId_part().get(index);
        if(valueMap[position] > 0){
            setShipAtPosition(parent, position, index, valueMap[position], true, valueMap, selectedShip);
        }
    }

    public boolean isDuplicatedShip()
    {
        for(int i = 0;i<50;i++){
            if(statusMap[i] == 2){
                return true;
            }
        }
        return false;
    }

    public void deleteOldPosition(AdapterView<?> parent, int position, int[] valueMap, int selectedShip){
        ArrayList<Integer> oldPosition = listShip[selectedShip-1].getPosition();
        for (int i = 0; i<oldPosition.size(); i++)
        {
            deleteShipAtOldPosition(parent, oldPosition.get(i), i, valueMap, selectedShip);
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
            grv_board.setBackgroundResource(id);
        }

    };
}
