package com.example.students_info_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.students_info_manager.Util.AppUtil;
import com.example.students_info_manager.Util.Student;
import com.example.students_info_manager.database.Dao;

import java.io.FileNotFoundException;

public class Info_Update_Activity extends AppCompatActivity {

    private EditText name;
    private EditText city;
    private EditText num;
    private EditText score;

    private ImageView head;
    private Bitmap img;

    private ImageButton btn_back;
    private ImageButton btn_submit;

    private Dao studentdao;
    private Student res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("学生信息修改");
        setContentView(R.layout.activity_info__update_);
        name=findViewById(R.id.update_edt_name);
        city=findViewById(R.id.update_edt_city);
        num=findViewById(R.id.update_edt_num);
        score=findViewById(R.id.update_edt_score);
        head=findViewById(R.id.update_head_img);
        btn_back=findViewById(R.id.btn_update_back);
        btn_submit=findViewById(R.id.btn_update_submit);
        studentdao =new Dao(Info_Update_Activity.this);
        //接收要修改的对象
        Intent intent=getIntent();
        res =new Student();
        img= AppUtil.byte_to_bitmap(intent.getByteArrayExtra("img"));
        res.setImg(img);
        res.setId(intent.getStringExtra("id"));
        res.setCity(intent.getStringExtra("city"));
        res.setScore(intent.getDoubleExtra("score",0));
        res.setName(intent.getStringExtra("name"));
        head.setImageBitmap(img);
        //设置预设值
        name.setText(res.getName());
        num.setText(res.getId());
        city.setText(res.getCity());
        score.setText(res.getScore()+"");
        //设置主键不可修改
        num.setEnabled(false);

        //为head添加点击事件
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);

            }
        });

        //为btn_submit添加提交点击事件
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1=name.getText().toString();
                String city1=city.getText().toString();
                String score1=score.getText().toString();
                //判定消息是否填写完整
                if(name1.equals("")||city1.equals("")||score1.equals(""))
                {
                    //不完整，弹出提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Info_Update_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("信息填写不完整，请核对" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
                else {
                    res.setImg(img);
                    res.setName(name1);
                    res.setCity(city1);
                    res.setScore(Double.parseDouble(score1));
                    Log.e("update","更新了哦");
                    studentdao.update(res);
                    //弹出添加成功提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Info_Update_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("更新成功" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
            }
        });

        //为btn_back添加返回点击事件
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Info_Update_Activity.this,Show_Info_Activity.class);
                intent.putExtra("id",res.getId());
                intent.putExtra("city",res.getCity());
                intent.putExtra("name",res.getName());
                intent.putExtra("score",res.getScore());
                intent.putExtra("img",AppUtil.bitmap_to_byte(res.getImg()));
                setResult(1,intent);
                finish();
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
                head.setImageURI(uri);
                try {
                    img = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}