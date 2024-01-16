package com.example.my51_iot_esp8266;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my51_iot_esp8266.ATask.ListInsert;
import com.example.my51_iot_esp8266.ATask.ListSelect;
import com.example.my51_iot_esp8266.Dto.MyItem;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public LinearLayout state_alert, light, secom, boiler, window;
    public ImageView ex_home, onLight, offLight, onlock, offlock, onboilerswitch, offboilerswitch, onwindow , offwindow;
    public TextView tv_mConnection, tv_LightVal, tv_SecomVal, tv_BoilerVal, tv_WinVal;

    public ArrayList<MyItem> myItemArrayList = new ArrayList<>();
    public ProgressDialog dialog;

    public int lightState = 0, secomState = 0, boilerState = 0, windowState = 0;      // 0:꺼짐,정지,잠금   1:켜짐,가동,해제

    @Override
    public void onResume() {
        loadState();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "CSSLed01";
                String value = "";
                if(lightState == 0){
                    value = "1";
                }else if(lightState == 1){
                    value = "0";
                }
                Log.d("main:lightValue", value);
                setHomeState(id, value);
            }
        });

        secom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "CSSSec01";
                String value = "";
                if(secomState == 0){
                    value = "1";
                }else if(secomState == 1){
                    value = "0";
                }
                Log.d("main:SecomValue", value);
                setHomeState(id, value);
            }
        });

    }

    // XML에서 아이디 찾아놓기
    public void findID(){
        tv_mConnection = findViewById(R.id.mConnectionStatus);
        state_alert = findViewById(R.id.status_alert);
        ex_home = findViewById(R.id.ex_home);
        light = findViewById(R.id.status_light);
        secom = findViewById(R.id.status_secom);
        boiler = findViewById(R.id.status_boiler);
        window = findViewById(R.id.status_window);
        onLight = findViewById(R.id.onLight);
        offLight = findViewById(R.id.offLight);
        onlock = findViewById(R.id.onLock);
        offlock = findViewById(R.id.offLock);
        onboilerswitch = findViewById(R.id.onboilerswitch);
        offboilerswitch = findViewById(R.id.offboilerswitch);
        onwindow = findViewById(R.id.onwindow);
        offwindow = findViewById(R.id.offwindow);

        tv_LightVal = findViewById(R.id.tv_LightVal);
        tv_SecomVal = findViewById(R.id.tv_SecomVal);
        tv_BoilerVal = findViewById(R.id.tv_BoilerVal);
        tv_WinVal = findViewById(R.id.tv_WinVal);
    }

    // 집 상태 가져오기
    public void loadState(){
        ListSelect listSelect = new ListSelect(myItemArrayList, dialog, this);
        try {
            listSelect.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        homeStateSetting();
    } // loadState()

    // 집 상태 셋팅하기
    public void setHomeState(String id, String value){
        ListInsert listInsert = new ListInsert(myItemArrayList, dialog, this, id, value);
        try {
            listInsert.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadState();
    } // setHomeState()

    public void homeStateSetting(){
        if(myItemArrayList.size() > 0) {

            tv_mConnection.setText("Wifi Connectted !!!");
            tv_mConnection.setTextColor(Color.BLUE);

            for (int i = 0; i < myItemArrayList.size(); i++) {
                if (myItemArrayList.get(i).getId().equals("CSSLed01")) {
                    if (myItemArrayList.get(i).getValue().equals("1")) {
                        onLight.setVisibility(View.VISIBLE);
                        offLight.setVisibility(View.GONE);
                        tv_LightVal.setText("전등 켜짐");
                        lightState = 1;
                    } else {
                        onLight.setVisibility(View.GONE);
                        offLight.setVisibility(View.VISIBLE);
                        tv_LightVal.setText("전등 꺼짐");
                        lightState = 0;
                    }
                }
                if (myItemArrayList.get(i).getId().equals("CSSSec01")) {
                    if (myItemArrayList.get(i).getValue().equals("1")) {
                        onlock.setVisibility(View.VISIBLE);
                        offlock.setVisibility(View.GONE);
                        tv_SecomVal.setText("쎄콤 해제");
                        secomState = 1;
                    } else {
                        onlock.setVisibility(View.GONE);
                        offlock.setVisibility(View.VISIBLE);
                        tv_SecomVal.setText("쎄콤 잠금");
                        secomState = 0;
                    }
                }
            } // for

            // 나머지 2개 그냥초기화
            onboilerswitch.setVisibility(View.GONE);
            offboilerswitch.setVisibility(View.VISIBLE);
            tv_BoilerVal.setText("보일러 정지");
            boilerState = 0;

            onwindow.setVisibility(View.GONE);
            offwindow.setVisibility(View.VISIBLE);
            tv_WinVal.setText("창문 닫힘");
            windowState = 0;

        }else { // if(myItemArrayList.size() > 0)
            tv_mConnection.setText("Wifi Not Connect ...");
            tv_mConnection.setTextColor(Color.RED);
        }
    }

}
