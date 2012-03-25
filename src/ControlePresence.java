import java.awt.BorderLayout;
import java.awt.Component;
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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


public class ControlePresence extends JFrame {

	private JPanel contentPane, panelDroite, panelGauche;

	private String cheminAnonyme = "./img/anonyme.jpg";

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
	 * Cr�ation de la fen�tre de contr�le de pr�sence
	 */
	public void majFenetre() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		// Affichage du titre de la fen�tre
		String labelTitre = "Contr�le de pr�sence - " +
							SelectionCours.getMatiereChoisie() + " - " +
							SelectionCours.getGroupeChoisi();
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
		
		// Affichage de la photo
		if(this.dernierEtudiant == null) {
			cheminImage = this.cheminAnonyme;
		}
		else {
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
				rechercheEtUpdate();
			}
		});
		
		textFieldInput.setPreferredSize(new Dimension(100, 20));
		textFieldInput.setMaximumSize(new Dimension(100, 20));
		boiteHorizontale2.add(textFieldInput);
		boiteHorizontale2.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale2);
		
		
		// Bouton de fin du contr�le de pr�sence
		Box boiteHorizontale3= Box.createHorizontalBox();
		boiteHorizontale3.add(Box.createGlue());
		JButton boutonFinControle = new JButton("Fin du contr�le");
		boutonFinControle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				terminerControle();
			}
		});
		
		boiteHorizontale3.add(boutonFinControle);
		boiteHorizontale3.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale3);
		
		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);

		
		// Bouton de retour
		Box boiteHorizontale4= Box.createHorizontalBox();
		boiteHorizontale4.add(Box.createGlue());
		JButton boutonRetour = new JButton("Retour");
		boutonRetour.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				retourSelectionCours();
			}
		});
		
		boiteHorizontale4.add(boutonRetour);
		boiteHorizontale4.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale4);
		
		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);
		
		
		// Gestion de la partie gauche (liste des �tudiants)
		panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());
		
		
		listeEtudiants = majListeEtudiants();
		listeEtudiants.setCellRenderer(new MaListeEleves());
		
		panelGauche.add(listeEtudiants, BorderLayout.CENTER);
		
		splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDroite);
		splitPane.setDividerLocation(300);

		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(titreFenetre, BorderLayout.NORTH);
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
	
	
	
	public void majPhoto() {
		String cheminImage = "";
		
		if(this.dernierEtudiant == null) {
			cheminImage = this.cheminAnonyme;
		}
		else {
			cheminImage = this.dernierEtudiant.getLienPhotoDisque();
		}
		
		ImageIcon icone = createImageIcon(cheminImage);
		
		labelphotoEleve.setIcon(icone);
	}
	
	
	
	public void rechercheEtUpdate() {
		String myfareTrouve = textFieldInput.getText();
		
		for (Etudiant etu : ListeEtudiants.etudiants) {
			if (etu.getNumeroMifare().equals(myfareTrouve)) {
				etu.setPresent(true);
				dernierEtudiant = etu;
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
				e1.printStackTrace();
			}
			return retour;
	}
	
	
	
	public void setDernierEtudiant(Etudiant etu){
		this.dernierEtudiant = etu;
	}
	
	
	
	/**
	 * Terminer le contr�le de pr�sence
	 */
	public void terminerControle() {
		// On d�sactive la touche VERR MAJ
		Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
		
		// On masque la fen�tre de contr�le et on affiche la fen�tre de v�rification
		Main.fenetreControle.setVisible(false);
		Main.fenetreListeAbsents.creerListeAbsents();
		Main.fenetreListeAbsents.setVisible(true);
	}
	
	
	
	/**
	 * Retourner � la fen�tre de s�l�ction du cours
	 */
	public void retourSelectionCours() {
		// On affiche un message d'information
		int choix = JOptionPane.showConfirmDialog((Component) null,
												  "Toutes les donn�es seront perdues ! Vous devrez scanner � nouveau tous les �tudiants.",
												  "Attention",
												  JOptionPane.OK_CANCEL_OPTION);
		
		if(choix == 0) {
			// On d�sactive la touche VERR MAJ
			Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
			
			// On masque la fen�tre de contr�le de pr�sence, on affiche la fen�tre de s�l�ction du cours
			Main.fenetreControle.setVisible(false);
			Main.fenetreSelectionCours.setVisible(true);
		}
	}
}
