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
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import model.Acceuille;
import model.Depot;
import model.Offre;
import util.Connect;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Acceuille")
public class AcceuilleResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AcceuilleResource
     */
    public AcceuilleResource() {
    }

    /**
     * Retrieves representation of an instance of service.AcceuilleResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() throws Exception {
         Connection co = new Connect().dbCo();
         Gson json = new Gson();
         ArrayList<Object> listAcceuille = new Acceuille().findAll(co);
         co.close();
         return json.toJson(listAcceuille);
    }

    /**
     * PUT method for updating or creating an instance of AcceuilleResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
