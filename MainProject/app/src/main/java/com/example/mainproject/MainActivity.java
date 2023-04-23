package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLoginPage;
    Button btnRegisterPage;
    Button btnHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_shop);
        AnhXa();
        ShowPage();
    }

    private void AnhXa(){
        btnLoginPage = (Button) findViewById(R.id.btnLoginPage);
        btnRegisterPage = (Button) findViewById(R.id.btnRegisterPage);
        btnHomePage = (Button) findViewById(R.id.btnHomePage);
    }
    private void ShowPage(){
        btnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuFragmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}