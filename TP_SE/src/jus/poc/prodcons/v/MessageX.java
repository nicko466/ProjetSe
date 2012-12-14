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
    
    String msg;
    
    public MessageX(String msg){
        this.msg=msg;
    }
    
    @Override
    public String toString(){
        return this.msg;
    }
}
