/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleksandar.devic.meetup.api.get.data.domen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Devic Laptop PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private String name;
    private String description;
    private Venue venue;

//    private Venue venue;
    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Dogadjaj: { \n"
                + "Naziv= \t" + name + ", \n opis: = " + description + ", \n"
                + "Mesto= \t" + venue + '}';
    }

}
