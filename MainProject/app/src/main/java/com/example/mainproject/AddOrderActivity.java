package com.example.mainproject;

import static com.example.mainproject.Constant.Const.MY_FRAGMENT;
import static com.example.mainproject.Constant.Const.MY_ID;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.ResponseToAPI;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity {

    private EditText editAddressOrder, editPhoneOrder;
    private TextView sumProduct;
    private Button btnAddOrder;
    private ImageButton btnBack;
    private RecyclerView rvAddOrder;
    private AddOrderItemAdapter addOrderItemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Cart cart;
    private ResponseToAPI responseToAPI;

    APIService apiService;
    SharedPreferences sharedpreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        AnhXa();
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(MY_ID, null);

        getCartToUser(Integer.parseInt(user_id));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 1).apply();
                startActivity(new Intent(AddOrderActivity.this, MenuFragmentActivity.class));
            }
        });

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 3).apply();
                String address = editAddressOrder.getText().toString();
                String phone = editPhoneOrder.getText().toString();

                addOrderToUser(Integer.parseInt(user_id), address, phone);

                startActivity(new Intent(AddOrderActivity.this, MenuFragmentActivity.class));
            }
        });

    }

    private void AnhXa(){
        editAddressOrder = (EditText) findViewById(R.id.editAddressOrder);
        editPhoneOrder = (EditText) findViewById(R.id.editPhoneOrder);
        sumProduct = (TextView) findViewById(R.id.sumProduct);
        btnAddOrder = (Button) findViewById(R.id.btnAddOrder);
        rvAddOrder = (RecyclerView) findViewById(R.id.rvAddOrder);
        btnBack = (ImageButton) findViewById(R.id.icback);
    }

    private void getCartToUser(int user_id){
        apiService = RetrofitClient.getInstrance();
        apiService.getCartToUser(user_id).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){

                    cart = response.body();

                    addOrderItemAdapter = new AddOrderItemAdapter(AddOrderActivity.this, cart.getCart_detail());

                    rvAddOrder.setAdapter(addOrderItemAdapter);
                    linearLayoutManager = new LinearLayoutManager(AddOrderActivity.this);
                    rvAddOrder.setLayoutManager(linearLayoutManager);

                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                    double sum = 0;
                    for(CartDetail cartDetail: cart.getCart_detail()){
                        sum = sum + cartDetail.getCount_SP() * cartDetail.getPets().getPrice();
                    }

                    sumProduct.setText(currencyFormatter.format(sum));
                }else {
                    Toast.makeText(AddOrderActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void addOrderToUser(int user_id, String address, String phone){
        apiService = RetrofitClient.getInstrance();
        apiService.addOrderToUser(user_id, address, phone).enqueue(new Callback<ResponseToAPI>() {
            @Override
            public void onResponse(Call<ResponseToAPI> call, Response<ResponseToAPI> response) {
                if(response.isSuccessful()){

                    responseToAPI = response.body();

                }else {
                    Toast.makeText(AddOrderActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseToAPI> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
