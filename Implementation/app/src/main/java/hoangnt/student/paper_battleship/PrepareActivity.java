package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class PrepareActivity extends AppCompatActivity {
    Integer[] arrayBackground;
    GridView grv_board;
    Button btnRotation;
    Button btnShip111, btnShip112, btnShip113, btnShip121, btnShip122, btnShip131, btnShip141;
    Button btnResetMap;
    AdapterGridViewMap itemImageAdapter;
    ArrayList<Ship> listShip;
    int selectedShip = -1;
    int previousSelectedShip;
    int[] statusMap;
    int[] valueMap;
    int isPlaced = 0;
    int forceDeleteShip = 0;
    private Handler mHandler = new Handler();
    TextView textViewTimeCountdown;
    int timeCountdown = 60;

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
        textViewTimeCountdown = (TextView) findViewById(R.id.textViewCountdown);
        arrayBackground= new Integer[50];
        statusMap = new int[50];
        valueMap = new int[50];
        for(int i = 0; i< 50; i++){
            arrayBackground[i]= R.drawable.backgrond_sea;
            statusMap[i] = 0;
            valueMap[i] = 0;
        }
        timeCountdownThread.run();
        listShip = new ArrayList<Ship>();

        itemImageAdapter = new AdapterGridViewMap(this, R.layout.map_cell,arrayBackground);
        grv_board.setAdapter(itemImageAdapter);
        initShip();

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

        grv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedShip > 0){
                    Ship shipSelected = listShip.get(selectedShip-1);
                    int type = shipSelected.getType();
                    int nextPost = -1;
                    int prePos = -1;
                    if(previousSelectedShip == selectedShip){
                        deleteOldPosition(shipSelected, parent, position);
                    }
                    if(forceDeleteShip == 0){
                        switch (type){
                            case 1:
                                setShipAtPosition(parent,position, shipSelected, 0);
                                break;
                            case 2: setShipAtPosition(parent,position, shipSelected,0);
                                if(shipSelected.getOrigentation() == 1){
                                    nextPost = position + 5;
                                    if(nextPost >= 50){
                                        nextPost = position - 5;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                }
                                else {
                                    nextPost = position - 1;
                                    if(nextPost/5 != position/5){
                                        nextPost = position+1;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                }
                                break;
                            case 3:
                                setShipAtPosition(parent,position, shipSelected,0);
                                if(shipSelected.getOrigentation()==1){
                                    nextPost = position + 5;
                                    if(nextPost >= 50){
                                        nextPost = position - 10;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                    prePos = position - 5;
                                    if(prePos < 0){
                                        prePos = position + 10;
                                    }
                                    setShipAtPosition(parent, prePos, shipSelected, 2);
                                }
                                else {
                                    nextPost = position - 1;
                                    if(nextPost/5 != position/5){
                                        nextPost = position+2;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                    prePos = position + 1;
                                    if(prePos/5 != position/5){
                                        prePos = position - 2;
                                    }
                                    setShipAtPosition(parent, prePos, shipSelected, 2);
                                }
                                break;
                            case 4:
                                setShipAtPosition(parent,position, shipSelected,0);
                                if(shipSelected.getOrigentation() == 1){
                                    nextPost = position + 5;
                                    if(nextPost >= 50){
                                        nextPost = position - 10;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                    prePos = position - 5;
                                    if(prePos < 0){
                                        prePos = position + 15;
                                    }
                                    setShipAtPosition(parent,prePos, shipSelected,2);
                                    nextPost = position + 10;
                                    if(nextPost >= 55){
                                        nextPost = position - 15;
                                    }
                                    else if(nextPost >= 50){
                                        nextPost = position - 10;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,3);
                                }
                                else {
                                    nextPost = position - 1;
                                    if(nextPost/5 != position/5){
                                        nextPost = position + 2;
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,1);
                                    prePos = position + 1;
                                    if(prePos/ 5 != position/5){
                                        prePos = position - 3;
                                    }
                                    setShipAtPosition(parent,prePos, shipSelected,2);
                                    nextPost = position - 2;
                                    if(nextPost/5 != position/5){
                                        if(nextPost % 5 == 4){
                                            nextPost = position + 2;
                                        }
                                        else {
                                            nextPost = position + 3;
                                        }
                                    }
                                    setShipAtPosition(parent,nextPost, shipSelected,3);
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
                rotateShip(listShip.get(selectedShip-1));
            }
        });

        btnResetMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMap();
            }
        });
    }

    private void resetMap() {
        itemImageAdapter = new AdapterGridViewMap(this, R.layout.map_cell,arrayBackground);
        grv_board.setAdapter(itemImageAdapter);
        initShip();
        for(int i = 0; i< 50; i++){
            statusMap[i] = 0;
            valueMap[i] = 0;
        }
    }

    private Runnable timeCountdownThread = new Runnable() {
        private boolean stop = false;
        @Override
        public void run() {
            timeCountdown = timeCountdown - 1;
            textViewTimeCountdown.setText("" + timeCountdown);
            if(timeCountdown <= 0){
                if(isDuplicatedShip()){
                    Ship shipSelected = listShip.get(selectedShip);
                    forceDeleteShip = 1;
                    grv_board.performItemClick(
                            grv_board.getAdapter().getView(shipSelected.getPosition().get(0), null, null),
                            shipSelected.getPosition().get(0),
                            grv_board.getAdapter().getItemId(shipSelected.getPosition().get(0)));
                }
                stop = true;
            }
            mHandler.postDelayed(this, 1000);
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
        ArrayList<Integer> id_part = new ArrayList<Integer>();
        ArrayList<Integer> position = new ArrayList<Integer>();
        ArrayList<Boolean> staus = new ArrayList<Boolean>();
        position.add(-1);
        staus.add(false);
        id_part.add(1);
        listShip.add(new Ship(id_part, 1, position, staus,1));

        id_part = new ArrayList<Integer>();
        id_part.add(2);
        listShip.add(new Ship(id_part, 1, position, staus,1));
        id_part = new ArrayList<Integer>();
        id_part.add(3);
        listShip.add(new Ship(id_part, 1, position, staus,1));

        position = new ArrayList<Integer>();
        staus = new ArrayList<Boolean>();
        id_part = new ArrayList<Integer>();
        id_part.add(4);
        id_part.add(5);
        position.add(-1);
        position.add(-1);
        staus.add(false);
        staus.add(false);
        listShip.add(new Ship(id_part,1, position, staus,2));
        id_part = new ArrayList<Integer>();
        id_part.add(6);
        id_part.add(7);
        listShip.add(new Ship(id_part, 1, position, staus,2));
        position = new ArrayList<Integer>();
        staus = new ArrayList<Boolean>();
        id_part = new ArrayList<Integer>();
        id_part.add(8);
        id_part.add(9);
        id_part.add(10);
        position.add(-1);
        position.add(-1);
        position.add(-1);
        staus.add(false);
        staus.add(false);
        staus.add(false);
        listShip.add(new Ship(id_part, 1, position, staus,3));
        position = new ArrayList<Integer>();
        staus = new ArrayList<Boolean>();
        id_part = new ArrayList<Integer>();
        id_part.add(11);
        id_part.add(12);
        id_part.add(13);
        id_part.add(14);
        position.add(-1);
        position.add(-1);
        position.add(-1);
        position.add(-1);
        staus.add(false);
        staus.add(false);
        staus.add(false);
        staus.add(false);
        listShip.add(new Ship(id_part,1, position, staus,4));

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
                case 2 : btnShip121.setEnabled(false);
                    break;
                case 3 : btnShip131.setEnabled(false);
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

    public void setShipAtPosition(AdapterView<?> parent, int position, Ship ship, int index){
        View childView=  (View)parent.getChildAt(position);
        TextView textView = childView.findViewById(R.id.cell_grid);
        textView.setBackgroundResource(R.drawable.ship_1);
        ship.getPosition().set(index, position);
        ship.getStatus().set(index, true);
        statusMap[position] +=1;
        valueMap[position] += ship.getId_part().get(index);
    }

    public void deleteShipAtOldPosition(AdapterView<?> parent, int position, Ship ship, int index){
        View childView=  (View)parent.getChildAt(position);
        TextView textView = childView.findViewById(R.id.cell_grid);
        textView.setBackgroundResource(R.drawable.backgrond_sea);
        ship.getPosition().set(index, position);
        statusMap[position] = 0;
        valueMap[position] -= ship.getId_part().get(index);
        if(valueMap[position] > 0){
            setShipAtPosition(parent, position, ship, index);
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

    public void deleteOldPosition(Ship ship, AdapterView<?> parent, int position){
        ArrayList<Integer> oldPosition = ship.getPosition();
        for (int i = 0; i<oldPosition.size(); i++)
        {
            deleteShipAtOldPosition(parent, oldPosition.get(i), ship, i);
        }
    }
}
