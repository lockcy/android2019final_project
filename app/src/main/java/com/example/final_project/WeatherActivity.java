package com.example.final_project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Message;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private TextView textView1;
    private EditText editText1;
    private Button button1,button2;
    private Handler handler;
    private static final String MESSAGE_TITLE = "result";

    public static final String TAG="WeatherActivity";
    final static String key="yourkey";
    final static String URL="http://v.juhe.cn/weather/index?format=2&key="+key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        initHandler();
        initListener();
    }
    public void initView(){
        textView1=findViewById(R.id.textView_result);
        editText1=findViewById(R.id.city);
        button1=findViewById(R.id.btn_query);
        button2=findViewById(R.id.btn_cancel2);
        textView1.setText("");
    }
    private void initHandler() {
        handler = new Handler() {
            @ Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString(MESSAGE_TITLE);
                textView1.setText(result);
            }
        };
    }

    public void initListener(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String cityname = editText1.getText().toString();
                    if (TextUtils.isEmpty(cityname)) {
                        Toast.makeText(WeatherActivity.this, "城市名不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        final String URL1 = URL + "&cityname=" + cityname;
                        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .build();
                        //OkHttpClient okHttpClient = new OkHttpClient();
                        final Request request = new Request.Builder()
                                .url(URL1)
                                .build();
                        final Call call = okHttpClient.newCall(request);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.d(TAG,"run: "+URL1);
                                    Response response = call.execute();
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String resultcode = jsonObject.getString("resultcode").trim();
                                    Log.d(TAG, "run: " + resultcode);
                                    String result1 = null;
                                    if (resultcode.equals("200")) {
                                        String result = jsonObject.getString("result");
                                        Log.i(TAG, result);
                                        JSONObject jsonObject1 = new JSONObject(result);
                                        JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("today"));
                                        result1 = "日期: " + jsonObject2.getString("date_y") + " " + jsonObject2.getString("week") + "\n" +
                                                jsonObject2.get("weather") + " 温度：" + jsonObject2.getString("temperature") + " 风力："  +
                                                jsonObject2.getString("wind") +"\n"+ "穿衣建议：" + jsonObject2.getString("dressing_advice");

                                    }

                                    Message msg = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(MESSAGE_TITLE, result1);
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    String result = e.toString();
                                    Message msg = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(MESSAGE_TITLE, result);
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                }
                            }
                        }).start();
                    }
                }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
