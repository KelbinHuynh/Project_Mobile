package com.example.mainproject;

import static com.example.mainproject.Constant.Const.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Model.User;
import com.example.mainproject.ResponeAPI.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText editTxtUserName;
    TextView txtForgotPassword;
    TextView txtRegisterPage;
    EditText editTxtPassword;
    ImageView imgShowHidePass;
    CheckBox ckbRememberLogin;

    SharedPreferences sharedpreferences;
    SharedPreferences sharedpreferences1;
    APIService apiService;
    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        setClickable();
        ShowHidePass();
        sharedpreferences1 = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        editTxtUserName.setText(sharedpreferences1.getString("taikhoan",null));
        editTxtPassword.setText(sharedpreferences1.getString("matkhau",null));
        ckbRememberLogin.setChecked(sharedpreferences1.getBoolean("trangthai", false));
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTxtUserName.getText().toString().trim();
                String password = editTxtPassword.getText().toString().trim();

                Log.d("test", "đã click");
                if(ckbRememberLogin.isChecked()){
                    SharedPreferences.Editor editor = sharedpreferences1.edit();
                    editor.putString("taikhoan", username);
                    editor.putString("matkhau", password);
                    editor.putBoolean("trangthai", true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = sharedpreferences1.edit();
                    editor.remove("taikhoan");
                    editor.remove("matkhau");
                    editor.remove("trangthai");
                    editor.commit();
                }



                userLogin(username, password);

            }
        });
    }

    //Ánh xạ các nút trong xml
    private void AnhXa(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        editTxtUserName = (EditText) findViewById(R.id.editTxtUserName);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtRegisterPage = (TextView) findViewById(R.id.txtRegisterPage);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        imgShowHidePass = (ImageView) findViewById(R.id.imgShowHidePass);
        ckbRememberLogin = (CheckBox) findViewById(R.id.ckbRememberLogin);
    }

    //thực hiện ClickAble cho textview để hiển thị trang ForgotPass
    private void setClickable(){
        //Lấy giá trị text trong textview đưa vào ss
        SpannableString ss = new SpannableString(txtForgotPassword.getText().toString());
        SpannableString ss1 = new SpannableString(txtRegisterPage.getText().toString());
        //Thực hiện show forgotpass page bằng clickable khi kích vào nút ForgotPass
        ClickableSpan showForgotPasswordPage = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        ClickableSpan showRegisterPage = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        };

        ss.setSpan(showForgotPasswordPage,0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(showRegisterPage,0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtForgotPassword.setText(ss);
        txtRegisterPage.setText(ss1);

        txtForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
        txtRegisterPage.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //Show password khi click vào Image hide
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

    private void userLogin(final String username, final String password){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        apiService = RetrofitClient.getInstrance();

        apiService.login(username, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    ResponseLogin responeLogin = response.body();
                    if (responeLogin.getMessage().equals("Login successfull")){
                        User user = responeLogin.getUser();

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(MY_USERNAME, user.getUsername());
                        editor.putString(MY_ID, String.valueOf(user.getUser_id()));
                        Log.d("UserID", String.valueOf(user.getUser_id()) );
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

                        if(user.getRole().equals("Quản lý")){
                            startActivity(new Intent(LoginActivity.this, SenderFragmentActivity.class));
                        }else startActivity(new Intent(LoginActivity.this, MenuFragmentActivity.class));


                    }else {
                        Toast.makeText(LoginActivity.this, "The username or password is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

}
