/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jus.poc.prodcons.*;

/**
 *
 * @author rb-ka
 */


public class Producteur extends Acteur implements _Producteur {
    
    private int nbMess = 0;
    private MessageX msg = null;
    private int nombreDeMessageAEmettre;

    /**
     * Constructeur de Producteur 
     * @param observateur
     * @param moyenneTempsDeTraitement variable jouant sur le sleep du Thread
     * @param deviationTempsDeTraitement variable jouant sur le sleep du Thread
     * @throws ControlException 
     */
    public Producteur(Observateur observateur, int moyenneTempsDeTraitement, 
            int deviationTempsDeTraitement,int NombreMoyenDeProduction , int DeviationNombreMoyenDeProduction) throws ControlException{
        super(1, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
        this.nombreDeMessageAEmettre = Aleatoire.valeur(NombreMoyenDeProduction,DeviationNombreMoyenDeProduction);
        
    }

    @Override
    public void run() {
        while(nbMess < getNombreDeMessageAEmettre()){
            produire();
            System.out.println(msg);
            deposer();
            try{
                    Thread.sleep(Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100);              
            }
            catch(Exception e){}
        }
    }
    
    /**
     * Permet de produire le message du producteur en remplissant l'attribut msg du producteur
     */
    public void produire(){
        this.nbMess++;
        MessageX message = new MessageX(this, "");
        this.msg = message;
    }
    
    /**
     *Permet de déposer le message du producteur dans le tampon
     */
    public void deposer(){
        try {
            TestProdCons.tampon.put(this, msg);
        } catch (Exception ex) {
            Logger.getLogger(Producteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int getNombreDeMessageAEmettre() {
        return nombreDeMessageAEmettre;
    }

    public void setNombreDeMessageAEmettre(int nombreDeMessageAEmettre) {
        this.nombreDeMessageAEmettre = nombreDeMessageAEmettre;
    }
    
        @Override
    public int moyenneTempsDeTraitement() {
        return this.moyenneTempsDeTraitement;
    }

    @Override
    public int deviationTempsDeTraitement() {
        return this.deviationTempsDeTraitement;
    }

    @Override
    public int nombreDeMessages() {
        return this.nbMess;
    }
    
}
