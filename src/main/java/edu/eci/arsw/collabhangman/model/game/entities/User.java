/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game.entities;

import java.util.LinkedList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author hcadavid
 */

@Document(collection = "User")
public class User {
    
    @Id
    private int id;
    
    private String name;
    
    private String photoUrl;
    
    private LinkedList<Score> scores;

    public User(int id, String name, String photoUrl,LinkedList<Score> scores) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.scores=scores;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LinkedList<Score> getScores() {
        return scores;
    }

    public void setScores(LinkedList<Score> scores) {
        this.scores = scores;
    }
    
    
    
}
