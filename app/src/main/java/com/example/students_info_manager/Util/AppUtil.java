package com.example.students_info_manager.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/*
    工具类
    提供一些辅助方法
 */
public class AppUtil {
    /*
    传入bitmap将其转为字节流数组
 */
    public static byte[]  bitmap_to_byte(Bitmap bitmap)
    {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap .compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] head_img = stream.toByteArray();
            return head_img;
        }
        else {
            return null;
        }
    }

    /*
        传入byte数组转为bitmap
     */
    public static Bitmap  byte_to_bitmap(byte[] head_img){
        Bitmap bitmap= BitmapFactory.decodeByteArray(head_img , 0, head_img.length);
        return bitmap;
    }

    /*
    对传入的Arraylis按照学号排序
    flag=1,升序，flag=0降序
     */
    public static void sortbyid(ArrayList<Student> datas, int flag)
    {
        Collections.sort(datas, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return Integer.valueOf(student.getId())-Integer.valueOf(t1.getId());
            }
        });
        if (flag==0)
        {
            Collections.reverse(datas);
        }
    }

    /*
        对传入的Arraylis按照姓名排序
        flag=1,升序，flag=0降序
     */
    public static void sortbyname(ArrayList<Student> datas,int flag)
    {
        Collections.sort(datas, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return Collator.getInstance(Locale.CHINESE).compare(student.getName(),t1.getName());
            }
        });
        if (flag==0)
        {
            Collections.reverse(datas);
        }
    }

    /*
        对传入的Arraylis按照籍贯排序
        flag=1,升序，flag=0降序
     */
    public static void sortbyciyt(ArrayList<Student> datas,int flag)
    {
        Collections.sort(datas, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return Collator.getInstance(Locale.CHINESE).compare(student.getCity(),t1.getCity());
            }
        });
        if (flag==0)
        {
            Collections.reverse(datas);
        }
    }

/*
        对传入的Arraylis按照成绩排序
        flag=1,升序，flag=0降序
     */
    public  static  void sortbyscore(ArrayList<Student> datas,int flag)
    {
        Collections.sort(datas, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                double res=student.getScore()-t1.getScore();
                if (res>0)
                {
                    return 1;
                }
                else if (res<0)
                {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        if (flag==0)
        {
            Collections.reverse(datas);
        }
    }


/*
    条件过滤
 */
    public static void studentfilter(ArrayList<Student> org_datas,ArrayList<Student> datas,String id,String name,String city,String score) {
        datas.clear();
        for (Student student:org_datas)
        {
            if (student.getId().indexOf(id)==0&&student.getName().indexOf(name)==0&&student.getCity().indexOf(city)==0&&(student.getScore()+"").indexOf(score)==0){
                datas.add(student);
                Log.e("studentfilter: ",student.getId() );
            }
        }
    }

}
