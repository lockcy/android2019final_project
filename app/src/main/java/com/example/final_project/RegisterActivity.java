package com.example.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.final_project.s_use;
import com.example.final_project.BackCode;
import com.example.final_project.user;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {
    private EditText editText1,editText2,editText3;
    private Button button1,button2;
    public static final  String TAG="RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initView();
        this.initListener();
    }
    private void initView(){
        editText1=findViewById(R.id.userName1);
        editText2=findViewById(R.id.phone);
        editText3=findViewById(R.id.passWord1);
        button1=findViewById(R.id.btn_confirm);
        button2=findViewById(R.id.btn_cancel);
    }
    private void initListener(){
        s_use temp=new s_use();
        final String URL=temp.getURL()+"register.php";
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = editText1.getText().toString();
                String phonenumber=editText2.getText().toString();
                String password = CommonUtils.hash("MD5",editText3.getText().toString(),16);
                //String password =editText3.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(RegisterActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client=new OkHttpClient.Builder().build();
                    RequestBody post=new FormBody.Builder()
                            .add("username",username)
                            .add("phonenumber",phonenumber)
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
                                            Toast.makeText(RegisterActivity.this, "含有非法字符串", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                //finish();
                                            }
                                            else if (backCode.code==0){
                                                Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
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
