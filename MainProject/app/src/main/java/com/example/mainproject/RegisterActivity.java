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

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    TextView txtLoginPage;
    EditText editTxtPassword;
    ImageView imgShowHidePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        setClickable();
        ShowHidePass();
    }

    //Ánh xạ các nút trong xml
    private void AnhXa(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtLoginPage = (TextView) findViewById(R.id.txtLoginPage);
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
}
