package com.example.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import com.google.gson.Gson;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private EditText editText1,editText2;
    private Button button1,button2,button3;
    public static final  String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
        this.initListener();

    }
    private void initView(){
        editText1=findViewById(R.id.userName);
        editText2=findViewById(R.id.passWord);
        button1=findViewById(R.id.btn_forget);
        button2=findViewById(R.id.btn_login);
        button3=findViewById(R.id.btn_register);
    }


    private void initListener(){
        s_use temp=new s_use();
        final String URL=temp.getURL()+"login.php";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请联系管理员lockcysec@qq.com", Toast.LENGTH_LONG).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for testing*/
//                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
//                //intent.putExtra("username","test");
//                startActivity(intent);

                String username = editText1.getText().toString();
                String password = editText2.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client=new OkHttpClient.Builder().build();
                    password = CommonUtils.hash("MD5",password,16);
                    RequestBody post=new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
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
                                        //获取json
                                        String nn=response.body().string();
                                        if (nn.trim().equals("含有非法字符串")){
                                            Toast.makeText(MainActivity.this, "含有非法字符串", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            JSONObject jsonObject = new JSONObject(nn);
                                            //解析
                                            BackCode backCode = new Gson().fromJson(jsonObject.toString(), BackCode.class);
                                            //解析的结果
                                            Log.i(TAG, "run: code：" + backCode.code);
                                            Log.i(TAG, "run: message：" + backCode.message);
                                            //分析结果
                                            /*在这里判断成功的为 code = 1,失败为0，若有更多失败原因可以再添加message查看*/
                                            if (backCode.code == 1) {
                                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                                                intent.putExtra("username",editText1.getText().toString());
                                                intent.putExtra("password",editText2.getText().toString());
                                                startActivity(intent);
                                                //finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
