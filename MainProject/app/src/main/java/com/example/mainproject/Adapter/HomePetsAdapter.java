package com.example.mainproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.Pets;
import com.example.mainproject.ProfileProductActivity;
import com.example.mainproject.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomePetsAdapter extends RecyclerView.Adapter<HomePetsAdapter.NewPetsViewHolder> {
    private boolean isFav = false;
    private List<Pets> petsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public HomePetsAdapter(Context context, List<Pets> datas){
        mContext = context;
        petsList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NewPetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_homepets, parent, false);
        return new NewPetsViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(NewPetsViewHolder holder, int position){
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        Pets pet = petsList.get(position);
        holder.txtPetName.setText(pet.getPets_name());

        holder.txtPetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.txtPetName.getContext(), ProfileProductActivity.class);
                intent.putExtra("pets_id", pet.getPets_id());
                holder.txtPetName.getContext().startActivity(intent);
            }
        });


        Log.d("TestImageToPet", pet.getImagesList().get(0).getImageLink().toString());

        Glide.with(mContext).load(pet.getImagesList().get(0).getImageLink()).into(holder.imgPetImage);

        holder.imgPetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imgPetImage.getContext(), ProfileProductActivity.class);
                intent.putExtra("pets_id", pet.getPets_id());
                holder.imgPetImage.getContext().startActivity(intent);
            }
        });

        if(pet.getGender() == 1){
            holder.imgGender.setImageResource(R.drawable.ic_male);
        }else{
            holder.imgGender.setImageResource(R.drawable.ic_female);
        }
        holder.txtPrice.setText(currencyFormatter.format(pet.getPrice()));
        holder.myfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFav){
                    holder.myfavorite.setImageResource(R.drawable.ic_favorite);
                    isFav = true;
                }else {
                    holder.myfavorite.setImageResource(R.drawable.ic_unfavorite);
                    isFav = false;
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return petsList.size();
    }

    class NewPetsViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPetName;
        private ImageView imgGender;
        private TextView txtPrice;
        private ImageView myfavorite;
        private ImageView imgPetImage;

        public  NewPetsViewHolder(View itemView){
            super(itemView);
            txtPetName = (TextView) itemView.findViewById(R.id.txtPetName);
            imgGender = (ImageView) itemView.findViewById(R.id.imgGender);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            myfavorite = (ImageView) itemView.findViewById(R.id.myfavorite);
            imgPetImage = (ImageView) itemView.findViewById(R.id.imgPetImage);
        }
    }
}
