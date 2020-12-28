package com.example.students_info_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.students_info_manager.Util.AppUtil;
import com.example.students_info_manager.Util.Student;
import com.example.students_info_manager.database.Dao;

import java.util.ArrayList;

public class Show_Info_Activity extends AppCompatActivity {
    private Spinner spinner_sort;
    private Spinner spinner_sort_way;
    //下拉框数据源
    static String[] sort_way={"学号","姓名","籍贯","成绩"};
    static String[] sort_up_down={"升序","降序"};
    //由于 spinner初始化时默认执行点击事件，添加条件变量来取消这一设置
    private Boolean is_first_sw;
    private Boolean is_first_sud;

    //学生数据表操作对象
    private Dao studentDao;
    //从数据库获得的数据集
    private ArrayList<Student> orign_datas;
    //实际传入recyclerview的数据集
    private ArrayList<Student> datas;
    //recyclerview适配器
    private StudentAdapter rev_adapter;
    private RecyclerView recyclerView;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private ImageButton btn_show_back;

    public Show_Info_Activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("学生成绩信息展示");
        setContentView(R.layout.activity_show__info_);
        spinner_sort=findViewById(R.id.spn_sort);
        spinner_sort_way=findViewById(R.id.spn_sortway);
        //初始化变量
        is_first_sud=true;
        is_first_sw=true;
        //初始化spinner适配器
        ArrayAdapter<String> adapter_sort=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sort_up_down);
        ArrayAdapter<String> adapter_sort_way=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sort_way);
        spinner_sort.setAdapter(adapter_sort);
        spinner_sort_way.setAdapter(adapter_sort_way);
        //给两个spinner添加选择监听事件
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(is_first_sud)
                {
                    is_first_sud=false;
                }
                else {
                    on_spinner_change();
                    rev_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_sort_way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (is_first_sw){
                    is_first_sw=false;
                }
                else {
                    on_spinner_change();
                    rev_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //从数据库获取数据
        studentDao=new Dao(Show_Info_Activity.this);
        datas=new ArrayList<>();
        orign_datas=new ArrayList<>();
        datas=studentDao.getALL();
        orign_datas=studentDao.getALL();
        //定义recyclerview适配器
        rev_adapter=new StudentAdapter(datas,Show_Info_Activity.this);
        //添加删除点击事件
        rev_adapter.setOnDeleteButtonClickListener(new StudentAdapter.OnDeleteButtonClickListener() {

            @Override
            public void onClick(final Student student, final int position) {
                final Student student1=student;
                final int position1=position;
                //弹出提示框确认是否删除
                AlertDialog.Builder builder  = new AlertDialog.Builder(Show_Info_Activity.this);
                builder.setTitle("确认" ) ;
                builder.setMessage("是否删除？" ) ;
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在数据库中删除
                        studentDao.delete(student1);
                        //在recyclerview中删除
                        datas.remove(position1);
                        //刷新recyclerview页面
                        rev_adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();



            }
        });
        //添加点击更新事件
        rev_adapter.setOnUpdateButtonClickListener(new StudentAdapter.OnUpdateButtonClickListener() {
            @Override
            public void onClick(int positon) {
                Intent intent= new Intent(Show_Info_Activity.this,Info_Update_Activity.class);
                intent.putExtra("id",datas.get(positon).getId());
                intent.putExtra("name",datas.get(positon).getName());
                intent.putExtra("city",datas.get(positon).getCity());
                intent.putExtra("score",datas.get(positon).getScore());
                byte[] img=AppUtil.bitmap_to_byte(datas.get(positon).getImg());
                intent.putExtra("img",img);
                startActivityForResult(intent,1);
            }
        });
        recyclerView=findViewById(R.id.recyv);
        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //添加适配器
        recyclerView.setAdapter(rev_adapter);

        editText1=findViewById(R.id.search_num);
        editText2=findViewById(R.id.search_name);
        editText3=findViewById(R.id.search_city);
        editText4=findViewById(R.id.search_score);
        /*
            为编辑框添加文本信息改变监听器
         */
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("s","变了");
                on_editText_change();
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                on_editText_change();
            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                on_editText_change();
            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                on_editText_change();
            }
        });
        /*
            添加返回事件
         */
        btn_show_back=findViewById(R.id.btn_show_back);
        btn_show_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Show_Info_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //设置默认进入的排序方式 升序 学号
        AppUtil.sortbyid(datas,1);
        rev_adapter.notifyDataSetChanged();
    }


    /*
    spinner变化时调用方法
     */
    private void on_spinner_change()
    {
        String upordown=(String) spinner_sort.getSelectedItem();
        String sortway=(String) spinner_sort_way.getSelectedItem();
        Log.e("sortway",sortway);
        int flag=-1;
        if (upordown=="降序"){
            flag=0;
        }
        else {
            flag=1;
        }
        switch (sortway){
            case "学号":
                AppUtil.sortbyid(datas,flag);
                break;
            case "姓名":
                AppUtil.sortbyname(datas,flag);
                break;
            case "籍贯":
                AppUtil.sortbyciyt(datas,flag);
                break;
            case "成绩":
                AppUtil.sortbyscore(datas,flag);
                break;
        }
    }

    /*
    editText变化时调用方法
     */
    private void on_editText_change()
    {
        String id=editText1.getText().toString();
        String name=editText2.getText().toString();
        String city=editText3.getText().toString();
        String score=editText4.getText().toString();
        AppUtil.studentfilter(orign_datas,datas,id,name,city,score);
        on_spinner_change();
        rev_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {
            orign_datas=studentDao.getALL();
            for (Student s: datas)
            {
                if (data.getStringExtra("id").equals(s.getId()))
                {
                    s.setName(data.getStringExtra("name"));
                    s.setCity(data.getStringExtra("city"));
                    s.setScore(data.getDoubleExtra("score",0));
                    s.setImg(AppUtil.byte_to_bitmap(data.getByteArrayExtra("img")));
                }
            }
            on_spinner_change();
            rev_adapter.notifyDataSetChanged();
        }
    }
}