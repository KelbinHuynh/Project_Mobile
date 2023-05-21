package com.example.mainproject;

import static com.example.mainproject.Constant.Const.*;

import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Adapter.*;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.*;

public class OrderFragment extends Fragment {

    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvOrder;

    APIService apiService;
    SharedPreferences sharedpreferences;
    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_order, container, false);


        AnhXa();
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(MY_ID, null);

        getOrderToUser(Integer.parseInt(user_id));

        return root;
    }

    private void AnhXa(){
        rvOrder = root.findViewById(R.id.rvOrder);
    }

    private void getOrderToUser(int user_id){
        apiService = RetrofitClient.getInstrance();
        apiService.getOrderToUser(user_id).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){

                    orderList = response.body();

                    orderAdapter = new OrderAdapter(getContext(), orderList);

                    rvOrder.setAdapter(orderAdapter);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rvOrder.setLayoutManager(linearLayoutManager);


                }else {
                    Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
