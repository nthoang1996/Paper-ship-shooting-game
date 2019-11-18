package com.example.preparescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> idImage;
    GridView grv_board;
    Button currentShip;
    Button btnRotation;
    Button btnShip111, btnShip112, btnShip113, btnShip121, btnShip122, btnShip131, btnShip141;
    ItemImageAdapter itemImageAdapter;
    int [][] board = new int[10][5];
    int lenShip;
    int rotation;
    int currentPosition;
    int start, end;
    int direction = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grv_board = (GridView) findViewById(R.id.gridview_Board);
        btnRotation = (Button)  findViewById(R.id.btn_Rotation);
        btnShip111 = (Button)  findViewById(R.id.btn_Ship1x1_1);
        btnShip112 = (Button)  findViewById(R.id.btn_Ship1x1_2);
        btnShip113 = (Button)  findViewById(R.id.btn_Ship1x1_3);
        btnShip121 = (Button)  findViewById(R.id.btn_Ship1x2_1);
        btnShip122 = (Button)  findViewById(R.id.btn_Ship1x2_2);
        btnShip131 = (Button)  findViewById(R.id.btn_Ship1x3);
        btnShip141 = (Button)  findViewById(R.id.btn_Ship1x4);
        idImage = new ArrayList<>();

        for(int i = 0; i<50;++i)
        {
            idImage.add(new Item(R.drawable.backgrond));
        }

        itemImageAdapter = new ItemImageAdapter(this, R.layout.item_image,idImage);
        grv_board.setAdapter(itemImageAdapter);

        grv_board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                if(lenShip == 0) return;

                direction = 5;

                if(lenShip == 1){
                    start = index;
                    end = index;
                }
                else if(lenShip == 2){
                   start = index - direction;
                   end = index;
                }
                else if(lenShip == 3) {
                    start = index - direction;
                    end = index + direction;
                }else if(lenShip == 4){
                    start = index - direction*2;
                    end = index + direction;
                }

                if(start < 0){
                    start = start + ((0 - start)/direction + 1)*direction;
                    end = start + (lenShip - 1)*direction;
                }
                if(end > 49){
                    end = end - ((end - 49)/direction + 1)*direction;
                    start = end - (lenShip - 1)*direction;
                }

                for(int i = start; i<=end; i+=direction)
                {
                    if(board[i/5][i%5] !=0) return;
                }

                for(int i = start; i<=end; i+=direction){
                    itemImageAdapter.getItem(i).setItem(R.drawable.selected);
                }


                itemImageAdapter.notifyDataSetChanged();
                currentShip.setEnabled(false);
                rotation = lenShip;
                currentPosition=index;
                lenShip = 0;

                //Log.d("ccc", idImage.get(i).toString());

                //Log.d("bbb", "there");
            }
        });
        btnShip111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lenShip = 1;
                currentShip = btnShip111;
            }
        });
        btnShip112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 1;
                currentShip = btnShip112;
            }
        });
        btnShip113.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 1;
                currentShip = btnShip113;
            }
        });
        btnShip121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 2;
                currentShip = btnShip121;
            }
        });
        btnShip122.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 2;
                currentShip = btnShip122;
            }
        });
        btnShip131.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 3;
                currentShip = btnShip131;
            }
        });
        btnShip141.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = start; i<=end; i+=direction){
                    board[i/5][i%5] = rotation;
                }

                lenShip = 4;
                currentShip = btnShip141;
            }
        });
        btnRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newStart = start;
                int newEnd = end;
                int newDirection = 1;


                if(rotation == 2){
                    newStart = currentPosition - newDirection;
                    newEnd = currentPosition;
                }else if(rotation == 3)
                {
                    newStart = currentPosition -newDirection;
                    newEnd = currentPosition +newDirection;
                }else if(rotation == 4){
                    newStart =currentPosition - newDirection*2;
                    newEnd=currentPosition+newDirection;
                }

                int currentRow = currentPosition/5;
                int nextRow = currentRow + 1;

                if(newStart< currentRow*5){
                    newStart = currentRow*5;
                    newEnd = currentRow + rotation - 1;
                }

                if(newEnd >= nextRow*5 - 1){
                    newEnd = nextRow*5 - 1;
                    newStart = newEnd - rotation +1 ;
                }

                for(int i = newStart; i<=newEnd; i+=newDirection)
                {
                    if(board[i/5][i%5] !=0) return;
                }

                for(int i = start; i<=end; i+=direction){
                    itemImageAdapter.getItem(i).setItem(R.drawable.backgrond);
                }

                start = newStart;
                end=newEnd;
                direction=newDirection;


                for(int i = start; i<=end; i+=direction){
                    itemImageAdapter.getItem(i).setItem(R.drawable.selected);
                }

                itemImageAdapter.notifyDataSetChanged();

            }
        });
       // idImage.set(1, R.drawable.selected);
    }

}
