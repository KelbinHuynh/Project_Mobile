package com.example.mainproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.Pets;
import com.example.mainproject.R;
import com.example.mainproject.ResponeAPI.ResponseCategoryPet;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CategoryPetsAdapter extends RecyclerView.Adapter<CategoryPetsAdapter.CategoryPetsViewHolder>{
    private boolean isFav = false;
    private List<ResponseCategoryPet> catePetsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private HomePetsAdapter petsListAdapter;
    private GridLayoutManager gridLayoutManager;

    public CategoryPetsAdapter(Context context, List<ResponseCategoryPet> datas){
        mContext = context;
        catePetsList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public CategoryPetsAdapter.CategoryPetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_categorypet, parent, false);
        return new CategoryPetsAdapter.CategoryPetsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPetsAdapter.CategoryPetsViewHolder holder, int position) {

        ResponseCategoryPet categoryPet = catePetsList.get(position);

        holder.category_name.setText(categoryPet.getCategory_name());
        petsListAdapter =  new HomePetsAdapter(holder.rvCategoryPets.getContext(), categoryPet.getPets());
        holder.rvCategoryPets.setAdapter(petsListAdapter);
        gridLayoutManager = new GridLayoutManager(holder.rvCategoryPets.getContext(),2 );
        holder.rvCategoryPets.setLayoutManager(gridLayoutManager);

    }

    @Override
    public int getItemCount() {
        return catePetsList.size();
    }

    class CategoryPetsViewHolder extends RecyclerView.ViewHolder{
        private TextView category_name;
        private RecyclerView rvCategoryPets;

        public  CategoryPetsViewHolder(View itemView){
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            rvCategoryPets = (RecyclerView) itemView.findViewById(R.id.listCategoryPets);
        }
    }
}
