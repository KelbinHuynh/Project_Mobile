package com.example.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.ResponseCategoryPet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private RecyclerView rvNewPets;
    private RecyclerView rvCategoryPets;
    private ImageView imgSearch;
    private HomePetsAdapter petsListAdapter;
    private CategoryPetsAdapter categoryPetsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    APIService apiService;

    User user;

    ViewGroup root;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == 4){
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

        PetsViewPaperAdapter adapter = new PetsViewPaperAdapter();
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        autoRun();

        getListNewPets();
        getListCategoryPets();

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(new Intent(getActivity(), FilterActivity.class)));
            }
        });

        return root;
    }

    private void AnhXa(){
        viewPager = root.findViewById(R.id.viewpage);
        circleIndicator = root.findViewById(R.id.circle_indicator);
        rvNewPets = root.findViewById(R.id.listNewPets);
        rvCategoryPets = root.findViewById(R.id.listCategoryPets);
        imgSearch = root.findViewById(R.id.imgSearch);
    }

    private void getListNewPets(){
        List<Pets> listPets = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        apiService = RetrofitClient.getInstrance();

        apiService.selectAvailableNewPet().enqueue(new Callback<List<Pets>>() {
            @Override
            public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                if(response.isSuccessful()){

                    List<Pets> petsList = response.body();

                    for (Pets pets: petsList){
                        Pets pet = new Pets();
                        pet.setPets_id(pets.getPets_id());
                        pet.setPets_name(pets.getPets_name());
                        pet.setWeight(pets.getWeight());
                        pet.setAge(pets.getAge());
                        pet.setGender(pets.getGender());
                        pet.setPrice(pets.getPrice());
                        pet.setCount(pets.getCount());
                        pet.setRated(pets.getRated());
                        pet.setNumOfRate(pets.getNumOfRate());
                        pet.setCreatedAt(pets.getCreatedAt());
                        pet.setUpdatedAt(pets.getUpdatedAt());

                        pet.setImagesList(pets.getImagesList());
                        pet.setStyleList(pets.getStyleList());
                        Log.d("Test Pet", pet.getPrice().toString());
                        listPets.add(pet);
                    }
//                    listPets.get(0).getPets_name();
                    //Set RecycleView for New Pets
                    petsListAdapter =  new HomePetsAdapter(getContext(), listPets);
                    rvNewPets.setAdapter(petsListAdapter);
                    gridLayoutManager = new GridLayoutManager(getContext(),2 );
                    rvNewPets.setLayoutManager(gridLayoutManager);
                }else {
                    Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pets>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void getListCategoryPets(){
        List<ResponseCategoryPet> listcategoryPets = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        apiService = RetrofitClient.getInstrance();

        apiService.selectAvailableCategoryPet().enqueue(new Callback<List<ResponseCategoryPet>>() {
            @Override
            public void onResponse(Call<List<ResponseCategoryPet>> call, Response<List<ResponseCategoryPet>> response) {
                if(response.isSuccessful()){
                    List<ResponseCategoryPet> listPets = response.body();
                    for(ResponseCategoryPet categoryPet : listPets){
                        ResponseCategoryPet pets = new ResponseCategoryPet();
                        pets.setCategory_id(categoryPet.getCategory_id());
                        pets.setCategory_name(categoryPet.getCategory_name());
                        pets.setCreatedAt(categoryPet.getCreatedAt());
                        pets.setUpdatedAt(categoryPet.getUpdatedAt());
                        pets.setPets(categoryPet.getPets());
                        listcategoryPets.add(pets);
                    }
                    categoryPetsAdapter =  new CategoryPetsAdapter(getContext(), listcategoryPets);
                    rvCategoryPets.setAdapter(categoryPetsAdapter);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rvCategoryPets.setLayoutManager(linearLayoutManager);
                }else {
                    Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCategoryPet>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }


    //Auto Rung Item in Slide
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
