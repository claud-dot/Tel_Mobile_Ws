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
import util.Fonction;

/**
 *
 * @author ASUS
 */
public class Depot extends BaseModel{
    private String numero;
    private double montant;
    private String dates;
    private String mdp;

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) throws Exception {
        Connection co = new Connect().dbCo();
        String sha1=Fonction.findOject(co, "getSha1('"+mdp+"')").toString();
        boolean mdpExist = Fonction.exist(co, " Client where tel = '"+this.getNumero()+"' and mdp='"+sha1+"'");
        if(mdpExist){
            this.mdp = mdp;
        }else{
            throw new Exception (" Number or password invalid ");
        }
    }

    public Depot(String numero, double montant, String dates, int id) {
        super(id);
        this.numero = numero;
        this.montant = montant;
        this.dates = dates;
    }

    public Depot() {
    }

    public Depot(String numero, double montant, String dates,String mdp) throws Exception {
        this.numero = numero;
        this.montant = montant;
        this.dates = dates;
        this.setMdp(mdp);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
       PreparedStatement prep =null;
        try{
            prep = c.prepareStatement("INSERT INTO Depot (numero,montant,dates) values ("+this.getNumero()+",'"+this.getMontant()+"','"+this.getDates()+"')");
            prep.execute();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(prep!=null){prep.close();}
        }
    }

    @Override
    public ArrayList<Object> allPropos(Connection co, String cond) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> depots = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM depot where "+cond);
            result = stat.executeQuery();
            depots = new ArrayList<Object>();
            while (result.next()){
                depots.add(new Depot(result.getString(2),result.getDouble(3),result.getString(4),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return depots;
    }
    
}
