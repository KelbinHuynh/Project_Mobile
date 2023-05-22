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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Adapter.CategoryFilterAdapter;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView rvListCategory;
    private TextView txtFemale, txtMale;
    private Button btnReset, btnSearch;
    private ImageButton icback;
    APIService apiService;
    SharedPreferences sharedpreferences;
    List<Category> categoryList;
    private LinearLayoutManager linearLayoutManager;
    private CategoryFilterAdapter categoryFilterAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        AnhXa();
        sharedpreferences = getSharedPreferences("dataFilter", Context.MODE_PRIVATE);
        getAllCategory();
    }

    private void AnhXa(){
        rvListCategory = (RecyclerView) findViewById(R.id.rvListCategory);
        txtMale = (TextView) findViewById(R.id.txtMale);
        txtFemale = (TextView) findViewById(R.id.txtFemale);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnReset = (Button) findViewById(R.id.btnReset);
        icback = (ImageButton) findViewById(R.id.icback);
    }

    private void getAllCategory(){
        apiService = RetrofitClient.getInstrance();
        apiService.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoryList = response.body();
                    Log.d("TAGName", categoryList.get(0).getCategory_name());
                    categoryFilterAdapter = new CategoryFilterAdapter(FilterActivity.this, categoryList);
                    rvListCategory.setAdapter(categoryFilterAdapter);
                    linearLayoutManager = new LinearLayoutManager(FilterActivity.this);
                    rvListCategory.setLayoutManager(linearLayoutManager);

                    btnReset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear().commit();
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(FilterActivity.this, SearchFinalActivity.class));
                        }
                    });

                    icback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear().commit();
                            finish();
                            startActivity(new Intent(FilterActivity.this, MenuFragmentActivity.class));
                        }
                    });

                }else {
                    Toast.makeText(FilterActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
