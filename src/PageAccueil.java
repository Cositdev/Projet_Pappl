import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class PageAccueil extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Création de la fenêtre
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
		
		//Gestion des deux boutons
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(2,0));
		JButton controler = new JButton("Commencer le contrôle de présence");
		controler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				commencerControle();
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
				telechargerListeEtudiants();
			}
		});
		
		milieux.add(majEtu);
		milieux.add(controler);
		milieux.add(majEtu);

		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(milieux, BorderLayout.CENTER);
	}
	
	
	public void commencerControle() {
		
		// On masque la fenêtre d'accueil, on affiche la fenêtre de séléction du cours
		Main.fenetreAccueil.setVisible(false);
		Main.fenetreSelectionCours.setVisible(true);
		
	}
	
	
	/**
	 * Télécharge la liste des étudiants depuis la base de données AGAP
	 */
	public void telechargerListeEtudiants() {
		
		String message;
		JOptionPane jop = new JOptionPane();
		
		// On télécharge la liste des étudiants depuis la base de données AGAP
		if(!ListeEtudiants.telechargerListeEtudiants()) {
			message = "Erreur lors de la suppression du fichier XML. Veuillez recommencer.";
		}
		else {
			message = "La liste des étudiants a bien été mise à jour !";
		}
		
		// Message d'information
		jop.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
		
		this.repaint();
	}
}
