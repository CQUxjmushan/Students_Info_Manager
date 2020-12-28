package com.example.students_info_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.students_info_manager.Util.Student;
import com.example.students_info_manager.database.Dao;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class Add_Info_Activity extends AppCompatActivity {

    private ImageView imv_head_add;
    private Bitmap bitmap;//用来存放用户选择的图片

    private EditText edt_num;
    private EditText edt_name;
    private EditText edt_city;
    private EditText edt_score;
    private ImageButton imgbtn_add_submit;
    private ImageButton imgbtn_add_back;

    //学生数据表操作对象
    private Dao studentdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__info_);
        setTitle("学生信息添加");
        studentdao =new Dao(Add_Info_Activity.this);
        //默认选中图片为默认头像
        bitmap=bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_head_image);
        imv_head_add=findViewById(R.id.add_head_img);
        edt_num=findViewById(R.id.edt_num);
        edt_name=findViewById(R.id.edt_name);
        edt_city=findViewById(R.id.edt_city);
        edt_score=findViewById(R.id.edt_score);
        imgbtn_add_back=findViewById(R.id.btn_add_back);
        imgbtn_add_submit=findViewById(R.id.btn_add_submit);
        //为imv_head_add添加点击事件
        imv_head_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);

            }
        });

        //为imgbtn_add_back添加返回点击事件
        imgbtn_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add_Info_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //为imgbtn_add_submit添加提交点击事件
        imgbtn_add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student=new Student();
                String id=edt_num.getText().toString();
                String name=edt_name.getText().toString();
                String city=edt_city.getText().toString();
                String score=edt_score.getText().toString();
                //判定消息是否填写完整
                if(id.equals("")||name.equals("")||city.equals("")||score.equals(""))
                {
                    //不完整，弹出提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Add_Info_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("信息填写不完整，请核对" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
                else {
                    student.setImg(bitmap);
                    student.setName(name);
                    student.setCity(city);
                    student.setId(id);
                    student.setScore(Double.parseDouble(score));
                    studentdao.insert(student);
                    //弹出添加成功提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Add_Info_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("添加成功" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                imv_head_add.setImageURI(uri);
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }









}