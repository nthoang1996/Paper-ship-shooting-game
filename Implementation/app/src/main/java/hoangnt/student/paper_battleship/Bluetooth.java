package hoangnt.student.paper_battleship;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import java.util.UUID;

public class Bluetooth {
    public static BluetoothDevice mBTDevice;
    public static BluetoothConnectionService mBluetoothConnection;
    public static BluetoothAdapter mBluetoothAdapter;
    public static Boolean isBond = false;
    public static String dataSending = "";

    public static String getDataSending() {
        return dataSending;
    }

    public static void setDataSending(String dataSending) {
        Bluetooth.dataSending = dataSending;
    }

    public static Boolean getIsBond() {
        return isBond;
    }

    public static void setIsBound(Boolean isBound) {
        Bluetooth.isBond = isBound;
    }

    public static BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public static void setmBluetoothAdapter(BluetoothAdapter mBluetoothAdapter) {
        Bluetooth.mBluetoothAdapter = mBluetoothAdapter;
    }

    public static void setBluetoothDevice(BluetoothDevice device) {
        mBTDevice = device;
    }

    public static BluetoothDevice getBluetoothDevice() {
        return mBTDevice;
    }

    public static void setBluetoothConection(BluetoothConnectionService connection) {
        mBluetoothConnection = connection;
    }

    public static BluetoothConnectionService getBluetoothConnection() {
        return mBluetoothConnection;
    }

    public static void startConnection(UUID id, Context context){
        Log.d("Bluetooth", "startBTConnection: Initializing RFCOM Bluetooth Connection with " + mBTDevice.getName() + " UUID: "+id);
        if (Bluetooth.getBluetoothConnection() == null) {
            Bluetooth.setBluetoothConection(new BluetoothConnectionService(context));
        }
        Log.d("startConnection", "NULL DEVICE : " + mBTDevice.getName());
        Bluetooth.getBluetoothConnection().startClient(mBTDevice,id);
    }
}
