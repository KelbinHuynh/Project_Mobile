package com.example.mainproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.ResponeAPI.ResponseSales;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesFragment extends Fragment {
    TextView txtCountOrder;
    TextView txtSumSales;
    APIService apiService;
    LineChart chartView;
    List<ResponseSales> responseSalesList;
    ViewGroup root;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.sales_statistic, container, false);


        AnhXa();
        getSales();
        return root;
    }

    private void AnhXa(){
//        txtSumSales = (TextView) root.findViewById(R.id.txtSumSales);
//        txtCountOrder = (TextView) root.findViewById(R.id.txtCountOrder);
        chartView = (LineChart) root.findViewById(R.id.chartView);
    }

    private void getSales(){
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        apiService = RetrofitClient.getInstrance();
        apiService.getSales().enqueue(new Callback<List<ResponseSales>>() {

            @Override
            public void onResponse(Call<List<ResponseSales>> call, Response<List<ResponseSales>> response) {
                responseSalesList = response.body();
                ArrayList<Entry> visitors = new ArrayList<>();
                ArrayList<String> theMonths = new ArrayList<>();

                for(int i = 0; i < responseSalesList.size(); i++){
                    Log.d("DateSales", formatter.format(responseSalesList.get(i).getDateOrder()));
                    visitors.add(new Entry(responseSalesList.get(i).getDateOrder(), (float)(responseSalesList.get(i).getSumSales()/1000000)));
                    theMonths.add("Tháng " + responseSalesList.get(i).getDateOrder());
                }
                visitors.add(new BarEntry(7, 420));
                visitors.add(new BarEntry(8, 420));
                LineDataSet lineDataSet = new LineDataSet(visitors, "Doanh Thu");
                lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSet.setValueTextColor(Color.BLACK);
                lineDataSet.setValueTextSize(16f);

//                XAxis xAxis = chartView.getXAxis();
//                xAxis.setValueFormatter(new IndexAxisValueFormatter(theMonths));

                LineData lineData = new LineData(lineDataSet);
                chartView.setData(lineData);
                chartView.getDescription().setText("Doanh thu theo tháng");
                chartView.animateY(2000);


//                Log.d("count", Integer.toString(responseSales.getCountOrder()));
//                Log.d("sum", Double.toString(responseSales.getSumSales()));
//                txtCountOrder.setText(Integer.toString(responseSales.getCountOrder()));
//                txtSumSales.setText(currencyFormatter.format(responseSales.getSumSales()));
            }

            @Override
            public void onFailure(Call<List<ResponseSales>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                Log.d("fail", t.toString());
            }
        });
    }

}
