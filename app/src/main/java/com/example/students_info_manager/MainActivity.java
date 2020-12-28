package com.example.students_info_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.students_info_manager.database.DatabaseHelper;


public class MainActivity extends AppCompatActivity {
    private Button btn_1;
    private Button btn_2;
//    private Button btn_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("学生成绩信息管理系统");
        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
   //     btn_3=findViewById(R.id.btn_3);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Add_Info_Activity.class);
                startActivity(intent);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Show_Info_Activity.class);
                startActivity(intent);
            }
        });


    }
}