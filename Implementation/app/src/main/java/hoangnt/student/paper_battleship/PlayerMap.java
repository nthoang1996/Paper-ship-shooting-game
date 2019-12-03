package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_map);

        statusMap = new int[50];
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
                    showDialogConfirmShoot(position, parent);
                }
            }
        });
        listenerRunable.run();
    }

    public void showDialogConfirmShoot(final int position, final AdapterView<?> parent){
        final Dialog dialogConfirm;
        dialogConfirm = new Dialog(PlayerMap.this);
        dialogConfirm.setContentView(R.layout.confirm_dialog);

        Button btnShoot = dialogConfirm.findViewById(R.id.btnOkConfirmDialog);
        Button btnCancel = dialogConfirm.findViewById(R.id.btnCancelConfirmDialog);

        btnShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("my-debuger", String.valueOf(position));
                String command = "Shoot: "+ position;
                Bluetooth.getBluetoothConnection().write(command.getBytes(Charset.defaultCharset()));
                Bluetooth.setYourTurn(false);
                View childView=  (View)parent.getChildAt(position);
                TextView textView = childView.findViewById(R.id.cell_grid);
                textView.setBackgroundResource(R.drawable.x);
                dialogConfirm.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();
            }
        });

        dialogConfirm.show();
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
                    Bluetooth.setDataSending("");
                }
                mHandler.postDelayed(this, 200);
            }
        }
    };
}
