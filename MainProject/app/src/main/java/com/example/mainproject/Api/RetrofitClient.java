package com.example.mainproject.Api;


public class RetrofitClient extends BaseClient{

    private static final String BASE_URL = "http://192.168.43.48:3000/api/";
    private static APIService apiService;
    public static APIService getInstrance(){
        if (apiService == null){
            return createService(APIService.class, BASE_URL);
        }
        return apiService;
    }
}
