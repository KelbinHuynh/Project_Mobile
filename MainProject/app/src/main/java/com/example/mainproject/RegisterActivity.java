package com.example.mainproject;

import static com.example.mainproject.Constant.Const.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.User;
import com.example.mainproject.ResponeAPI.ResponseRegister;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    int gender_id = 1;
    Button btnRegister;
    TextView txtLoginPage;
    EditText editTxtUsername, editTxtPassword, editTxtConfirmPassword, editTxtEmail, editTxtPhone, editTxtAddresses, editTxtName;
    RadioGroup radio_group;
    RadioButton radioMale, radioFemale;
    ImageView imgShowHidePass;
    APIService apiService;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        setClickable();
        ShowHidePass();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch(checkId){
                    case R.id.radioMale:
                        gender_id = 1;
                        break;

                    case R.id.radioFemale:
                        gender_id = 2;
                        break;
                }
            }
        });
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
    }

    //Ánh xạ các nút trong xml
    private void AnhXa(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtLoginPage = (TextView) findViewById(R.id.txtLoginPage);
        editTxtUsername = (EditText) findViewById(R.id.editTxtUsername);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        editTxtConfirmPassword = (EditText) findViewById(R.id.editTxtConfirmPassword);
        editTxtName = (EditText) findViewById(R.id.editTxtName);
        editTxtEmail = (EditText) findViewById(R.id.editTxtEmail);
        editTxtPhone = (EditText) findViewById(R.id.editTxtPhone);
        editTxtAddresses = (EditText) findViewById(R.id.editTxtAddresses);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        imgShowHidePass = (ImageView) findViewById(R.id.imgShowHidePass);
    }

    //thực hiện ClickAble cho textview để hiển thị trang LoginPage
    private void setClickable(){
        //Lấy giá trị text trong textview đưa vào ss
        SpannableString ss = new SpannableString(txtLoginPage.getText().toString());
        //Thực hiện show forgotpass page bằng clickable khi kích vào nút Login

        ClickableSpan showLoginPage = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        ss.setSpan(showLoginPage,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLoginPage.setText(ss);
        txtLoginPage.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void ShowHidePass(){
        imgShowHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTxtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    //Show Password
                    imgShowHidePass.setImageResource(R.drawable.icons8_eye_1);
                    editTxtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    //Hide Password
                    imgShowHidePass.setImageResource(R.drawable.icons8_hide);
                    editTxtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


    private void userRegister(){
        final String username = editTxtUsername.getText().toString().trim();
        final String password = editTxtPassword.getText().toString().trim();
        final String confirmPassword = editTxtConfirmPassword.getText().toString().trim();
        final String name = editTxtName.getText().toString().trim();
        final String email = editTxtEmail.getText().toString().trim();
        final String phone = editTxtPhone.getText().toString().trim();
        final String address = editTxtAddresses.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            editTxtUsername.setError("Please enter your username");
            editTxtUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            editTxtPassword.setError("Please enter your password");
            editTxtPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            editTxtConfirmPassword.setError("Please enter your confirm password");
            editTxtConfirmPassword.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)){
            editTxtConfirmPassword.setError("Please enter your correct confirm");
            editTxtConfirmPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)){
            editTxtUsername.setError("Please enter your name");
            editTxtUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)){
            editTxtEmail.setError("Please enter your email");
            editTxtEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)){
            editTxtPhone.setError("Please enter your phone");
            editTxtPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)){
            editTxtAddresses.setError("Please enter your address");
            editTxtAddresses.requestFocus();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        apiService = RetrofitClient.getInstrance();
        apiService.register(username, password, name, gender_id, email, phone, address).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.isSuccessful()){
                    ResponseRegister responseRegister = response.body();
                    if (responseRegister.getMessage().equals("Create succcessful")){
                        User user = responseRegister.getUser();

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(MY_USERNAME, user.getUsername());
                        editor.putString(MY_ID, String.valueOf(user.getUser_id()));
                        editor.putString(MY_NAME, user.getName());
                        editor.putString(MY_EMAIL, user.getEmail());
                        editor.putString(MY_GENDER, user.getGender());
                        editor.putString(MY_ROLE, user.getRole());
                        editor.putString(MY_AVATAR, user.getAvatar());
                        editor.putString(MY_PHONE, user.getPhone());
                        editor.putString(MY_ADDRESSES, user.getAddresses());
                        editor.putString(MY_CREATEAT, formatter.format(user.getCreateAt()));
                        editor.putString(MY_UPDATEAT, formatter.format(user.getUpdatedAt()));

                        editor.commit();

                        Intent intent = new Intent(RegisterActivity.this, MenuFragmentActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(RegisterActivity.this, "The username or email is already exist!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });

    }
}
