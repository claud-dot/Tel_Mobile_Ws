/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import header.TelMobCrossOrigins;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.MediaType;
import javax.xml.registry.infomodel.TelephoneNumber;
import util.Connect;
import model.Offre;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Offre")
public class OffreResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public OffreResource() {
    }

    /**
     * Retrieves representation of an instance of service.OffreResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws Exception {
        //TODO return proper representation object
         Connection co = new Connect().dbCo();
         Gson json = new Gson();
         ArrayList<Object> listOffre = new Offre().findAll(co);
         co.close();
         return json.toJson(listOffre);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonById(@PathParam("id") Integer id) throws Exception{
         Connection co = new Connect().dbCo();
         Gson json = new Gson();
         Map<String , Object> result =  new HashMap<String , Object>();
         try{
            ArrayList<Object> listOffre = new Offre().allPropos(co," idCateg = "+id);
            result.put("status", "Success");
            result.put("datas", listOffre);
         }catch(Exception e){
             result.put("status", e.getMessage());
         }finally{
             if(co!=null){ co.close();}
         }
         return json.toJson(result);
    }

    /**
     * PUT method for updating or creating an instance of OffreResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
