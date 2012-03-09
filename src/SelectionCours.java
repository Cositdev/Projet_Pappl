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
		listeGroupes.add("EI1_Gr1");
		listeGroupes.add("EI1_Gr2");
		listeGroupes.add("EI1_Gr3");
		listeGroupes.add("EI1_Gr4");
		listeGroupes.add("EI3_Info");
		
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
					setVisible(false); 
					
					ControlePresence controlePresence= new ControlePresence();
					controlePresence.setVisible(true);
					//controlePresence.run();
				}
			});

			JButton retour = new JButton("Retour");
			retour.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					setVisible(false);
				}
			});
			bas.add(retour);
			bas.add(validerChoix);

		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(milieux, BorderLayout.CENTER);
		contentPane.add(bas,BorderLayout.SOUTH);

	}

	public void remplireComboMatiere(){
		for(String s : listeMatieres){
			jcMatiere.addItem(s);
		}
	}
	public void remplireComboGroupe(){
		for(String s : listeGroupes){
			jcGroupe.addItem(s);
		}
	}
	
}
