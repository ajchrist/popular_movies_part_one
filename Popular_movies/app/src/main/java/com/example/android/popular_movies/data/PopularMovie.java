package com.example.android.popular_movies.data;

// POJO that holds a popular movie
public class PopularMovie {

    private String title;
    private String description;
    private double vote_average;
    private String releaseDate;
    private String imageURL;

    public PopularMovie(String title, String description, double vote_average, String releaseDate, String imageURL){
        this.title = title;
        this.description = description;
        this.vote_average = vote_average;
        this.releaseDate = reformatDate(releaseDate);
        this.imageURL = imageURL;
    }

    private String reformatDate(String date){
        String[] oldDate = date.split("-");
        return String.format("%s/%s/%s", oldDate[1], oldDate[2], oldDate[0]);
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getVote_average(){
        return String.valueOf(vote_average);
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getImageURL(){
        return imageURL;
    }

}
