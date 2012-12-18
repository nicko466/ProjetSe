/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jus.poc.prodcons.*;
import jus.poc.prodcons.ui.Affichage;

/**
 *
 * @author rb-ka
 */
public class TestProdCons extends Simulateur {
    //Les différents attributs présents dans le xml à parser...
    private int nbProd;
    private int nbCons;
    private int nbBuffer;
    private int tempsMoyenProduction;
    private int deviationTempsMoyenProduction;
    private int tempsMoyenConsommation;
    private int deviationTempsMoyenConsommation;
    private int nombreMoyenDeProduction;
    private int deviationNombreMoyenDeProduction;
    private int nombreMoyenNbExemplaire;
    private int deviationNombreMoyenNbExemplaire;
    //Le tampon indiquant la ressource auquelle les consommateurs et producteurs veulent accéder
    public static ProdCons tampon;
    //Nombre de messages lues par les consommateurs
    private static int messagesLuesConso;
    //Nombre de messages distincts à envoyer
    private static int messageAEnvoyer; 
    //Booleen indiquant que tous les messages ont été lus
    private static boolean STOP ;
    //Tableau de producteur     
    private Producteur tableauProducteur[];
    //Tableau de consommateur
    private Consommateur tableauConsommateur[];
    //Ralentissement du temps
    public static int Temps = 1000;
    
    public TestProdCons(Observateur observateur) {
        super(observateur);
        messageAEnvoyer = 0;
        messagesLuesConso = 0;
        STOP = false;
    }

    @Override
    protected void run() throws Exception {
        // le corps de votre programme principal
        String file = "options.xml";
        init(file);
        this.observateur.init(nbProd, nbCons, nbBuffer);
        new jus.poc.prodcons.ui.Affichage(nbProd, nbCons,nbBuffer);
        //Création des tableaux de consommateurs et producteur
        tableauConsommateur = new Consommateur[nbCons];
        tableauProducteur = new Producteur[nbProd];
        //Tampon que se partage producteur et consommateur  
        tampon = new ProdCons(nbBuffer);
        //Création des différents Producteurs
        for (int i =0 ; i!=nbProd;i++){
            Producteur p = new Producteur(this.observateur, this.tempsMoyenProduction,
                    this.deviationTempsMoyenProduction,this.nombreMoyenDeProduction,
                    this.deviationNombreMoyenDeProduction);
            tableauProducteur[i]=p;
            messageAEnvoyer+= p.getNombreDeMessageAEmettre();
            this.observateur.newProducteur(p);
            p.start();
        }
        Affichage.setNombreDeMessagesTotales(messageAEnvoyer);
        //Création des différents consommateurs
        for (int i =0 ; i!=nbCons;i++){
            Consommateur c = new Consommateur(this.observateur, this.tempsMoyenConsommation, this.deviationTempsMoyenConsommation);
            this.observateur.newConsommateur(c);
            tableauConsommateur[i]=c;
            c.start();
        }
        
    }

 
    
    /**
     * Recupere les parametres de l'application via le xml contenu dans le fichier dossier options dont le nom est file.
     * 
     */
   public void init(String file) {
        Properties properties = new Properties();
        FileInputStream fichier = null;
        try {
            fichier = new FileInputStream("src/jus/poc/prodcons/options/"+file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestProdCons.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            properties.loadFromXML(fichier);
        } catch (IOException ex) {
            Logger.getLogger(TestProdCons.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //On initialise les attributs 
        nbProd = Integer.parseInt(properties.getProperty("nbProd"));
        nbCons = Integer.parseInt(properties.getProperty("nbCons"));
        nbBuffer = Integer.parseInt(properties.getProperty("nbBuffer"));
        tempsMoyenProduction = Integer.parseInt(properties.getProperty("tempsMoyenProduction"));
        deviationTempsMoyenProduction = Integer.parseInt(properties.getProperty("deviationTempsMoyenProduction"));
        tempsMoyenConsommation = Integer.parseInt(properties.getProperty("tempsMoyenConsommation"));
        deviationTempsMoyenConsommation = Integer.parseInt(properties.getProperty("deviationTempsMoyenConsommation"));
        nombreMoyenDeProduction = Integer.parseInt(properties.getProperty("nombreMoyenDeProduction"));
        deviationNombreMoyenDeProduction = Integer.parseInt(properties.getProperty("deviationNombreMoyenDeProduction"));
        nombreMoyenNbExemplaire = Integer.parseInt(properties.getProperty("nombreMoyenNbExemplaire"));
        deviationNombreMoyenNbExemplaire = Integer.parseInt(properties.getProperty("deviationNombreMoyenNbExemplaire"));
        try {
            fichier.close();
        } catch (IOException ex) {
            Logger.getLogger(TestProdCons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void incrémenteNombreMessageConsommées(){
        messagesLuesConso++;
        Affichage.setNombreDeMessageLuesConso(messagesLuesConso);
        if (messagesLuesConso == messageAEnvoyer )
            STOP = true;
    }
    
    public static boolean getStop(){
        return STOP;
    }
    
    public static void main(String[] args) {
        new TestProdCons(new Observateur()).start();
    }
    
    
    public static int getTemps() {
        return Temps;
    }    
        
    public static int getMessagesLuesConso() {
        return messagesLuesConso;
    }

    public static int getMessageAEnvoyer() {
        return messageAEnvoyer;
    }
    
}