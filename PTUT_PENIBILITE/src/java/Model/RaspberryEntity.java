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
public class RaspberryEntity {
    private int idRasp;
    private String labelZone;
    private List<CapteurEntity> listCapt;

    public RaspberryEntity(int idRasp, String labelZone) {
        this.idRasp = idRasp;
        this.labelZone = labelZone;
        listCapt = new ArrayList<>();
    }
    
    public void ajouterCapteur(CapteurEntity c){
        listCapt.add(c);
    }
    
    public void supprimerCapteur(CapteurEntity c){
        listCapt.remove(c);
    }
    
    public List<CapteurEntity> getCapteur(){
        return listCapt;
    }
    
    public int getIdRasp() {
        return idRasp;
    }

    public void setIdRasp(int idRasp) {
        this.idRasp = idRasp;
    }

    public String getLabelZone() {
        return labelZone;
    }

    public void setLabelZone(String labelZone) {
        this.labelZone = labelZone;
    }
    
    
}
