package com.example.mainproject;

import static com.example.mainproject.Constant.Const.MY_ID;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView sumProduct, txtNotification;
    private Button btnOrder;
    SharedPreferences sharedpreferences;
    private LinearLayoutManager linearLayoutManager;
    private CartItemAdapter cartItemAdapter;
    private Cart cart;
    APIService apiService;

    ViewGroup root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);


        AnhXa();
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(MY_ID, null);

        getCartToUser(Integer.parseInt(user_id));

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(new Intent(getActivity(), AddOrderActivity.class)));
            }
        });

        return root;
    }

    private void AnhXa(){
        rvCart = root.findViewById(R.id.rvCart);
        sumProduct = root.findViewById(R.id.sumProduct);
        btnOrder = root.findViewById(R.id.btnOrder);
        txtNotification = root.findViewById(R.id.txtNotification);
    }

    private void getCartToUser(int user_id){
        apiService = RetrofitClient.getInstrance();
        apiService.getCartToUser(user_id).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){

                    cart = response.body();

                    if(cart.getCart_detail().size() == 0){
                        txtNotification.setText("Không có sản phẩm trong giỏ hàng");
                        btnOrder.setEnabled(false);
                    }

                    cartItemAdapter = new CartItemAdapter(getContext(), cart.getCart_detail());

                    rvCart.setAdapter(cartItemAdapter);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rvCart.setLayoutManager(linearLayoutManager);

                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                    double sum = 0;
                    for(CartDetail cartDetail: cart.getCart_detail()){
                        sum = sum + cartDetail.getCount_SP() * cartDetail.getPets().getPrice();
                    }

                    sumProduct.setText(currencyFormatter.format(sum));
                }else {
                    Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
