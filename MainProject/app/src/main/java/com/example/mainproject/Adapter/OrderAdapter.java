package com.example.mainproject.Adapter;

import static com.example.mainproject.Constant.Const.MY_FRAGMENT;
import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private List<Order> orderList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OrderItemAdapter orderItemAdapter;

    private LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedpreferences;
    APIService apiService;

    public OrderAdapter(Context context, List<Order> datas){
        mContext = context;
        orderList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_order, parent, false);
        return new OrderAdapter.OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        sharedpreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Order order = orderList.get(position);

        holder.txtOrderId.setText("Mã đơn hàng: " + String.valueOf(order.getOrder_id()));
        holder.addressOrder.setText(order.getAddress());
        holder.phoneOrder.setText(order.getPhone());
        holder.statusOrder.setText(order.getStatus_order().getStatus_name());

        if(order.getStatus_order().getStatus_id() == 3){
            holder.btnUserUpdateStatus.setText("Xác nhận nhận hàng");
        }else holder.btnUserUpdateStatus.setVisibility(View.GONE);

        double sum = 0;
        for(OrderDetail orderDetail: order.getOrder_detail()){
            sum = sum + orderDetail.getCount_SP() * orderDetail.getPets().getPrice();
        }
        holder.sumProduct.setText(currencyFormatter.format(sum));

        orderItemAdapter =  new OrderItemAdapter(holder.rvOrder.getContext(), order.getOrder_detail());
        holder.rvOrder.setAdapter(orderItemAdapter);
        linearLayoutManager = new LinearLayoutManager(holder.rvOrder.getContext());
        holder.rvOrder.setLayoutManager(linearLayoutManager);

        holder.btnUserUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(order.getStatus_order().getStatus_id() == 3){
                    updateStatusToOrder(4, order.getOrder_id());
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 3).apply();
                view.getContext().startActivity(new Intent(view.getContext(), MenuFragmentActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rvOrder;
        private TextView txtOrderId, sumProduct, statusOrder, addressOrder, phoneOrder;
        private Button btnUserUpdateStatus;

        public  OrderViewHolder(View itemView){
            super(itemView);
            rvOrder = (RecyclerView) itemView.findViewById(R.id.rvOrder);
            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);
            sumProduct = (TextView) itemView.findViewById(R.id.sumProduct);
            statusOrder = (TextView) itemView.findViewById(R.id.statusOrder);
            addressOrder = (TextView) itemView.findViewById(R.id.addressOrder);
            phoneOrder = (TextView) itemView.findViewById(R.id.phoneOrder);
            btnUserUpdateStatus = (Button) itemView.findViewById(R.id.btnUserUpdateStatus);
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
