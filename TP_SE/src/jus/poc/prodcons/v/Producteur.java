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


public class Producteur extends Acteur implements _Producteur {
    
    //Nombre de message distincts
    private int nbMess = 0;
    //Message du producteur à émettre.
    private MessageX msg = null;
    //Nombre de message distincts que le producteur doit émettre.
    private int nombreDeMessageAEmettre;
    //permet de calculer le nombre d'exemplaire d'un message
    private int nombreMoyenNbExemplaire;
    private int deviationNombreMoyenNbExemplaire;

    /**
     * Constructeur de Producteur 
     * @param observateur
     * @param moyenneTempsDeTraitement variable jouant sur le sleep du Thread
     * @param deviationTempsDeTraitement variable jouant sur le sleep du Thread
     * @throws ControlException 
     */
    public Producteur(Observateur observateur, int moyenneTempsDeTraitement, 
            int deviationTempsDeTraitement,int NombreMoyenDeProduction ,
            int DeviationNombreMoyenDeProduction,int nombreMoyenNbExemplaire,
            int deviationNombreMoyenNbExemplaire) throws ControlException{
        super(1, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
        this.nombreDeMessageAEmettre = Aleatoire.valeur(NombreMoyenDeProduction,DeviationNombreMoyenDeProduction);
        this.nombreMoyenNbExemplaire = nombreMoyenNbExemplaire;
        this.deviationNombreMoyenNbExemplaire = deviationNombreMoyenNbExemplaire;
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
        this.msg =new MessageX(this, "",getNombreMoyenNbExemplaire(),getDeviationNombreMoyenNbExemplaire());
        TestProdCons.setNombreMessagesTotale(TestProdCons.getNombreMessagesTotale()+msg.getNombreExemplaire());
        try {
            observateur.productionMessage(this, msg, Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement)*100);
        } catch (ControlException ex) {
            Logger.getLogger(Producteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *Permet de déposer le message du producteur dans le tampon
     */
    public void deposer(){
        try {
            TestProdCons.tampon.put(this, msg);
            TestProdCons.tampon.initNombreExemplaire(msg.getNombreExemplaire());
            observateur.depotMessage(this, msg);
        } catch (Exception ex) {
            Logger.getLogger(Producteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int getNombreDeMessageAEmettre() {
        return nombreDeMessageAEmettre;
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
    
    
    public int getDeviationNombreMoyenNbExemplaire() {
        return deviationNombreMoyenNbExemplaire;
    }

    public void setDeviationNombreMoyenNbExemplaire(int deviationNombreMoyenNbExemplaire) {
        this.deviationNombreMoyenNbExemplaire = deviationNombreMoyenNbExemplaire;
    }

    public int getNombreMoyenNbExemplaire() {
        return nombreMoyenNbExemplaire;
    }

    public void setNombreMoyenNbExemplaire(int nombreMoyenNbExemplaire) {
        this.nombreMoyenNbExemplaire = nombreMoyenNbExemplaire;
    }
    
    
}
