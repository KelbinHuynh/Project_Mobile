package com.example.mainproject.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Model.Style;
import com.example.mainproject.R;

import java.util.List;

public class CategoryStyleAdapter extends RecyclerView.Adapter<CategoryStyleAdapter.CategoryStyleViewHolder>{
    private List<Style> styleList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean isChoose;
    SharedPreferences sharedpreferences;

    public CategoryStyleAdapter(Context context, List<Style> datas){
        mContext = context;
        styleList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public CategoryStyleAdapter.CategoryStyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_category_style, parent, false);
        return new CategoryStyleAdapter.CategoryStyleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryStyleAdapter.CategoryStyleViewHolder holder, int position) {
        Style style = styleList.get(position);
        int i = position;
        sharedpreferences = mContext.getSharedPreferences("dataFilter", Context.MODE_PRIVATE);
        holder.btnStyle.setText(style.getStyle_name());
        holder.btnStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChoose = sharedpreferences.getBoolean(String.valueOf(i+1), false);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(!isChoose){
                    holder.btnStyle.setBackground(mContext.getResources().getDrawable(R.drawable.custom_texview_filter_choose));
                    editor.putBoolean(String.valueOf(i+1), true).apply();
                }else{
                    holder.btnStyle.setBackground(mContext.getResources().getDrawable(R.drawable.custom_textview_filter));
                    editor.putBoolean(String.valueOf(i+1), false).apply();
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return styleList.size();
    }

    class CategoryStyleViewHolder extends RecyclerView.ViewHolder{
        private Button btnStyle;

        public  CategoryStyleViewHolder(View itemView){
            super(itemView);
            btnStyle = (Button) itemView.findViewById(R.id.btnStyle);
        }
    }
}
