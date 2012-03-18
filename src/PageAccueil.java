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
	 * Cr�ation de la fen�tre
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
		JLabel Titre = new JLabel("Contr�le de pr�sence");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);
		
		//Gestion des deux boutons
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(2,0));
		JButton controler = new JButton("Commencer le contr�le de pr�sence");
		controler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				commencerControle();
			}
		});  
		JPanel milieuxbas = new JPanel();
		milieuxbas.setLayout(new FlowLayout()); 
		
		String boutonListe = "T�l�charger la liste des �tudiants";
		File fichierXml = new File("etudiants.xml");
		
		if(fichierXml.exists()) {
			boutonListe = "Mettre � jour la liste des �tudiants";
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
		
		// On masque la fen�tre d'accueil, on affiche la fen�tre de s�l�ction du cours
		Main.fenetreAccueil.setVisible(false);
		Main.fenetreSelectionCours.setVisible(true);
		
	}
	
	
	/**
	 * T�l�charge la liste des �tudiants depuis la base de donn�es AGAP
	 */
	public void telechargerListeEtudiants() {
		
		String message;
		JOptionPane jop = new JOptionPane();
		
		// On t�l�charge la liste des �tudiants depuis la base de donn�es AGAP
		if(!ListeEtudiants.telechargerListeEtudiants()) {
			message = "Erreur lors de la suppression du fichier XML. Veuillez recommencer.";
		}
		else {
			message = "La liste des �tudiants a bien �t� mise � jour !";
		}
		
		// Message d'information
		jop.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
		
		this.repaint();
	}
}
