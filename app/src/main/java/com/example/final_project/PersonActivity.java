package com.example.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PersonActivity extends AppCompatActivity {
    private Button button1, button2, button3,button4,button5,button6,button7,button8,button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        this.initView();
        this.initListener();
    }

    private void initView() {
        button1 = findViewById(R.id.button_p1);
        button2 = findViewById(R.id.button_p2);
        button3=findViewById(R.id.button_p3);
        button4=findViewById(R.id.button_p4);
        button5=findViewById(R.id.button_p5);
        button6=findViewById(R.id.button_p6);
        button7 = findViewById(R.id.button_p7);
        button8= findViewById(R.id.button_p8);
        button9= findViewById(R.id.button_p9);

    }

    private void initListener() {
        s_use temp = new s_use();
        final String URL = temp.getURL() + "register.php";
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=getIntent();
                String name=intent1.getStringExtra("username");
                String password=intent1.getStringExtra("password");
                Intent intent2 = new Intent(PersonActivity.this, InfoActivity.class);
                intent2.putExtra("username",name);
                intent2.putExtra("password",password);
                startActivity(intent2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PersonActivity.this, WeatherActivity.class);
                startActivity(intent3);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(PersonActivity.this, PortActivity.class);
                startActivity(intent4);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(PersonActivity.this, CodeActivity.class);
                startActivity(intent5);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8 = new Intent(PersonActivity.this, DirActivity.class);
                startActivity(intent8);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(PersonActivity.this, WhoisActivity.class);
                startActivity(intent6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7=new Intent(PersonActivity.this,HashActivity.class);
                startActivity(intent7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonActivity.this, "under construction！", Toast.LENGTH_SHORT).show();
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9=new Intent(PersonActivity.this,ShellActivity.class);
                startActivity(intent9);
            }
        });
    }
}
