/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import model.Appel;
import util.Connect;
import util.Fonction;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Appel")
public class AppelResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AppelResource
     */
    public AppelResource() {
    }

    /**
     * Retrieves representation of an instance of service.AppelResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AppelResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postJson(String datas) throws Exception{
        Connection co =new Connect().dbCo();
        Gson json = new Gson();
        Map<String , Object> result = new HashMap<String, Object>();
        String [] listSumilation = new String[4];
        try{
            Appel apel = json.fromJson(datas, Appel.class);
            System.out.println(datas+" tel : "+apel.getNumeroDest());
            if(!Fonction.exist(co, " client where tel='"+apel.getNumero()+"'")){
                throw new Exception("MyNumber invalid");
            }
            
            if(!Fonction.exist(co, " client where tel='"+apel.getNumeroDest()+"'")){
                throw new Exception("NumeroDest not exist");
            }
            listSumilation  = Fonction.getSimulation(co, 30, apel.getDates(), apel.getDure(), 1000);
            result.put("status", "success");
            result.put("datas", listSumilation);
        }catch(Exception e){
            result.put("status", e.getMessage());
        }
        return json.toJson(result);
    }
}
