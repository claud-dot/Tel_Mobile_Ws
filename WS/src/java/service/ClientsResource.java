/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
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
import model.Client;
import model.Debit;
import model.Offre;
import util.Connect;
import util.Fonction;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Clients")
public class ClientsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClientsResource
     */
    public ClientsResource() {
    }

    /**
     * Retrieves representation of an instance of service.ClientsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() throws Exception {
        Connection co = new Connect().dbCo();
        Gson json = new Gson();
        ArrayList<Object> listClient = new Client().findAll(co);
        co.close();
        return json.toJson(listClient);
    }

    /**
     * PUT method for updating or creating an instance of ClientsResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getCompte(String datas) throws Exception{
        Connection co =new Connect().dbCo();
        Gson json = new Gson();
        Map<String , Object> result = new HashMap<String, Object>();
        try{
            Client cl = json.fromJson(datas, Client.class);
            cl.setTel(cl.getTel());
            cl.setMdp(cl.getMdp());
            double sold = (double) Fonction.findOject(co, " sold from sold where numero='"+cl.getTel()+"'");
            result.put("status", "success");
            result.put("sold", sold);
            result.put("datas", cl.allPropos(co, " tel ='"+cl.getTel()+"'"));
        }catch(Exception e){
            result.put("status", e.getMessage());
        }finally{
            if(co!=null){co.close();}
        }
        return json.toJson(result);
    }
}
