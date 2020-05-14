package me.xujichang.lib.connections.retrofit;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xujichang on 2020/5/14.
 */
public class Retrofit2Helper {

    public static <T> T getApi(Class<T> pClass) {
        return createRetrofit2(pClass, generateBaseUrl(pClass));
    }

    private static String generateBaseUrl(Class<?> pClass) {

        TargetUrl locTargetUrl = pClass.getAnnotation(TargetUrl.class);
        if (null == locTargetUrl) {
            throw new CommonHttpException("在API定义的类:" + pClass.getName() + "，未定义@TargetUrl");
        }
        if (TextUtils.isEmpty(locTargetUrl.value())) {
            throw new CommonHttpException("在API定义的类:" + pClass.getName() + "，@TargetUrl的值不能为空");
        }
        return locTargetUrl.value();
    }

    private static <T> T createRetrofit2(Class<T> pClass, String baseUrl) {
        GsonBuilder vGsonBuilder = new GsonBuilder()
                .setPrettyPrinting();

        HttpLoggingInterceptor locInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder vClientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(locInterceptor);

        Retrofit.Builder vBuilder = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(vGsonBuilder.create()))
                .baseUrl(baseUrl)
                .client(vClientBuilder.build());

        Retrofit vRetrofit = vBuilder.build();
        return vRetrofit.create(pClass);
    }
}
