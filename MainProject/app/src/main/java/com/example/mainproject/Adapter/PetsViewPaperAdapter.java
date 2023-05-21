package com.example.mainproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.*;
import com.example.mainproject.R;

import java.util.List;

public class PetsViewPaperAdapter extends PagerAdapter {
    private List<Pets> petsList;

    public PetsViewPaperAdapter() {

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_images_indicator, container, false);
        ImageView imgViewIndica = view.findViewById(R.id.imgViewIndica);
        imgViewIndica.setImageResource(R.drawable.pet_test);
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        return 4;
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
