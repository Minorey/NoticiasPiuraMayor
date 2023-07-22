package com.utp.testinnp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.utp.testinnp.R;
import com.utp.testinnp.adapter.PostAdapter;
import com.utp.testinnp.api.http.ApiService;
import com.utp.testinnp.api.http.WordPressClient;
import com.utp.testinnp.model.Post;
import com.utp.testinnp.sqlite.PostDB;
import com.utp.testinnp.util.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFavoritos extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private View favPost;
    private RecyclerView favList;
    private List<Post> sqLitePostList;
    private List<Post> postList;

    @Override
     public View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);



        favPost = view.findViewById(R.id.postRecycler);
        sqLitePostList = PostDB.getInstance(getContext()).getAllDbPosts();
        Log.d("FavoritePosts", ""+sqLitePostList.size());
        setFavListContent(true, sqLitePostList);
        return view;
    }


    public void setFavListContent(final boolean withProgress, final List<Post> favPostList) {


        if (InternetConnection.checkInternetConnection(getActivity().getApplicationContext())){

            ApiService api = WordPressClient.getApiService();
            Call<List<Post>> call = api.getPosts();


            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    Log.d("RetrofitResponseFL", "Status Code " + response.code());

                    ArrayList<Post> myList = new ArrayList<Post>();

                    postList = response.body();

                    for (Post post : postList) {

                        for (Post dbPost : favPostList) {
                            if (post.getId() == dbPost.getWpPostId()) {
                                myList.add(post);
                            }
                        }
                    }


                    Log.d("FavoritePostsRespone", "" + sqLitePostList.size());

                    favList = (RecyclerView) getView().findViewById(R.id.postRecycler);
                    favList.setHasFixedSize(true);
                    favList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    favList.setAdapter(new PostAdapter(getActivity().getApplicationContext(), myList));



                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.d("RetrofitResponseFL", "Error");

                }
            });


        }else{
            Snackbar.make(favPost, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    @Override
     public void onResume() {
        super.onResume();

        sqLitePostList = PostDB.getInstance(getContext()).getAllDbPosts();
        setFavListContent(true, sqLitePostList);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.my_home:
                Intent intent = new Intent(getActivity().getApplicationContext(), FragmentHome.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}