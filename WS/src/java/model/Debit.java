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
import java.util.Calendar;
import java.util.GregorianCalendar;
import util.BaseModel;
import util.Connect;
import util.Fonction;

/**
 *
 * @author ASUS
 */
public class Debit extends BaseModel{
    public int idOffre;
    public String numero;
    public String dateDeb;
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

    public Debit() {
    }

    public Debit(int idOoffre, String numero, String dateDeb, String dates,String mdp) throws Exception {
        this.idOffre = idOoffre;
        this.numero = numero;
        this.dateDeb = dateDeb;
        this.setDates(dates);
        this.setMdp(mdp);
    }

    public Debit(int idOffre, String numero, String dateDeb, String dates, int id) throws Exception {
        super(id);
        this.idOffre = idOffre;
        this.numero = numero;
        this.dateDeb = dateDeb;
       this.setDates(dates);
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOoffre) {
        this.idOffre = idOffre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) throws Exception {
        Connection co =  new Connect().dbCo();
        int dure = (int)Fonction.findOject(co, " dure from offre where id ="+this.idOffre);
        Calendar calendarFin =  Fonction.getDate(this.getDateDeb(),"/");
        calendarFin.add(Calendar.MINUTE, dure);
        this.dates = Fonction.calendarConvert(calendarFin);
        co.close();
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
        ArrayList<Object> debits = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM debit");
            result = stat.executeQuery();
            debits = new ArrayList<Object>();
            while (result.next()){
                debits.add(new Debit(result.getInt(2),result.getString(3),result.getString(4),result.getString(5),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return debits;
    }

    @Override
    public Object findById(Connection co, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Connection c) throws Exception {
        PreparedStatement prep =null;
        try{
            prep = c.prepareStatement("INSERT INTO Debit (idOffre,numero,dateDeb,dates) values ("+this.getIdOffre()+",'"+this.getNumero()+"','"+this.getDateDeb()+"','"+this.getDates()+"')");
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
        ArrayList<Object> debits = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM debit where "+cond);
            System.out.println("SELECT * FROM debit where "+cond);
            result = stat.executeQuery();
            debits = new ArrayList<Object>();
            while (result.next()){
                debits.add(new Debit(result.getInt(2),result.getString(3),result.getString(4),result.getString(5),result.getInt(1)));
             }
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return debits;
    }
}
