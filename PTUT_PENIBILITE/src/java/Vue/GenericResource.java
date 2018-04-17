/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Model.DAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author leman
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    private DAO dao;
    	

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() throws SQLException {
         dao = new DAO();
    }
    
/* EXEMPLE architecture REST envoie JSON
    /**
     * Retrieves representation of an instance of Controller.GenericResource
     * @return an instance of java.lang.String
     */
/*
    @GET
    @Path("dukes")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray dukes() {
         return Json.createArrayBuilder()
                 .add(duke("smart",18))
                 .add(duke("nice",43))
                 .build();
    }
    

    public JsonObject duke(String name, int age) {
         return Json.createObjectBuilder()
                 .add("name",name)
                 .add("age",age)
                 .build();
    }
*/   
    @GET
    @Path("allRaspberry")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getRaspberry() throws Exception {
         // On serialise notre liste en JSON grâce à la librairie GSON
         String gson = new com.google.gson.Gson().toJson(dao.AllRaspberry());
         //System.out.print(gson);
         return  Json.createArrayBuilder()
                 .add(gson)
                 .build();
    }
    
    @GET
    @Path("allCapteur")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getCapteur() throws Exception {
         // On serialise notre liste en JSON grâce à la librairie GSON
         String gson = new com.google.gson.Gson().toJson(dao.AllCapteur());
         //System.out.print(gson);
         return  Json.createArrayBuilder()
                 .add(gson)
                 .build();
    }
    
    @GET
    @Path("allMesure")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getMesure() throws Exception {
         // On serialise notre liste en JSON grâce à la librairie GSON
         String gson = new com.google.gson.Gson().toJson(dao.AllMesure());
         //System.out.print(gson);
         return  Json.createArrayBuilder()
                 .add(gson)
                 .build();
    }
    
    @GET
    @Path("allEvenement")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getEvenement() throws Exception {
         // On serialise notre liste en JSON grâce à la librairie GSON
         String gson = new com.google.gson.Gson().toJson(dao.AllEvenement());
         String fin = "{\"evenement\":" + gson + "}";
        System.out.print(fin);
         return  Json.createArrayBuilder()
                 .add(gson)
                 .build();
    }
    
    @GET
    @Path("connect")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConnection(@QueryParam("user") String user)throws Exception{
        if(dao.testConnectionForUser(user)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @PUT
    @Path("ajoutEvenement")
    public Response addEvenement(@QueryParam("user") String user, @QueryParam("rasp") String rasp, @QueryParam("date") String date, @QueryParam("type") String typeEv){

        try {            
            dao.AjouterEvenement(user, Integer.parseInt(rasp), date, typeEv);
           
        } catch (Exception ex) {
                System.out.println("Erreur de l'ajout");
                System.out.println(ex.getMessage());
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("ajoutMesure")
    public Response addMesure(@QueryParam("rasp") String rasp, @QueryParam("type") String type, @QueryParam("valeur") String valeur, @QueryParam("date") String date){

        try {           
            int idCapt = dao.getIdCapt(Integer.parseInt(rasp), type);
            
            dao.AjouterMesure(date, Float.parseFloat(valeur), idCapt);
           
        } catch (Exception ex) {
                System.out.println("Erreur de l'ajout");
                System.out.println(ex.getMessage());
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok().build();
    }
}
