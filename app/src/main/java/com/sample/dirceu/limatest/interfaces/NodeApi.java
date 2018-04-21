package com.sample.dirceu.limatest.interfaces;

import com.sample.dirceu.limatest.model.Node;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface NodeApi {

    @GET
    Observable<List<Node>> getNodes(@Url String url);

}
