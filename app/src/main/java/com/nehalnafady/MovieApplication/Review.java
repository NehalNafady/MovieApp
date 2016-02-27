package com.nehalnafady.MovieApplication;

import java.net.URL;

/**
 * Created by nehal nafady on 2/24/2016.
 */
public class Review {

    String id ;
    String content;
    String author ;
    URL url ;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
