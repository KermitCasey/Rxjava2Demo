package com.wesley.rxjavademo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ObjectConstructor;
import com.wesley.rxjavacore.base.BaseApp;
import com.wesley.rxjavacore.utils.LogUtil;
import com.wesley.rxjavacore.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wesley.rxjavacore.base.BaseApp.getAppContext;

/**
 * Created by weiss on 2016/12/23.
 */
public class GankApi {

    public static final String BASE_URL = "http://gank.io/api/";
    public static final int DEFAULT_TIMEOUT = 7676;

    private static GankApi INSTANCE = null;
    public Retrofit retrofit;
    private static GankApiService service;

    //构造方法私有
    private GankApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); //20Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(GankApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {

    }

    //获取单例
    public static GankApi initClient() {
        synchronized (BASE_URL){
            if (null == INSTANCE){
                synchronized (BASE_URL){
                    INSTANCE = new GankApi();
                }
            }
        }
        return INSTANCE;
    }

    public static GankApiService get(){
        if (null == INSTANCE || null == service){
            initClient();
        }
        return service;
    }


    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected(BaseApp.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtil.d("Okhttp no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected(BaseApp.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}