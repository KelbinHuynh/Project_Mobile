package com.example.mainproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.ResponeAPI.ResponseCategoryPet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductManagerFragment extends Fragment {
    APIService apiService;
    private CategoryListSenderAdapter categoryPetsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvCategoryList;
    private Button btnInsertProduct;
    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_sender_product, container, false);
        AnhXa();
        getListPets();
        return root;
    }

    private void AnhXa(){
        rvCategoryList = root.findViewById(R.id.rvCategoryList);
        btnInsertProduct = root.findViewById(R.id.btnInsertProduct);
    }

    private void getListPets(){
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
                    categoryPetsAdapter =  new CategoryListSenderAdapter(getContext(), listcategoryPets);
                    rvCategoryList.setAdapter(categoryPetsAdapter);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rvCategoryList.setLayoutManager(linearLayoutManager);
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
}
