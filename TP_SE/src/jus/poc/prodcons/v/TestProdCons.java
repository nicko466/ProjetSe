/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.v;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jus.poc.prodcons.*;

/**
 *
 * @author rb-ka
 */
public class TestProdCons extends Simulateur {
    //Les différents attributs présents dans le xml à parser...
    int nbProd;
    int nbCons;
    int nbBuffer;
    int tempsMoyenProduction;
    int deviationTempsMoyenProduction;
    int tempsMoyenConsommation;
    int deviationTempsMoyenConsommation;
    int nombreMoyenDeProduction;
    int deviationNombreMoyenDeProduction;
    int nombreMoyenNbExemplaire;
    int deviationNombreMoyenNbExemplaire;
    static ProdCons tampon;

    public TestProdCons(Observateur observateur) {
        super(observateur);
    }

    @Override
    protected void run() throws Exception {
        // le corps de votre programme principal
        String file = "options.xml";
        init(file);
        this.observateur.init(nbProd, nbCons, nbBuffer);
        //Tampon que se partage producteur et consommateur
        tampon = new ProdCons(nbBuffer);
        //Création des différents consommateurs
        for (int i =0 ; i!=nbCons;i++){
            Consommateur c = new Consommateur(this.observateur, this.getTempsMoyenConsommation(), this.getDeviationTempsMoyenConsommation());
            this.observateur.newConsommateur(c);
            c.start();
        }
        //Création des différents Producteurs
        for (int i =0 ; i!=nbProd;i++){
            Producteur p = new Producteur(this.observateur, this.getTempsMoyenProduction(), this.getDeviationTempsMoyenProduction(),new MessageX("producteur :"+i));
            this.observateur.newProducteur(p);
            p.start();
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
}