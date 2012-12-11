/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import java.util.logging.Level;
import java.util.logging.Logger;
import jus.poc.prodcons.*;



/**
 *
 * @author rb-ka
 */
public class Consommateur extends Acteur implements _Consommateur {
    
    private int nbMess = 0;
    private Message msg = null;
    private ProdCons buffer = null;
    //private Aleatoire traitement;
    private int moyenneTempsDeTraitement;
    private int deviationTempsDeTraitement;
    
    
    public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons buff) throws ControlException{
        super(2, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
        this.buffer=buff; 
        
        this.deviationTempsDeTraitement=deviationTempsDeTraitement;
        this.moyenneTempsDeTraitement=moyenneTempsDeTraitement;      
    }
    
    @Override
    public void run() {
        boolean mess = true;
        while(mess){
            try{
                msg= this.buffer.get(this);
                if(msg!=null){
                    nbMess++;
                    Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100);
                }
                else{
                    mess=false;
                }
            }
            catch(Exception e){}
        }
    }    

    @Override
    public int nombreDeMessages() {
        return this.nbMess;
    }


    
}
