/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.ui.Affichage;

/**
 *
 * @author rb-ka
 */

public class ProdCons implements Tampon {
    
    private int taille;
    private int nombreDeMessageEnAttente;
    private final Message[] buffer;
    private int indice_insert,indice_lect;
    private final Lock lock;
    private final Condition notFull ;
    private final Condition notEmpty;
    
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
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition(); 
    }
 
    /**
     * producteur depose un message dans le buffer
     * @param 
     */    
    @Override   
    public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {
        lock.lock();
        try {
            while ( enAttente()==taille() ){
                notFull.await();
            }
            Affichage.select(1, prod.identification());
             Thread.sleep(TestProdCons.getTemps());
            Affichage.addData("           P"+prod.identification()+"           ", indice_insert);
             Thread.sleep(TestProdCons.getTemps());
            buffer[indice_insert]=msg;
            indice_insert=(indice_insert+1)%taille;
            nombreDeMessageEnAttente++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
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
        lock.lock();
        try{
            while (enAttente() == 0 ){
                notEmpty.await();
            }
            Affichage.select(2, cons.identification());
             Thread.sleep(TestProdCons.getTemps());
            Affichage.deleteData(indice_lect);
            Thread.sleep(TestProdCons.getTemps());
            Message buff = buffer[indice_lect];
            buffer[indice_lect]=null;
            indice_lect = (indice_lect+1)%taille;
            nombreDeMessageEnAttente--;
            notFull.signal();
            return buff; 
        }finally{
            lock.unlock();
        }   
    }
    
    /**
     * renvoie le nombre de messages en attente
     * @param 
     * @return
     */     
    @Override
    public int enAttente() {
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



