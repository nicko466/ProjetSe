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
        super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);    
        this.deviationTempsDeTraitement=deviationTempsDeTraitement;
        this.moyenneTempsDeTraitement=moyenneTempsDeTraitement;
    }
    
    @Override
    public void run() {
        while(!TestProdCons.getStop()){
            
        System.out.println("thread : "+Thread.currentThread());
            retirer();
            consommer();
            try{
                Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100); 
            }
            catch(Exception e){}
        }
        System.out.println("thread : "+Thread.currentThread()+"  sort");
    }
    
    /**
     * Permet de retirer un message du tampon
     */
    public void retirer(){
        nbMess++;
        try {
            if(!TestProdCons.getStop()){
            this.setMsg(TestProdCons.tampon.get(this));
            
            observateur.retraitMessage(this, msg);
            }
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
                +getNbMess()+" contenant : \n\t\t"+getMsg()+"\n");
        TestProdCons.incrémenteNombreMessageConsommées();
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

    public MessageX getMsg() {
        return msg;
    }

    private void setMsg(Message msg) {
        this.msg = (MessageX) msg;
    }

    public int getNbMess() {
        return nbMess;
    }

    private void setNbMess(int nbMess) {
        this.nbMess = nbMess;
    }
    
}
