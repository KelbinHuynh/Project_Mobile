package com.example.mainproject.Adapter;

import static com.example.mainproject.Constant.Const.MY_FRAGMENT;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.MenuFragmentActivity;
import com.example.mainproject.Model.*;
import com.example.mainproject.R;
import com.example.mainproject.ResponeAPI.ResponseToAPI;
import com.example.mainproject.SenderFragmentActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListSenderAdapter extends RecyclerView.Adapter<OrderListSenderAdapter.OrderListSenderViewHolder>{
    private List<Order> orderList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OrderItemSenderAdapter orderItemSenderAdapter;

    private LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedpreferences;
    APIService apiService;

    public OrderListSenderAdapter(Context context, List<Order> datas){
        mContext = context;
        orderList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public OrderListSenderAdapter.OrderListSenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.list_item_sender_order, parent, false);
        return new OrderListSenderAdapter.OrderListSenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListSenderAdapter.OrderListSenderViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        sharedpreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Order order = orderList.get(position);

        holder.txtOrderId.setText("Mã đơn hàng: " + String.valueOf(order.getOrder_id()));
        holder.addressOrder.setText(order.getAddress());
        holder.phoneOrder.setText(order.getPhone());
        holder.statusOrder.setText(order.getStatus_order().getStatus_name());

        if(order.getStatus_order().getStatus_id() == 1){
            holder.btnUpdateStatus.setText("Giao Hàng");
        }else if(order.getStatus_order().getStatus_id() == 2){
            holder.btnUpdateStatus.setText("Đã giao");
        }else holder.btnUpdateStatus.setVisibility(View.GONE);

        double sum = 0;
        for(OrderDetail orderDetail: order.getOrder_detail()){
            sum = sum + orderDetail.getCount_SP() * orderDetail.getPets().getPrice();
        }
        holder.sumProduct.setText(currencyFormatter.format(sum));

        orderItemSenderAdapter =  new OrderItemSenderAdapter(holder.rvOrder.getContext(), order.getOrder_detail());
        holder.rvOrder.setAdapter(orderItemSenderAdapter);
        linearLayoutManager = new LinearLayoutManager(holder.rvOrder.getContext());
        holder.rvOrder.setLayoutManager(linearLayoutManager);

        holder.btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(order.getStatus_order().getStatus_id() == 1){
                    updateStatusToOrder(2, order.getOrder_id());
                }else if(order.getStatus_order().getStatus_id() == 2){
                    updateStatusToOrder(3, order.getOrder_id());
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 1).apply();
                view.getContext().startActivity(new Intent(view.getContext(), SenderFragmentActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderListSenderViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rvOrder;
        private TextView txtOrderId, sumProduct, statusOrder, addressOrder, phoneOrder;
        private Button btnUpdateStatus;

        public  OrderListSenderViewHolder(View itemView){
            super(itemView);
            rvOrder = (RecyclerView) itemView.findViewById(R.id.rvOrder);
            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);
            sumProduct = (TextView) itemView.findViewById(R.id.sumProduct);
            statusOrder = (TextView) itemView.findViewById(R.id.statusOrder);
            addressOrder = (TextView) itemView.findViewById(R.id.addressOrder);
            phoneOrder = (TextView) itemView.findViewById(R.id.phoneOrder);
            btnUpdateStatus = (Button) itemView.findViewById(R.id.btnUpdateStatus);
        }
    }

    private void updateStatusToOrder(int status_order, int order_id){
        apiService = RetrofitClient.getInstrance();
        apiService.updateStatusOrder(status_order, order_id).enqueue(new Callback<ResponseToAPI>() {
            @Override
            public void onResponse(Call<ResponseToAPI> call, Response<ResponseToAPI> response) {
                if(response.isSuccessful()){
                    ResponseToAPI responseToAPI = response.body();
                }
            }

            @Override
            public void onFailure(Call<ResponseToAPI> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
