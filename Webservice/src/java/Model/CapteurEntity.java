/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leman
 */
public class CapteurEntity {
    private final int idCapt;
    private String labelCapt;
    private List<MesureEntity> listMesure;
    private int idRasp;

    public CapteurEntity(int idCapt, String labelCapt, int idRasp) {
        this.idCapt = idCapt;
        this.labelCapt = labelCapt;
        this.idRasp = idRasp;
        listMesure = new ArrayList<>();
    }
    
    public void ajouterMesure(MesureEntity m){
        listMesure.add(m);
    }
    
    public List<MesureEntity> getMesure(){
        return listMesure;
    }
    
    public void supprimerMesure(MesureEntity m){
        listMesure.remove(m);
    }
    
    public void setLabelCapt(String labelCapt) {
        this.labelCapt = labelCapt;
    }

    public void setIdRasp(int idRasp) {
        this.idRasp = idRasp;
    }

    public int getIdCapt() {
        return idCapt;
    }

    public String getLabelCapt() {
        return labelCapt;
    }

    public int getIdRasp() {
        return idRasp;
    }
    
    
}
