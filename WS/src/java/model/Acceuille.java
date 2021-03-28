/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
public class Acceuille extends BaseModel {
    private String nom;

    public Acceuille(String nom) {
        this.nom = nom;
    }

    public Acceuille() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Acceuille(int id,String nom) {
        super(id);
        this.setNom(nom);
    }

    @Override
    public ArrayList<Object> findAll(Connection co) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> acc = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM acceuille");
            result = stat.executeQuery();
            acc = new ArrayList<Object>();
            while (result.next()){
                acc.add(new Acceuille(result.getInt(1),result.getString(2)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return acc;
    }

    @Override
    public Object findById(Connection co, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection c) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> allPropos(Connection co, String cond) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
