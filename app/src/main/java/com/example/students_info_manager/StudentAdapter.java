package com.example.students_info_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.students_info_manager.Util.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewholder> {

    private ArrayList<Student> datas;
    private Context context;
    //定义删除和更新按钮的点击回调接口
    public interface OnDeleteButtonClickListener
    {
        void onClick(Student student,int position);
    }

    public interface OnUpdateButtonClickListener
    {
        void onClick(int positon);
    }
    //声明接口
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnUpdateButtonClickListener onUpdateButtonClickListener;
    //定义修改相应监听器的方法
    public  void setOnDeleteButtonClickListener(OnDeleteButtonClickListener onDeleteButtonClickListener)
    {
        this.onDeleteButtonClickListener=onDeleteButtonClickListener;
    }

    public void  setOnUpdateButtonClickListener(OnUpdateButtonClickListener onUpdateButtonClickListener)
    {
        this.onUpdateButtonClickListener=onUpdateButtonClickListener;
    }

    public StudentAdapter(ArrayList<Student> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewholder myViewHolder = new viewholder(LayoutInflater.from(context).inflate(R.layout.item_show_student,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
            holder.img_head.setImageBitmap(datas.get(position).getImg());
            holder.textView_num.setText((String)datas.get(position).getId());
            holder.textView_name.setText((String)datas.get(position).getName());
            holder.textView_city.setText((String)datas.get(position).getCity());
            holder.textView_score.setText(datas.get(position).getScore()+"");
            holder.imageButton_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteButtonClickListener!=null)
                    {
                        Student s=datas.get(position);
                        Student student=new Student();
                        student.setScore(s.getScore());
                        student.setId(s.getId());
                        student.setCity(s.getCity());
                        student.setName(s.getName());
                        student.setImg(s.getImg());
                        onDeleteButtonClickListener.onClick(student,position);
                    }
                }
            });
            holder.imageButton_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onUpdateButtonClickListener!=null)
                    {
                        onUpdateButtonClickListener.onClick(position);
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        private ImageView img_head;
        private TextView textView_num;
        private TextView textView_name;
        private TextView textView_city;
        private TextView textView_score;
        private ImageButton imageButton_update;
        private ImageButton imageButton_delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_head=itemView.findViewById(R.id.img_show_head);
            textView_num=itemView.findViewById(R.id.tex_num);
            textView_name=itemView.findViewById(R.id.tex_name);
            textView_city=itemView.findViewById(R.id.tex_city);
            textView_score=itemView.findViewById(R.id.tex_score);
            imageButton_update=itemView.findViewById(R.id.btn_show_update);
            imageButton_delete=itemView.findViewById(R.id.btn_show_delete);
        }
    }


}
