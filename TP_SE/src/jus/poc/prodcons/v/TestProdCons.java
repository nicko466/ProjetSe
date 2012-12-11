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
public class TestProdCons extends Simulateur {

    public TestProdCons(Observateur observateur) {
       
        super(observateur);
        
    }

    protected void run() throws Exception {
        // le corps de votre programme principal
    }
    
    /**
     * Recupere les parametres de l'application via le xml
     * 
     */
    protected static void init(String file) {
        
        
        
        final class Properties extends java.util.Properties {

            private static final long serialVersionUID = 1L;

            public int get(String key) {
                return Integer.parseInt(getProperty(key));
            }

            public Properties(String file) {
                try {
                    loadFromXML(ClassLoader.getSystemResourceAsStream(file));
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }
        }
        
        Properties option = new Properties("jus/poc/rw/options/"+file);    
        
    }
    
    

    public static void main(String[] args) {

        new TestProdCons(new Observateur()).start();

    }
}