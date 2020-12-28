package com.example.students_info_manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.students_info_manager.Util.AppUtil;
import com.example.students_info_manager.Util.Student;

import java.util.ArrayList;

/*
    用于实现数据库的一系列CURD操作
 */
public class Dao {
    private final DatabaseHelper helper;

    public Dao(Context context){
        helper=new DatabaseHelper(context);
    }

    public void insert(Student student){
        SQLiteDatabase db=helper.getWritableDatabase();
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加学号
        cValue.put("ID",student.getId());
        //添加姓名
        cValue.put("name",student.getName());
        //添加籍贯
        cValue.put("city",student.getCity());
        //添加成绩
        cValue.put("score",student.getScore());
        //添加头像
        cValue.put("img", AppUtil.bitmap_to_byte(student.getImg()));
    //调用insert()方法插入数据
        db.insert("Student",null,cValue);
        db.close();
    }

    public void delete(Student student){
        SQLiteDatabase db=helper.getWritableDatabase();
        //删除条件
        String whereClause = "id=?";
        //删除条件参数
        String[] whereArgs = {student.getId()};
        //执行删除
        db.delete("Student",whereClause,whereArgs);
        db.close();
    }

    public ArrayList<Student> getALL(){
        SQLiteDatabase db=helper.getReadableDatabase();
        ArrayList<Student> result=new ArrayList<>();
        //查询获得游标
        Cursor cursor = db.query ("Student",null,null,null,null,null,null);
        //遍历列表
        while (cursor.moveToNext())
        {
             Student student=new Student();
             //读取信息
             student.setId(cursor.getString(cursor.getColumnIndex("ID")));
             student.setCity(cursor.getString(cursor.getColumnIndex("city")));
             student.setName(cursor.getString(cursor.getColumnIndex("name")));
             student.setScore(cursor.getDouble(cursor.getColumnIndex("score")));
             student.setImg(AppUtil.byte_to_bitmap(cursor.getBlob(cursor.getColumnIndex("img"))));
             result.add(student);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void update(Student student){
        SQLiteDatabase db=helper.getWritableDatabase();
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加学号
        cValue.put("ID",student.getId());
        //添加姓名
        cValue.put("name",student.getName());
        //添加籍贯
        cValue.put("city",student.getCity());
        //添加成绩
        cValue.put("score",student.getScore());
        //添加头像
        cValue.put("img", AppUtil.bitmap_to_byte(student.getImg()));
        //修改条件
        String whereClause = "ID=?";
        //修改添加参数
        String[] whereArgs={student.getId()};
        //修改
        db.update("Student",cValue,whereClause,whereArgs);
        db.close();
    }


}
