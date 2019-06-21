package com.example.final_project;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PortActivity extends AppCompatActivity {
    private EditText editText1;
    private Button button1,button2;
    private TextView textView1;
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8;
    private Handler handler;
    private static final String MESSAGE_TITLE = "result";

    public static final String TAG="PortActivity";
	s_use temp = new s_use();
    final String URL = temp.getURL() + "nmap.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        initView();
        initHandler();
        initListener();
    }
    public void initView(){
        editText1=findViewById(R.id.ip);
        textView1=findViewById(R.id.result);
        button1=findViewById(R.id.btn_scan);
        button2=findViewById(R.id.btn_cancel);
        checkBox1=findViewById(R.id.checkBox);
        checkBox2=findViewById(R.id.checkBox2);
        checkBox3=findViewById(R.id.checkBox3);
        checkBox4=findViewById(R.id.checkBox4);
        checkBox5=findViewById(R.id.checkBox5);
        checkBox6=findViewById(R.id.checkBox6);
        checkBox7=findViewById(R.id.checkBox7);
        checkBox8=findViewById(R.id.checkBox8);

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
                String temp="00000000";
                char[]para=temp.toCharArray();
                if (TextUtils.isEmpty(ip)) {
                    Toast.makeText(PortActivity.this, "IP或域名不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (checkBox1.isChecked()) {
                        para[0] = '1';
                    }
                    if (checkBox2.isChecked()) {
                        para[1] = '1';
                    }
                    if (checkBox3.isChecked()) {
                        para[2] = '1';
                    }
                    if (checkBox4.isChecked()) {
                        para[3] = '1';
                    }
                    if (checkBox5.isChecked()) {
                        para[4] = '1';
                    }
                    if (checkBox6.isChecked()) {
                        para[5] = '1';
                    }
                    if (checkBox7.isChecked()) {
                        para[6] = '1';
                    }
                    if (checkBox8.isChecked()) {
                        para[7] = '1';
                    }

                    final String URL1 = URL + "para=" + Arrays.toString(para).replaceAll("[\\[\\]\\s,]", "") + "&ip=" + ip;
                    Log.v(TAG, "run:" + URL1);
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
                                Response response = call.execute();
                                String result = response.body().string();
                                Log.v(TAG, "run: " + result);
                                Message msg = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString(MESSAGE_TITLE, result);
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
