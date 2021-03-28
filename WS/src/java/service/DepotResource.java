/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Debit;
import model.Depot;
import model.Offre;
import util.Connect;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Depot")
public class DepotResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DepotResource
     */
    public DepotResource() {
    }

    /**
     * Retrieves representation of an instance of service.DepotResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of DepotResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @GET
    @Path("{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonById(@PathParam("num") String num) throws Exception{
         Connection co = new Connect().dbCo();
         Gson json = new Gson();
         ArrayList<Object> listDepot = new Depot().allPropos(co," numero = '"+num+"'");
         co.close();
         return json.toJson(listDepot);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postJson(String datas) throws Exception{
        Connection co =new Connect().dbCo();
        Gson json = new Gson();
        try{
            Depot depot = json.fromJson(datas, Depot.class);
            depot.insert(co);
        }catch(Exception e){
            e.printStackTrace();
        }
        co.close();
    }
}
