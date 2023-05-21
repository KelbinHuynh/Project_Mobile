package com.example.mainproject;

import static com.example.mainproject.Constant.Const.*;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mainproject.Api.APIService;
import com.example.mainproject.Api.RetrofitClient;
import com.example.mainproject.Constant.Const;
import com.example.mainproject.Constant.RealPathUtil;
import com.example.mainproject.Model.*;
import com.example.mainproject.ResponeAPI.ResponseUpload;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateImageActivity extends AppCompatActivity {
    Button btnChoose, btnUpload;
    ImageButton icback;
    ImageView imageViewChoose;
    APIService apiService;
    User user;
    private Uri mUri;
    private ProgressDialog mProgressDialog;
    private String username;
    public static final int MY_REQUEST_CODE=100;
    SharedPreferences sharedpreferences;
    public static final String TAG = UpdateImageActivity.class.getName();

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.e(TAG, "onActivityResult");
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if(data == null){
                    return;
                }
                Uri uri = data.getData();
                mUri = uri;
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageViewChoose.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        AnhXa();

        String avatar = sharedpreferences.getString(MY_AVATAR, null);
        Glide.with(UpdateImageActivity.this).load(avatar).into(imageViewChoose);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUri != null){
                    UploadImage1();
                }

            }
        });

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(MY_FRAGMENT, 2).apply();
                startActivity(new Intent(UpdateImageActivity.this, MenuFragmentActivity.class));
            }
        });
    }
    private void AnhXa(){
        btnChoose = findViewById(R.id.btnChoose);
        imageViewChoose = findViewById(R.id.imgChoose);
        btnUpload = findViewById(R.id.btnUpload);
        icback = findViewById(R.id.icback);
    }

    public static String[] storage_permissions ={
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };


    public static String[] permissions(){
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            p = storge_permissions_33;
        }else{
            p = storage_permissions;
        }
        return p;
    }

    private void CheckPermission(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            requestPermissions(permissions(),MY_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    public void UploadImage1(){

        username = sharedpreferences.getString(MY_USERNAME, null);
        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);

        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part partbodyavatar = MultipartBody.Part.createFormData(MY_AVATAR, file.getName(), requestFile);

        apiService= RetrofitClient.getInstrance();
        apiService.upload(requestUsername, partbodyavatar).enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {

                ResponseUpload imageUpload = response.body();
                Log.d("Test", response.body().getMessage());
                if (imageUpload.getSuccess()){
                    Glide.with(UpdateImageActivity.this).load(imageUpload.getUser().getAvatar()).into(imageViewChoose);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MY_AVATAR, imageUpload.getUser().getAvatar()).apply();
                    Log.d("ThanhCong", "Thành công");
                    Toast.makeText(UpdateImageActivity.this,"Thành công", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(UpdateImageActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("TAG", t.toString());
                Toast.makeText(UpdateImageActivity.this, "Gọi API thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }
}
