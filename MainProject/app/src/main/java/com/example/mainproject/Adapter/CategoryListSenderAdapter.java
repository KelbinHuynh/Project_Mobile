package com.example.mainproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.example.mainproject.R;
import com.example.mainproject.ResponeAPI.ResponseCategoryPet;


import java.util.List;

public class CategoryListSenderAdapter extends RecyclerView.Adapter<CategoryListSenderAdapter.CategoryListSenderViewHolder>{

    private List<ResponseCategoryPet> catePetsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private PetListSenderAdapter petsListAdapter;
    private LinearLayoutManager linearLayoutManager;

    public CategoryListSenderAdapter(Context context, List<ResponseCategoryPet> datas){
        mContext = context;
        catePetsList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public CategoryListSenderAdapter.CategoryListSenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.list_item_sender_category, parent, false);
        return new CategoryListSenderAdapter.CategoryListSenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListSenderAdapter.CategoryListSenderViewHolder holder, int position) {

        ResponseCategoryPet categoryPet = catePetsList.get(position);


        holder.category_name.setText("Thú cưng: " + categoryPet.getCategory_name());
        Log.d("CheckSender", categoryPet.getCategory_name());
        Log.d("CheckSender", categoryPet.getPets().get(0).getPets_name());
        petsListAdapter =  new PetListSenderAdapter(holder.rvPets.getContext(), categoryPet.getPets());
        holder.rvPets.setAdapter(petsListAdapter);
        linearLayoutManager = new LinearLayoutManager(holder.rvPets.getContext());
        holder.rvPets.setLayoutManager(linearLayoutManager);

    }

    @Override
    public int getItemCount() {
        return catePetsList.size();
    }

    class CategoryListSenderViewHolder extends RecyclerView.ViewHolder{
        private TextView category_name;
        private RecyclerView rvPets;

        public  CategoryListSenderViewHolder(View itemView){
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            rvPets = (RecyclerView) itemView.findViewById(R.id.rvPets);
        }
    }
}
