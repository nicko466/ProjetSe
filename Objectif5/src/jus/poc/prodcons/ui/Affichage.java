/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jus.poc.prodcons.ui;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonListener;
import jus.poc.prodcons.v.TestProdCons;
import org.omg.CORBA.DATA_CONVERSION;
import sun.awt.RepaintArea;

/**
 *
 * @author nicko2
 */
public class Affichage extends JFrame implements ChangeListener{
    
  private static JPanel panProducteur;
  private static JCheckBox[] rbProducteur; 
  private static JPanel panConsommateur;
  private static JCheckBox[] rbConsommateur;
  private ButtonGroup group = new ButtonGroup();
  private static JPanel panTampon;
  private int nbProd;
  private int nbCons;
  private static String data[]; 
  private JSlider temps;
  private static int idConsoSelectionne = 0;
  private static int idProdSelectionne = 0;
    
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
    panTampon = new JPanel();
    panConsommateur= new JPanel();
    panProducteur = new JPanel();
    initJPanelConsommateur(nbCons);
    initJPanelProducteur();
    initJPanelTampon(12);
    //Placement des différents JPanel
    this.add(panProducteur,BorderLayout.WEST);
    this.add(panConsommateur,BorderLayout.EAST);
    this.add(panTampon,BorderLayout.CENTER);
    this.add(temps,BorderLayout.SOUTH);
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
                 // rbProducteur[idProdSelectionne].repaint(); 
                  rbProducteur[id].setSelected(true);
                 // rbProducteur[id].repaint(); 
              }
      //S'il s'agit d'un consommateur
      if(type==2){
                  rbConsommateur[idConsoSelectionne].setSelected(false);
                  idConsoSelectionne = id;
                 // rbConsommateur[idConsoSelectionne].repaint(); 
                  rbConsommateur[id].setSelected(true);
                 // rbConsommateur[id].repaint(); 
      }
  }
  
  public void initJPanelTampon(int taille){
      JLabel labelProd = new JLabel("Tampon");
      JList list = new JList(data);
      list.setLayoutOrientation(JList.VERTICAL);
      panTampon.setLayout(new BoxLayout(panTampon, BoxLayout.Y_AXIS));
      panTampon.add(labelProd);
      panTampon.add(list);
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
            System.out.println(" VALEUR : "+temps.getValue());
    }
}
