package com.example.mainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Model.Style;
import com.example.mainproject.R;

import java.util.List;

public class StyleListSenderAdapter extends RecyclerView.Adapter<StyleListSenderAdapter.StyleListSenderViewHolder>{
    private List<Style> styleList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public StyleListSenderAdapter(Context context, List<Style> datas){
        mContext = context;
        styleList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public StyleListSenderAdapter.StyleListSenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_category_style, parent, false);
        return new StyleListSenderAdapter.StyleListSenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleListSenderAdapter.StyleListSenderViewHolder holder, int position) {
        Style style = styleList.get(position);
        holder.btnStyle.setText(style.getStyle_name());

    }

    @Override
    public int getItemCount(){
        return styleList.size();
    }

    class StyleListSenderViewHolder extends RecyclerView.ViewHolder{
        private Button btnStyle;

        public  StyleListSenderViewHolder(View itemView){
            super(itemView);
            btnStyle = (Button) itemView.findViewById(R.id.btnStyle);
        }
    }
}
