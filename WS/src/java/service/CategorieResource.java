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
import model.Categorie;
import model.Offre;
import util.Connect;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Categorie")
public class CategorieResource {

    @Context
    private UriInfo context;
    Connection co = new Connect().dbCo();

    /**
     * Creates a new instance of CategorieResource
     */
    public CategorieResource() {
    }

    /**
     * Retrieves representation of an instance of service.CategorieResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws Exception {
        //TODO return proper representation object
        Gson json = new Gson();
        ArrayList<Object> listOffre = new Categorie().findAll(co);
        co.close();
        return json.toJson(listOffre);
    }

    /**
     * PUT method for updating or creating an instance of CategorieResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
