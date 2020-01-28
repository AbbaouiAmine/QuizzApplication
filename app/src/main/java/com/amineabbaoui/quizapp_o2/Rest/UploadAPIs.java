package com.amineabbaoui.quizapp_o2.Rest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadAPIs {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(@Part("description") String description1,@Part MultipartBody.Part photo);
}