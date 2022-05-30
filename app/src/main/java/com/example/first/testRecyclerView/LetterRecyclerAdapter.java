package com.example.first.testRecyclerView;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.R;
import com.example.first.letters.Letters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LetterRecyclerAdapter extends RecyclerView.Adapter<LetterRecyclerAdapter.ViewHolder> {
    private List<Letters> mlettersList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //获取
        ImageView imv;
        TextView name;
        TextView count;
        ////保存子项最外层布局，分别为lettersView和imv注册点击事件
        View lettersView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lettersView = itemView;//保存子项最外层布局
            imv = itemView.findViewById(R.id.letter_image);
            name = itemView.findViewById(R.id.letter_name);
            count = itemView.findViewById(R.id.letter_count);
        }
    }

    //
    public LetterRecyclerAdapter(List<Letters> lettersList) {
        mlettersList = lettersList;
    }

    @NonNull
    @Override
    public LetterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //用于创建ViewHolder实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.letters_item2, null, false);//这里的null写parent会使单个item占满屏幕
        ViewHolder holder = new ViewHolder(view);

        //分别注册点击事件
        holder.lettersView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取点击的position，对应的name
                int position = holder.getAdapterPosition();
                Letters letters = mlettersList.get(position);

                Toast.makeText(v.getContext(), "你点击了view： " + letters.getName(), Toast.LENGTH_SHORT).show();

            }
        });
        holder.imv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Letters letters = mlettersList.get(position);

                Toast.makeText(v.getContext(), "你点击了image： " + letters.getName(), Toast.LENGTH_SHORT).show();

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LetterRecyclerAdapter.ViewHolder holder, int position) {
        //赋值
        Letters letters = mlettersList.get(position);
        holder.imv.setImageResource(letters.getImageId());
        holder.name.setText(letters.getName());
        holder.count.setText(letters.getCount());
    }

    public int getItemCount() {
        return mlettersList.size();
    }
}
