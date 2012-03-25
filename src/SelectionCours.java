import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SelectionCours extends JFrame {

	private JPanel contentPane;
	private ArrayList<String> listeMatieres,listeGroupes;
	private static String matiereChoisie = "";
	private static String groupeChoisi = "";
	
	JComboBox<String> jcMatiere,jcGroupe;
	
	
	/**
	 * Create the frame.
	 */
	public SelectionCours() {
		
		// Liste des matières
		listeMatieres = new ArrayList<String>();
		listeMatieres.add("SRETI");listeMatieres.add("OBJET");listeMatieres.add("GELOL");listeMatieres.add("SRETI");
		listeMatieres.add("BDONN");listeMatieres.add("QLOGI");listeMatieres.add("TREEL");listeMatieres.add("GRAFI");
		
		
		// Liste des groupes
		listeGroupes = new ArrayList<String>();
		listeGroupes.add("EI3INFO");
		listeGroupes.add("EI3ISIS");
		listeGroupes.add("EI3INFO-SI");
		listeGroupes.add("EI3INFO-GI");
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 349, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// Titre de la fenêtre
		JPanel haut = new JPanel();
		JLabel Titre = new JLabel("Selection d\'un cours");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);
		
		// Milieu de la fenêtre
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(3,0));
		
		// Liste déroulante des matières
		JPanel milieux1 = new JPanel();
		milieux1.setLayout(new FlowLayout());
		JLabel jlMatiere = new JLabel("Sélectionnez la matière");
		jcMatiere = new JComboBox<String>();
		remplireComboMatiere();
		milieux1.add(jlMatiere);
		milieux1.add(jcMatiere);
		milieux.add(milieux1);
		
		// Liste déroulante des groupes
		JPanel milieux2 = new JPanel();
		milieux2.setLayout(new FlowLayout());
		JLabel jlgroupe = new JLabel("Sélectionnez le groupe");
		jcGroupe = new JComboBox<String>();
		remplireComboGroupe();
		milieux2.add(jlgroupe);
		milieux2.add(jcGroupe);
		
		milieux.add(milieux2);
		
		// Date
		JPanel milieux3 = new JPanel();
		milieux3.setLayout(new FlowLayout());
		JLabel jlDate1 = new JLabel("Date");
		JLabel jlDate2 = new JLabel("1er Janvier 2034 - 02h00");
		milieux3.add(jlDate1);
		milieux3.add(jlDate2);
		
		milieux.add(milieux3);
		
		// Bouton valider
		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		JButton validerChoix = new JButton("Valider");
		validerChoix.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				commencer();
			}
		});
		
		// Bouton retour
		JButton retour = new JButton("Retour");
		retour.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				annuler();
			}
		});
		
		
		bas.add(retour);
		bas.add(validerChoix);

		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(milieux, BorderLayout.CENTER);
		contentPane.add(bas,BorderLayout.SOUTH);
	}
	
	
	
	public void remplireComboMatiere() {
		for(String s : listeMatieres) {
			jcMatiere.addItem(s);
		}
	}
	
	
	
	public void remplireComboGroupe() {
		for(String s : listeGroupes) {
			jcGroupe.addItem(s);
		}
	}
	
	
	
	/**
	 * Retourner à la fenêtre d'accueil
	 */
	public void annuler() {
		// On masque la fenêtre de séléction du cours, on affiche la fenêtre d'accueil
		Main.fenetreSelectionCours.setVisible(false);
		Main.fenetreAccueil.setVisible(true);
	}
	
	
	
	/**
	 * Commencer le contrôle de présence (passage à la fenêtre de contrôle)
	 */
	public void commencer() {
		// On récupère les valeurs des listes déroulantes
		matiereChoisie = (String) jcMatiere.getSelectedItem();
		groupeChoisi = (String) jcGroupe.getSelectedItem();
		
		// On récupère la liste des étudiants du groupe choisi dans le fichier XML
		ListeEtudiants.lireFichierXML(groupeChoisi);
		
		// On télécharge les photos des étudiants concernés
		ListeEtudiants.telechargerPhotos();
		
		// On active la touche VERR MAJ (pour que le scan des cartes retourne des chiffres)
		Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
		
		// On affiche la fenêtre de contrôle, on masque la fenêtre de séléction du cours
		Main.fenetreSelectionCours.setVisible(false);
		Main.fenetreControle.majFenetre();
		Main.fenetreControle.setVisible(true);
	}
	
	
	public static String getMatiereChoisie() {
		return matiereChoisie;
	}

	public static String getGroupeChoisi() {
		return groupeChoisi;
	}
}
