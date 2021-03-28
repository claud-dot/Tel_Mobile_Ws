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
public class DetailOffre extends BaseModel {
    private double montant;
    private double internet;
    private double appel;
    private double sms;
    private String numero;
    private String dates;

    public DetailOffre() {
    }

    public DetailOffre(double montant, double internet, double appel, double sms, String numero, String dates, int id) {
        super(id);
        this.montant = montant;
        this.internet = internet;
        this.appel = appel;
        this.sms = sms;
        this.numero = numero;
        this.dates = dates;
    }

    public DetailOffre(double montant, double internet, double appel, double sms, String numero, String dates) {
        this.montant = montant;
        this.internet = internet;
        this.appel = appel;
        this.sms = sms;
        this.numero = numero;
        this.dates = dates;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getInternet() {
        return internet;
    }

    public void setInternet(double internet) {
        this.internet = internet;
    }

    public double getAppel() {
        return appel;
    }

    public void setAppel(double appel) {
        this.appel = appel;
    }

    public double getSms() {
        return sms;
    }

    public void setSms(double sms) {
        this.sms = sms;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
    
    

    @Override
    public ArrayList<Object> findAll(Connection co) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> detOffre = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM vappeldetails where "+cond);
            result = stat.executeQuery();
            detOffre = new ArrayList<Object>();
            while (result.next()){
                detOffre.add(new DetailOffre(this.getMontant(), this.getInternet(), this.getAppel(), this.getSms(), this.getNumero(), this.getDates()));
            }
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return detOffre;
    }
}
