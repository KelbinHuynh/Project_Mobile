package com.example.mainproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mainproject.Adapter.ImageViewPaperAdapter;
import com.example.mainproject.Model.Images;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<Images> imagesList;
    ViewGroup root;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);

        AnhXa();
        imagesList = getListImages();
        ImageViewPaperAdapter adapter = new ImageViewPaperAdapter(imagesList);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        autoRun();

        return root;
    }

    private void AnhXa(){
        viewPager = root.findViewById(R.id.viewpage);
        circleIndicator = root.findViewById(R.id.circle_indicator);
    }

    private List<Images> getListImages(){
        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.cat_drive_1));
        list.add(new Images(R.drawable.background_login));
        list.add(new Images(R.drawable.cat_drive_1));
        list.add(new Images(R.drawable.background_login));
        return list;
    }

    //Auto Rung Item in Slide
    private void autoRun(){

        //call runable
        handler.postDelayed(runnable, 3000);

        //lắng nghe viewpaper chuyển trang
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume(){
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}
