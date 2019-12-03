package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.Serializable;

public class HostGameActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MainActivity";
    Button btnHostGame;
    Button btnPracticeWithBot;
    Button btnBack;

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game);

        btnHostGame = findViewById(R.id.btnHostOnlineGame);
        btnHostGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);

                IntentFilter intentFilter = new IntentFilter(Bluetooth.getmBluetoothAdapter().ACTION_SCAN_MODE_CHANGED);
                registerReceiver(mBroadcastReceiver2,intentFilter);

                showDialogHostGame();
            }
        });
        btnPracticeWithBot = findViewById(R.id.btnPracticeWithBOT);
        btnPracticeWithBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostGameActivity.this, PrepareActivity.class);
                intent.putExtra("Mode", "0");
                startActivity(intent);
            }
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

   public void showDialogHostGame(){
        final Dialog dialogWaiting;
        dialogWaiting = new Dialog(HostGameActivity.this);
        dialogWaiting.setContentView(R.layout.waiting_dialog);

        final Button btnOk = (Button) dialogWaiting.findViewById(R.id.btnOkWaitingDialog);
        final TextView textViewMsg = (TextView) dialogWaiting.findViewById(R.id.textViewWaitingDialog);
        btnOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(Bluetooth.isBond){
                   textViewMsg.setText(R.string.enemy_spotted);
                   btnOk.setVisibility(View.INVISIBLE);
                   Intent intent_game = new Intent(HostGameActivity.this, PrepareActivity.class);
                   intent_game.putExtra("Mode", "1");
                   intent_game.putExtra("isHost", "1");
                   startActivity(intent_game);
               }else{
                   textViewMsg.setText(R.string.no_enemy);
                   textViewMsg.setText(R.string.continue_find);
               }
           }
       });
        dialogWaiting.show();
    }
}
