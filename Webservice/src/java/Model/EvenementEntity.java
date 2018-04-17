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

public class EvenementEntity {
    private final int idEv;
    private String idUser;
    private int idRasp;
    private String date;
    private String typeEvenement;

    public EvenementEntity(int idEv,String idUser, int idRasp, String date, String ev) {
        this.idUser = idUser;
        this.idRasp = idRasp;
        this.date = date;
        this.idEv = idEv;
        this.typeEvenement = ev;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdRasp() {
        return idRasp;
    }

    public void setIdRasp(int idRasp) {
        this.idRasp = idRasp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }
    
    
}
