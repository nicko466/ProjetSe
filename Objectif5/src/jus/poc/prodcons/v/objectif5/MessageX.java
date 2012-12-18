/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif5;

import jus.poc.prodcons.Message;
/**
 *
 * @author rb-ka
 */
public class MessageX implements Message {
    private String msg;
    
    /**
     * Constructeur de messageX
     * @param idProducteur
     * @param msg 
     */
    public MessageX(int idProducteur,String msg){
        this.msg="Emission du Producteur : "+idProducteur+msg;
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

}
