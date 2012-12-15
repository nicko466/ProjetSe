/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import java.util.concurrent.Semaphore;
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
    private Semaphore notFull;
    private Semaphore notEmpty;
    private Semaphore mutexIn;
    private Semaphore mutexOut;
    
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
        notFull = new Semaphore(taille);
        notEmpty = new Semaphore(0);
        mutexIn = new Semaphore(1);
        mutexOut = new Semaphore(1);
    }
 
    /**
     * producteur depose un message dans le buffer
     * @param 
     */    
    @Override   
    public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {   
        notFull.acquire();
        mutexIn.acquire();
        buffer[indice_insert]=msg;
        indice_insert=(indice_insert+1)%taille;
        nombreDeMessageEnAttente++;
        mutexIn.release();
        notEmpty.release();
    }
    
    
    /**
     * Fonction qui met en attente le consommateur cons en attendant qu'un message soit disponible dans le tampon
     * @param cons le consommateur 
     * @return
     * @throws Exception
     * @throws InterruptedException 
     */  
    @Override
    public Message get(_Consommateur cons) throws Exception, InterruptedException {
        notEmpty.acquire();
        mutexOut.acquire();
        Message buff = buffer[indice_lect];
        buffer[indice_lect]=null;
        indice_lect = (indice_lect+1)%taille;
        nombreDeMessageEnAttente--;
        mutexOut.release();
        notFull.release();
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
