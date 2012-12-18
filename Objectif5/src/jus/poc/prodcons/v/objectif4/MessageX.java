/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif4;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;
/**
 *
 * @author rb-ka
 */
public class MessageX implements Message {
    //Champ string, le message du producteur    
    private String msg;
    //Indique le nombre d'exemplaire du producteur
    private int nombreExemplaire;


    
    /**
     * Constructeur du message X
     * @param producteur
     * @param msg
     * @param nombreMoyenNbExemplaire
     * @param deviationNombreMoyenNbExemplaire 
     */
    public MessageX(int idProducteur,String msg,int nombreMoyenNbExemplaire,int deviationNombreMoyenNbExemplaire){
        this.msg="Emission du Producteur : "+idProducteur+" "
        +msg;
        this.nombreExemplaire = (Aleatoire.valeur(nombreMoyenNbExemplaire, deviationNombreMoyenNbExemplaire));
       System.out.println("Nombre exemplaire : "+this.nombreExemplaire);
    }
    
    @Override
    public String toString(){
        return this.msg;
    }
    
    public int getNombreExemplaire() {
        return nombreExemplaire;
    }


}
