import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdom.*;
import org.jdom.output.*;

import sun.org.mozilla.javascript.internal.JavaScriptException;


public class PageAccueil extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Lancement du programme");
					PageAccueil frame = new PageAccueil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PageAccueil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//Gestion du titre
		JPanel haut = new JPanel();
		JLabel Titre = new JLabel("Contrôle de présence");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);
		
		//Gestion des trois boutons
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(3,0));
		JButton controler = new JButton("Commencer le contrôle de présence");
		controler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				commencer();
			}
		});  
		JPanel milieuxbas = new JPanel();
		milieuxbas.setLayout(new FlowLayout()); 
		
		String boutonListe = "Télécharger la liste des étudiants";
		File fichierXml = new File("etudiants.xml");
		
		if(fichierXml.exists()) {
			boutonListe = "Mettre à jour la liste des étudiants";
		}
		
		JButton majEtu = new JButton(boutonListe);
		majEtu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				telechargerListes();
			}
		});
		
		JButton majPhoto= new JButton("Télécharger les photos des étudiants");
		if(!fichierXml.exists()) {
			majPhoto.setEnabled(false);
		}
		majPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				telechargerPhotos();
			}
		});
		
		milieux.add(majEtu);
		milieux.add(majPhoto);
		milieux.add(controler);
		milieux.add(majEtu);
		milieux.add(majPhoto);

		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(milieux, BorderLayout.CENTER);

	}
	
	public void commencer(){
		setVisible(false); 
		
		SelectionCours selection = new SelectionCours();
		selection.setVisible(true);
	}
	
	
	/*
	 * telechargerListes()
	 * Télécharge la liste des étudiants à partir de la base de données AGAP.
	 * Les données sont stockées dans le fichier etudiants.xml
	 */
	public void telechargerListes() {
		
		JOptionPane jop = new JOptionPane();
		File fichierXml = new File("etudiants.xml");
		String messageInfo = "";
		
		if(fichierXml.exists()) {
			// Il y a déjà un fichier XML, on le supprime
			if(!fichierXml.delete()) {
				System.out.println("Erreur lors de la suppression du fichier etudiants.xml");
			}
			
			messageInfo = "La liste des étudiants a bien été mise à jour !";
		}
		else {
			// Il n'y a pas encore de fichier XML
			messageInfo = "La liste des étudiants a bien été téléchargée.";
		}
		
		// Connexion au serveur
		// ...
		
		// Création du fichier XML
		Element racine = new Element("etudiants");
		org.jdom.Document document = new Document(racine);
		
		
		Element etudiant = new Element("etudiant");
		racine.addContent(etudiant);
		
		Element nom = new Element("nom");
		nom.setText("Bugnet");
		etudiant.addContent(nom);
		
		Element prenom = new Element("prenom");
		prenom.setText("Guillaume");
		etudiant.addContent(prenom);
		
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream("etudiants.xml"));
		}
		catch(java.io.IOException e) {
			System.out.println("Erreur lors de l'enregistrement du fichier etudiants.xml");
			System.out.println(e.getMessage());
		}
		
		// Message d'information
		jop.showMessageDialog(null, messageInfo, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void telechargerPhotos(){
		JOptionPane jop = new JOptionPane();
		
		jop.showMessageDialog(null,"Non implémenté pour le moment",
				"Impossible", JOptionPane.ERROR_MESSAGE);
	}
}
