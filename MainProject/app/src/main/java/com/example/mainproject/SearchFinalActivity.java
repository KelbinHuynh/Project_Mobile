package com.example.mainproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.mainproject.Adapter.HomePetsAdapter;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.Pets;
import com.example.mainproject.ResponeAPI.RequestPet;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFinalActivity extends AppCompatActivity {
    private RecyclerView rvListPetSearch;
    private boolean isChoose;
    private JsonArray datas;
    private ImageButton icback;
    private HomePetsAdapter petsListAdapter;

    APIService apiService;
    SharedPreferences sharedpreferences;
    private GridLayoutManager gridLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_final);
        AnhXa();
        List<Integer> styleList = new ArrayList<>();
        sharedpreferences = getSharedPreferences("dataFilter", Context.MODE_PRIVATE);
        for(int i = 1; i<100; i++){
            isChoose = sharedpreferences.getBoolean(String.valueOf(i), false);
            if(isChoose){
                styleList.add(i);
            }
        }

//        datas = new JsonArray();
//        for(int j = 0; j < styleList.size(); j++){
//            datas.add(styleList.get(j));
//        }
//        JsonObject object = new JsonObject();
//        object.add("style", datas);
//        Log.d("TestJSON", object.getAsString());

        RequestPet styleidList = new RequestPet(styleList);

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear().commit();
                finish();
                startActivity(new Intent(SearchFinalActivity.this, FilterActivity.class));
            }
        });
        filterPetToStyle(styleidList);
    }

    private void AnhXa(){
        rvListPetSearch = (RecyclerView) findViewById(R.id.rvListPetSearch);
        icback = (ImageButton) findViewById(R.id.icback);
    }

    private void filterPetToStyle(RequestPet requestPet){
        List<Pets> listPets = new ArrayList<>();
        apiService = RetrofitClient.getInstrance();

        apiService.filterPetToStyle(requestPet).enqueue(new Callback<List<Pets>>() {
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
                    petsListAdapter =  new HomePetsAdapter(SearchFinalActivity.this, listPets);
                    rvListPetSearch.setAdapter(petsListAdapter);
                    gridLayoutManager = new GridLayoutManager(SearchFinalActivity.this,2 );
                    rvListPetSearch.setLayoutManager(gridLayoutManager);
                }else {
                    Toast.makeText(SearchFinalActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pets>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
