/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public abstract class BaseModel {
    public int id;

    public BaseModel(int id) {
        this.id = id;
    }

    public BaseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public abstract ArrayList<Object> findAll(Connection co) throws Exception;
    public abstract Object findById(Connection co, String id) throws Exception;
    public abstract void insert(Connection c) throws Exception;
    public abstract ArrayList<Object> allPropos(Connection co,String cond) throws Exception;
}
