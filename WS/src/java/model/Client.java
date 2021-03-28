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
import javax.persistence.Column;
import javax.validation.constraints.Size;
import util.BaseModel;
import util.Connect;
import util.Fonction;

/**
 *
 * @author ASUS
 */
public class Client extends BaseModel{
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String mdp;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMdp() {
        return mdp;
    }

    public Client() {
    }

    public void setMdp(String mdp) throws Exception {
       Connection co = new Connect().dbCo();
        String sha1=Fonction.findOject(co, "getSha1('"+mdp+"')").toString();
        boolean mdpExist = Fonction.exist(co, " Client where tel = '"+this.getTel()+"' and mdp='"+sha1+"'");
        if(mdpExist){
            this.mdp = mdp;
        }else{
            throw new Exception (" Number or password invalid ");
        }
    }

    public Client(String tel, String mdp) throws Exception {
        this.setTel(tel);
        this.setMdp(mdp);
    }
    
    
    public Client(String nom, String prenom, String tel, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.mdp = mdp;
    }

    public Client(String nom, String prenom, String tel, String email, String mdp, int id) {
        super(id);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.mdp = mdp;
    }
    
    @Override
    public ArrayList<Object> findAll(Connection co) throws Exception {
        PreparedStatement stat = null;
        ResultSet result = null;
        ArrayList<Object> clients = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Client");
            result = stat.executeQuery();
            clients = new ArrayList<Object>();
            while (result.next()){
                clients.add(new Client(result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return clients;
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
        ArrayList<Object> clients = null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT * FROM Client "+cond);
            result = stat.executeQuery();
            clients = new ArrayList<Object>();
            while (result.next()){
                clients.add(new Client(result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getInt(1)));
             }
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return clients;
    }
    
}
