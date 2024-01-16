package com.example.user.myvoicetalk;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BloothController extends AppCompatActivity {

    // Debugging
    private static final String TAG = "BloothController";

    static final int REQUEST_ENABLE_BT = 10;
    BluetoothAdapter mBluetoothAdapter;

    // 블루투스 연결결과
    boolean result = false;

    Set<BluetoothDevice> mDevices;

    InputStream mInputStream = null;
    OutputStream mOutputStream = null;
    int mPairedDeviceCount = 0;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket = null;
    String mStrDelimiter = "\n";
    Thread mWorkerThread = null;

    // Layout
    private Button onBtn, offBtn;
    private ImageButton btn_Connect, go, back, left, right;

    protected void onDestroy() {
        try {
            this.mWorkerThread.interrupt();
            this.mInputStream.close();
            this.mOutputStream.close();
            this.mSocket.close();
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blooth_controller);

        Log.e(TAG, "onCreate");
        // 블루투스 연결
        btn_Connect = (ImageButton)findViewById(R.id.btn_connect);
        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBluetooth();
            }
        });



        // 방향제어
        go = (ImageButton) findViewById(R.id.go);
        go.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                if(MotionEvent.ACTION_DOWN == action){
                    BloothController.this.sendData("a");
                }else if(MotionEvent.ACTION_UP == action){
                    BloothController.this.sendData("e");
                }
                return true;
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                if(MotionEvent.ACTION_DOWN == action){
                    BloothController.this.sendData("b");
                }else if(MotionEvent.ACTION_UP == action){
                    BloothController.this.sendData("e");
                }
                return true;
            }
        });

        left = (ImageButton) findViewById(R.id.left);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                if(MotionEvent.ACTION_DOWN == action){
                    BloothController.this.sendData("c");
                }else if(MotionEvent.ACTION_UP == action){
                    BloothController.this.sendData("f");
                }


                return true;
            }
        });
        right = (ImageButton) findViewById(R.id.right);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                if(MotionEvent.ACTION_DOWN == action){
                    BloothController.this.sendData("d");
                }else if(MotionEvent.ACTION_UP == action){
                    BloothController.this.sendData("f");
                }


                return true;
            }
        });

        // 온오프
        onBtn = (Button) findViewById(R.id.onBtn);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloothController.this.sendData("1");
            }
        });
        offBtn = (Button) findViewById(R.id.offBtn);
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloothController.this.sendData("0");
            }
        });

    }

    BluetoothDevice getDeviceFromBondedList(String name) {
        for (BluetoothDevice device : this.mDevices) {
            if (name.equals(device.getName())) {
                return device;
            }
        }
        return null;
    }

    public void sendData(String msg) {
        try {
            this.mOutputStream.write(new StringBuilder(String.valueOf(msg)).append(this.mStrDelimiter).toString().getBytes());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "\ub370\uc774\ud130 \uc804\uc1a1 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();

        }
    }

    void connectToSelectedDevice(String selectedDeviceName) {
        this.mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        try {
            this.mSocket = this.mRemoteDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            this.mSocket.connect();
            this.mOutputStream = this.mSocket.getOutputStream();
            this.mInputStream = this.mSocket.getInputStream();

            Toast.makeText(getApplicationContext(),"블루투스 연결성공!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "\ube14\ub8e8\ud22c\uc2a4 \uc5f0\uacb0 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();

        }
    }

    void selectDevice() {
        this.mDevices = this.mBluetoothAdapter.getBondedDevices();
        this.mPairedDeviceCount = this.mDevices.size();
        if (this.mPairedDeviceCount == 0) {
            Toast.makeText(getApplicationContext(), "\ud398\uc5b4\ub9c1\ub41c \uc7a5\uce58\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("\ube14\ub8e8\ud22c\uc2a4 \uc7a5\uce58 \uc120\ud0dd");
        List<String> listItems = new ArrayList();

        for (BluetoothDevice device : this.mDevices) {
            listItems.add(device.getName());
        }

        listItems.add("\ucde8\uc18c");

        final CharSequence[] items = (CharSequence[]) listItems.toArray(new CharSequence[listItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == BloothController.this.mPairedDeviceCount) {
                    Toast.makeText(BloothController.this.getApplicationContext(), "\uc5f0\uacb0\ud560 \uc7a5\uce58\ub97c \uc120\ud0dd\ud558\uc9c0 \uc54a\uc558\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();
                    return;
                }
                BloothController.this.connectToSelectedDevice(items[item].toString());
            }
        });

        builder.setCancelable(false);
        builder.create().show();

    }


    void checkBluetooth() {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "\uae30\uae30\uac00 \ube14\ub8e8\ud22c\uc2a4\ub97c \uc9c0\uc6d0\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();
        } else if (this.mBluetoothAdapter.isEnabled()) {
            selectDevice();
        } else {
            Toast.makeText(getApplicationContext(), "\ud604\uc7ac \ube14\ub8e8\ud22c\uc2a4\uac00 \ube44\ud65c\uc131 \uc0c1\ud0dc\uc785\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), REQUEST_ENABLE_BT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT /*10*/:
                if (resultCode != -1) {
                    if (resultCode == 0) {
                        Toast.makeText(getApplicationContext(), "\ube14\ub8e8\ud22c\uc2a4\ub97c \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc5b4 \ud504\ub85c\uadf8\ub7a8\uc744 \uc885\ub8cc\ud569\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                selectDevice();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
