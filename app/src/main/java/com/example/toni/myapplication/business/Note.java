package com.example.toni.myapplication.business;

import java.io.Serializable;

/**
 * Created by toni on 18/01/2015.
 */
public class Note implements Serializable{
    private Integer id;
    private String name;

    public Note(String name, Integer id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
