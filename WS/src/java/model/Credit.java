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
public class Credit extends BaseModel {
    public String numero;
    public double credit;
    public String dates;
    public String mdp;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) throws Exception {
        Connection co = new Connect().dbCo();
        double sold =(double)Fonction.findOject(co, " sold from sold where numero");
        co.close();
        sold-=credit;
        if(sold>0){
            this.credit = credit;
            throw new Exception("Transaction Reussi");
        }else{
            throw new Exception("Sold Insuffisant");
        }
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

    public Credit(String numero, double credit, String dates, int id) throws Exception {
        super(id);
        this.numero = numero;
        this.setCredit(credit);
        this.dates = dates;
    }

    public Credit(String numero, double credit, String dates,String mdp) throws Exception {
        this.numero = numero;
        this.setCredit(credit);
        this.dates = dates;
        this.setMdp(mdp);
    }

    public Credit() {
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
            prep = c.prepareStatement("INSERT INTO Credit (numero,montant,dates) values ('"+this.getNumero()+"','"+this.getCredit()+"','"+this.getDates()+"')");
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
        ArrayList<Object> credit = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM credit whre "+cond);
            result = stat.executeQuery();
            credit = new ArrayList<Object>();
            while (result.next()){
                credit.add(new Credit(result.getString(2),result.getDouble(3),result.getString(4),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return credit;
    }
    
}
