/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonArray;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author leman
 */
public class MyServletContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Demarrage");
        /* 
        Utile pour l'utilisation d'une API distante
        */
        /*
        final Runnable task = new Runnable() {
             
        @Override
        public void run() {
            System.out.println("exécuté toutes les 20 secondes"); 
            String uri = "https://api.thingspeak.com/channels/440647/feeds/last.json?api_key=POIFRJN3JXL5KD1E";
            Type mapType;
            Map<String, String[]> son = new HashMap<String, String[]>();
            String tempDate;
            String[] date = new String[2];

            try {

                URL url = new URL(uri);
                
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
                }
                
                BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));              
                
                String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
                    
                    mapType = new TypeToken<Map<String, String>>(){}.getType();  
                    son = new Gson().fromJson(output, mapType);
                    // Field1 : Temperature, Field2: Humidité, created_at : date, entry_id: idThingSpeak
                    
                    //System.out.println(son.get("field1"));
                    //System.out.println(son.get("created_at"));
                    
                    tempDate = son.get("created_at")+ ""; 
                    date = tempDate.split("T");
                    date[1] = date[1].substring(0, date[1].length()-1);
                    
		}
                
                DAO dao = new DAO();
                
                try {
                
                dao.AjouterMesure(date[0], date[1], Float.parseFloat(son.get("field1")+""), 1);
                dao.AjouterMesure(date[0], date[1], Float.parseFloat(son.get("field2")+""), 2);
                
                }catch(Exception e){
                    System.out.println("Mesure deja inseré");
                }

		conn.disconnect();
                
                
                       
            }catch(Exception e){
                System.out.println("Erreur d'accès ThingSpeak ou pas de données dans l'hebergeur");
            }
            
        }
    };
         
    final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task, 0, 20, TimeUnit.SECONDS);*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.exit(0);
    }
    
}
