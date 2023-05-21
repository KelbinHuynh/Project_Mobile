package com.example.mainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Model.*;
import com.example.mainproject.R;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.CategoryFilterHolder>{

    private List<Category> categoryList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private GridLayoutManager gridLayoutManager;
    private CategoryStyleAdapter categoryStyleAdapter;
    public CategoryFilterAdapter(Context context, List<Category> datas){
        mContext = context;
        categoryList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryFilterAdapter.CategoryFilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_list_style_cate, parent, false);
        return new CategoryFilterAdapter.CategoryFilterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFilterAdapter.CategoryFilterHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtNameCate.setText(category.getCategory_name());
        categoryStyleAdapter =new CategoryStyleAdapter(holder.rvListStyle.getContext(), category.getStyleList());
        holder.rvListStyle.setAdapter(categoryStyleAdapter);
        gridLayoutManager = new GridLayoutManager(holder.rvListStyle.getContext(),3);
        holder.rvListStyle.setLayoutManager(gridLayoutManager);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryFilterHolder extends RecyclerView.ViewHolder{
        private TextView txtNameCate;
        private RecyclerView rvListStyle;

        public  CategoryFilterHolder(View itemView){
            super(itemView);
            rvListStyle = (RecyclerView) itemView.findViewById(R.id.rvListStyle);
            txtNameCate = (TextView) itemView.findViewById(R.id.txtNameCate);
        }
    }
}
