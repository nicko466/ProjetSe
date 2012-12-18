/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Message;
/**
 *
 * @author rb-ka
 */
public class MessageX implements Message {
    //Champ string, le message du producteur    
    private String msg;
    //indique le producteur possédant le message
    private Producteur producteur;
    //Indique le nombre d'exemplaire du producteur
    private int nombreExemplaire;


    
    /**
     * Constructeur du message X
     * @param producteur
     * @param msg
     * @param nombreMoyenNbExemplaire
     * @param deviationNombreMoyenNbExemplaire 
     */
    public MessageX(Producteur producteur,String msg,int nombreMoyenNbExemplaire,int deviationNombreMoyenNbExemplaire){
        this.msg="Emission du Producteur : "+producteur.identification()+" "
        + "Message n° "+producteur.nombreDeMessages()+msg;
        this.producteur = producteur;
        this.nombreExemplaire = (Aleatoire.valeur(nombreMoyenNbExemplaire, deviationNombreMoyenNbExemplaire));
       System.out.println("Nombre exemplaire : "+this.nombreExemplaire);
    }
    
    @Override
    public String toString(){
        return this.msg;
    }
    
    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Producteur getProducteur() {
        return producteur;
    }
    
    public int getNombreExemplaire() {
        return nombreExemplaire;
    }

    private void setNombrExemplaire(int nombreDExemplaire) {
        this.nombreExemplaire = nombreDExemplaire;
    }

}
