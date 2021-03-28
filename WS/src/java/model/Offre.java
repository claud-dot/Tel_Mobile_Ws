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
public class Offre extends BaseModel{
    public int idCateg;
    public String nom;
    public int dureOffre;
    public int dure;
    public double prix;

    public Offre(int idCateg, String nom, int dureOffre, int dure, double prix) {
        this.idCateg = idCateg;
        this.nom = nom;
        this.dureOffre = dureOffre;
        this.dure = dure;
        this.prix = prix;
    }

    public Offre(int idCateg, String nom, int dureOffre, int dure, double prix, int id) {
        super(id);
        this.idCateg = idCateg;
        this.nom = nom;
        this.dureOffre = dureOffre;
        this.dure = dure;
        this.prix = prix;
    }

    public int getIdCateg() {
        return idCateg;
    }

    public void setIdCateg(int idCateg) {
        this.idCateg = idCateg;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDureOffre() {
        return dureOffre;
    }

    public void setDureOffre(int dureOffre) {
        this.dureOffre = dureOffre;
    }

    public int getDure() {
        return dure;
    }

    public void setDure(int dure) {
        this.dure = dure;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
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
        ArrayList<Object> offres = null;
        Gson json = null;
        try {
            json = new Gson();
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Offre");
            result = stat.executeQuery();
            offres = new ArrayList<Object>();
            while (result.next()){
                offres.add(new Offre(result.getInt(2),result.getString(3),result.getInt(4),result.getInt(5),result.getDouble(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return offres;
        
    }

    public Offre() {
    }

    @Override
    public Object findById(Connection co,String id) throws Exception {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> allPropos(Connection co,String cond) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> offres = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Offre where "+cond);
            result = stat.executeQuery();
            offres = new ArrayList<Object>();
            while (result.next()){
                offres.add(new Offre(result.getInt(2),result.getString(3),result.getInt(4),result.getInt(5),result.getDouble(6),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return offres;
    }
    
    
}
