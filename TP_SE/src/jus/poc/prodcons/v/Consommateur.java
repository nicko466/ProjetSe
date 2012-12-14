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
    //private Aleatoire traitement;
    private int moyenneTempsDeTraitement;
    private int deviationTempsDeTraitement;
    
    /**
     * Conscructeur du consommateur 
     * @param observateur
     * @param moyenneTempsDeTraitement
     * @param deviationTempsDeTraitement
     * @throws ControlException 
     */
    public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, 
            int deviationTempsDeTraitement) throws ControlException{
        super(2, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);    
        this.deviationTempsDeTraitement=deviationTempsDeTraitement;
        this.moyenneTempsDeTraitement=moyenneTempsDeTraitement;
    }
    
    @Override
    public void run() {
        boolean mess = true;
        while(mess){
            try{
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

    public int getDeviationTempsDeTraitement() {
        return deviationTempsDeTraitement;
    }

    private void setDeviationTempsDeTraitement(int deviationTempsDeTraitement) {
        this.deviationTempsDeTraitement = deviationTempsDeTraitement;
    }

    public int getMoyenneTempsDeTraitement() {
        return moyenneTempsDeTraitement;
    }

    private void setMoyenneTempsDeTraitement(int moyenneTempsDeTraitement) {
        this.moyenneTempsDeTraitement = moyenneTempsDeTraitement;
    }

    public Message getMsg() {
        return msg;
    }

    private void setMsg(Message msg) {
        this.msg = msg;
    }

    public int getNbMess() {
        return nbMess;
    }

    private void setNbMess(int nbMess) {
        this.nbMess = nbMess;
    }
    
}
