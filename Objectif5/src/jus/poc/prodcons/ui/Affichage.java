/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.ui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jus.poc.prodcons.v.objectif5.TestProdCons;

/**
 *
 * @author nicko2
 */
public class Affichage extends JFrame implements ChangeListener{
    
  private static JPanel panProducteur;
  private static JCheckBox[] rbProducteur; 
  private static JPanel panConsommateur;
  private static JPanel panInformation;
  private static JCheckBox[] rbConsommateur;
  private ButtonGroup group = new ButtonGroup();
  private static JPanel panTampon;
  private int nbProd;
  private int nbCons;
  private static String data[]; 
  private JSlider temps;
  private static int idConsoSelectionne = 0;
  private static int idProdSelectionne = 0;
  private static int nombreDeMessageLuesConso = 0;
  private static int nombreDeMessagesTotales = 0;
  private static JLabel descompte;

    
  public Affichage(int nbProd,int nbCons,int nbBuffer){
    this.setTitle("Producteur/Consommateur");
    //this.setSize(400, 500);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    //Initialisation du champ data
    data = new String[nbBuffer];
    for(int i =0;i<nbBuffer;i++){
        data[i]="          vide          ";
    }
    //initialisation du nombre de Producteur && du nombre de Consommateur
    this.nbProd = nbProd;
    this.nbCons = nbCons;
    temps = new JSlider(0,2000);
    temps.addChangeListener(this);
    //Initialisation des panels
    panInformation = new JPanel();
    panTampon = new JPanel();
    panConsommateur= new JPanel();
    panProducteur = new JPanel();
    initJPanelConsommateur(nbCons);
    initJPanelProducteur();
    initJPanelTampon();
    initJPanelInformation();
    //Placement des différents JPanel
    this.add(panProducteur,BorderLayout.WEST);
    this.add(panConsommateur,BorderLayout.EAST);
    this.add(panTampon,BorderLayout.CENTER);
    this.add(panInformation,BorderLayout.SOUTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    this.pack();
    this.setVisible(true);
  }
  
  
  public void initJPanelProducteur(){
      JLabel labelProd = new JLabel("Producteur");
      panProducteur.setLayout(new BoxLayout(panProducteur, BoxLayout.Y_AXIS));
      panProducteur.add(labelProd);
      rbProducteur = new JCheckBox[nbProd];
      for(int i =0 ; i<nbProd;i++){
          rbProducteur[i] = new JCheckBox("Producteur : "+(i+1),false);
          panProducteur.add(rbProducteur[i]);
      }
      
  }
  
  public void initJPanelConsommateur(int nbCons){
      JLabel labelCons = new JLabel("Consommateur");
      panConsommateur.setLayout(new BoxLayout(panConsommateur, BoxLayout.Y_AXIS));
      panConsommateur.add(labelCons);
      rbConsommateur = new JCheckBox[nbCons];
      for(int i =0 ; i<nbCons;i++){
          rbConsommateur[i] = new JCheckBox("Consommateur : "+(i+1),false);
          panConsommateur.add(rbConsommateur[i]);
      }
  }
    
  public static void select(int type,int id){
      id--;
      //S'il s'agit d'un producteur
      if (type==1){
                  rbProducteur[idProdSelectionne].setSelected(false);
                  idProdSelectionne = id; 
                  rbProducteur[id].setSelected(true);
              }
      //S'il s'agit d'un consommateur
      if(type==2){
                  rbConsommateur[idConsoSelectionne].setSelected(false);
                  idConsoSelectionne = id;
                  rbConsommateur[id].setSelected(true); 
      }
  }
  
  public void initJPanelTampon(){
      JLabel labelProd = new JLabel("Tampon");
      JList list = new JList(data);
      list.setLayoutOrientation(JList.VERTICAL);
      panTampon.setLayout(new BoxLayout(panTampon, BoxLayout.Y_AXIS));
      panTampon.add(labelProd);
      panTampon.add(list);
  }
  
  public void initJPanelInformation(){
      //Panel slide
      JPanel slide = new JPanel();
      slide.setLayout(new BorderLayout());
      slide.add(new JLabel("Vitesse d'execution"),BorderLayout.NORTH);
      slide.add(temps);
      panInformation.setLayout(new FlowLayout());
      //Panel information
      JPanel Info = new JPanel();
      Info.setLayout(new FlowLayout());
      Info.add(new JLabel("Messages lus :"));
      descompte = new JLabel(nombreDeMessageLuesConso+"/"+nombreDeMessagesTotales);
      Info.add(descompte);
      panInformation.add(Info);
      panInformation.add(slide);
  }
  
  public static void addData(String s,int position){
     data[position]=s;
     panTampon.repaint();
  }
  
  public static void deleteData(int position){
      data[position]="         vide         ";
      panTampon.repaint();
  }
    @Override
    public void stateChanged(ChangeEvent ce) {
            TestProdCons.Temps = temps.getValue();
    }
    
    
    public static void setNombreDeMessageLuesConso(int nombreDeMessageLuesConso) {
        Affichage.nombreDeMessageLuesConso = nombreDeMessageLuesConso;
        descompte.repaint();
        System.out.println(" value : "+nombreDeMessageLuesConso);
    }

    public static void setNombreDeMessagesTotales(int nombreDeMessagesTotales) {
        Affichage.nombreDeMessagesTotales = nombreDeMessagesTotales;
         descompte.repaint();
    }
}
