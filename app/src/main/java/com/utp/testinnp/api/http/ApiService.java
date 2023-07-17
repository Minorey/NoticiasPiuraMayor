package com.utp.testinnp.api.http;

import com.utp.testinnp.api.params.HttpParams;
import com.utp.testinnp.model.Media;
import com.utp.testinnp.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET(HttpParams.POST_ALL)
    Call<List<Post>> getPosts();

    @GET(HttpParams.POST_ID)
    Call<Post> getPostById(@Path("id") int postId);

    @GET(HttpParams.MEDIA_FEATURED)
    Call<Media> getPostThumbnail(@Path("featured_media") int media);

}
