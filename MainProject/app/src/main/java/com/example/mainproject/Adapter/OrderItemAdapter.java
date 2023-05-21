package com.example.mainproject.Adapter;

import static com.example.mainproject.Constant.Const.SHARED_PREFS;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Model.Order;
import com.example.mainproject.Model.OrderDetail;
import com.example.mainproject.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>{
    private List<OrderDetail> orderDetailList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    double sumPrice;
    SharedPreferences sharedpreferences;

    public OrderItemAdapter(Context context, List<OrderDetail> datas){
        mContext = context;
        orderDetailList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public OrderItemAdapter.OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_orderitem, parent, false);
        return new OrderItemAdapter.OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.OrderItemViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        sharedpreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        OrderDetail orderDetail = orderDetailList.get(position);

        holder.txtNameProduct.setText(orderDetail.getPets().getPets_name());
        holder.txtPriceProduct.setText(currencyFormatter.format(orderDetail.getPets().getPrice()));
        holder.countProduct.setText(String.valueOf(orderDetail.getCount_SP()));
        Glide.with(mContext).load(orderDetail.getPets().getImagesList().get(0).getImageLink()).into(holder.imgProduct);
        sumPrice = orderDetail.getCount_SP() * orderDetail.getPets().getPrice();
        holder.txtSumPrice.setText(currencyFormatter.format(sumPrice));
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView txtNameProduct, txtPriceProduct, txtSumPrice, countProduct;

        public  OrderItemViewHolder(View itemView){
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtNameProduct = (TextView) itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            txtSumPrice = (TextView) itemView.findViewById(R.id.txtSumPrice);
            countProduct = (TextView) itemView.findViewById(R.id.countProduct);
        }
    }
}
