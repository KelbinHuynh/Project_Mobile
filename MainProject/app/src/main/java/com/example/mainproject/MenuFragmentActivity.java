package com.example.mainproject;

import static android.content.ContentValues.TAG;
import static com.example.mainproject.Constant.Const.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.mainproject.Adapter.DrawerItem;
import com.example.mainproject.Adapter.DrawerMenuAdapter;
import com.example.mainproject.Adapter.SimpleItem;
import com.example.mainproject.Adapter.SpaceItem;
import com.roger.catloadinglibrary.CatLoadingView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MenuFragmentActivity extends AppCompatActivity implements DrawerMenuAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_CART = 1;
    private static final int POS_ACCOUNT = 2;
    private static final int POS_ORDER = 3;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    SharedPreferences sharedpreferences;
    private TextView user_name;
    private CircleImageView user_avatar;
    private SlidingRootNav slidingRootNav;
    MyAsyncTask myAsyncTask;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_root);

        Toolbar toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
        myAsyncTask = new MyAsyncTask(MenuFragmentActivity.this);
        myAsyncTask.execute();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu_container)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();


        DrawerMenuAdapter adapter = new DrawerMenuAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_CART),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_ORDER),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        user_name = (TextView) findViewById(R.id.user_name);
        user_avatar = (CircleImageView) findViewById(R.id.user_avatar);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(MY_NAME, null);
        String avatar = sharedpreferences.getString(MY_AVATAR, null);
        int fragmentSelect = sharedpreferences.getInt(MY_FRAGMENT, 0);
        Log.d(TAG, String.valueOf(fragmentSelect));
        if(name == null){
            user_name.setText("Đăng nhập");
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenuFragmentActivity.this, LoginActivity.class));
                }
            });
        }else{
            user_name.setText(name);
        }
        if(avatar == null){
            user_avatar.setImageResource(R.drawable.default_avatar);
        }else Glide.with(this).load(avatar).into(user_avatar);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(fragmentSelect);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            Intent intent = new Intent(MenuFragmentActivity.this, LoginActivity.class);
            sharedpreferences.edit().clear().commit();
            finish();
            startActivity(intent);
        }

        slidingRootNav.closeMenu();
        Log.d("Test: ",String.valueOf(position));
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String id = sharedpreferences.getString(MY_ID, null);
        if (position == POS_HOME){
            HomeFragment homeFragment = new HomeFragment();
            showFragment(homeFragment);
        }

        if (position == POS_ACCOUNT){
            if (id == null){
                startActivity(new Intent(MenuFragmentActivity.this, LoginActivity.class));
            }else{
                ProfileFragment profileFragment = new ProfileFragment();
                showFragment(profileFragment);
            }

        }

        if(position == POS_CART){
            if (id == null){
                startActivity(new Intent(MenuFragmentActivity.this, LoginActivity.class));
            }else{
                CartFragment cartFragment = new CartFragment();
                showFragment(cartFragment);
            }

        }

        if(position == POS_ORDER){
            if (id == null){
                startActivity(new Intent(MenuFragmentActivity.this, LoginActivity.class));
            }else{
                OrderFragment orderFragment = new OrderFragment();
                showFragment(orderFragment);
            }

        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


}
