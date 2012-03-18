import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


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
		
		listeMatieres = new ArrayList<String>();
		listeMatieres.add("ANUME");listeMatieres.add("EZEZE");listeMatieres.add("FGDFS");listeMatieres.add("VCXV");
		listeMatieres.add("SRETI");listeMatieres.add("PATAT1");listeMatieres.add("PATAT6");listeMatieres.add("VCXVV");
		listeMatieres.add("COPIN");listeMatieres.add("PATAT2");listeMatieres.add("PATAT7");listeMatieres.add("UYUTR");
		listeMatieres.add("GELOL");listeMatieres.add("PATAT3");listeMatieres.add("PATAT8");listeMatieres.add("PATATH");
		listeMatieres.add("MESTR");listeMatieres.add("PATAT4");listeMatieres.add("PATAT9");listeMatieres.add("PATATZ");
		listeMatieres.add("PATAT");listeMatieres.add("PATAT5");listeMatieres.add("PATAT0");listeMatieres.add("PATATQ");
		
		
		listeGroupes = new ArrayList<String>();
		listeGroupes.add("A");
		listeGroupes.add("B");
		listeGroupes.add("C");
		listeGroupes.add("EI3SIM");listeGroupes.add("EI3MATER");listeGroupes.add("EI3ENERG");
		listeGroupes.add("EI3INFO");listeGroupes.add("EI3DPSI");listeGroupes.add("EI3ISIS");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 349, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//Gestion du titre
		JPanel haut = new JPanel();
		JLabel Titre = new JLabel("Selection d\'un cours");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);

		//Gestion du milieu
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(3,0));
			
		//Gestion du milieu 1
		JPanel milieux1 = new JPanel();
		milieux1.setLayout(new FlowLayout());
		JLabel jlMatiere = new JLabel("Sélectionnez la matière");
		jcMatiere = new JComboBox<String>();
		remplireComboMatiere();
		milieux1.add(jlMatiere);
		milieux1.add(jcMatiere);
		milieux.add(milieux1);
		
		//Gestion du milieu 2
		JPanel milieux2 = new JPanel();
		milieux2.setLayout(new FlowLayout());
		JLabel jlgroupe = new JLabel("Sélectionnez le groupe");
		jcGroupe = new JComboBox<String>();
		remplireComboGroupe();
		milieux2.add(jlgroupe);
		milieux2.add(jcGroupe);
			
		milieux.add(milieux2);
			
		//Gestion du milieu 3
		JPanel milieux3 = new JPanel();
		milieux3.setLayout(new FlowLayout());
		JLabel jlDate1 = new JLabel("Date");
		JLabel jlDate2 = new JLabel("1er Janvier 2034 - 02h00");
		milieux3.add(jlDate1);
		milieux3.add(jlDate2);
		
		milieux.add(milieux3);

		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		JButton validerChoix = new JButton("Valider");
		validerChoix.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				commencer();
			}
		});

		JButton retour = new JButton("Retour");
		retour.addMouseListener(new MouseAdapter() {
			@Override
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
		
		System.out.println(SelectionCours.matiereChoisie);
		System.out.println(matiereChoisie);
		System.out.println(SelectionCours.getMatiereChoisie());
		
		// On récupère la liste des étudiants du groupe choisi dans le fichier XML
		ListeEtudiants.lireFichierXML(groupeChoisi);
		
		// On télécharge les photos des étudiants concernés
		ListeEtudiants.telechargerPhotos();
		
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
