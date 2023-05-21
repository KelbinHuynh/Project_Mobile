package com.example.mainproject;

import static com.example.mainproject.Constant.Const.MY_ID;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.example.mainproject.Adapter.OrderAdapter;
import com.example.mainproject.Adapter.OrderListSenderAdapter;
import com.example.mainproject.Api.*;
import com.example.mainproject.Model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagerFragment extends Fragment {
    private List<Order> orderList;
    private OrderListSenderAdapter orderListSenderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvOrder;

    APIService apiService;
    SharedPreferences sharedpreferences;
    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_sender_order, container, false);


        AnhXa();

        getAllOrder();

        return root;
    }

    private void AnhXa(){
        rvOrder = root.findViewById(R.id.rvOrder);
    }

    private void getAllOrder(){
        apiService = RetrofitClient.getInstrance();
        apiService.getAllOrder().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()){

                    orderList = response.body();

                    orderListSenderAdapter = new OrderListSenderAdapter(getContext(), orderList);

                    rvOrder.setAdapter(orderListSenderAdapter);
                    linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    rvOrder.setLayoutManager(linearLayoutManager);
                    orderListSenderAdapter.notifyDataSetChanged();

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
