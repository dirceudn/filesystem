package com.sample.dirceu.limatest.services;

import android.os.Environment;

import com.sample.dirceu.limatest.interfaces.NodeApi;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.util.Utility;

import java.io.File;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class ServiceManager {

    private NodeApi nodeApi;

    public ServiceManager(){
        nodeApi = createService();

    }

    private static int cacheSize = 10 * 1024 * 1024; // 10 MB
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(),
            "HttpCache");
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Utility.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());


    public static NodeApi createService(){
        httpClient.cache(cache);
        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(NodeApi.class);

    }


    public Observable<List<Node>> getNode(String url) {
        return nodeApi.getNodes(url);
    }

}
