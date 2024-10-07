package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

public class Novel {
    private String key;
    private String title;
    private String author;
    private int year;
    private String synopsis;
    private boolean isFavorite;

    public Novel() {
        // Constructor sin argumentos para Firebase
    }

    public Novel(String title, String author, int year, String synopsis) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.synopsis = synopsis;
        this.isFavorite = false;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getSynopsis() { return synopsis; }
    public boolean isFavorite() { return isFavorite; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}