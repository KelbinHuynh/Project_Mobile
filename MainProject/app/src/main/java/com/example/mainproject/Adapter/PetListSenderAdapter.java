package com.example.mainproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.Pets;
import com.example.mainproject.R;
import com.example.mainproject.ResponeAPI.ResponseCategoryPet;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PetListSenderAdapter extends RecyclerView.Adapter<PetListSenderAdapter.PetListSenderViewHolder>{


    private List<Pets> petsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LinearLayoutManager linearLayoutManager;
    private StyleListSenderAdapter styleListSenderAdapter;

    public PetListSenderAdapter(Context context, List<Pets> datas){
        mContext = context;
        petsList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public PetListSenderAdapter.PetListSenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.list_item_sender_pet, parent, false);
        return new PetListSenderAdapter.PetListSenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetListSenderAdapter.PetListSenderViewHolder holder, int position) {

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        Pets pet = petsList.get(position);

        holder.txtNameProduct.setText(pet.getPets_name());
        holder.txtPriceProduct.setText(currencyFormatter.format(pet.getPrice()));
        holder.countProduct.setText(String.valueOf(pet.getCount()));
        Glide.with(mContext).load(pet.getImagesList().get(0).getImageLink()).into(holder.imgProduct);


        styleListSenderAdapter =  new StyleListSenderAdapter(holder.rvStyle.getContext(), pet.getStyleList());
        holder.rvStyle.setAdapter(styleListSenderAdapter);
        linearLayoutManager = new LinearLayoutManager(holder.rvStyle.getContext());
        holder.rvStyle.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }

    class PetListSenderViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameProduct, txtPriceProduct, countProduct;
        private RecyclerView rvStyle;
        private ImageView imgProduct;
        private Button btnUpdateProduct, btnDeleteProduct;

        public  PetListSenderViewHolder(View itemView){
            super(itemView);
            txtNameProduct = (TextView) itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            countProduct = (TextView) itemView.findViewById(R.id.countProduct);

            rvStyle = (RecyclerView) itemView.findViewById(R.id.rvStyle);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            btnUpdateProduct = (Button) itemView.findViewById(R.id.btnUpdateProduct);
            btnDeleteProduct = (Button) itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}
