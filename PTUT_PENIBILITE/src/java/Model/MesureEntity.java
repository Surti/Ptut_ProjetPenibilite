/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author leman
 */
public class MesureEntity {
    private final String date;
    private float donnee;
    private int idCapt;

    public MesureEntity(String date, float donnee, int idCapt) {
        this.date = date;
        this.donnee = donnee;
        this.idCapt = idCapt;
    }

    public void setDonnee(float donnee) {
        this.donnee = donnee;
    }

    public void setIdCapt(int idCapt) {
        this.idCapt = idCapt;
    }

    public String getDate() {
        return date;
    }

    public float getDonnee() {
        return donnee;
    }

    public int getIdCapt() {
        return idCapt;
    }
    
    
}
