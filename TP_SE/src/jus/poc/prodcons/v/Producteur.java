/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import jus.poc.prodcons.*;

/**
 *
 * @author rb-ka
 */


public class Producteur extends Acteur implements _Producteur {
    
    Aleatoire alea;
    private int nbMess = 0;
    private Message msg = null;
    private ProdCons buffer = null;
    //private Aleatoire traitement;
    private int moyenneTempsDeTraitement;
    private int deviationTempsDeTraitement;

    public Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Message msg) throws ControlException{
        
        super(1, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
        this.msg = msg;
        
    }
    
    @Override
    public int identification() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int moyenneTempsDeTraitement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deviationTempsDeTraitement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int nombreDeMessages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
