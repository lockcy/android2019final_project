package com.example.final_project;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WhoisActivity extends AppCompatActivity {

    private Button button1,button2;
    private EditText editText1;
    private TextView textView1;
    public static final  String TAG="WhoisActivity";

    private Handler handler;
    private static final String MESSAGE_TITLE = "result";

    final static String URL="http://apis.haoservice.com/whois?domain=";
    final static String key="04b25200482947df9c45c41eac9e9279";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whois);
        initView();
        initHandler();
        initListener();
    }

    public void initView(){
        button1=findViewById(R.id.btn_query);
        button2=findViewById(R.id.btn_cancel);
        editText1=findViewById(R.id.ip);
        textView1=findViewById(R.id.result);
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
                String ip = editText1.getText().toString();
                if (TextUtils.isEmpty(ip)) {
                    Toast.makeText(WhoisActivity.this, "ip或域名不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String URL1 = URL + ip+"&key="+key;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15L, TimeUnit.SECONDS)
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
                                Response response = call.execute();
                                String temp=response.body().string();
                                String newResult="";
                                //temp=temp.replaceAll("\\\\n", "\n");
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString(MESSAGE_TITLE,temp);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
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
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
