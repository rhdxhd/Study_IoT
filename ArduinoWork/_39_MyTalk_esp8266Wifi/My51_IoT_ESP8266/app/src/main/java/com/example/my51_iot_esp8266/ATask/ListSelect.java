package com.example.my51_iot_esp8266.ATask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.my51_iot_esp8266.Dto.MyItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.my51_iot_esp8266.Common.CommonMethod.ipConfig;

// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class ListSelect extends AsyncTask<Void, Void, Void> {
    // 생성자
    ArrayList<MyItem> myItemArrayList;
    ProgressDialog progressDialog;
    Context context;

    public ListSelect(ArrayList<MyItem> myItemArrayList, ProgressDialog progressDialog, Context context) {
        this.myItemArrayList = myItemArrayList;
        this.progressDialog = progressDialog;
        this.context = context;
    }


    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setTitle("데이터 받는중");
//        progressDialog.setMessage("데이터 받는 중입니다\n" + "잠시만 기다려주세요 ...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        myItemArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/arduGetLed";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

//        if(progressDialog != null){
//            progressDialog.dismiss();
//        }

        Log.d("Sub1", "List Select Complete!!!");

    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myItemArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public MyItem readMessage(JsonReader reader) throws IOException {
        String id = "", name = "", value = "", updatetime = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("name")) {
                name = reader.nextString();
            } else if (readStr.equals("value")) {
                value = reader.nextString();
            } else if (readStr.equals("updatetime")) {
                updatetime = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", id + "," + name + "," + value + "," + updatetime);
        return new MyItem(id, name, value, updatetime);

    }

    /*public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }*/

}
