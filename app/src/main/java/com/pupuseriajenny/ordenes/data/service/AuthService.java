package com.pupuseriajenny.ordenes.data.service;

import com.pupuseriajenny.ordenes.DTOs.LoginModel;
import com.pupuseriajenny.ordenes.DTOs.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("api/Auth/login")
    Call<LoginResponse> login(@Body LoginModel loginModel);
}