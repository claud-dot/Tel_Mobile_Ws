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
public class Appel extends BaseModel {
    public String numero;
    public String numeroDest;
    public int dure;
    public String dates;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroDest() {
        return numeroDest;
    }

    public void setNumeroDest(String numeroDest) {
        this.numeroDest = numeroDest;
    }

    public int getDure() {
        return dure;
    }

    public void setDuree(int dure) {
        this.dure = dure;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appel(String numero, String numeroDest, int duree, String dates, int id) {
        super(id);
        this.numero = numero;
        this.numeroDest = numeroDest;
        this.dure = duree;
        this.setDates(dates);
    }

    public Appel(String numero, String numeroDest, int dure, String dates) {
        this.numero = numero;
        this.numeroDest = numeroDest;
        this.dure = dure;
        this.setDates(dates);
    }
    
    
    
    public Appel() {
    }
     
    @Override
    public ArrayList<Object> findAll(Connection co) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> appel = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM acceuille");
            result = stat.executeQuery();
            appel = new ArrayList<Object>();
            while (result.next()){
                appel.add(new Acceuille(result.getInt(1),result.getString(2)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return appel;
    }

    @Override
    public Object findById(Connection co, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection c) throws Exception {
         PreparedStatement prep =null;
        try{
            prep = c.prepareStatement("INSERT INTO Appel (numero,numeroDest,dure,dates) values ('"+this.getNumero()+"','"+this.getNumeroDest()+"',"+this.getDure()+",'"+this.getDates()+"')");
            prep.execute();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(prep!=null){prep.close();}
        }
    }

    @Override
    public ArrayList<Object> allPropos(Connection co, String cond) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
