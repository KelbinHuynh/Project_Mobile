package com.example.mainproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.*;
import com.example.mainproject.R;

public class InforPetViewPageAdapter  extends PagerAdapter {
    private Pets pet;

    public InforPetViewPageAdapter(Pets pet){
        this.pet = pet;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_images_indicator, container, false);
        ImageView imgViewIndica = view.findViewById(R.id.imgViewIndica);
        ImagesPet imagesPet = pet.getImagesList().get(position);
        Glide.with(container).load(imagesPet.getImageLink()).into(imgViewIndica);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(pet.getImagesList() != null){
            return pet.getImagesList().size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }
}
