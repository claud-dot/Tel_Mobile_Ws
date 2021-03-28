/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.BaseModel;
import util.Connect;

/**
 *
 * @author ASUS
 */
public class Categorie extends BaseModel
{
    public String nom;
    public int idAcc;

    public Categorie(String nom, int idAcc, int id) {
        super(id);
        this.nom = nom;
        this.idAcc = idAcc;
    }

    public Categorie(String nom, int idAcc) {
        this.nom = nom;
        this.idAcc = idAcc;
    }

    public Categorie() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    @Override
    public ArrayList<Object> findAll(Connection co) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> categorie = null;
        Gson json = null;
        try {
            json = new Gson();
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Categorie");
            result = stat.executeQuery();
            categorie = new ArrayList<Object>();
            while (result.next()){
                categorie.add(new Categorie(result.getString(3),result.getInt(2),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return categorie;
    }

    @Override
    public Object findById(Connection co,String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection c) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> allPropos(Connection co,String cond) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> categorie = null;
        Gson json = null;
        try {
            json = new Gson();
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Categorie where "+cond);
            result = stat.executeQuery();
            categorie = new ArrayList<Object>();
            while (result.next()){
                categorie.add(new Categorie(result.getString(3),result.getInt(2),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return categorie;
    }
    
}
