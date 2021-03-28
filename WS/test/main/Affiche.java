/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import model.Debit;
import util.Connect;
import util.Fonction;
import static util.Fonction.getSimulDureOffre;

/**
 *
 * @author ASUS
 */
public class Affiche {
    public static void main(String[] args) throws Exception {
         Fonction fct = new Fonction();
         Connection co = new Connect().dbCo();
        // String [] simul = fct.getSimulation(co, 30,30, "2021-03-18 16:00", 90, 1000);
         //System.out.println(simul[1]+"");
    }
}
