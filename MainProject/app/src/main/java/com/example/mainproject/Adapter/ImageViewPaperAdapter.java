package com.example.mainproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mainproject.Model.Images;
import com.example.mainproject.R;

import java.util.List;

public class ImageViewPaperAdapter extends PagerAdapter {
    private List<Images> imagesList;

    public ImageViewPaperAdapter(List<Images> imagesList){this.imagesList = imagesList;}

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_images_indicator, container, false);
        ImageView imageView =view.findViewById(R.id.imgViewIndica);
        Images images = imagesList.get(position);
        imageView.setImageResource(images.getImagesId());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(imagesList != null){
            return imagesList.size();
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
