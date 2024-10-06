package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

public class Novel {

    private int id;
    private String title;
    private String author;
    private int year;
    private String synopsis;
    private boolean isFavorite;


    public Novel() {
        //Segun me han explicado para firebase no hacen falta argumentos en el constructor
    }

    public Novel(String title, String author, int year, String synopsis) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.synopsis = synopsis;
        this.isFavorite = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getSynopsis() { return synopsis; }
    public boolean isFavorite() { return isFavorite; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}