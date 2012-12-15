/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import jus.poc.prodcons.Message;
/**
 *
 * @author rb-ka
 */
public class MessageX implements Message {
    private String msg;
    private Producteur producteur;
    
    /**
     * Constructeur du message du producteur contenant identification du producteur
     * et le nombre de messages envoyés par le producteur 
     * @param producteur
     * @param msg 
     */
    public MessageX(Producteur producteur,String msg){
        this.msg="Emission du Producteur : "+producteur.identification()+" "
        + "Message n° "+producteur.nombreDeMessages()+msg;
        this.producteur = producteur;
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

}
