/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif4;

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
    //Indique la quantité de message présent dans le tampon en attente d'être consommer
    private int nombreDeMessageEnAttente;
    private Message[] buffer;
    private int indice_insert,indice_lect;
    private Semaphore notFull;
    private Semaphore notEmpty;
    private Semaphore mutexIn;
    private Semaphore mutexOut;
    //Utilisé pour la getion des exemplaires. On possède un sémaphore pour chaque case du buffer indiquant le nombre d'exemplaire.
    private Semaphore[] semaphoreNombreExemplaire;
    
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
        notEmpty = new Semaphore(taille);
        notFull = new Semaphore(taille);
        mutexIn = new Semaphore(1);
        mutexOut = new Semaphore(1);
        semaphoreNombreExemplaire = new Semaphore[taille];
        //Initialisation des semaphores pour à 0
        for (int i=0;i!=taille;i++){
            semaphoreNombreExemplaire[i] = new Semaphore(0);
        }
    }
 
    /**
     * producteur depose un message dans le buffer
     * @param 
     */    
    @Override   
    public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {   
        //Verifie qu'il existe une place disponible dans le tampon
        notFull.acquire();
        mutexIn.acquire();
        //Permet d'ajouter le nombre d'exemplaire en attente dans la case
        nombreDeMessageEnAttente += semaphoreNombreExemplaire[indice_insert].availablePermits();
        buffer[indice_insert]=msg;
        indice_insert=(indice_insert+1)%taille;
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
        semaphoreNombreExemplaire[indice_lect].acquire();
        mutexOut.acquire();
        Message buff = buffer[indice_lect];
        nombreDeMessageEnAttente--;
        //On test si la case du tampon ne contient plus d'exemplaire du message// si la case du tampon est vide ou pas
        if (semaphoreNombreExemplaire[indice_lect].availablePermits()==0){
            buffer[indice_lect]=null;
            indice_lect = (indice_lect+1)%taille;
            notFull.release();
            notEmpty.acquire();
            TestProdCons.incrémenteNombreDeMessagesDistinctsLues();
        }
        mutexOut.release();
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

    /**
     * Permet d'initialiser le semaphore à l'indice indice_insert du tableau de semaphore à la valeur nombreExemplaire
     * @param nombreExemplaire indique le nombre d'exemplaire d'un messageX d'un producteur
     */
    public void initNombreExemplaire(int nombreExemplaire){
        semaphoreNombreExemplaire[indice_insert].release(nombreExemplaire);
    }
    
}
