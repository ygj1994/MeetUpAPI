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
public class Group {

    
    //Clasa za drugi nacin pozivanja api-a ne koristi se!!!
    private String name;
    private String urlname;
    private String city;
    private String country;

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Group{" + "name=" + name + ", urlname=" + urlname + ", city=" + city + ", country=" + country + '}';
    }

}
