/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif4;

import java.util.logging.Level;
import java.util.logging.Logger;
import jus.poc.prodcons.*;



/**
 *
 * @author rb-ka
 */
public class Consommateur extends Acteur implements _Consommateur {
    
    //Nombre de message lu par le consommateur
    private int nbMess = 0;
    //Le message que lit le consommateur
    private MessageX msg = null;
    
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
        while(!TestProdCons.getStop()){
            retirer();
            consommer();
            try{
                Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100); 
            }
            catch(Exception e){}
        }
    }
    
    /**
     * Permet de retirer un message du tampon
     */
    public void retirer(){
        nbMess++;
        try {
            msg = (MessageX)TestProdCons.tampon.get(this);
//            System.out.println("valeur de msg "+msg.toString());
            observateur.retraitMessage(this, (Message)msg);
        } catch (Exception ex) {
            Logger.getLogger(Consommateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Affiche le message que le consommateur à retirer du tampon
     */
    public void consommer(){
        try {   
            observateur.consommationMessage(this, msg, Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100);
        } catch (ControlException ex) {
            Logger.getLogger(Consommateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Consommateur : "+this.identification()+" à consommer le message n° "
                +nbMess+" contenant : \n\t\t"+msg+"\n");
        TestProdCons.incrémenteNombreMessageConsommées();
    }
    
    
    @Override
    public int nombreDeMessages() {
        return this.nbMess;
    }
    
}
