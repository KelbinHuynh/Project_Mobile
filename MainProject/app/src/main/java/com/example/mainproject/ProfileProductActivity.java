package com.example.mainproject;

import static com.example.mainproject.Constant.Const.MY_FRAGMENT;
import static com.example.mainproject.Constant.Const.MY_ID;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import androidx.viewpager.widget.ViewPager;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.ResponseToAPI;

import me.relex.circleindicator.CircleIndicator;
import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileProductActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImageButton btnBack;
    private TextView txtPetName, txtAgePet, txtWeightPet;
    private ImageView myfavorite, imgGender;
    private RecyclerView listStylePets;
    private Button btnAddCart;
    private LinearLayoutManager linearLayoutManager;
    private StylePetInforAdapter styleListAdapter;
    private StepperTouch stepperTouch;
    private int countCart, maxCount ;
    APIService apiService;
    SharedPreferences sharedpreferences;
    int idPet;
    private boolean isFav = false;


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == 3){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_product);

        AnhXa();
        stepperTouch.setMinValue(0);
        Intent intent = getIntent();
        idPet = intent.getIntExtra("pets_id", 1);

        getInformationPet(idPet);

    }

    private void AnhXa(){
        viewPager = (ViewPager) findViewById(R.id.imgPetImage);
        circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        btnBack = (ImageButton) findViewById(R.id.icback);
        txtPetName = (TextView) findViewById(R.id.txtPetName);
        txtAgePet = (TextView) findViewById(R.id.txtAgePet);
        txtWeightPet = (TextView) findViewById(R.id.txtWeightPet);
        listStylePets = (RecyclerView) findViewById(R.id.listStylePets);
        btnAddCart = (Button) findViewById(R.id.btnAddCart);
        stepperTouch = (StepperTouch) findViewById(R.id.stepperTouch);
        imgGender = (ImageView) findViewById(R.id.imgGender);
        myfavorite = (ImageView) findViewById(R.id.myfavorite);

    }

    private void getInformationPet(int id){
        apiService = RetrofitClient.getInstrance();
        apiService.selectInformationPet(id).enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {
                if(response.isSuccessful()){
                    Pets petInfor = response.body();
                    InforPetViewPageAdapter adapter = new InforPetViewPageAdapter(petInfor);
                    viewPager.setAdapter(adapter);
                    circleIndicator.setViewPager(viewPager);
                    autoRun();

                    Log.d("TestInfor", petInfor.getPets_name());
                    txtPetName.setText(petInfor.getPets_name());
                    txtWeightPet.setText(petInfor.getWeight().toString());
                    txtAgePet.setText(petInfor.getAge());
                    if(petInfor.getGender() == 1){
                        imgGender.setImageResource(R.drawable.ic_male);
                    }else{
                        imgGender.setImageResource(R.drawable.ic_female);
                    }
                    myfavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!isFav){
                                myfavorite.setImageResource(R.drawable.ic_favorite);
                                isFav = true;
                            }else {
                                myfavorite.setImageResource(R.drawable.ic_unfavorite);
                                isFav = false;
                            }
                        }
                    });

                    stepperTouch.setMaxValue(petInfor.getCount());

                    stepperTouch.addStepCallback(new OnStepCallback() {
                        @Override
                        public void onStep(int value, boolean b) {
                            countCart = value;
                            Log.d("CountCart", String.valueOf(countCart));
                            if (value > 0){
                                btnAddCart.setEnabled(true);
                            }else btnAddCart.setEnabled(false);
                        }
                    });

                    styleListAdapter =  new StylePetInforAdapter(ProfileProductActivity.this, petInfor.getStyleList());

                    listStylePets.setAdapter(styleListAdapter);
                    linearLayoutManager = new LinearLayoutManager(ProfileProductActivity.this, RecyclerView.HORIZONTAL, false);
                    listStylePets.setLayoutManager(linearLayoutManager);

                    btnAddCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkProductToCart(petInfor.getPets_id(), countCart);
                        }
                    });

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProfileProductActivity.this, MenuFragmentActivity.class);
                            startActivity(intent);
                        }
                    });

                }else {
                    Toast.makeText(ProfileProductActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });

    }

    private void checkProductToCart(int pet_id, int count_Sp){

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        int user_id = Integer.
                parseInt(sharedpreferences.getString(MY_ID, null));
        apiService = RetrofitClient.getInstrance();
        apiService.checkProductToCart(user_id, pet_id).enqueue(new Callback<ResponseToAPI>() {
            @Override
            public void onResponse(Call<ResponseToAPI> call, Response<ResponseToAPI> response) {
                if(response.isSuccessful()){
                    ResponseToAPI responseToAPI = response.body();
                    if (!responseToAPI.isSuccess()){
                        insertProductToCart(user_id, pet_id, count_Sp);
                    }else{
                        Toast.makeText(ProfileProductActivity.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(ProfileProductActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseToAPI> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void insertProductToCart(int user_id, int pets_id, int count_SP){
        apiService = RetrofitClient.getInstrance();
        apiService.addProductToCart(user_id, pets_id, count_SP).enqueue(new Callback<ResponseToAPI>() {
            @Override
            public void onResponse(Call<ResponseToAPI> call, Response<ResponseToAPI> response) {
                if(response.isSuccessful()){
                    ResponseToAPI responseToAPI = response.body();
                    if (responseToAPI.isSuccess()){
                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(MY_FRAGMENT, 1).apply();
                        Toast.makeText(ProfileProductActivity.this, "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ProfileProductActivity.this, MenuFragmentActivity.class));
                    }else{
                        Toast.makeText(ProfileProductActivity.this, "Thêm sản phẩm vào giỏ hàng không thành công!", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(ProfileProductActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseToAPI> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void autoRun(){

        //call runable
        handler.postDelayed(runnable, 6000);

        //lắng nghe viewpaper chuyển trang
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 6000);
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
