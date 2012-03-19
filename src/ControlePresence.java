import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


public class ControlePresence extends JFrame {

	private JPanel contentPane, panelDroite, panelGauche;

	private String cheminAnonyme = "./img/anonyme.jpg";
	private String cheminCheck = "./img/check.png";
	
	private Etudiant dernierEtudiant;
	
	private JTextField textFieldInput;
	private JSplitPane splitPane;
	private JLabel labelphotoEleve ;
	private JList listeEtudiants;
	

	public ControlePresence() {
		this.dernierEtudiant = null;
		majFenetre();
	}
	
	
	/**
	 * Création de la fenêtre de contrôle de présence
	 */
	public void majFenetre() {
		/*
		 * creation de la fenetre
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Affichage du titre de la fenêtre
		String labelTitre = "Contrôle de présence - " +
							SelectionCours.getMatiereChoisie() + " - " +
							SelectionCours.getGroupeChoisi();
		JPanel haut = new JPanel();
		JLabel titreFenetre = new JLabel(labelTitre);
		titreFenetre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		
		
		// Gestion de la partie droite (image + champ de texte)
		panelDroite = new JPanel();
		panelDroite.setLayout(new BorderLayout());
		
		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
		Box boiteHorizontale1= Box.createHorizontalBox();
		boiteHorizontale1.add(Box.createGlue());
		
		String cheminImage = "";
		System.out.println("mise en place de la photo");
		if(this.dernierEtudiant == null) {
			System.out.println("le dernier etudiant est NULL");
			cheminImage = this.cheminAnonyme;
		}
		else {
			System.out.println("Hop, on change et on met celle de " + dernierEtudiant.getPrenom());
			cheminImage = this.dernierEtudiant.getLienPhotoDisque();
		}
		
		ImageIcon icone = createImageIcon(cheminImage);
		
		labelphotoEleve = new JLabel(icone, JLabel.CENTER);
		labelphotoEleve.setPreferredSize(new Dimension(100, 100));
		boiteHorizontale1.add(labelphotoEleve);
		boiteHorizontale1.add(Box.createGlue());
		
		boiteVerticale.add(boiteHorizontale1);
		
		Box boiteHorizontale2= Box.createHorizontalBox();
		boiteHorizontale2.add(Box.createGlue());
		textFieldInput = new JTextField();
		textFieldInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rechercheETUpdate();
			}
		});
		
		textFieldInput.setPreferredSize(new Dimension(100, 20));
		textFieldInput.setMaximumSize(new Dimension(100, 20));
		boiteHorizontale2.add(textFieldInput);
		boiteHorizontale2.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale2);
		
		Box boiteHorizontale3= Box.createHorizontalBox();
		boiteHorizontale3.add(Box.createGlue());
		JButton boutonFinControle = new JButton("Fin du contrôle");
		boutonFinControle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				terminerControle();
			}
		});
		
		boiteHorizontale3.add(boutonFinControle);
		boiteHorizontale3.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale3);
		
		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);

		
		// Gestion de la partie gauche (liste des étudiants)
		panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());
		

		// create a cell renderer to add the appropriate icon
		listeEtudiants = majListeEtudiants();

		listeEtudiants.setCellRenderer(new MaListeEleves());


		panelGauche.add(listeEtudiants, BorderLayout.CENTER);

		splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panelGauche, panelDroite);
		splitPane.setDividerLocation(300);

		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(titreFenetre, BorderLayout.NORTH);
		// contentPane.add(panelDroite, BorderLayout.EAST);
	}
	
	
	public JList majListeEtudiants() {
		int nombreEtudiants = ListeEtudiants.etudiants.size();
		int position = 0;
		Etudiant[] vecteurEtudiants = new Etudiant[nombreEtudiants];
		
		for (Etudiant etu : ListeEtudiants.etudiants) {
			vecteurEtudiants[position] = etu;
			position ++;
		}
		
		JList liste = new JList(vecteurEtudiants);
		return liste;
	}
	
	public void majPhoto(){
		String cheminImage = "";

		System.out.println("mise en place de la photo");
		if(this.dernierEtudiant == null) {
			System.out.println("le dernier etudiant est NULL");
			cheminImage = this.cheminAnonyme;
		}
		else {
			System.out.println("Hop, on change et on met celle de " + dernierEtudiant.getPrenom() + ":" + dernierEtudiant.getLienPhotoDisque());
			cheminImage = this.dernierEtudiant.getLienPhotoDisque();
		}
		
		ImageIcon icone = createImageIcon(cheminImage);
		
		labelphotoEleve.setIcon(icone);
		
	}
	public void rechercheETUpdate() {
		String myfareTrouve = textFieldInput.getText();
		for (Etudiant etu : ListeEtudiants.etudiants) {
			if (etu.getNumeroMifare().equals(myfareTrouve)) {
				etu.setPresent(true);
				dernierEtudiant = etu;
				/*System.out.println(myfareTrouve
						+ " a ete considere comme present : c\'est "
						+ etu.getNom());*/
			}
		}
		textFieldInput.setText("");
		majListeEtudiants();
		this.majPhoto();

		this.repaint();

	}

	public static ImageIcon createImageIcon(String path) {
			ImageIcon retour = new ImageIcon() ;
	
			
            try {
				Image img = ImageIO.read(new File(path));
            	Image mieux = img.getScaledInstance(80, 100, 1);
            	retour.setImage(mieux);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return retour;
	}
	
	public static ImageIcon createImageListe(String path){
		ImageIcon retour = new ImageIcon() ;
		System.out.println("Appel de la fonction createImageListe");
		try {
			Image img = ImageIO.read(new File(path));
        	Image mieux = img.getScaledInstance(30, 30, 1);
        	retour.setImage(mieux);
    		System.out.println("on renvoie bien l'image : " + path);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return retour;
	}
	
	public void setDernierEtudiant(Etudiant etu){
		this.dernierEtudiant = etu;
	}
	
	
	/**
	 * Terminer le contrôle de présence
	 */
	public void terminerControle() {
		// On désactive la touche VERR MAJ
		Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
		
		// On masque la fenêtre de contrôle et on affiche la fenêtre de vérification
		Main.fenetreControle.setVisible(false);
		Main.fenetreListeAbsents.setVisible(true);
	}
	
	
}
