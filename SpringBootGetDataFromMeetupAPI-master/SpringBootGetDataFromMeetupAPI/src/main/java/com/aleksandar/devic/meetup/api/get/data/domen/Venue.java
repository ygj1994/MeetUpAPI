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
public class Venue {

    private String name;
    private String address_1;
    private String city;
    private String country;
    private String localized_country_name;

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
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

    public String getLocalized_country_name() {
        return localized_country_name;
    }

    public void setLocalized_country_name(String localized_country_name) {
        this.localized_country_name = localized_country_name;
    }

    @Override
    public String toString() {
        return " naziv:= " + name + ", adresa:= " + address_1 + ", grad= " + city + ", county:= " + country + ", zemlja:= " + localized_country_name;
    }

}
