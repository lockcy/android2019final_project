package com.example.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class HashActivity extends AppCompatActivity {
    private static final String[]type={"MD5","SHA256","SHA512"};
    private EditText editText1;
    private Spinner spinner1;
    private Button button1,button2;
    private TextView textView1;
    private ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash);
        initView();
        initListener();
    }

    public void initView(){
        editText1=findViewById(R.id.hashString);
        spinner1=findViewById(R.id.spinner);
        button1=findViewById(R.id.btn_confirm1);
        button2=findViewById(R.id.btn_cancel3);
        textView1=findViewById(R.id.textView_result1);
        adapter1 = new ArrayAdapter<String>(HashActivity.this, android.R.layout.simple_spinner_item, type);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(0);
    }
    public void initListener(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=spinner1.getSelectedItemPosition();
                String pure=editText1.getText().toString();
                if (TextUtils.isEmpty(pure)) {
                    Toast.makeText(HashActivity.this, "字符串不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (position==0){
                        textView1.setText(CommonUtils.hash("MD5",pure,16));
                    }
                    else if (position==1){
                        textView1.setText(CommonUtils.hash("sha256",pure,16));
                    }
                    else{
                        textView1.setText(CommonUtils.hash("sha512",pure,16));
                    }
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
