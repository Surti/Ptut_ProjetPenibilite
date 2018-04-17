/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author leman
 */
public class DAO {
        private final DataSource myDataSource;

    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO() throws SQLException {
            this.myDataSource = getDataSource();
    }
    
    private DataSource getDataSource() throws SQLException 
    {
        // Connection à la BD
        org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
        ds.setDatabaseName("ProjetPeni");
        ds.setUser("root");
        ds.setPassword("root");
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        return ds;
    }
    
    
    /**
     * *
     * @return Liste des valeur dans la table Evenement
     * @throws SQLException
     */
    public List<EvenementEntity> AllEvenement() throws Exception {
        
        List<EvenementEntity> result = new ArrayList<EvenementEntity>();
        
        String sql = "SELECT IdEv, IdUser, IdRasp, Date, TYPEEVENEMENT  From Evenement ";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            int idEv = rs.getInt("IdEv"); 
                            String idUser = rs.getString("IdUser");
                            int idRasp = rs.getInt("IdRasp");
                            String date = rs.getString("Date");
                            String typeE = rs.getString("TYPEEVENEMENT");
                             
                            EvenementEntity s = new EvenementEntity(idEv, idUser, idRasp, date, typeE);
                           
                            result.add(s);
                    }
            }
            return result;        
        
    }
    
    /**
     * *
     * @return Liste des valeur dans la table Raspberry
     * @throws SQLException
     */
    public List<RaspberryEntity> AllRaspberry() throws Exception {
        List<RaspberryEntity> result = new ArrayList<RaspberryEntity>();
        
        String sql = "SELECT IdRasp, LabelZone From Raspberry ";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            int idRasp = rs.getInt("IdRasp");
                            String label = rs.getString("LabelZone");
                                                         
                            RaspberryEntity s = new RaspberryEntity(idRasp, label);
                            
                            ajouterCapteurRasp(s,idRasp);
                           
                            result.add(s);
                    }
            }
            return result;     
        
    }

    /**
     * *
     * Remplit La Liste Des capteurs pour un IdRasp
     * @throws SQLException
     */
    private void ajouterCapteurRasp(RaspberryEntity r,int idrasp) throws SQLException {
             
        String sql = "SELECT IdCapt, LabelCapt From Capteur Where IdRasp = "+ idrasp;
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            int idCapt = rs.getInt("IdCapt");
                            String label = rs.getString("LabelCapt");
                                                         
                            CapteurEntity c = new CapteurEntity(idCapt, label, idrasp);
                            
                            r.ajouterCapteur(c);
                    }
            }
    }
    
    /**
     * *
     * @return Liste des valeur dans la table Mesure
     * @throws SQLException
     */
    public List<MesureEntity> AllMesure() throws Exception {
        List<MesureEntity> result = new ArrayList<MesureEntity>();
        
        String sql = "SELECT Date,Donnee, IdCapt From Mesure";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            String date = rs.getString("Date");
                            float donnee = rs.getFloat("Donnee");
                            int idcapt = rs.getInt("IdCapt");
                                                         
                            MesureEntity s = new MesureEntity(date, donnee, idcapt);
                           
                            result.add(s);
                    }
            }
            return result;     
        
    }
    
    /**
     * *
     * @return Liste des valeur dans la table Capteur
     * @throws SQLException
     */
    public List<CapteurEntity> AllCapteur() throws Exception {
        List<CapteurEntity> result = new ArrayList<CapteurEntity>();
        
        String sql = "SELECT IdCapt, LabelCapt, IdRasp From Capteur ";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            int idCapt = rs.getInt("IdCapt");
                            String label = rs.getString("LabelCapt");
                            int idRasp = rs.getInt("IdRasp");
                                                                                     
                            CapteurEntity c = new CapteurEntity(idCapt, label, idRasp);
                            
                            ajouterMesureCapt(c,idCapt);
                           
                            result.add(c);
                    }
            }
            return result;     
        
    }

    /**
     * *
     * Remplit la liste de mesure pour un IdCapt
     * @throws SQLException
     */
    private void ajouterMesureCapt(CapteurEntity c, int idc) throws SQLException {
        
        String sql = "SELECT Date,Donnee, IdCapt From Mesure Where IdCapt = "+ idc;
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                            String Date = rs.getString("Date");
                            float donnee = rs.getFloat("Donnee");
                            int idCapt = rs.getInt("IdCapt");
                            
                            MesureEntity m = new MesureEntity(Date, donnee, idCapt);
                            
                            c.ajouterMesure(m);
                    }
            }
    }
    
    /**
     **
     * @param identifiant
     * @param nom
     * @param prenom
     * @return number of insert row
     * @throws SQLException
     */
    public int AjouterUtilisateur(String identifiant, String nom, String prenom) throws Exception {
            int result = 0;
            String sql = "INSERT INTO Utilisateur VALUES (?,?, ?)";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, identifiant);
                    stmt.setString(2, nom);
                    stmt.setString(3, prenom);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
     /**
     **
     * @param idRasp
     * @param label
     * @return number of insert row
     * @throws SQLException
     */
    public int AjouterRaspberry(int idRasp, String label) throws Exception {
            int result = 0;
            String sql = "INSERT INTO Raspberry VALUES (?, ?)";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idRasp);
                    stmt.setString(2, label);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
         /**
     **
     * @param idCapt
     * @param idRasp
     * @param label
     * @return number of insert row
     * @throws SQLException
     */
    public int AjouterCapteur(int idCapt, String label, int idRasp) throws Exception {
            int result = 0;
            String sql = "INSERT INTO Capteur VALUES (?, ?, ?)";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idCapt);
                    stmt.setString(2, label);
                    stmt.setInt(3,idRasp);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
     /**
     **
     * @param idUser
     * @param idRasp
     * @param date
     * @param typeEv

     * @return number of insert row
     * @throws SQLException
     */
    public int AjouterEvenement(String idUser,int idRasp, String date, String typeEv) throws Exception {
            int result = 0;
            String sql = "INSERT INTO Evenement(IDUSER,IDRASP,TYPEEVENEMENT,DATE) VALUES (?, ?, ?, ?)";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, idUser);
                    stmt.setInt(2,idRasp);
                    stmt.setString(3, typeEv);
                    stmt.setString(4, date);
                    
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /*
    public int ModifierSessionEval(String idUser,int idRasp, String date,int eval) throws SQLException{
            int result = 0;
            String sql = "UPDATE SESSION SET "
                    + "evaluation = (evaluation + ?)/2"
                    + "Where IDUSER = ?"
                    + "And IDRASP = ?"
                    + "And Date = ?";
            
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, eval);
                    stmt.setString(2, idUser);
                    stmt.setInt(3, idRasp);
                    stmt.setString(4, date);
                    
                    result = stmt.executeUpdate();
            }
            
            return result;
    }
    
        public int ModifierSessionCom(String idUser,int idRasp, String date,String com) throws SQLException{
            int result = 0;
            String sql = "UPDATE SESSION SET "
                    + "commentaire = commentaire || ' ' || ? "
                    + "Where IDUSER = ?"
                    + "And IDRASP = ?"
                    + "And Date = ?";
            
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, com);
                    stmt.setString(2, idUser);
                    stmt.setInt(3, idRasp);
                    stmt.setString(4, date);
                    
                    result = stmt.executeUpdate();
            }
            
            return result;
    }
    
    public int ModifierSessionDuree(String idUser,int idRasp, String date, int duree) throws SQLException{
            int result = 0;
            String sql = "UPDATE SESSION "
                    + "Set duree = duree + ?"
                    + "Where IDUSER = ?"
                    + "And IDRASP = ?"
                    + "And Date = ?";
            
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, duree);
                    stmt.setString(2, idUser);
                    stmt.setInt(3, idRasp);
                    stmt.setString(4, date);
                    
                    result = stmt.executeUpdate();
            }
            
            return result;
    }
    */
    
    /**
     **
     * @param date
     * @param heure
     * @param donnee
     * @param idCapt
     * @return number of insert row
     * @throws SQLException
     */
    public int AjouterMesure(String date, float donnee ,int idCapt) throws Exception {
            int result = 0;
            String sql = "INSERT INTO Mesure VALUES (?, ?, ?)";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, date);
                    stmt.setFloat(2,donnee);
                    stmt.setInt(3, idCapt);
                    
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /**
     **
     *
     * @param id
     * @return number of delete row
     * @throws SQLException
     */
    public int SupprimerUtilisateur(String id) throws Exception {
            int result = 0;
            String sql = "DELETE FROM Utilisateur WHERE Identifiant = ?";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, id);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /**
     **
     *
     * @param date
     * @return number of delete row
     * @throws SQLException
     */
    public int SupprimerMesure(String date) throws Exception {
            int result = 0;
            String sql = "DELETE FROM Mesure WHERE Date = ?";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, date);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /**
     **
     *
     * @param idCapt
     * @return number of delete row
     * @throws SQLException
     */
    public int SupprimerCapteur(int idCapt) throws Exception {
            int result = 0;
            String sql = "DELETE FROM Capteur WHERE IdCapt = ?";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idCapt);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /**
     **
     *
     * @param idRasp
     * @return number of delete row
     * @throws SQLException
     */
    public int SupprimerRaspberry(int idRasp) throws Exception {
            int result = 0;
            String sql = "DELETE FROM Raspeberry WHERE IdRasp = ?";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idRasp);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    /**
     **
     *
     * @param idEv
     * @return number of delete row
     * @throws SQLException
     */
    public int SupprimerEvenement(int idEv) throws Exception {
            int result = 0;
            String sql = "DELETE FROM Evenement WHERE IdRasp = ? AND IdUser = ? AND Date = ?";
            try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idEv);
                    result = stmt.executeUpdate();
            }
            return result;
    }
    
    public String testDAO(){
        return "TESTJSON";
    }

    public boolean testConnectionForUser(String user) throws Exception {
        int result = 0;
        String sql = "Select 1 From Utilisateur Where IDENTIFIANT = ?";
        try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, user);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                         result += 1;
                    }
            }
        if(result == 1)
            return true;
        else return false;
    }

    /*
    public boolean getEvenementFromUserToDate(String user, String date, int i) throws SQLException {
         int result = 0;
        String sql = "Select 1 From Evenement Where IDUSER = ? and date = ? and idRasp = ?";
        try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, user);
                    stmt.setString(2, date);
                    stmt.setInt(3, i);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                         result += 1;
                    }
            }
        if(result == 1)
            return true;
        else return false;
    }*/

    public int getIdCapt(int idRasp, String label) throws Exception {
        int result = 0;
        String sql = "Select IDCAPT From CAPTEUR Where idRASP = ? and LABELCAPT = ?";
        try (Connection connection = myDataSource.getConnection(); 
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, idRasp);
                    stmt.setString(2, label);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                         result = rs.getInt("IdCapt");
                    }
            }
        return result;
    }
}
