import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class FenetreListeAbsents extends JFrame {

	private JPanel contentPane;
	private ArrayList<Etudiant> listeEtudiants;
	private ArrayList<Etudiant> listeAbsents;
	private JTextArea textAreaEtudiantsAbsents;
	/**
	 * Create the frame.
	 */
	public void creerListeAbsents(){
		listeAbsents = new ArrayList<Etudiant>();
		textAreaEtudiantsAbsents = new JTextArea();
		for(Etudiant etu:listeEtudiants){
			if(!etu.getPresent()){
				listeAbsents.add(etu);
				textAreaEtudiantsAbsents.setText(textAreaEtudiantsAbsents.getText()+etu.getPrenom() + " " + etu.getNom()+ "\n");
			}
			
		}
	}
	
	
	public FenetreListeAbsents(ArrayList<Etudiant> listeEtudiants) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.listeEtudiants=listeEtudiants;
		
		creerListeAbsents();
		
		
		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
			Box boiteHorizontale1= Box.createHorizontalBox();
			boiteHorizontale1.add(Box.createGlue());
			JLabel labelDescription = new JLabel("Liste des absents de ce cours : ");

			boiteHorizontale1.add(labelDescription);
			boiteHorizontale1.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale1);
		
			Box boiteHorizontale2= Box.createHorizontalBox();
			boiteHorizontale2.add(Box.createGlue());
			boiteHorizontale2.add(textAreaEtudiantsAbsents);
			boiteHorizontale2.add(Box.createGlue());
		
	boiteVerticale.add(boiteHorizontale2);
	boiteVerticale.add(Box.createGlue());
	
	JPanel panel_boutons = new JPanel();
	panel_boutons.setLayout(new FlowLayout());
	JButton boutonAnnuler = new JButton("Annuler");
	boutonAnnuler.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			setVisible(false); 
			System.exit(0);
		}
	});
	JButton boutonEnvoyer = new JButton("Envoyer");
	boutonEnvoyer.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			envoyer();
		}
	});
	panel_boutons.add(boutonAnnuler);
	panel_boutons.add(boutonEnvoyer);
	contentPane.add(boiteVerticale,BorderLayout.CENTER);
	contentPane.add(panel_boutons,BorderLayout.SOUTH);
		
		
	}
	
	
	public void envoyer(){
		JOptionPane jop = new JOptionPane();
		
		jop.showMessageDialog(null,"Non implémenté pour le moment",
				"Impossible", JOptionPane.ERROR_MESSAGE);
	}
}
