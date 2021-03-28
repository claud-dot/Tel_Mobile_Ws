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
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import model.Debit;
import model.Offre;
import util.Connect;
import util.Fonction;

/**
 * REST Web Service
 *
 * @author ASUS
 */
@Path("Debit")
public class DebitResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DebitResource
     */
    public DebitResource() {
    }

    /**
     * Retrieves representation of an instance of service.DebitResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws Exception {
         Connection co = new Connect().dbCo();
         Gson json = new Gson();
         ArrayList<Object> listDebit = new Debit().findAll(co);
         Debit[] lObjet = new Debit[listDebit.size()];
         for(int i = 0 ; i<lObjet.length;i++){
             lObjet[i] = (Debit)listDebit.get(i);
         }
         co.close();
         return json.toJson(lObjet);
    }

    /**
     * PUT method for updating or creating an instance of DebitResource
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
        try{
            Debit debit = json.fromJson(datas, Debit.class);
            debit.setDates(debit.getDateDeb());
            debit.setMdp(debit.getMdp());
            debit.insert(co);
            //System.out.println("----"+debit.getMdp());
            result.put("status", "success");
        }catch(Exception e){
            result.put("status", e.getMessage());
        }finally{
            if(co!=null){co.close();}
        }
        return json.toJson(result);
    }
}
