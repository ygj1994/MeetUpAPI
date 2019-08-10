/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleksandar.devic.meetup.api.get.data.domen;

import java.util.List;

/**
 *
 * @author Devic Laptop PC
 */
public class Events {
    
    private List<Event> results;

    public Events() {
    }

    public List<Event> getResults() {
        return results;
    }

    public void setResults(List<Event> results) {
        this.results = results;
    }
    
    
    
}
