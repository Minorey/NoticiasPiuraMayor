package com.utp.testinnp.api.params;

public class HttpParams {
    //Base url
    public static final String BASE_URL = "https://noticiaspiura30.pe/wp-json/wp/v2/";

    //Post
    public static final String POST_ID = "posts/{id}";
    public static final String POST_ALL = "posts/?filter[category_name]=country&per_page=99";
    public static final String AUTHOR_POST = "/users/12";
     public  static  final  String CAT = "posts?[cat]=11";

    //Media
    public static final String MEDIA_FEATURED = "media/{featured_media}";

    //Comment
    public static final String API_POST_A_COMMENT = "comments?";
    public static final String COMMENT_AUTHOR_NAME = "author_name";
    public static final String COMMENT_AUTHOR_EMAIL = "author_email";
    public static final String COMMENT_CONTENT = "content";

}
