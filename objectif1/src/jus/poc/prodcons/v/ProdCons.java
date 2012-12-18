/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

/**
 *
 * @author rb-ka
 */

public class ProdCons implements Tampon {
    
    private int taille;
    private int nombreDeMessageEnAttente;
    private Message[] buffer;
    private int indice_insert,indice_lect;
    
    /**
     * constructeur
     * @param n : taille du buffer
     */
    public ProdCons(int n){
        // taille du buffer
        taille = n;
        // initialisation du buffer
        buffer = new Message[taille];
        // le nombre de messages dans le buffer
        nombreDeMessageEnAttente=0;
        // indice d'insertion
        indice_insert=0;
        // indice de lecture
        indice_lect=0;
    }
 
    /**
     * producteur depose un message dans le buffer
     * @param 
     */    
    @Override   
    public synchronized void put(_Producteur prod, Message msg) throws Exception, InterruptedException {       
        while(nombreDeMessageEnAttente == taille){
            try{
             wait();   
            }catch(InterruptedException e){}            
        }
        buffer[indice_insert]=msg;
        indice_insert=(indice_insert+1)%taille;
        nombreDeMessageEnAttente++;
        notifyAll();        
    }
    
    
    /**
     * Fonction qui met en attente le consommateur cons en attendant qu'un message soit disponible dans le tampon
     * @param cons le consommateur 
     * @return
     * @throws Exception
     * @throws InterruptedException 
     */  
    @Override
    public synchronized Message get(_Consommateur cons) throws Exception, InterruptedException {
        while(enAttente()==0){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        Message buff = buffer[indice_lect];
        buffer[indice_lect]=null;
        indice_lect = (indice_lect+1)%taille;
        nombreDeMessageEnAttente--;
        notifyAll();
        return buff;       
    }
    
    /**
     * renvoie le nombre de messages en attente
     * @param 
     * @return
     */     
    @Override
    public synchronized int enAttente() {
        return nombreDeMessageEnAttente;
    }
    
    /**
     * renvoie taille du buffer
     * @param
     * @return int : taille
     */ 
    @Override
    public int taille() {
        return taille;
    }


}
