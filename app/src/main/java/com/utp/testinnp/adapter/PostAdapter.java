package com.utp.testinnp.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.utp.testinnp.R;
import com.utp.testinnp.app.PostActivity;
import com.utp.testinnp.model.Post;


import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//vreascion del recyclerview
public class PostAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Post> posts;


    //Constructor
    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_entry, parent, false);
        return new PostViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = posts.get(position);
        final PostViewHolder postHolder = (PostViewHolder) holder;
        postHolder.setCurrentPost(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView cardPt;
        private TextView cardEx;

        private TextView tvDate;

        private TextView cardAutor;
        private ImageView cardImage;
        private Post currentPost;

        public PostViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            cardPt = itemView.findViewById(R.id.cardPt);
            cardEx = itemView.findViewById(R.id.cardEx);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardAutor = itemView.findViewById(R.id.tvAuthor);

            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setCurrentPost(Post post) {
            currentPost = post;
            String title = post.getTitle().get("rendered").toString().replaceAll("\"", "");
            String excerpt = post.getExcerpt().get("rendered").toString().replaceAll("\"", "");
            String Date = post.getDate();
            Date = dateTime(Date);

            JsonObject yoastHeadJson = post.getYoast_head_json();

            // Obtener el objeto JSON "og_image" dentro de "yoast_head_json"
            JsonObject ogImageJson = yoastHeadJson.getAsJsonArray("og_image").get(0).getAsJsonObject();

            // Obtener la URL del campo "url" dentro de "og_image"
            String imageUrl = ogImageJson.get("url").getAsString();

            String author = post.getYoast_head_json().get("author").toString().replaceAll("noticiaspiura30.com","");


            Picasso.get().load(imageUrl).into(cardImage);

            cardPt.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
            cardEx.setText(Html.fromHtml(excerpt, Html.FROM_HTML_MODE_LEGACY));
            tvDate.setText(Html.fromHtml(Date, Html.FROM_HTML_MODE_LEGACY));
            cardAutor.setText(Html.fromHtml(author,Html.FROM_HTML_MODE_LEGACY));

        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

            String title = currentPost.getTitle().get("rendered").toString().replaceAll("\"", "");
            String content = currentPost.getContent().get("rendered").toString().replaceAll("\"", "");
            String excerpt = currentPost.getExcerpt().get("rendered").toString().replaceAll("\"", "");



            content = contentFilter(content, "<ins", "</ins>");
            content = videoFilter(content, "<iframe", "/iframe>");

            Intent intent = PostActivity.createIntent(v.getContext(), currentPost.getId(),
                    currentPost.getFeatured_media(), Html.fromHtml(title,
                            Html.FROM_HTML_MODE_LEGACY).toString(), excerpt, content);
            v.getContext().startActivity(intent);
        }



        public String dateTime(String t){
            PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
            String time = null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
                Date date = simpleDateFormat.parse(t);
                time = prettyTime.format(date);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            return time;

        }

        public String getCountry(){
            Locale locale = Locale.getDefault();
            String country = locale.getCountry();
            return country.toLowerCase();
        }
        public String contentFilter(String content, String first, String last) {

            String contentOutput;
            String contentResult;


            //set index
            int firstIndex = content.indexOf(first);
            int lastIndex = content.lastIndexOf(last);

            if (firstIndex != -1 || lastIndex != -1) {

                //get substring
                contentOutput = content.substring(firstIndex, lastIndex + last.length());

                //replace
                contentResult = content.replace(contentOutput, "");

            } else {
                contentResult = content;
            }
            return contentResult;
        }

        public String videoFilter(String content, String first, String last) {

            String oldContentSubstring;
            String newContentSubstring;
            String contentResult;


            //set index
            int firstIndex = content.indexOf(first);
            int lastIndex = content.lastIndexOf(last);

            if (firstIndex != -1 || lastIndex != -1) {

                //get substring
                oldContentSubstring = content.substring(firstIndex, lastIndex + last.length());

                newContentSubstring = "<div class=\"videoWrapper\">" + oldContentSubstring + "</div>";

                contentResult = content.replace(oldContentSubstring, newContentSubstring);

            } else {
                contentResult = content;
            }
            return contentResult;

        }
    }
}
