/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import model.Debit;
import model.DetailOffre;
import model.Offre;

/**
 *
 * @author ASUS
 */
public class Fonction {
    public static Calendar getDate(String date,String separator){
        Calendar calendrier = Calendar.getInstance();
        String[] dateGlob = date.split(" ");
        String[] dat = dateGlob[0].trim().split(separator);
        String[] time = dateGlob[1].trim().split(":");
        
        int year = new Integer(dat[0].trim());
        int month =new Integer(dat[1].trim()) ;
        int day = new Integer(dat[2].trim());
        int hr = new Integer(time[0].trim());
        int mn = new Integer(time[1].trim());
        //int s = new Integer(time[2].trim());
        
        calendrier.set(year, month, day, hr, mn);
        return calendrier;
    }
    
   public static String calendarConvert(Calendar date){
        String month = date.get(Calendar.MONTH)+"";
        String day = date.get(Calendar.DATE)+"";
        return date.get(Calendar.YEAR)+"-"+month+"-"+day+" "+date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE)+":"+date.get(Calendar.SECOND);
    }
    
    public static Object findOject(Connection co,String cond) throws Exception{
        PreparedStatement stat = null;
        ResultSet result = null;
        Object Obj =  null;
        try {
            co =  new Connect().dbCo();
            stat= co.prepareStatement("SELECT "+ cond);
            result = stat.executeQuery();
            Obj = new ArrayList<Object>();
            result.next();
            Obj = result.getObject(1);
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }finally{
            if(result!=null){result.close();}
            if(stat!=null){stat.close();}
        }
        return Obj;
    }
    
    public static String[] getSimulation(Connection co ,double taux,String dateAppel,int dureSimulation,int credIt) throws Exception{
        String[] retour = new String[4];
        int conso = 0, credit = credIt,sumDureeOffre = 0,muniteRest = 0,dureOffre = 0,stockLastTime = 0,totalMinute=0;
        Calendar debutDat =  Calendar.getInstance(),finDate = Calendar.getInstance(),lastDate = Calendar.getInstance();
        ArrayList<Object> listDebit = new Debit().allPropos(co, " dates > '"+dateAppel+"' order by datedeb asc");
        
         
        for(int i = 0 ; i < listDebit.size() ; i++){
            Debit debit = (Debit)listDebit.get(i);
            dureOffre = (int)findOject(co, " dure from offre where id ="+debit.getIdOffre());
            sumDureeOffre += dureOffre;
            muniteRest = (int) getSimulDureOffre(taux, dureOffre); //Munite de l'offre par rapport au oerateur

            debutDat = getDate(dateAppel,"-"); //Date debut d'appel
            finDate = getDate(debit.getDates(),"-"); //Date fin du forfait
            lastDate = getDate(findOject(co," Max(dates) from debit where numero='"+debit.getNumero()+"'").toString(),"-");

            bouclDest(credit,i,conso,muniteRest,totalMinute,dureSimulation,debutDat,finDate,listDebit.size(),stockLastTime,credIt,taux);
        }
        if((sumDureeOffre-conso)<= dureSimulation){
            retour[0] = totalMinute+"";
        }else{
            retour[0] = (sumDureeOffre-conso)+"";
        }
        retour[1] = muniteRest+"";
        retour[2] = credit+"";
        retour[3] = calendarConvert(debutDat);
        return  retour;
    }
    
    public static double getSimulDureOffre(double tauxOp,int duree){
        return duree-(tauxOp*duree/100);
    }

    public static double getSimuleDureCredit(int credit,double tauxOp){
        double dureCredit = (credit/2.5)/60;
        return dureCredit-((dureCredit*tauxOp)/100);
    }
    
    public static double getCreditRestant(double dure,double tauxOp,int credit){
        return (dure*credit)/getSimuleDureCredit(credit, tauxOp);
    }

    public static void bouclDest(int credit,int i,int conso,int muniteRest,int totalMinute,int dureSimulation,Calendar debutDat,Calendar finDate,int sizeList,int stockLastTime,int credIt,double  taux){
        while(conso<dureSimulation){
            conso+=1;                                                                       //Consomation passé
            muniteRest-=1;                                                                  //Munite restant
            totalMinute=conso;                                                              //Total du munite Consommer avant la fin du duree Offre
            debutDat.add(Calendar.MINUTE,1);                                                //Date de la fin d'appel avant la fin du duree d'offre
            if(debutDat.compareTo(finDate) == 0 || muniteRest == 0){
                if(i==(sizeList-1) && muniteRest==0){
                    stockLastTime = conso;
                    conso+=Math.round(getSimuleDureCredit(credIt, taux));
                    totalMinute = conso;                                                    //Total du munite Consommer apres la fin du duree Offre c-a-d munite offre et credit 
                    debutDat.add(Calendar.MINUTE,(int)getSimuleDureCredit(credIt, taux));   //Date de la fin d'appel apres la fin du duree d'ofrre
                    if(conso > dureSimulation){                                             //Au cas ou conso depasse le dure a simuler 
                        muniteRest+=conso- dureSimulation;                                  //Minute restant au cas ou son credit n'est pas encore 
                        conso = dureSimulation-stockLastTime;
                        credit -= getCreditRestant(conso, taux, credit);
                    }else{
                        conso = dureSimulation-stockLastTime;
                        credit -= getCreditRestant(conso, taux, credit);
                    }

                    if(credit<0){
                        credit = 0;
                    }
                    conso = dureSimulation;
                }
                break;
            } 
        }
    }
    
    public static boolean exist(Connection co,String cond) throws SQLException{
        String req = "Select count(id) from "+cond;
        PreparedStatement prep = null;
        ResultSet result = null;
        boolean exist = false;
        System.out.println(req);
        try{
            prep = co.prepareStatement(req);
            result = prep.executeQuery();
            result.next();
            if(result.getInt(1)!=0){
                exist = true;
            }else{
                exist = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(result!=null){result.close();}
            if(prep!=null){ prep.close();}
        }
        return exist;
    }
    
    
    public static ArrayList<Object> simuler(Connection co,String numero,String dateAppel,int dureApel) throws Exception{
        ArrayList <Object>dataSimulaion = new ArrayList<Object>();
        Calendar dateFin =Calendar.getInstance();  
        ArrayList listOffre= new DetailOffre().allPropos(co, " numero ='"+numero+"' and dates>'"+dateAppel+"'");
        int conso = 0;
        for(int i = 0 ; i<listOffre.size();i++){
            DetailOffre detail = (DetailOffre)listOffre.get(i);
            while(conso<dureApel){
                conso++;
            }
        }
        return dataSimulaion;
    }
    
}
