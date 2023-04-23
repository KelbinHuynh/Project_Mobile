package com.example.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView txtForgotPassword;
    TextView txtRegisterPage;
    EditText editTxtPassword;
    ImageView imgShowHidePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        setClickable();
        ShowHidePass();
    }

    //Ánh xạ các nút trong xml
    private void AnhXa(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtRegisterPage = (TextView) findViewById(R.id.txtRegisterPage);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        imgShowHidePass = (ImageView) findViewById(R.id.imgShowHidePass);
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


}
