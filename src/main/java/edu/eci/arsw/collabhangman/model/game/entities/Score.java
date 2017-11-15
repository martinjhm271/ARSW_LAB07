/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game.entities;

import java.util.Date;


/**
 *
 * @author hcadavid
 */


public class Score {
    
    
    private Date fechaObtencionPuntaje;
    
    private int puntaje;
    
    

    public Score(int puntaje, Date fechaObtencionPuntaje) {
        this.puntaje = puntaje;
        this.fechaObtencionPuntaje = fechaObtencionPuntaje;
    }

    public Score() {
    }

    public Date getFechaObtencionPuntaje() {
        return fechaObtencionPuntaje;
    }

    public void setFechaObtencionPuntaje(Date fechaObtencionPuntaje) {
        this.fechaObtencionPuntaje = fechaObtencionPuntaje;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    
    
    
}
