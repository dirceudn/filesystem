package com.sample.dirceu.limatest.interfaces;

import com.sample.dirceu.limatest.model.Node;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NodeApi {

    @GET
    public Call<Node> getNodes(@Url String url);

}
