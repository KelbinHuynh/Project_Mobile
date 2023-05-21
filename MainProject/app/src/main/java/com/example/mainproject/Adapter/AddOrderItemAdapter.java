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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Model.CartDetail;
import com.example.mainproject.Model.OrderDetail;
import com.example.mainproject.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AddOrderItemAdapter extends RecyclerView.Adapter<AddOrderItemAdapter.AddOrderItemViewHolder>{
    private List<CartDetail> cartDetailList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    double sumPrice;
    SharedPreferences sharedpreferences;

    public AddOrderItemAdapter(Context context, List<CartDetail> datas){
        mContext = context;
        cartDetailList = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public AddOrderItemAdapter.AddOrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_orderitem, parent, false);
        return new AddOrderItemAdapter.AddOrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddOrderItemAdapter.AddOrderItemViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        sharedpreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        CartDetail cartDetail = cartDetailList.get(position);

        holder.txtNameProduct.setText(cartDetail.getPets().getPets_name());
        holder.txtPriceProduct.setText(currencyFormatter.format(cartDetail.getPets().getPrice()));
        holder.countProduct.setText(String.valueOf(cartDetail.getCount_SP()));
        Glide.with(mContext).load(cartDetail.getPets().getImagesList().get(0).getImageLink()).into(holder.imgProduct);
        sumPrice = cartDetail.getCount_SP() * cartDetail.getPets().getPrice();
        holder.txtSumPrice.setText(currencyFormatter.format(sumPrice));
    }

    @Override
    public int getItemCount() {
        return cartDetailList.size();
    }

    class AddOrderItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView txtNameProduct, txtPriceProduct, txtSumPrice, countProduct;

        public  AddOrderItemViewHolder(View itemView){
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtNameProduct = (TextView) itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            txtSumPrice = (TextView) itemView.findViewById(R.id.txtSumPrice);
            countProduct = (TextView) itemView.findViewById(R.id.countProduct);
        }
    }
}
