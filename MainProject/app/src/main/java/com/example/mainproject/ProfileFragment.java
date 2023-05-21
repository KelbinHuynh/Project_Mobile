package com.example.mainproject;

import static com.example.mainproject.Constant.Const.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.ResponseUpload;


import retrofit2.*;

public class ProfileFragment extends Fragment {

    private ImageView imgAvatar;
    private TextView txtMaID, txtNameLogin;
    private EditText txtName, txtEmail, txtGender, txtAddress, txtPhone;
    private Button btnEditName, btnEditEmail, btnEditAddress, btnEditPhone, btnSaveProfile;
    SharedPreferences sharedpreferences;
    APIService apiService;
    private ResponseUpload responseUpload;


    ViewGroup root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        root = (ViewGroup) inflater.inflate(R.layout.fragment_profile_user, container, false);
        AnhXa();

        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(MY_NAME, null);
        String avatar = sharedpreferences.getString(MY_AVATAR, null);
        String user_id = sharedpreferences.getString(MY_ID, null);
        String gender_name = sharedpreferences.getString(MY_GENDER, null);
        String email = sharedpreferences.getString(MY_EMAIL, null);
        String phone = sharedpreferences.getString(MY_PHONE, null);
        String addresses = sharedpreferences.getString(MY_ADDRESSES, null);
        String username = sharedpreferences.getString(MY_USERNAME, null);

        txtNameLogin.setText(username);
        txtName.setText(name);
        txtMaID.setText(user_id);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtAddress.setText(addresses);
        txtGender.setText(gender_name);
        if(avatar == null){
            imgAvatar.setImageResource(R.drawable.default_avatar);
        }else{
            Glide.with(getActivity()).load(avatar).into(imgAvatar);
        }


        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtName.setEnabled(true);
                txtName.setFocusable(true);
            }
        });
        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail.setEnabled(true);
            }
        });
        btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtAddress.setEnabled(true);
            }
        });
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPhone.setEnabled(true);
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateImageActivity.class));
            }
        });

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String addresses = txtAddress.getText().toString();
                String phone = txtPhone.getText().toString();

                editInforUser(name, email, addresses, phone, Integer.parseInt(user_id));
            }
        });

        return root;
    }

    private void AnhXa(){
        imgAvatar = root.findViewById(R.id.imgAvatar);
        txtMaID = root.findViewById(R.id.txtMaID);
        txtNameLogin = root.findViewById(R.id.txtNameLogin);
        txtName = root.findViewById(R.id.txtName);
        txtEmail = root.findViewById(R.id.txtEmail);
        txtGender = root.findViewById(R.id.txtGender);
        txtAddress = root.findViewById(R.id.txtAddress);
        txtPhone = root.findViewById(R.id.txtPhone);
        btnEditName = root.findViewById(R.id.btnEditName);
        btnEditEmail = root.findViewById(R.id.btnEditEmail);
        btnEditAddress = root.findViewById(R.id.btnEditAddress);
        btnEditPhone = root.findViewById(R.id.btnEditPhone);
        btnSaveProfile = root.findViewById(R.id.btnSaveProfile);

    }

    private void editInforUser(String name, String email, String addresses, String phone, int user_id){
        apiService = RetrofitClient.getInstrance();
        apiService.editInforUser(name, email, addresses, phone, user_id).enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                if(response.isSuccessful()){

                    responseUpload = response.body();

                    User user = responseUpload.getUser();
                    sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MY_NAME, user.getName());
                    editor.putString(MY_ADDRESSES, user.getAddresses());
                    editor.putString(MY_PHONE, user.getPhone());
                    editor.putString(MY_EMAIL, user.getEmail());
                    editor.putInt(MY_FRAGMENT, 2);
                    editor.apply();

                    getActivity().finish();
                    startActivity(new Intent(getActivity(), MenuFragmentActivity.class));

                }else {
                    Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}
