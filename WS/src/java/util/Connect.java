/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class Connect {
    private final String url = "jdbc:postgresql://localhost/telmobile";
    private final String user = "tokyclaud";
    private final String password = "123456";
    
    public Connect(){}
    public Connection dbCo() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
}
