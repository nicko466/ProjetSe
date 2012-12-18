/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v.objectif2;

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
    //Nombre de messages à envoyer
    private static int messageAEnvoyer; 
    //Booleen indiquant que tous les messages ont été lus
    private static boolean STOP ;
    
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
        //Tampon que se partage producteur et consommateur  
        tampon = new ProdCons(nbBuffer);
        //Création des différents Producteurs
        for (int i =0 ; i!=nbProd;i++){
            Producteur p = new Producteur(this.observateur, this.getTempsMoyenProduction(),
                    this.getDeviationTempsMoyenProduction(),this.getNombreMoyenDeProduction(),this.getDeviationNombreMoyenDeProduction());
            messageAEnvoyer+= p.getNombreDeMessageAEmettre();
            this.observateur.newProducteur(p);
            p.start();
        } 
        Affichage.setNombreDeMessagesTotales(messageAEnvoyer);
        //Création des différents consommateurs
        for (int i =0 ; i!=nbCons;i++){
            Consommateur c = new Consommateur(this.observateur, this.getTempsMoyenConsommation(), this.getDeviationTempsMoyenConsommation());
            this.observateur.newConsommateur(c);
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
        if (messagesLuesConso == messageAEnvoyer ){
            STOP = true;
            System.exit(0);
        }
    }
    
    public static boolean getStop(){
        return STOP;
    }
    
    public static void main(String[] args) {
        new TestProdCons(new Observateur()).start();
    }
    
    public int getDeviationNombreMoyenDeProduction() {
        return deviationNombreMoyenDeProduction;
    }

    private void setDeviationNombreMoyenDeProduction(int deviationNombreMoyenDeProduction) {
        this.deviationNombreMoyenDeProduction = deviationNombreMoyenDeProduction;
    }

    public int getDeviationNombreMoyenNbExemplaire() {
        return deviationNombreMoyenNbExemplaire;
    }

    private void setDeviationNombreMoyenNbExemplaire(int deviationNombreMoyenNbExemplaire) {
        this.deviationNombreMoyenNbExemplaire = deviationNombreMoyenNbExemplaire;
    }

    public int getDeviationTempsMoyenConsommation() {
        return deviationTempsMoyenConsommation;
    }

    private void setDeviationTempsMoyenConsommation(int deviationTempsMoyenConsommation) {
        this.deviationTempsMoyenConsommation = deviationTempsMoyenConsommation;
    }

    public int getDeviationTempsMoyenProduction() {
        return deviationTempsMoyenProduction;
    }

    private void setDeviationTempsMoyenProduction(int deviationTempsMoyenProduction) {
        this.deviationTempsMoyenProduction = deviationTempsMoyenProduction;
    }

    public int getNbBuffer() {
        return nbBuffer;
    }

    private void setNbBuffer(int nbBuffer) {
        this.nbBuffer = nbBuffer;
    }

    public int getNbCons() {
        return nbCons;
    }

    private void setNbCons(int nbCons) {
        this.nbCons = nbCons;
    }

    public int getNbProd() {
        return nbProd;
    }

    private void setNbProd(int nbProd) {
        this.nbProd = nbProd;
    }

    public int getNombreMoyenDeProduction() {
        return nombreMoyenDeProduction;
    }

    private void setNombreMoyenDeProduction(int nombreMoyenDeProduction) {
        this.nombreMoyenDeProduction = nombreMoyenDeProduction;
    }

    public int getNombreMoyenNbExemplaire() {
        return nombreMoyenNbExemplaire;
    }

    private void setNombreMoyenNbExemplaire(int nombreMoyenNbExemplaire) {
        this.nombreMoyenNbExemplaire = nombreMoyenNbExemplaire;
    }

    public int getTempsMoyenConsommation() {
        return tempsMoyenConsommation;
    }

    private void setTempsMoyenConsommation(int tempsMoyenConsommation) {
        this.tempsMoyenConsommation = tempsMoyenConsommation;
    }

    public int getTempsMoyenProduction() {
        return tempsMoyenProduction;
    }

    private void setTempsMoyenProduction(int tempsMoyenProduction) {
        this.tempsMoyenProduction = tempsMoyenProduction;
    }
    
    public static int getMessagesLuesConso() {
        return messagesLuesConso;
    }

    public static int getMessageAEnvoyer() {
        return messageAEnvoyer;
    }
}