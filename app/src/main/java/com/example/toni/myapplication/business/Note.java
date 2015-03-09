package com.example.toni.myapplication.business;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by toni on 18/01/2015.
 */
public class Note implements Serializable{
    private Integer id;
    private String name;
    private Date dateCreation;

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    private Date dateModification;

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Note(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
