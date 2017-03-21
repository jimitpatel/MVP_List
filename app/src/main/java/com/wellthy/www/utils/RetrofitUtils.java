package com.wellthy.www.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wellthy.www.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    public static final String API_BASE_URL = "http://bwellthy.getsandbox.com/";

    private static OkHttpClient.Builder _httpClient = new OkHttpClient.Builder();
    private static Gson _gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    private static Retrofit.Builder _builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(_gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) throws Exception {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, @Nullable Context context) throws Exception {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            _httpClient.addInterceptor(interceptor);
        }

        OkHttpClient client = _httpClient.build();
        Retrofit retrofit = _builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static Gson getGson() {
        return _gson;
    }
}