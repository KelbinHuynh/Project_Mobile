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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.MenuFragmentActivity;
import com.example.mainproject.Model.*;
import com.example.mainproject.R;
import com.example.mainproject.ResponeAPI.ResponseToAPI;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{

    private List<CartDetail> cartDetails;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    int count;
    double sumPrice;
    APIService apiService;
    SharedPreferences sharedpreferences;

    public CartItemAdapter(Context context, List<CartDetail> datas){
        mContext = context;
        cartDetails = datas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public CartItemAdapter.CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mLayoutInflater.inflate(R.layout.item_list_cart, parent, false);
        return new CartItemAdapter.CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.CartItemViewHolder holder, int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        sharedpreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        CartDetail cartDetail = cartDetails.get(position);

        Glide.with(mContext).load(cartDetail.getPets().getImagesList().get(0).getImageLink()).into(holder.imgProduct);

        holder.txtNameProduct.setText(cartDetail.getPets().getPets_name());
        holder.txtPriceProduct.setText(currencyFormatter.format(cartDetail.getPets().getPrice()));
        holder.countProduct.setText(String.valueOf(cartDetail.getCount_SP()));
        count = cartDetail.getCount_SP();
        sumPrice = cartDetail.getCount_SP() * cartDetail.getPets().getPrice();
        holder.txtSumPrice.setText(currencyFormatter.format(sumPrice));
        if (count == 1){
            holder.btnMinus.setEnabled(false);
        }else {
            holder.btnMinus.setEnabled(true);
        }

        if (count == cartDetail.getPets().getCount()){
            holder.btnPlus.setEnabled(false);
        }else {
            holder.btnPlus.setEnabled(true);
        }

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                UpdateCountOfItem(count,cartDetail.getCartdetail_id());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 1).apply();
                view.getContext().startActivity(new Intent(view.getContext(), MenuFragmentActivity.class));
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count - 1;
                UpdateCountOfItem(count,cartDetail.getCartdetail_id());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 1).apply();
                view.getContext().startActivity(new Intent(view.getContext(), MenuFragmentActivity.class));
            }
        });

        holder.btndeleteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDetailToCart(cartDetail.getCartdetail_id());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 1).apply();
                view.getContext().startActivity(new Intent(view.getContext(), MenuFragmentActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartDetails.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private Button btnPlus, btnMinus;
        private TextView txtNameProduct, txtPriceProduct, txtSumPrice, countProduct;
        private ImageButton btndeleteDetail;

        public  CartItemViewHolder(View itemView){
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            btnPlus = (Button) itemView.findViewById(R.id.btnPlus);
            btnMinus = (Button) itemView.findViewById(R.id.btnMinus);
            txtNameProduct = (TextView) itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            txtSumPrice = (TextView) itemView.findViewById(R.id.txtSumPrice);
            countProduct = (TextView) itemView.findViewById(R.id.countProduct);
            btndeleteDetail = (ImageButton) itemView.findViewById(R.id.btndeleteDetail);
        }
    }

    private void UpdateCountOfItem(int count_SP, int cartdetail_id){
        apiService = RetrofitClient.getInstrance();
        apiService.updateCountOfItem(count_SP, cartdetail_id).enqueue(new Callback<ResponseToAPI>() {
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

    private void DeleteDetailToCart(int cartdetail_id){
        apiService = RetrofitClient.getInstrance();
        apiService.deleteCartDetail(cartdetail_id).enqueue(new Callback<ResponseToAPI>() {
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
