package com.example.user.myvoicetalk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnLRLightOn, btnLRLightOff, btnGasLightOn, btnGasLightOff;


////블루투스///////////////////////////////////////////////
    //======================================================================================// 블루투스

    // 사용자 정의 함수로 블루투스 활성 상태의 변경 결과를 App으로 알려줄때 식별자로 사용됨(0보다 커야함)
    static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;

    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트.
    BluetoothAdapter mBluetoothAdapter;

    /**
     * BluetoothDevice 로 기기의 장치정보를 알아낼 수 있는 자세한 메소드 및 상태값을 알아낼 수 있다.
     * 연결하고자 하는 다른 블루투스 기기의 이름, 주소, 연결 상태 등의 정보를 조회할 수 있는 클래스.
     * 현재 기기가 아닌 다른 블루투스 기기와의 연결 및 정보를 알아낼 때 사용.
     */
    BluetoothDevice mRemoteDevie;

    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';


    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;

    TextView receiveHeart, receiveTemperature;
    ImageButton mButtonSend, btnBluetooth;

    boolean isBlueTooth = false;

    //======================================================================================// 블루투스

////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(getApplicationContext(), BloothController.class);
        startActivity(intent);*/



        btnLRLightOn = findViewById(R.id.button1);
        btnLRLightOff = findViewById(R.id.button2);
        btnGasLightOn = findViewById(R.id.button3);
        btnGasLightOff = findViewById(R.id.button4);

        btnLRLightOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("g");         // 상태 확인문자 우노보드로 보내기
                try {
                    Thread.sleep(1000); // 잠시대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beginListenForData();  // 상태 확인
            }
        });

        btnLRLightOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("h");         // 상태 확인문자 우노보드로 보내기
                try {
                    Thread.sleep(1000); // 잠시대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beginListenForData();  // 상태 확인
            }
        });

        btnGasLightOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("i");         // 상태 확인문자 우노보드로 보내기
                try {
                    Thread.sleep(1000); // 잠시대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beginListenForData();  // 상태 확인
            }
        });

        btnGasLightOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("j");         // 상태 확인문자 우노보드로 보내기
                try {
                    Thread.sleep(1000); // 잠시대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beginListenForData();  // 상태 확인
            }
        });

        // 블루투스 활성화 시키는 메소드
        btnBluetooth = (ImageButton) findViewById(R.id.btnBluetooth);

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBluetooth();
            }
        });

    }

///////////Bluethooth///////////////////////////////////////////////////////////
    void checkBluetooth() {
        /**
         * getDefaultAdapter() : 만일 폰에 블루투스 모듈이 없으면 null 을 리턴한다.
         이경우 Toast를 사용해 에러메시지를 표시하고 앱을 종료한다.
         */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {  // 블루투스 미지원
            Toast.makeText(getApplicationContext(), "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
//            finish();  // 앱종료
        } else { // 블루투스 지원
            /** isEnable() : 블루투스 모듈이 활성화 되었는지 확인.
             *               true : 지원 ,  false : 미지원
             */
            if (!mBluetoothAdapter.isEnabled()) { // 블루투스 지원하며 비활성 상태인 경우.
                Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // REQUEST_ENABLE_BT : 블루투스 활성 상태의 변경 결과를 App 으로 알려줄 때 식별자로 사용(0이상)
                /**
                 startActivityForResult 함수 호출후 다이얼로그가 나타남
                 "예" 를 선택하면 시스템의 블루투스 장치를 활성화 시키고
                 "아니오" 를 선택하면 비활성화 상태를 유지 한다.
                 선택 결과는 onActivityResult 콜백 함수에서 확인할 수 있다.
                 */
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else // 블루투스 지원하며 활성 상태인 경우.
                selectDevice();
        }
    }

    // 블루투스 지원하며 활성 상태인 경우.
    void selectDevice() {
        // 블루투스 디바이스는 연결해서 사용하기 전에 먼저 페어링 되어야만 한다
        // getBondedDevices() : 페어링된 장치 목록 얻어오는 함수.
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if (mPariedDeviceCount == 0) { // 페어링된 장치가 없는 경우.
            Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
//            finish(); // App 종료.
        }
        // 페어링된 장치가 있는 경우.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");

        // 각 디바이스는 이름과(서로 다른) 주소를 가진다. 페어링 된 디바이스들을 표시한다.
        List<String> listItems = new ArrayList<String>();

        //페어링된 기기 쿼리
        for (BluetoothDevice device : mDevices) {
            // device.getName() : 단말기의 Bluetooth Adapter 이름을 반환.
            listItems.add(device.getName());
        }

        listItems.add("취소");  // 취소 항목 추가.

        // CharSequence : 변경 가능한 문자열.
        // toArray : List형태로 넘어온것 배열로 바꿔서 처리하기 위한 toArray() 함수.
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        // toArray 함수를 이용해서 size만큼 배열이 생성 되었다.
        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getApplicationContext(), "연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    finish();
                } else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
                }
            }

        });

        builder.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();
        alert.show();
    }

    //  connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄.
    //   실제 데이터 송수신을 위해서는 소켓으로부터 입출력 스트림을 얻고 입출력 스트림을 이용하여 이루어 진다.
    void connectToSelectedDevice(String selectedDeviceName) {
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        mRemoteDevie = getDeviceFromBondedList(selectedDeviceName);
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와
            //                                           통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는
            //  BluetoothSocket 오브젝트를 리턴함.
            mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            // 데이터 송수신을 위한 스트림 얻기.
            // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            // 1. 데이터를 보내기 위한 OutputStrem
            // 2. 데이터를 받기 위한 InputStream
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            // 데이터 수신 준비.
            //beginListenForData();

        } catch (Exception e) { // 블루투스 연결 중 오류 발생
            Toast.makeText(getApplicationContext(),
                    "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            finish();  // App 종료
        }
    }

    // 우노보드로 데이터 보내기
    void sendData(String msg) {
        try {
            this.mOutputStream.write(new StringBuilder(String.valueOf(msg)).append(this.mStrDelimiter).toString().getBytes());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "\ub370\uc774\ud130 \uc804\uc1a1 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();

        }
    }

    public void beginListenForData() {
        final Handler handler = new Handler();
        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.
        Log.d("beginListenForData", "1");
        // 문자열 수신 쓰레드.
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("beginListenForData", "2");
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
//                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = mInputStream.available();   // 수신 데이터 확인

                        Log.d("byteAvailable", byteAvailable+"");

                        if (byteAvailable > 0) {                        // 데이터가 수신된 경우.
                            byte[] packetBytes = new byte[byteAvailable];
                            // read(buf[]) : 입력스트림에서 buf[] 크기만큼 읽어서 저장 없을 경우에 -1 리턴.

                            //                       Log.d("byteAvailable", String.valueOf(byteAvailable));
                            Log.d("packetBytes", packetBytes.length + "");

                            mInputStream.read(packetBytes);
                            //                           readBufferPosition = 0;
                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == mCharDelimiter) {
                                    Log.d("b", "b if : " + (int) b);
//                                readBuffer[readBufferPosition++] = b;
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");

                                    //                                   Log.d("data", data + "\n");

                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        // 수신된 문자열 데이터에 대한 처리.
                                        @Override
                                        public void run() {
                                            // mStrDelimiter = '\n';
//                                            receiveHeart.setText(data.substring(0, 3));
//                                            receiveTemperature.setText(data.substring(3, 8));
                                            Log.d("run", "문자 처리");
                                            Log.d("ACAC", "" + data.length());

                                            String tempData =  data.substring(0, 1);
                                            Log.d("tempData", tempData);

                                            if(tempData.equals("c")){
                                                Log.d("Main"," 거실에 등 상태는 켜진상태입니다");
                                            }else if(tempData.equals("b")){
                                                Log.d("Main"," 거실에 등 상태는 꺼진상태입니다");
                                            }

                                            if(tempData.equals("e")){
                                                Log.d("Main","가스렌지 상태는 꺼진상태입니다");
                                            }else if(tempData.equals("f")){
                                                Log.d("Main","가스렌지  상태는 켜진상태입니다");
                                            }
                                        }

                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                    Log.d("b", "b else : " + (int) b + " / readBufferPosition : " + readBufferPosition);
                                }
                            }
                        }

                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Log.d("Error ", e.getMessage());
                        //                       Toast.makeText(getApplicationContext(), "데이터 수신 중 오류가 발생 했습니다.", Toast.LENGTH_LONG).show();
//                        finish();            // App 종료.
                    }
 //               } //while문 종료
            }

        });
        mWorkerThread.start();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for (BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if (name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // startActivityForResult 를 여러번 사용할 땐 이런 식으로
        // switch 문을 사용하여 어떤 요청인지 구분하여 사용함.
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) { // 블루투스 활성화 상태
                    selectDevice();
                    Toast.makeText(getApplicationContext(), "블루투스 켜짐", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 비활성화 상태 (종료)
                    Toast.makeText(getApplicationContext(), "블루투스를 사용할 수 없어 프로그램을 종료안해",
                            Toast.LENGTH_SHORT).show();
//                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

/////Bluethooth/////////////////////////////////////////////////////////////////



}





