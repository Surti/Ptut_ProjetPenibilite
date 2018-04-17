/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author qlacoste
 */
public class UtilisateurEntity {
    
    	private final String identifiant;
	private String nom;
        private String prenom;

	public UtilisateurEntity(String identifiant, String nom, String prenom) {
		this.identifiant = identifiant;
		this.nom = nom;
                this.prenom = prenom;
	}

	/**
	 * Get the value of identifiant
	 *
	 * @return the value of identifiant
	 */
	public String getIdentifiant() {
		return identifiant;
	}

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
    
}
