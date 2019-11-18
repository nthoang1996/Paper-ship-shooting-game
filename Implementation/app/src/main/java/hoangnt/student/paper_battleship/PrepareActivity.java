package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
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
//    int [][] board = new int[10][5];
//    int lenShip;
//    int rotation;
//    int currentPosition;
//    int start, end;
//    int direction = 5;

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
        arrayBackground= new Integer[50];
        statusMap = new int[50];
        valueMap = new int[50];
        for(int i = 0; i< 50; i++){
            arrayBackground[i]= R.drawable.backgrond_sea;
            statusMap[i] = 0;
            valueMap[i] = 0;
        }
        listShip = new ArrayList<Ship>();

        itemImageAdapter = new AdapterGridViewMap(this, R.layout.map_cell,arrayBackground);
        grv_board.setAdapter(itemImageAdapter);
        initShip();

        btnShip111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuplicatedShip() == false){
                    selectedShip = 1;
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
                    resetSelection();
                    btnShip131.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                }
            }
        });

        btnShip141.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDuplicatedShip() == false){
                    selectedShip = 7;
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
                                    prePos = position + 15;
                                }
                                setShipAtPosition(parent, prePos, shipSelected, 2);
                            }
                            else {
                                nextPost = position - 1;
                                if(nextPost/5 != position/5){
                                    nextPost = position+1;
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

//        grv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
//
//                if(lenShip == 0) return;
//
//                direction = 5;
//
//                if(lenShip == 1){
//                    start = index;
//                    end = index;
//                }
//                else if(lenShip == 2){
//                    start = index - direction;
//                    end = index;
//                }
//                else if(lenShip == 3) {
//                    start = index - direction;
//                    end = index + direction;
//                }else if(lenShip == 4){
//                    start = index - direction*2;
//                    end = index + direction;
//                }
//
//                if(start < 0){
//                    start = start + ((0 - start)/direction + 1)*direction;
//                    end = start + (lenShip - 1)*direction;
//                }
//                if(end > 49){
//                    end = end - ((end - 49)/direction + 1)*direction;
//                    start = end - (lenShip - 1)*direction;
//                }
//
//                for(int i = start; i<=end; i+=direction)
//                {
//                    if(board[i/5][i%5] !=0) return;
//                }
//
////                for(int i = start; i<=end; i+=direction){
////                    itemImageAdapter.getItem(i).setItem(R.drawable.selected);
////                }
//
//
//                itemImageAdapter.notifyDataSetChanged();
//                currentShip.setEnabled(false);
//                rotation = lenShip;
//                currentPosition=index;
//                lenShip = 0;
//
//                //Log.d("ccc", idImage.get(i).toString());
//
//                //Log.d("bbb", "there");
//            }
//        });
//        btnShip111.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lenShip = 1;
//                currentShip = btnShip111;
//            }
//        });
//        btnShip112.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 1;
//                currentShip = btnShip112;
//            }
//        });
//        btnShip113.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 1;
//                currentShip = btnShip113;
//            }
//        });
//        btnShip121.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 2;
//                currentShip = btnShip121;
//            }
//        });
//        btnShip122.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 2;
//                currentShip = btnShip122;
//            }
//        });
//        btnShip131.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 3;
//                currentShip = btnShip131;
//            }
//        });
//        btnShip141.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for(int i = start; i<=end; i+=direction){
//                    board[i/5][i%5] = rotation;
//                }
//
//                lenShip = 4;
//                currentShip = btnShip141;
//            }
//        });
//        btnRotation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int newStart = start;
//                int newEnd = end;
//                int newDirection = 1;
//
//
//                if(rotation == 2){
//                    newStart = currentPosition - newDirection;
//                    newEnd = currentPosition;
//                }else if(rotation == 3)
//                {
//                    newStart = currentPosition -newDirection;
//                    newEnd = currentPosition +newDirection;
//                }else if(rotation == 4){
//                    newStart =currentPosition - newDirection*2;
//                    newEnd=currentPosition+newDirection;
//                }
//
//                int currentRow = currentPosition/5;
//                int nextRow = currentRow + 1;
//
//                if(newStart< currentRow*5){
//                    newStart = currentRow*5;
//                    newEnd = currentRow + rotation - 1;
//                }
//
//                if(newEnd >= nextRow*5 - 1){
//                    newEnd = nextRow*5 - 1;
//                    newStart = newEnd - rotation +1 ;
//                }
//
//                for(int i = newStart; i<=newEnd; i+=newDirection)
//                {
//                    if(board[i/5][i%5] !=0) return;
//                }
//
////                for(int i = start; i<=end; i+=direction){
////                    itemImageAdapter.getItem(i).setItem(R.drawable.backgrond);
////                }
////
////                start = newStart;
////                end=newEnd;
////                direction=newDirection;
////
////
////                for(int i = start; i<=end; i+=direction){
////                    itemImageAdapter.getItem(i).setItem(R.drawable.selected);
////                }
////
////                itemImageAdapter.notifyDataSetChanged();
//
//            }
//        });
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
        btnShip111.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip112.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip113.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip121.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip122.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip131.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnShip141.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
