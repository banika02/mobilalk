package com.example.concert_shop;

public class Ticket {
    private String artistName;
    private String venue;
    private String description;
    private String price;
    private String image;

    public Ticket() {}

    public Ticket(String artistName, String venue, String description, String price, String image) {
        this.artistName = artistName;
        this.venue = venue;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Ticket(String artistName, String venue, String description, String price) {
        this.artistName = artistName;
        this.venue = venue;
        this.description = description;
        this.price = price;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getVenue() {return venue;}

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}
}
