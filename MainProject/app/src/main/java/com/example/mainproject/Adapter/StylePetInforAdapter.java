package com.example.mainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Model.*;
import com.example.mainproject.R;

import java.util.List;

public class StylePetInforAdapter extends RecyclerView.Adapter<StylePetInforAdapter.StylePetsViewHolder>{
    private List<Style> styleList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public StylePetInforAdapter(Context context, List<Style> datas){
        mContext = context;
        styleList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public StylePetInforAdapter.StylePetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_style, parent, false);
        return new StylePetInforAdapter.StylePetsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StylePetsViewHolder holder, int position) {
        Style style = styleList.get(position);
        holder.btnStyle.setText(style.getStyle_name());
    }

    @Override
    public int getItemCount(){
        return styleList.size();
    }

    class StylePetsViewHolder extends RecyclerView.ViewHolder{
        private Button btnStyle;

        public  StylePetsViewHolder(View itemView){
            super(itemView);
            btnStyle = (Button) itemView.findViewById(R.id.btnStyle);
        }
    }
}
