package top.gtf35.controlcenter.common.utils.network;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.gtf35.controlcenter.common.utils.LogUtils;

public class HttpUtil {

    public static void sendHttpRequest(final Context context, String address, okhttp3.Callback callback) {
        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
                for (Cookie cookie : cookies){
                    persistentCookieStore.add(cookie);
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
                return persistentCookieStore.get();
            }
        });
        OkHttpClient client = builder
                .retryOnConnectionFailure(false)
                .build();
        Request request = new Request.Builder()
                .addHeader("Connection", "close")
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static String sendHttpRequest(final Context context, String address){
        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
                for (Cookie cookie : cookies){
                    persistentCookieStore.add(cookie);
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(context);
                return persistentCookieStore.get();
            }
        });
        OkHttpClient client = builder.readTimeout(3, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(address)
                .get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.w("sendHttpRequest同步请求出错：" + e.toString());
            return "###Error :" + e.getMessage();
        }
    }

}